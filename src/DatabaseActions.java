import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseActions extends Database {

    // Constructor
    public DatabaseActions() {
        super();
    }

    // Method to fetch and print data from the database
    public ReadJob printDataFromDatabase(long jobID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        ReadJob job = new ReadJob();

        try {
            connection = DriverManager.getConnection(URL, User, Password);
            String sql = "SELECT * FROM db.cleaned_full_job_description WHERE `Job Id` =" + String.valueOf(jobID);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            result = preparedStatement.executeQuery();

            //IF job found
            if (result.next()) {

                // Read all the elements
                job.setId(result.getLong("Job Id"));
                job.setExperience(result.getString("Experience"));
                job.setQualifications(result.getString("Qualifications"));
                job.setSalaryRange(result.getString("Salary Range"));
                job.setLocation(result.getString("location"));
                job.setCountry(result.getString("Country"));
                job.setLatitude(result.getDouble("latitude"));
                job.setLongitude(result.getDouble("longitude"));
                job.setWorkType(result.getString("Work Type"));
                job.setCompanySize(result.getInt("Company Size"));
                job.setJobPostingDate(result.getString("Job Posting Date"));
                job.setPreference(result.getString("Preference"));
                job.setContactPerson(result.getString("Contact Person"));
                job.setContact(result.getString("Contact"));
                job.setJobTitle(result.getString("Job Title"));
                job.setRole(result.getString("Role"));
                job.setJobPortal(result.getString("Job Portal"));
                job.setJobDescription(result.getString("Job Description"));
                job.setBenefits(result.getString("Benefits"));
                job.setSkills(result.getString("Skills"));
                job.setResponsibilities(result.getString("Responsibilities"));
                job.setCompany(result.getString("Company"));
                job.setCompanyProfileJson(result.getString("Company Profile"));
            }
            else{
                return null;
            }

        }
        //Exception
        catch (SQLException e) {
            System.err.println("Connection failed or query execution error: " + e.getMessage());
            return null;
        } finally {
            // Close resources
            try {
                if (result != null) result.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            }
            //Exception
            catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                return null;
            }
        }
        return job;
    }


    public void addDataToDatabase(ReadJob job) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // Establish connection
            connection = DriverManager.getConnection(URL, User, Password);
            System.out.println("Connection established successfully.");

            String sql = "INSERT INTO db.cleaned_full_job_description (" +
                    "`Job Id`, experience, Qualifications, `Salary Range`, location, Country, latitude, longitude, " +
                    "`Work Type`, `Company Size`, `Job Posting Date`, Preference, `Contact Person`, Contact, `Job Title`, Role, " +
                    "`Job Portal`, `Job Description`, Benefits, Skills, Responsibilities, Company, `Company Profile`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sql);

            // Set parameters dynamically
            preparedStatement.setLong(1, job.getId());
            preparedStatement.setString(2, job.getExperience());
            preparedStatement.setString(3, job.getQualifications());
            preparedStatement.setString(4, job.getSalaryRange());
            preparedStatement.setString(5, job.getLocation());
            preparedStatement.setString(6, job.getCountry());
            preparedStatement.setDouble(7, job.getLatitude());
            preparedStatement.setDouble(8, job.getLongitude());
            preparedStatement.setString(9, job.getWorkType());
            preparedStatement.setInt(10, job.getCompanySize());
            preparedStatement.setString(11, job.getJobPostingDate());
            preparedStatement.setString(12, job.getPreference());
            preparedStatement.setString(13, job.getContactPerson());
            preparedStatement.setString(14, job.getContact());
            preparedStatement.setString(15, job.getJobTitle());
            preparedStatement.setString(16, job.getRole());
            preparedStatement.setString(17, job.getJobPortal());
            preparedStatement.setString(18, job.getJobDescription());
            preparedStatement.setString(19, job.getBenefits());
            preparedStatement.setString(20, job.getSkills());
            preparedStatement.setString(21, job.getResponsibilities());
            preparedStatement.setString(22, job.getCompany());
            preparedStatement.setString(23, job.getCompanyProfileJson());

            // Execute the update
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
            e.printStackTrace(); // Log or handle this error more appropriately in production
        } finally {
            // Close resources in finally block
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }


    public ReadJob getJob(long jobID) {
        ReadJob job = new ReadJob();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            connection = DriverManager.getConnection(URL, User, Password);
            System.out.println("Connection established successfully.");
            String sql = "SELECT * FROM db.cleaned_full_job_description WHERE `Job Id` =" + String.valueOf(jobID);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            result = preparedStatement.executeQuery();

            //IF job found
            if (result.next()) {
                // Read all the elements
                job.setId(result.getLong("Job Id"));
                job.setExperience(result.getString("Experience"));
                job.setQualifications(result.getString("Qualifications"));
                job.setSalaryRange(result.getString("Salary Range"));
                job.setLocation(result.getString("location"));
                job.setCountry(result.getString("Country"));
                job.setLatitude(result.getDouble("latitude"));
                job.setLongitude(result.getDouble("longitude"));
                job.setWorkType(result.getString("Work Type"));
                job.setCompanySize(result.getInt("Company Size"));
                job.setJobPostingDate(result.getString("Job Posting Date"));
                job.setPreference(result.getString("Preference"));
                job.setContactPerson(result.getString("Contact Person"));
                job.setContact(result.getString("Contact"));
                job.setJobTitle(result.getString("Job Title"));
                job.setRole(result.getString("Role"));
                job.setJobPortal(result.getString("Job Portal"));
                job.setJobDescription(result.getString("Job Description"));
                job.setBenefits(result.getString("Benefits"));
                job.setSkills(result.getString("Skills"));
                job.setResponsibilities(result.getString("Responsibilities"));
                job.setCompany(result.getString("Company"));
                job.setCompanyProfileJson(result.getString("Company Profile"));


            }
        }
        //Exception
        catch (SQLException e) {
            System.err.println("Connection failed or query execution error: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (result != null) result.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            }
            //Exception
            catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }


        return job;
    }


    public boolean checkID(long jobID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {
            connection = DriverManager.getConnection(URL, User, Password);
            System.out.println("Connection established successfully.");
            String sql = "SELECT * FROM db.cleaned_full_job_description WHERE `Job Id` =" + String.valueOf(jobID);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            result = preparedStatement.executeQuery();

            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void updateElements(ReadJob job, long ID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish connection
            connection = DriverManager.getConnection(URL, User, Password);
            System.out.println("Connection established successfully.");

            String sql = "UPDATE db.cleaned_full_job_description SET "
                    + "`Job Id` = ?, experience = ?, Qualifications = ?,`Salary Range` = ?, location = ?, Country = ?, "
                    + "latitude = ?, longitude = ?, `Work Type` = ?,`Company Size` = ?, `Job Posting Date` = ?, "
                    + "Preference = ?, `Contact Person` = ?, Contact = ?,`Job Title` = ?, Role = ?, "
                    + "`Job Portal` = ?, `Job Description` = ?, Benefits = ?, Skills = ?, Responsibilities = ?, "
                    + "Company = ?, `Company Profile` = ? "
                    + "WHERE `Job Id` = ?";
            preparedStatement = connection.prepareStatement(sql);

            // Set parameters dynamically
            preparedStatement.setLong(1, job.getId());
            preparedStatement.setString(2, job.getExperience());
            preparedStatement.setString(3, job.getQualifications());
            preparedStatement.setString(4, job.getSalaryRange());
            preparedStatement.setString(5, job.getLocation());
            preparedStatement.setString(6, job.getCountry());
            preparedStatement.setDouble(7, job.getLatitude());
            preparedStatement.setDouble(8, job.getLongitude());
            preparedStatement.setString(9, job.getWorkType());
            preparedStatement.setInt(10, job.getCompanySize());
            preparedStatement.setString(11, job.getJobPostingDate());
            preparedStatement.setString(12, job.getPreference());
            preparedStatement.setString(13, job.getContactPerson());
            preparedStatement.setString(14, job.getContact());
            preparedStatement.setString(15, job.getJobTitle());
            preparedStatement.setString(16, job.getRole());
            preparedStatement.setString(17, job.getJobPortal());
            preparedStatement.setString(18, job.getJobDescription());
            preparedStatement.setString(19, job.getBenefits());
            preparedStatement.setString(20, job.getSkills());
            preparedStatement.setString(21, job.getResponsibilities());
            preparedStatement.setString(22, job.getCompany());
            preparedStatement.setString(23, job.getCompanyProfileJson());
            preparedStatement.setLong(24, ID);


            // Execute the update
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("Record not found for update.");
            }

        } catch (SQLException e) {
            System.err.println("Error updating data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }


    public void delete(long ID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish connection
            connection = DriverManager.getConnection(URL, User, Password);
            System.out.println("Connection established successfully.");

            // Prepare the SQL DELETE query
            String sql = "DELETE FROM db.cleaned_full_job_description WHERE `Job Id` = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, ID);


            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Job with ID " + ID + " was deleted successfully.");
            } else {
                System.out.println("No job found with ID " + ID);
            }

        }
        catch (SQLException e) {
            System.err.println("Error deleting data: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }






    public List<String[]> filter (String title, String country, String type){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String[]> data = new ArrayList<>(); // Initialize data list


        try {
            // Establish connection
            connection = DriverManager.getConnection(URL, User, Password);
            System.out.println("Connection established successfully.");

            String sql = "Select * from db.cleaned_full_job_description"
                    + " WHERE `Job Title` = ? AND Country = ? AND `Work Type` = ?";
            preparedStatement = connection.prepareStatement(sql);

            // Set parameters dynamically
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, country);


            // Execute the update
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String[] row = new String[23];
                row[0] = String.valueOf(resultSet.getLong("Job Id"));
                row[1] = resultSet.getString("Experience");
                row[2] = resultSet.getString("Qualifications");
                row[3] = resultSet.getString("Salary Range");
                row[4] = resultSet.getString("Location");
                row[5] = resultSet.getString("Country");
                row[6] = String.valueOf(resultSet.getDouble("Latitude"));
                row[7] = String.valueOf(resultSet.getDouble("Longitude"));
                row[8] = resultSet.getString("Work Type");
                row[9] = String.valueOf(resultSet.getInt("Company Size"));
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
            }
        }


        catch (SQLException e) {
            System.err.println("Error finding data: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            }
            catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    return data;
    }

    public double[] getJobStatistics() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        double[] jobStatistics = new double[6];
        try {
            // Establish connection
            connection = DriverManager.getConnection(URL, User, Password);
            System.out.println("Connection established successfully.");

            String sql = """
        SELECT 
            AVG(LOWER_SALARY) AS Average_Low_Salary,
            AVG(UPPER_SALARY) AS Average_High_Sal,
            AVG(LOWER_EXPERIENCE) AS Average_Low_Experience,
            AVG(UPPER_EXPERIENCE) AS Average_High_Experience,
            COUNT(*) AS JobCount
        FROM (
            SELECT 
                CAST(REPLACE(SUBSTRING_INDEX(SUBSTRING_INDEX(`Salary Range`, '-', 1), '$', -1), 'K', '') AS UNSIGNED) AS LOWER_SALARY,
                CAST(REPLACE(SUBSTRING_INDEX(SUBSTRING_INDEX(`Salary Range`, '-', -1), '$', -1), 'K', '') AS UNSIGNED) AS UPPER_SALARY,
                CAST(SUBSTRING_INDEX(`Experience`, ' to ', 1) AS UNSIGNED) AS LOWER_EXPERIENCE,
                CAST(SUBSTRING_INDEX(`Experience`, ' to ', -1) AS UNSIGNED) AS UPPER_EXPERIENCE
            FROM db.cleaned_full_job_description
            WHERE `Salary Range` LIKE '$%-%K' AND `Experience` LIKE '% to % Years'
        ) salary_experience_ranges;
        """;

            // Prepare and execute query
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            // Extract the result
            if (resultSet.next()) {
                jobStatistics[0] = resultSet.getDouble("Average_Low_Salary");
                jobStatistics[1] = resultSet.getDouble("Average_High_Sal");
                jobStatistics[2] = resultSet.getDouble("Average_Low_Experience");
                jobStatistics[3] = resultSet.getDouble("Average_High_Experience");
                jobStatistics[4] =  (double) resultSet.getInt("JobCount");
            }
        } catch (SQLException e) {
            System.err.println("Error finding data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return jobStatistics;
    }
}

