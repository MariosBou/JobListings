import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableData extends AbstractTableModel {

    private List<String[]> data = new ArrayList<>();
    private static final int LOAD_INCREMENT = 40;
    private int loadedrows = 0;
    private String[] columnNames = {
            "Job Id", "Experience", "Qualifications", "Salary Range", "Location",
            "Country", "Latitude", "Longitude", "Work Type", "Company Size",
            "Job Posting Date", "Preference", "Contact Person", "Contact",
            "Job Title", "Role", "Job Portal", "Job Description",
            "Benefits", "Skills", "Responsibilities", "Company", "Company Profile"
    };

    protected static final String URL = "jdbc:mysql://127.0.0.1:3306/db";
    protected static final String User = "root";
    protected static final String Password = "trapezaki44!";

    public TableData() {
        loadDataFromDatabase();
    }

    // Get row count for the table
    @Override
    public int getRowCount() {
        return data.size();
    }

    // Get column count for the table
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    // Get column names
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // Get the value at specific row and column
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    public void loadDataFromDatabase(){
        try (Connection connection = DriverManager.getConnection(URL, User, Password)) {
            // Update the query to only select the desired columns
            String query = "SELECT `Job Id`, Experience, Qualifications, `Salary Range`, Location, Country, Latitude, Longitude, `Work Type`, " +
                    "`Company Size`, `Job Posting Date`, Preference, `Contact Person`, Contact, `Job Title`, Role, `Job Portal`, " +
                    "`Job Description`, Benefits, Skills, Responsibilities, Company, `Company Profile` " +
                    "FROM db.cleaned_full_job_description LIMIT " + LOAD_INCREMENT + " OFFSET " + loadedrows;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int rows = 0;
            while (resultSet.next()) {
                String[] row = new String[23];

                row[0] = String.valueOf(resultSet.getLong("Job Id"));
                row[1] = resultSet.getString("Experience");
                row[2] = resultSet.getString("Qualifications");
                row[3] = resultSet.getString("Salary Range");
                row[4] = resultSet.getString("Location");
                row[5] = resultSet.getString("Country");
                row[6] = resultSet.getString("Latitude");
                row[7] = resultSet.getString("Longitude");
                row[8] = resultSet.getString("Work Type");
                row[9] = resultSet.getString("Company Size");
                row[10] = resultSet.getString("Job Posting Date");
                row[11] = resultSet.getString("Preference");
                row[12] = resultSet.getString("Contact Person");
                row[13] = resultSet.getString("Contact");
                row[14] = resultSet.getString("Job Title");
                row[15] = resultSet.getString("Role");
                row[16] = resultSet.getString("Job Portal");
                row[17] = resultSet.getString("Job Description");
                row[18] = resultSet.getString("Benefits");
                row[19] = resultSet.getString("Skills");
                row[20] = resultSet.getString("Responsibilities");
                row[21] = resultSet.getString("Company");
                row[22] = resultSet.getString("Company Profile");

                data.add(row);
                rows++;
            }


            loadedrows += rows;
            resultSet.close();
            statement.close();
            fireTableRowsInserted(data.size() - rows, data.size() - 1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void loadDataFromDatabase(DefaultTableModel tableModel) {
        try (Connection connection = DriverManager.getConnection(URL, User, Password)) {
            String query = "SELECT `Job Id`, Experience, Qualifications, `Salary Range`, Location, Country, Latitude, Longitude, `Work Type`, " +
                    "`Company Size`, `Job Posting Date`, Preference, `Contact Person`, Contact, `Job Title`, Role, `Job Portal`, " +
                    "`Job Description`, Benefits, Skills, Responsibilities, Company, `Company Profile` " +
                    "FROM db.cleaned_full_job_description LIMIT " + LOAD_INCREMENT + " OFFSET " + loadedrows;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int rows = 0;
            while (resultSet.next()) {

                Object[] row = new Object[23];
                row[0] = String.valueOf(resultSet.getLong("Job Id"));
                row[1] = resultSet.getString("Experience");
                row[2] = resultSet.getString("Qualifications");
                row[3] = resultSet.getString("Salary Range");
                row[4] = resultSet.getString("Location");
                row[5] = resultSet.getString("Country");
                row[6] = resultSet.getString("Latitude");
                row[7] = resultSet.getString("Longitude");
                row[8] = resultSet.getString("Work Type");
                row[9] = resultSet.getString("Company Size");
                row[10] = resultSet.getString("Job Posting Date");
                row[11] = resultSet.getString("Preference");
                row[12] = resultSet.getString("Contact Person");
                row[13] = resultSet.getString("Contact");
                row[14] = resultSet.getString("Job Title");
                row[15] = resultSet.getString("Role");
                row[16] = resultSet.getString("Job Portal");
                row[17] = resultSet.getString("Job Description");
                row[18] = resultSet.getString("Benefits");
                row[19] = resultSet.getString("Skills");
                row[20] = resultSet.getString("Responsibilities");
                row[21] = resultSet.getString("Company");
                row[22] = resultSet.getString("Company Profile");

                tableModel.addRow(row);
                rows++;
            }

            loadedrows += rows;
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> getData() {
        return data;
    }
}
