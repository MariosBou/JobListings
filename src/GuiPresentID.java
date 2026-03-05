import com.mysql.cj.protocol.x.XMessage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GuiPresentID {

    public GuiPresentID(ReadJob job,JFrame frame1) {

        JFrame frame = new JFrame("Job Details");
        DatabaseActions db = new DatabaseActions();
        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        addField(panel, gbc, "Job ID (Integer):",new IntegerOnly(), String.valueOf(job.getId()), 0);
        addField(panel, gbc, "Experience (String):",new JTextField(20), job.getExperience(), 1);
        addField(panel, gbc, "Qualifications (String):",new JTextField(20), job.getQualifications(), 2);
        addField(panel, gbc, "Salary Range (String):", new JTextField(20), job.getSalaryRange(), 3);
        addField(panel, gbc, "Location (String):", new JTextField(20), job.getLocation(), 4);
        addField(panel, gbc, "Country (String):",new JTextField(20), job.getCountry(), 5);
        addField(panel, gbc, "Latitude (Double):", new IntegerOnly(), String.valueOf(job.getLatitude()), 6);
        addField(panel, gbc, "Longitude (Double):", new IntegerOnly(), String.valueOf(job.getLongitude()), 7);
        addField(panel, gbc, "Work Type (String):",new JTextField(20), job.getWorkType(), 8);
        addField(panel, gbc, "Company Size (Integer):",new IntegerOnly(), String.valueOf(job.getCompanySize()), 9);
        addField(panel, gbc, "Job Posting Date (yyyy-mm-dd):", new IntegerOnly(), job.getJobPostingDate(), 10);
        addField(panel, gbc, "Preference (String):", new JTextField(20), job.getPreference(), 11);
        addField(panel, gbc, "Contact Person (String):",  new JTextField(20), job.getContactPerson(), 12);
        addField(panel, gbc, "Contact (String):", new JTextField(20), job.getContact(), 13);
        addField(panel, gbc, "Job Title (String):",  new JTextField(20), job.getJobTitle(), 14);
        addField(panel, gbc, "Role (String):", new JTextField(20), job.getRole(), 15);
        addField(panel, gbc, "Job Portal (String):", new JTextField(20), job.getJobPortal(), 16);
        addField(panel, gbc, "Description (String):",new JTextField(20), job.getJobDescription(), 17);
        addField(panel, gbc, "Benefits (String):", new JTextField(20), job.getBenefits(), 18);
        addField(panel, gbc, "Skills (String):",  new JTextField(20), job.getSkills(), 19);
        addField(panel, gbc, "Responsibilities (String):",  new JTextField(20), job.getResponsibilities(), 20);
        addField(panel, gbc, "Company (String):",  new JTextField(20), job.getCompany(), 21);
        addField(panel, gbc, "Company Profile (String):",  new JTextField(20), job.getCompanyProfileJson(), 22);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        headerPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        // Add title
        JLabel headerLabel = new JLabel("View Job Details", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        headerPanel.add(headerLabel, BorderLayout.CENTER);



        // Back button
        JButton backMenuButton = new JButton("Menu");
        backMenuButton.setMnemonic(KeyEvent.VK_M);
        backMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame1.setEnabled(true);
                frame.dispose();
            }
        });
        buttonPanel.add(backMenuButton);

        // Action Buttons (Delete, Update, Export)
        addActionButtons(buttonPanel, job, db, frame,frame1);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocation(520,20);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    private void addActionButtons(JPanel buttonPanel,ReadJob job, DatabaseActions db, JFrame frame,JFrame frame1) {

        //Delete Button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setMnemonic(KeyEvent.VK_D);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Message message = new Message(job,frame1);
            }
        });
        buttonPanel.add(deleteButton);

        //Update Button
        JButton updateButton = new JButton("Update");
        updateButton.setMnemonic(KeyEvent.VK_U);
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                GuiUpdateID guiUpdateID = new GuiUpdateID(job,frame1);
            }
        });
        buttonPanel.add(updateButton);

        //Export Button
        JButton exportButton = new JButton("Export");
        exportButton.setMnemonic(KeyEvent.VK_E);
        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportToCSV(job);
            }
        });
        buttonPanel.add(exportButton);
    }

    private void exportToCSV(ReadJob job) {
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
                // Write column headers
                for (int col = 0; col < columns.length; col++) {
                    writer.write(columns[col]);
                    if (col < columns.length - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();

                // Write data, handling commas in field values
                writeQuotedField(writer, String.valueOf(job.getId()));
                writeQuotedField(writer, job.getExperience());
                writeQuotedField(writer, job.getQualifications());
                writeQuotedField(writer, job.getSalaryRange());
                writeQuotedField(writer, job.getLocation());
                writeQuotedField(writer, job.getCountry());
                writeQuotedField(writer, String.valueOf(job.getLatitude()));
                writeQuotedField(writer, String.valueOf(job.getLongitude()));
                writeQuotedField(writer, job.getWorkType());
                writeQuotedField(writer, String.valueOf(job.getCompanySize()));
                writeQuotedField(writer, job.getJobPostingDate());
                writeQuotedField(writer, job.getPreference());
                writeQuotedField(writer, job.getContactPerson());
                writeQuotedField(writer, job.getContact());
                writeQuotedField(writer, job.getJobTitle());
                writeQuotedField(writer, job.getRole());
                writeQuotedField(writer, job.getJobPortal());
                writeQuotedField(writer, job.getJobDescription());
                writeQuotedField(writer, job.getBenefits());
                writeQuotedField(writer, job.getSkills());
                writeQuotedField(writer, job.getResponsibilities());
                writeQuotedField(writer, job.getCompany());
                writeQuotedField(writer, job.getCompanyProfileJson());

                writer.newLine();

                Message message = new Message("Data exported Successfully!");
            } catch (IOException e) {
                Message message = new Message("Error writing to file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            Message message = new Message("Export operation canceled.");
        }
    }

    // Helper method to write fields with quotes, escaping commas
    private void writeQuotedField(BufferedWriter writer, String value) throws IOException {
        if (value != null) {
            writer.write("\"" + value.replace("\"", "\"\"") + "\"");
        } else {
            writer.write("\"\"");
        }
        writer.write(",");
    }



    private void addField(JPanel panel, GridBagConstraints gbc, String label, JTextField field,String value, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        field.setText(value);
        field.setEditable(false);
        panel.add(field, gbc);
    }

}