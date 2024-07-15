package StudentGrader;
import java.sql.* ;
import java.util.ArrayList;
import java.util.List;

public class BackendDB {
    static Connection link = null ;
    private static Statement statement = null ;
    private static ResultSet result = null ;
    public static void connect()   {
        String driver = "com.mysql.cj.jdbc.Driver" ;
        String url = "jdbc:mysql://localhost:3306/student" ; // student is a database. You can choose any database
        String username = "root" ;
        String password = "agastya" ;
        try {
            Class.forName(driver);
            link = DriverManager.getConnection(url, username, password);

            if (link != null) {
                System.out.println("Connection established successfully !");
            }

        }
        catch(Exception e) { System.out.println(e); }
    }

    private static void getStatementReady() {
        try {
            statement = link.createStatement() ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertRecords(String[] record) throws SQLException, ClassNotFoundException  {
        getStatementReady();
        String query = "INSERT INTO student_record VALUES (" ;
        for(String entry : record) {query = query.concat(entry) ;}
        query = query + ')' ;
        statement.addBatch(query);
        statement.executeBatch() ;
        statement.close() ;

    }
    public static List<String> retrieveRecord (String primaryKey) throws SQLException, ClassNotFoundException{
        getStatementReady();
        String query = "SELECT * FROM student_record WHERE rollno = " + "'" + primaryKey + "'" ;
        result = statement.executeQuery(query) ;
        List<String> outputRecord = new ArrayList<>() ;
        while (result.next()) {
                for (int i = 1; i <= 8; i++) {
                    outputRecord.add(result.getString(i));
                }
            }
        statement.close() ;
        return outputRecord;
    }
    public static List<List<String>> retrieveAllRecords() throws SQLException, ClassNotFoundException{

        getStatementReady();
        String query = "SELECT * FROM student_record" ;
        result = statement.executeQuery(query) ;
        List<List<String>> outputRecord = new ArrayList<>() ;
        while(result.next()) {
            List<String>rowData = new ArrayList<>() ;
            for (int i = 1; i <= 8; i++) {
                rowData.add(result.getString(i));
            }
            outputRecord.add(rowData) ;
        }
        statement.close() ;
        return outputRecord ;
    }
    public static void deleteRecords(String rollno) throws SQLException, ClassNotFoundException {
        getStatementReady();
        String query = "DELETE FROM student_record where rollno = " + "'" +rollno + "'" ;
        statement.addBatch(query);
        statement.executeBatch() ;
        statement.close() ;
    }
}
