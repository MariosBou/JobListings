import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GuiFilter {

    public GuiFilter(JTextField jobtitle, JTextField country, JTextField workingtype,JFrame frame1) {
        DatabaseActions db = new DatabaseActions();
        JFrame frame = new JFrame();
        int counter = 0;
        double min = 0, max = 0, minYear = 0, maxYear = 0;
        JPanel panel = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(3 ,2,0,5));
        JPanel bottomPanel = new JPanel();
        List<String[]> data = db.filter(jobtitle.getText(), country.getText(), workingtype.getText());

        String[] columns = {"Job Id", "Experience", "Qualifications", "Salary Range", "Location",
                "Country", "Latitude", "Longitude", "Work Type", "Company Size",
                "Job Posting Date", "Preference", "Contact Person", "Contact",
                "Job Title", "Role", "Job Portal", "Job Description",
                "Benefits", "Skills", "Responsibilities", "Company", "Company Profile"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (String[] row : data) {
            tableModel.addRow(row);
            counter++;

            String salaryRange = row[3];
            if (salaryRange != null && salaryRange.contains("-")) {
                try {
                    String[] salaryParts = salaryRange.replace("$", "").replace("K", "").split("-");
                    double minSalary = Double.parseDouble(salaryParts[0]) * 1000; // Convert to full salary
                    double maxSalary = Double.parseDouble(salaryParts[1]) * 1000; // Convert to full salary
                    min += minSalary;
                    max += maxSalary;
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error processing salary range: " + salaryRange);
                }
            }

            // Process Experience Range
            String experienceRange = row[1];
            if (experienceRange != null && experienceRange.contains(" to ")) {
                try {
                    String[] experienceParts = experienceRange.replace("Years", "").trim().split(" to ");
                    double minYears = Double.parseDouble(experienceParts[0].trim());
                    double maxYears = Double.parseDouble(experienceParts[1].trim());
                    minYear += minYears;
                    maxYear += maxYears;
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error processing experience range: " + experienceRange);
                }
            }
        }

        JTable table = new JTable(tableModel);
        // Scroll Pane for table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        //Open PresentGui
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
                        viewJob(integerOnly, frame); // Invoke your custom method
                    }
                }
            }
        });

        // Sort Button
        String[] options = {"Ascending", "Descending"};
        JComboBox<String> option6 = new JComboBox<>(options);
        option6.setBackground(new Color(255, 255, 255));  // White background for combo box
        option6.setForeground(new Color(0, 0, 0));  // Black text for options
        option6.setFont(new Font("Arial", Font.BOLD, 16));
        option6.setPreferredSize(new Dimension(120,40));
        option6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) option6.getSelectedItem();
                if (selectedOption.equals("Ascending")) {
                    // Sort data in ascending order based on the low-end of experience
                    Collections.sort(data, new Comparator<String[]>() {
                        @Override
                        public int compare(String[] o1, String[] o2) {
                            String experience1 = o1[1]; // Experience column
                            String experience2 = o2[1]; // Experience column
                            int lowEnd1 = getLowEndOfExperience(experience1);
                            int lowEnd2 = getLowEndOfExperience(experience2);
                            return Integer.compare(lowEnd1, lowEnd2);
                        }
                    });
                } else if (selectedOption.equals("Descending")) {
                    // Sort data in descending order based on the low-end of experience
                    Collections.sort(data, new Comparator<String[]>() {
                        @Override
                        public int compare(String[] o1, String[] o2) {
                            String experience1 = o1[1]; // Experience column
                            String experience2 = o2[1]; // Experience column
                            int lowEnd1 = getLowEndOfExperience(experience1);
                            int lowEnd2 = getLowEndOfExperience(experience2);
                            return Integer.compare(lowEnd2, lowEnd1); // Reverse order for descending
                        }
                    });
                }
                // Update the table model with sorted data
                DefaultTableModel newTableModel = new DefaultTableModel(columns, 0);
                for (String[] row : data) {
                    newTableModel.addRow(row);
                }
                table.setModel(newTableModel);
            }
        });


        // JTable settings
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Disable auto resizing of columns to ensure horizontal scrolling
        table.setPreferredScrollableViewportSize(new Dimension(800, 300)); // Set preferred size
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
        table.setEnabled(false);
        table.setRowHeight(40);
        table.setBackground(new Color(245, 245, 245));  // Light background color for rows
        table.setGridColor(Color.GRAY);  // Grid lines
        table.setSelectionBackground(new Color(198, 210, 255)); // Highlight selected rows
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Use a more readable font
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));  // Bold header font
        table.getTableHeader().setBackground(new Color(180, 220, 240));  // Light blue header background


        if(counter>0) {

            JLabel label1,label2,label3,label4,label5;
            labelPanel.add(label1 = new JLabel("We found " + counter + " Jobs"));
            labelPanel.add( new JLabel(""));
            labelPanel.add(label4 = new JLabel(String.format("The average highest salary is $%.2fK",(max / (double) counter)/1000.00)));
            labelPanel.add(label5 = new JLabel(String.format("The average highest years of experience is %.2f",maxYear / (double) counter)));
            labelPanel.add(label2 = new JLabel(String.format("The average lowest salary is $%.2fK", (min / (double) counter)/1000.00)));
            labelPanel.add(label3 = new JLabel(String.format("The average lowest years of experience is %.2f",minYear / (double) counter)));


            label1.setFont(new Font("Arial", Font.BOLD, 14));
            label2.setFont(new Font("Arial", Font.PLAIN, 14));
            label3.setFont(new Font("Arial", Font.PLAIN, 14));
            label4.setFont(new Font("Arial", Font.PLAIN, 14));
            label5.setFont(new Font("Arial", Font.PLAIN, 14));

            // Panel settings
            labelPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            labelPanel.setBorder(BorderFactory.createEtchedBorder());

            bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
            bottomPanel.add(getBackMenu(frame,frame1));
            bottomPanel.add(option6);
            bottomPanel.add(getBackExport(data, columns));


            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel.add(scrollPane, BorderLayout.CENTER);
            panel.add(bottomPanel, BorderLayout.NORTH);
            panel.add(labelPanel, BorderLayout.SOUTH);

            // Frame Settings
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);
            frame.setLayout(new BorderLayout());
            frame.add(panel, BorderLayout.CENTER);  // Add the main panel
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);

            // Ensure components are laid out properly
            frame.revalidate();
            frame.repaint();
        }
        else {
            frame1.setEnabled(true);
            Message message = new Message("No jobs found");
        }
    }

    private int getLowEndOfExperience(String experienceRange) {
        // Extracts the low end of the experience range (e.g., "4" from "4 to 13 Years")
        if (experienceRange != null && experienceRange.contains(" to ")) {
            try {
                String[] experienceParts = experienceRange.replace("Years", "").trim().split(" to ");
                return Integer.parseInt(experienceParts[0].trim());
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Error processing experience range: " + experienceRange);
            }
        }
        return Integer.MAX_VALUE; // Return a large value if the range is not valid
    }

    private void exportToCSV(List<String[]> data, String[] columns) {
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
                // Write the columns header
                writer.write(String.join(",", columns));
                writer.newLine();

                // Write each row to the CSV file
                for (String[] row : data) {
                    // Quote each value in the row to avoid issues with commas inside the data
                    String[] quotedRow = new String[row.length];
                    for (int i = 0; i < row.length; i++) {
                        quotedRow[i] = "\"" + row[i].replace("\"", "\"\"") + "\""; // Escape quotes inside the values
                    }
                    writer.write(String.join(",", quotedRow));
                    writer.newLine();
                }

                // Success message
                Message message = new Message("Data exported successfully!");
            } catch (IOException e) {
                Message message = new Message("Error writing to file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // User canceled the operation
                Message message = new Message("Export operation canceled.");
        }
    }


    private JButton getBackExport(List<String[]> data,String[] columns) {
        JButton export = new JButton("Export");
        export.setForeground(Color.WHITE);  // White text
        export.setFocusPainted(false);  // Remove border when focused
        export.setBackground(new Color(56, 103, 214));  // Original blue
        export.setFont(new Font("Arial", Font.BOLD, 16));

        // Hover effect for button
        export.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                export.setBackground(new Color(76, 123, 235));  // Lighter blue on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                export.setBackground(new Color(56, 103, 214));  // Original blue
            }
        });

        export.setMnemonic(KeyEvent.VK_E);
        export.setPreferredSize(new Dimension(120, 40));
        export.setToolTipText("Export the Table");
        export.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportToCSV(data,columns);
            }
        });
        return export;
    }


    private JButton getBackMenu(JFrame frame,JFrame frame1) {
        JButton menu = new JButton("Menu");
        menu.setForeground(Color.WHITE);  // White text
        menu.setFocusPainted(false);
        menu.setBackground(new Color(56, 103, 214));  // Original blue
        menu.setFont(new Font("Arial", Font.BOLD, 16));

        // Hover effect for button
        menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menu.setBackground(new Color(76, 123, 235));  // Lighter blue on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menu.setBackground(new Color(56, 103, 214));  // Original blue
            }
        });

        menu.setMnemonic(KeyEvent.VK_M);
        menu.setPreferredSize(new Dimension(120, 40));
        menu.setToolTipText("Return to menu");
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frame1.setEnabled(true);
            }
        });
        return menu;
    }

    private void viewJob(IntegerOnly jobIdField,JFrame frame) {
        DatabaseActions db = new DatabaseActions();
        ReadJob job = db.printDataFromDatabase(Long.parseLong(jobIdField.getText()));
        frame.setEnabled(false);
        GuiPresentID gui = new GuiPresentID(job,frame);
    }
}
