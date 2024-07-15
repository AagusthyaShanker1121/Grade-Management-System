package StudentGrader;

import java.sql.SQLException;
import java.util.List;

import static StudentGrader.BackendDB.retrieveAllRecords;

public class test {
    public static void main(String[] args) {
        BackendDB.connect() ;
        List<List<String>> records;
        try {
            records = retrieveAllRecords();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // iterating the list to enter records into the database
//        for (List<String> row : records) {
//            String[] rowData = new String[8];
//            int i = 0;
//            for (String data : row) {
//                rowData[i] = data;
//                i++;
//            }
//            tableModel.addRow(rowData);
//        }
        for(List<String> row : records) {
            for(String data : row) {
                System.out.print(data + " " );
            }
            System.out.println();
        }

        try {
            BackendDB.link.close() ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
