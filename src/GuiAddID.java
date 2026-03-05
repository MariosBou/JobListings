import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GuiAddID {
    //Attributes
    JTextField textExperience,textQualifications,textSalary,textLocation,textCountry;
    JTextField textWork,textPreference,textContact,textContactPerson,textTitle,textRole,textPortal,textCompany;
    JTextField textDescription,textSkills,textResponsibilities,textProfile,textBenefits;
    IntegerOnly textID,textLatitude,textLongitude,textSize,textDate;

    public GuiAddID(JFrame frame1) {

        //Initialize
        JFrame frame = new JFrame("Add a new Job");
        JPanel headerPanel = new JPanel(new BorderLayout());
        JButton button = new JButton("Add");
        button.setMnemonic(KeyEvent.VK_A);

        // Add title
        JLabel headerLabel = new JLabel("Add Job Details", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        addField(panel, gbc, "Job ID (Integer):", textID = new IntegerOnly(), 0);
        addField(panel, gbc, "Experience (String):", textExperience = new JTextField(20), 1);
        addField(panel, gbc, "Qualifications (String):", textQualifications = new JTextField(20), 2);
        addField(panel, gbc, "Salary Range (String):", textSalary = new JTextField(20), 3);
        addField(panel, gbc, "Location (String):", textLocation = new JTextField(20), 4);
        addField(panel, gbc, "Country (String):", textCountry = new JTextField(20), 5);
        addField(panel, gbc, "Latitude (Double):", textLatitude = new IntegerOnly(), 6);
        addField(panel, gbc, "Longitude (Double):", textLongitude = new IntegerOnly(), 7);
        addField(panel, gbc, "Work Type (String):", textWork = new JTextField(20), 8);
        addField(panel, gbc, "Company Size (Integer):", textSize = new IntegerOnly(), 9);
        addField(panel, gbc, "Job Posting Date (yyyy-mm-dd):", textDate = new IntegerOnly(), 10);
        addField(panel, gbc, "Preference (String):", textPreference = new JTextField(20), 11);
        addField(panel, gbc, "Contact Person (String):", textContactPerson = new JTextField(20), 12);
        addField(panel, gbc, "Contact (String):", textContact = new JTextField(20), 13);
        addField(panel, gbc, "Job Title (String):", textTitle = new JTextField(20), 14);
        addField(panel, gbc, "Role (String):", textRole = new JTextField(20), 15);
        addField(panel, gbc, "Job Portal (String):", textPortal = new JTextField(20), 16);
        addField(panel, gbc, "Description (String):", textDescription = new JTextField(20), 17);
        addField(panel, gbc, "Benefits (String):", textBenefits = new JTextField(20), 18);
        addField(panel, gbc, "Skills (String):", textSkills = new JTextField(20), 19);
        addField(panel, gbc, "Responsibilities (String):", textResponsibilities = new JTextField(20),20);
        addField(panel, gbc, "Company (String):", textCompany = new JTextField(20), 21);
        addField(panel, gbc, "Company Profile (String):", textProfile = new JTextField(20), 22);



        //Add Action
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatabaseActions db=new DatabaseActions();
                ReadJob job = new ReadJob();

                resetAllCellBordersToOriginal();


                if (textID.CheckLong() && textLatitude.CheckDouble() && textLongitude.CheckDouble() && textSize.CheckInteger() && textDate.CheckDate() && getEmptyFields()) {
                    if(!db.checkID(Long.parseLong(textID.getText()))) {
                        job.setId(Integer.parseInt(textID.getText()));
                        job.setExperience(textExperience.getText());
                        job.setQualifications(textQualifications.getText());
                        job.setSalaryRange(textSalary.getText());
                        job.setLocation(textLocation.getText());
                        job.setCountry(textCountry.getText());
                        job.setLatitude(Double.parseDouble(textLatitude.getText()));
                        job.setLongitude(Double.parseDouble(textLongitude.getText()));
                        job.setWorkType(textWork.getText());
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
                        String profileText = textProfile.getText();
                        profileText = "\"" + profileText.replace("\"", "\\\"") + "\"";
                        job.setCompanyProfileJson(profileText);
                        frame.dispose();
                        frame1.setEnabled(true);
                        db.addDataToDatabase(job);
                        Message message = new Message("Job Added Successfully");
                    }
                    else {
                        Message message = new Message("Job found with the same ID");
                        textID.setBorder(new LineBorder(Color.RED, 3));
                    }
                }
                else {
                    //Make empty Cells Red
                    checkForEmptyFields();

                    if (!textID.getText().isEmpty()) textID.CheckLong();
                    if (!textLatitude.getText().isEmpty()) textLatitude.CheckDouble();
                    if (!textLongitude.getText().isEmpty()) textLongitude.CheckDouble();
                    if (!textSize.getText().isEmpty()) textSize.CheckInteger();
                    if (!textDate.getText().isEmpty()) textDate.CheckDate();

                    Message message = new Message("Please correct the red fields");
                }
            }
        });


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(getBackMenu(frame,frame1));
        buttonPanel.add(button);


        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocation(520,20);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(headerPanel,BorderLayout.NORTH);
        frame.pack();
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setVisible(true);

    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    public void checkForEmptyFields() {
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

    public void resetAllCellBordersToOriginal() {
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

    public boolean getEmptyFields(){
        if(getEmptyField(textID) && getEmptyField(textExperience) && getEmptyField(textQualifications) && getEmptyField(textSalary) && getEmptyField(textLocation) &&
                getEmptyField(textCountry) && getEmptyField(textLatitude) && getEmptyField(textLongitude) && getEmptyField(textSize) && getEmptyField(textDate) &&
                getEmptyField(textPreference) && getEmptyField(textContactPerson) && getEmptyField(textContact) && getEmptyField(textTitle) && getEmptyField(textRole) &&
                getEmptyField(textWork) && getEmptyField(textPortal) && getEmptyField(textDescription) && getEmptyField(textSkills) && getEmptyField(textResponsibilities) &&
                getEmptyField(textProfile) && getEmptyField(textBenefits) && getEmptyField(textCompany)) {
            return true;
        }
        return false;
    }

    public boolean getEmptyField(JTextField field){
        if (field.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean getEmptyField(IntegerOnly field){
        if (field.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public void NullCell(JTextField field) {
        if (field.getText().isEmpty()) {
            field.setBorder(new LineBorder(Color.RED, 2));
        }
    }

    public void NullCell(IntegerOnly field){
        if (field.getText().isEmpty()) {
            field.setBorder(new LineBorder(Color.RED, 2));
        }
    }

    public JButton getBackMenu(JFrame frame,JFrame frame1) {
        JButton menu = new JButton("Menu");
        menu.setMnemonic(KeyEvent.VK_M);
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frame1.setEnabled(true);
                Message message =new Message("The Job was not Added");
            }
        });
        return menu;
    }

    public void CellOriginal(JTextField field) {
        field.setBorder(new LineBorder(Color.BLACK, 1));
    }

    public void CellOriginal(IntegerOnly field) {
        field.setBorder(new LineBorder(Color.BLACK, 1));
    }

}