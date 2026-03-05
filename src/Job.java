public class Job {

    // General job attributes
    private long id;
    private String jobTitle;
    private String location;
    private String country;
    private double latitude;
    private double longitude;
    private String workType;
    private String salaryRange;
    private String company;
    private int companySize;

    // Getters and setters for general attributes
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getWorkType() { return workType; }
    public void setWorkType(String workType) { this.workType = workType; }

    public String getSalaryRange() { return salaryRange; }
    public void setSalaryRange(String salaryRange) { this.salaryRange = salaryRange; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public int getCompanySize() { return companySize; }
    public void setCompanySize(int companySize) { this.companySize = companySize; }
}
