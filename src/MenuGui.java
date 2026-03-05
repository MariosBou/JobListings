import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MenuGui  {
    DatabaseActions db = new DatabaseActions();

    public MenuGui() {
        JFrame frame = new JFrame("Job Listings Menu");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        // Main Panel setup
        JPanel mainPanel = createMainPanel(frame);

        // Frame settings
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createMainPanel(JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout(20, 10));
        panel.setBackground(Color.LIGHT_GRAY);

        TablePanelWrapper tablePanelWrapper = createTablePanel(frame);
        JPanel buttonPanel = createButtonPanel(frame,tablePanelWrapper.getTable(),tablePanelWrapper.getModel());
        JPanel filterPanel = createFilterPanel(frame);
        JPanel statsPanel = createStatsPanel(frame);

        // Assemble components
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.SOUTH);
        panel.add(filterPanel, BorderLayout.WEST);
        panel.add(tablePanelWrapper.getPanel(), BorderLayout.CENTER);

        return panel;
    }

    private TablePanelWrapper createTablePanel(JFrame frame) {
        JPanel tablePanel = new JPanel(new BorderLayout());
        // Table data setup
        TableData tableData = new TableData();
        String[] columns = {"Job Id", "Experience", "Qualifications", "Salary Range", "Location",
                "Country", "Latitude", "Longitude", "Work Type", "Company Size",
                "Job Posting Date", "Preference", "Contact Person", "Contact",
                "Job Title", "Role", "Job Portal", "Job Description",
                "Benefits", "Skills", "Responsibilities", "Company", "Company Profile"
        };
        String[][] data = new String[tableData.getRowCount()][tableData.getColumnCount()];
        for (int i = 0; i < tableData.getRowCount(); i++) {
            data[i] = tableData.getData().get(i);
        }
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        JTable table = new JTable(tableModel);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(145);
        }

        // Setup table appearance
        setupTableAppearance(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
                if (!scrollBar.getValueIsAdjusting() && scrollBar.getValue() + scrollBar.getVisibleAmount() == scrollBar.getMaximum()) {
                    tableData.loadDataFromDatabase(tableModel);
                }
            }
        });
        tablePanel.add(scrollPane, BorderLayout.CENTER);


        // Handle double-click on table row
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();

                    // Get selected row
                    int row = table.rowAtPoint(e.getPoint());
                    int col = table.columnAtPoint(e.getPoint());

                    if (row >= 0 && col >= 0) {
                        // Get the value of the double-clicked cell
                        Object cellValue = table.getValueAt(row, 0);
                        IntegerOnly integerOnly = new IntegerOnly();
                        integerOnly.setText(cellValue.toString());
                        viewJob(integerOnly,frame);
                    }
                }
            }
        });
        TablePanelWrapper tablePanelWrapper = new TablePanelWrapper(tablePanel,table,tableModel);
        return tablePanelWrapper;
    }

    private void setupTableAppearance(JTable table) {
        table.setEnabled(false);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setBackground(new Color(245, 245, 245));  // Light background color for rows
        table.setGridColor(Color.GRAY);  // Grid lines
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));  // Bold header font
        table.getTableHeader().setBackground(new Color(180, 220, 240));  // Light blue header background
    }

    private JPanel createButtonPanel(JFrame frame,JTable table,DefaultTableModel tableModel) {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 13, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 0));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        IntegerOnly jobIdField = new IntegerOnly();

        buttonPanel.add(createButton("Exit",e -> {
            frame.setEnabled(false);
            Message message = new Message(frame); }));
        buttonPanel.add(new JLabel("             Give Job ID: "));
        buttonPanel.add(jobIdField);
        buttonPanel.add(createButton("View", e -> viewJob(jobIdField,frame)));
        buttonPanel.add(createButton("Update", e -> updateJob(jobIdField,frame)));
        buttonPanel.add(createButton("Delete", e -> deleteJob(jobIdField,frame)));
        buttonPanel.add(new JLabel(""));
        buttonPanel.add(new JLabel(""));
        buttonPanel.add(createButton("Add", e -> addJob(frame)));
        buttonPanel.add(createButton("Export", e -> { exportToCSV(table);}));
        buttonPanel.add(sortButton(tableModel,table));

        return buttonPanel;
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        if(text!="Exit") button.setMnemonic(text.charAt(0));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        if(text == "Add" || text == "Export") button.setBackground(new Color(204, 153, 0));
        else if (text == "Exit") button.setBackground(new Color(180, 0, 0));
        else if(text == "Filter") button.setBackground(new Color(0, 123, 255));
        else button.setBackground(new Color(0, 180, 0));
        button.setForeground(Color.WHITE);
        button.addActionListener(listener);
        return button;
    }

    private static String getCurrentFilePath() {
        try {
            return new java.io.File(".").getCanonicalPath();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void exportToCSV(JTable table) {
        // Define the column headers
        String[] columns = {
                "Job Id", "Experience", "Qualifications", "Salary Range", "Location",
                "Country", "Latitude", "Longitude", "Work Type", "Company Size",
                "Job Posting Date", "Preference", "Contact Person", "Contact",
                "Job Title", "Role", "Job Portal", "Job Description",
                "Benefits", "Skills", "Responsibilities", "Company", "Company Profile"
        };

        // Create a JFileChooser to allow the user to select the file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose File to Save");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Ensure the file has a .csv extension
            if (!filePath.endsWith(".csv")) {
                filePath += ".csv";
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                // Write the column headers
                for (int col = 0; col < columns.length; col++) {
                    writer.write(columns[col]);
                    if (col < columns.length - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();

                // Write the table data
                for (int row = 0; row < table.getRowCount(); row++) {
                    for (int col = 0; col < columns.length; col++) {
                        Object cellValue = table.getValueAt(row, col);
                        writer.write(cellValue == null ? "" : cellValue.toString());
                        if (col < columns.length - 1) {
                            writer.write(",");
                        }
                    }
                    writer.newLine();
                }

                // Show success message
                Message message = new Message( "Data exported successfully to ");
            } catch (IOException e) {
                e.printStackTrace();
                Message message = new Message( "Error occurred while saving file: " + e.getMessage());
            }
        } else {
            Message message = new Message("Export operation canceled.");
        }
    }


    //Sort Button
    private JComboBox<String> sortButton(DefaultTableModel tableModel, JTable table) {

        String[] options = {"Ascending", "Descending"};
        JComboBox<String> option6 = new JComboBox<>(options);
        option6.setBackground(new Color(204, 153, 0));
        option6.setForeground(Color.WHITE);
        option6.setPreferredSize(new Dimension(150, 35));
        option6.setFont(new Font("Arial", Font.PLAIN, 16));
        option6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) option6.getSelectedItem();

                // Create a TableRowSorter and apply it to the table model
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
                table.setRowSorter(sorter);

                // Set the sort order based on the selected option
                List<RowSorter.SortKey> sortKeys = new ArrayList<>();
                if (selectedOption.equals("Ascending")) {
                    sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING)); // Assuming "Experience" is in column 1
                } else if (selectedOption.equals("Descending")) {
                    sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
                }

                sorter.setSortKeys(sortKeys);

                // Set the custom comparator for "Experience"
                sorter.setComparator(1, new Comparator<String>() {
                    @Override
                    public int compare(String experience1, String experience2) {
                        // Extract the lower bound from the "Experience" string (before the "to")
                        int lowerBound1 = getLowerBound(experience1);
                        int lowerBound2 = getLowerBound(experience2);

                        // Compare based on the lower bound of the experience
                        return Integer.compare(lowerBound1, lowerBound2);
                    }
                });
            }
        });
        return option6;
    }

    private int getLowerBound(String experience) {
        try {
            String lowerBoundString = experience.split(" to ")[0].trim();
            return Integer.parseInt(lowerBoundString);
        } catch (Exception ex) {
            return 0;
        }
    }

    private JPanel createFilterPanel(JFrame frame) {
        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(Color.LIGHT_GRAY);
        filterPanel.setLayout(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Filter Jobs", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), Color.GRAY));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Filter Label
        JLabel filterLabel = new JLabel("Filter Jobs");
        filterLabel.setFont(new Font("Arial", Font.BOLD, 18));
        filterLabel.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        filterPanel.add(filterLabel, gbc);

        // Job Title
        JLabel jobTitleLabel = new JLabel("Job Title:");
        jobTitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField jobTitleField = new JTextField();
        jobTitleField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        filterPanel.add(jobTitleLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        filterPanel.add(jobTitleField, gbc);

        // Country
        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField countryField = new JTextField();
        countryField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        filterPanel.add(countryLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        filterPanel.add(countryField, gbc);

        // Work Type
        JLabel workTypeLabel = new JLabel("Work Type:");
        workTypeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField workTypeField = new JTextField();
        workTypeField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        filterPanel.add(workTypeLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        filterPanel.add(workTypeField, gbc);

        JButton filterButton = createButton("Filter",e -> {
            if(!jobTitleField.getText().isEmpty() && !workTypeField.getText().isEmpty() && !countryField.getText().isEmpty()) {
                frame.setEnabled(false);
                GuiFilter gui = new GuiFilter(jobTitleField, workTypeField, countryField, frame);
                jobTitleField.setBorder(new LineBorder(Color.BLACK, 1));
                workTypeField.setBorder(new LineBorder(Color.BLACK, 1));
                countryField.setBorder(new LineBorder(Color.BLACK,1));
            }
            else{
                Message message = new Message("Please fill all the fields");
                if(jobTitleField.getText().isEmpty()) jobTitleField.setBorder(new LineBorder(Color.RED, 2));
                else jobTitleField.setBorder(new LineBorder(Color.BLACK, 1));
                if(workTypeField.getText().isEmpty()) workTypeField.setBorder(new LineBorder(Color.RED, 2));
                else workTypeField.setBorder(new LineBorder(Color.BLACK, 1));
                if(countryField.getText().isEmpty()) countryField.setBorder(new LineBorder(Color.RED, 2));
                else countryField.setBorder(new LineBorder(Color.BLACK,1));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        filterPanel.add(filterButton, gbc);

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 10, 10);
        filterPanel.add(new JLabel(), gbc);

        jobTitleField.setPreferredSize(new Dimension(70, 20));
        countryField.setPreferredSize(new Dimension(70, 20));
        workTypeField.setPreferredSize(new Dimension(70, 20));


        return filterPanel;
    }

    private JPanel createStatsPanel(JFrame frame) {
        JPanel statsPanel = new JPanel(new GridLayout(2, 2));
        statsPanel.setBackground(Color.LIGHT_GRAY);

        // Placeholder labels for immediate UI feedback
        JLabel jobCountLabel = new JLabel("Jobs found: Loading...");
        JLabel salaryRangeLabel = new JLabel("Salary Range: Loading...");
        JLabel experienceRangeLabel = new JLabel("Experience Range (Years): Loading...");

        // Set fonts and borders
        Font labelFont = new Font("Arial", Font.BOLD, 16);

        jobCountLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 0));
        jobCountLabel.setFont(labelFont);

        salaryRangeLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 0));
        salaryRangeLabel.setFont(labelFont);

        experienceRangeLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 0));
        experienceRangeLabel.setFont(labelFont);

        // Add placeholders to panel
        statsPanel.add(jobCountLabel);
        statsPanel.add(new JLabel("")); // Empty space for alignment
        statsPanel.add(salaryRangeLabel);
        statsPanel.add(experienceRangeLabel);

        // SwingWorker to load statistics
        SwingWorker<Void, Void> statsWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                // Fetch job statistics in the background
                double[] jobStatistics = db.getJobStatistics(); // Assume this is a time-consuming call
                // Update the UI labels on the Event Dispatch Thread (EDT)
                SwingUtilities.invokeLater(() -> {
                    jobCountLabel.setText("Jobs found: " + (int) jobStatistics[4]);
                    salaryRangeLabel.setText("Salary Range: $" + jobStatistics[0] + "K - $" + jobStatistics[1] + "K");
                    experienceRangeLabel.setText("Experience Range (Years): " + jobStatistics[2] + " - " + jobStatistics[3]);
                });
                return null;
            }

            @Override
            protected void done() {
                // Any additional actions after the data has been fetched
                statsPanel.revalidate();
                statsPanel.repaint();
            }
        };

        statsWorker.execute(); // Start the SwingWorker

        return statsPanel;
    }


    private void viewJob(IntegerOnly jobIdField,JFrame frame) {
        if(jobIdField.CheckLong()) {
            System.out.println("asdfas");
            DatabaseActions db = new DatabaseActions();
            ReadJob job = db.printDataFromDatabase(Long.parseLong(jobIdField.getText()));
            if (job != null) {
                frame.setEnabled(false);
                GuiPresentID gui = new GuiPresentID(job,frame);
            } else {
                Message message = new Message("No job found with this ID: " + jobIdField.getText());
            }
        }
    }

    private void updateJob(IntegerOnly jobIdField,JFrame frame) {
        if(jobIdField.CheckLong()) {
            DatabaseActions dbActions = new DatabaseActions();
            ReadJob job = db.printDataFromDatabase(Long.parseLong(jobIdField.getText()));
            if (job != null) {
                frame.setEnabled(false);
                GuiUpdateID gui = new GuiUpdateID(job,frame);
            } else {
                Message message = new Message("No job found with this ID: " + jobIdField.getText());
            }
        }
    }

    private void deleteJob(IntegerOnly jobIdField,JFrame frame) {
        if(jobIdField.CheckLong()) {
            DatabaseActions dbActions = new DatabaseActions();
            ReadJob job = db.printDataFromDatabase(Long.parseLong(jobIdField.getText()));
            if (job != null) {
                frame.setEnabled(false);
                Message gui = new Message(job,frame);
            } else {
                Message message = new Message("No job found with this ID: " + jobIdField.getText());
            }
        }
    }

    private void addJob(JFrame frame) {
        frame.setEnabled(false);
        GuiAddID gui = new GuiAddID(frame);
    }
}
