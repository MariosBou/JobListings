import java.util.List;

abstract public class Database {

    //Attributes
    protected static final String URL = "jdbc:mysql://127.0.0.1:3306/db";
    protected static final String User = "UseYourOwnUsername";
    protected static final String Password = "UseYourOwnPassword";

    public abstract ReadJob printDataFromDatabase(long jobID);

    public abstract void addDataToDatabase(ReadJob job);

    public abstract  boolean checkID(long id);

    public abstract  void updateElements(ReadJob job,long ID);

    public abstract void delete(long ID);

    public abstract List<String[]> filter (String title, String country, String type);

    public abstract double[] getJobStatistics();

}
