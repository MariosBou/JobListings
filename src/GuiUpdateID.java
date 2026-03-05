import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GuiUpdateID {

    // Fields
    private JTextField textExperience, textQualifications, textSalary, textLocation, textCountry;
    private JTextField textWork, textPreference, textContact, textContactPerson, textTitle, textRole, textPortal, textCompany;
    private JTextField textDescription, textSkills, textResponsibilities, textProfile, textBenefits;
    private IntegerOnly textID, textLatitude, textLongitude, textSize, textDate;
    private JButton update;
    private ReadJob job;
    private DatabaseActions actions;

    public GuiUpdateID(ReadJob job1,JFrame frame1) {

        //Get job data
        actions = new DatabaseActions();
        job = actions.getJob(job1.getId());
        Long ID = job.getId();

        JFrame frame = new JFrame("Update Job Information");
        frame.setLayout(new BorderLayout());


        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.LIGHT_GRAY);
        JLabel headerLabel = new JLabel("Update Job Details", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add fields to main panel
        addField(mainPanel, gbc, "Job ID (Integer):", textID = new IntegerOnly(), String.valueOf(job.getId()), 0);
        addField(mainPanel, gbc, "Experience (String):", textExperience = new JTextField(20), job.getExperience(), 1);
        addField(mainPanel, gbc, "Qualifications (String):", textQualifications = new JTextField(20), job.getQualifications(), 2);
        addField(mainPanel, gbc, "Salary Range (String):", textSalary = new JTextField(20), job.getSalaryRange(), 3);
        addField(mainPanel, gbc, "Location (String):", textLocation = new JTextField(20), job.getLocation(), 4);
        addField(mainPanel, gbc, "Country (String):", textCountry = new JTextField(20), job.getCountry(), 5);
        addField(mainPanel, gbc, "Latitude (Double):", textLatitude = new IntegerOnly(), String.valueOf(job.getLatitude()), 6);
        addField(mainPanel, gbc, "Longitude (Double):", textLongitude = new IntegerOnly(), String.valueOf(job.getLongitude()), 7);
        addField(mainPanel, gbc, "Work Type (String):", textWork = new JTextField(20), job.getWorkType(), 8);
        addField(mainPanel, gbc, "Company Size (Integer):", textSize = new IntegerOnly(), String.valueOf(job.getCompanySize()), 9);
        addField(mainPanel, gbc, "Job Posting Date (yyyy-mm-dd):", textDate = new IntegerOnly(), job.getJobPostingDate(), 10);
        addField(mainPanel, gbc, "Preference (String):", textPreference = new JTextField(20), job.getPreference(), 11);
        addField(mainPanel, gbc, "Contact Person (String):", textContactPerson = new JTextField(20), job.getContactPerson(), 12);
        addField(mainPanel, gbc, "Contact (String):", textContact = new JTextField(20), job.getContact(), 13);
        addField(mainPanel, gbc, "Job Title (String):", textTitle = new JTextField(20), job.getJobTitle(), 14);
        addField(mainPanel, gbc, "Role (String):", textRole = new JTextField(20), job.getRole(), 15);
        addField(mainPanel, gbc, "Job Portal (String):", textPortal = new JTextField(20), job.getJobPortal(), 16);
        addField(mainPanel, gbc, "Description (String):", textDescription = new JTextField(20), job.getJobDescription(), 17);
        addField(mainPanel, gbc, "Benefits (String):", textBenefits = new JTextField(20), job.getBenefits(), 18);
        addField(mainPanel, gbc, "Skills (String):", textSkills = new JTextField(20), job.getSkills(), 19);
        addField(mainPanel, gbc, "Responsibilities (String):", textResponsibilities = new JTextField(20), job.getResponsibilities(), 20);
        addField(mainPanel, gbc, "Company (String):", textCompany = new JTextField(20), job.getCompany(), 21);
        addField(mainPanel, gbc, "Company Profile (String):", textProfile = new JTextField(20), job.getCompanyProfileJson(), 22);
        // Footer Panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        footerPanel.setBackground(Color.LIGHT_GRAY);

        update = new JButton("Update");
        update.setMnemonic(KeyEvent.VK_U);
        update.addActionListener(e -> {
            if (getEmptyFields()) {
                updateJobDetails();
                actions.updateElements(job, ID);
                frame.dispose();
                frame1.setEnabled(true);
                Message message =new Message("Job Updated Successfully! ");
            } else if (actions.checkID(Long.parseLong(textID.getText()))) {
                textID.setBorder(new LineBorder(Color.RED, 2));
                Message message =new Message("Job Found with this ID! ");
            } else {
                resetAllCellBordersToOriginal();

                //Make empty Cells Red
                checkForEmptyFields();

                if (!textID.getText().isEmpty()) textID.CheckLong();
                if (!textLatitude.getText().isEmpty()) textLatitude.CheckDouble();
                if (!textLongitude.getText().isEmpty()) textLongitude.CheckDouble();
                if (!textSize.getText().isEmpty()) textSize.CheckInteger();
                if (!textDate.getText().isEmpty()) textDate.CheckDate();

                Message message = new Message("Please correct the red fields");
            }
        });

        footerPanel.add(getBackMenu(frame,frame1));
        footerPanel.add(update);

        // Add Panels to Frame
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(footerPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocation(520,20);
        frame.setResizable(true);
        frame.pack();
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, String value, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        field.setText(value);
        panel.add(field, gbc);
    }

    private JButton getBackMenu(JFrame frame,JFrame frame1) {
        JButton menu = new JButton("Menu");
        menu.setMnemonic(KeyEvent.VK_M);
        menu.setToolTipText("Return to menu");
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frame1.setEnabled(true);
                Message message =new Message("The Job was not Updated");
            }
        });
        return menu;
    }

    private boolean getEmptyFields(){
        if(getEmptyField(textID) && getEmptyField(textExperience) && getEmptyField(textQualifications) && getEmptyField(textSalary) && getEmptyField(textLocation) &&
                getEmptyField(textCountry) && getEmptyField(textLatitude) && getEmptyField(textLongitude) && getEmptyField(textSize) && getEmptyField(textDate) &&
                getEmptyField(textPreference) && getEmptyField(textContactPerson) && getEmptyField(textContact) && getEmptyField(textTitle) && getEmptyField(textRole) &&
                getEmptyField(textWork) && getEmptyField(textPortal) && getEmptyField(textDescription) && getEmptyField(textSkills) && getEmptyField(textResponsibilities) &&
                getEmptyField(textProfile) && getEmptyField(textBenefits) && getEmptyField(textCompany)) {
            return true;
        }
        return false;
    }

    private void checkForEmptyFields() {
        NullCell(textID);
        NullCell(textExperience);
        NullCell(textQualifications);
        NullCell(textSalary);
        NullCell(textLocation);
        NullCell(textCountry);
        NullCell(textLatitude);
        NullCell(textLongitude);
        NullCell(textSize);
        NullCell(textDate);
        NullCell(textPreference);
        NullCell(textContactPerson);
        NullCell(textContact);
        NullCell(textTitle);
        NullCell(textRole);
        NullCell(textWork);
        NullCell(textPortal);
        NullCell(textDescription);
        NullCell(textSkills);
        NullCell(textResponsibilities);
        NullCell(textProfile);
        NullCell(textBenefits);
        NullCell(textSkills);
        NullCell(textResponsibilities);
        NullCell(textCompany);
    }

    private void resetAllCellBordersToOriginal() {
        CellOriginal(textID);
        CellOriginal(textExperience);
        CellOriginal(textQualifications);
        CellOriginal(textSalary);
        CellOriginal(textLocation);
        CellOriginal(textCountry);
        CellOriginal(textLatitude);
        CellOriginal(textLongitude);
        CellOriginal(textSize);
        CellOriginal(textDate);
        CellOriginal(textPreference);
        CellOriginal(textContactPerson);
        CellOriginal(textContact);
        CellOriginal(textTitle);
        CellOriginal(textRole);
        CellOriginal(textWork);
        CellOriginal(textPortal);
        CellOriginal(textDescription);
        CellOriginal(textSkills);
        CellOriginal(textResponsibilities);
        CellOriginal(textProfile);
        CellOriginal(textBenefits);
        CellOriginal(textCompany);
    }


    private void NullCell(JTextField field) {
        if (field.getText().isEmpty()) {
            field.setBorder(new LineBorder(Color.RED, 2));
        }
    }

    private void NullCell(IntegerOnly field){
        if (field.getText().isEmpty()) {
            field.setBorder(new LineBorder(Color.RED, 2));
        }
    }

    private void CellOriginal(JTextField field) {
        field.setBorder(new LineBorder(Color.BLACK, 1));
    }

    private void CellOriginal(IntegerOnly field) {
        field.setBorder(new LineBorder(Color.BLACK, 1));
    }

    private boolean getEmptyField(JTextField field){
        if(field.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean getEmptyField(IntegerOnly field){
        if(field.getText().isEmpty()) {
            return false;
        }
        return true;
    }


    private void updateJobDetails() {
        job.setId(Long.parseLong(textID.getText()));
        job.setExperience(textExperience.getText());
        job.setQualifications(textQualifications.getText());
        job.setSalaryRange(textSalary.getText());
        job.setLocation(textLocation.getText());
        job.setCountry(textCountry.getText());
        job.setLatitude(Double.parseDouble(textLatitude.getText()));
        job.setLongitude(Double.parseDouble(textLongitude.getText()));
        job.setCompanySize(Integer.parseInt(textSize.getText()));
        job.setJobPostingDate(textDate.getText());
        job.setPreference(textPreference.getText());
        job.setContactPerson(textContactPerson.getText());
        job.setContact(textContact.getText());
        job.setJobTitle(textTitle.getText());
        job.setRole(textRole.getText());
        job.setJobPortal(textPortal.getText());
        job.setJobDescription(textDescription.getText());
        job.setBenefits(textBenefits.getText());
        job.setSkills(textSkills.getText());
        job.setResponsibilities(textResponsibilities.getText());
        job.setCompany(textCompany.getText());
        job.setCompanyProfileJson(textProfile.getText());
    }
}
