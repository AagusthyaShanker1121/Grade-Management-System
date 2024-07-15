package StudentGrader;
import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.* ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.sql.SQLException;
import java.util.List;

import static StudentGrader.BackendDB.retrieveAllRecords;


public class DisplayAllRecords {
    static JPanel displayPanel , buttonPanel;
    DefaultTableModel tableModel ;
    JTable table ;
    JButton back , reload;
    DisplayAllRecords() throws SQLException, ClassNotFoundException {
        // creating a table and configuring its header
        Object[] columnNames = {" Roll Number " , " Name " , " Age " , " Mathematics Score " , " Science Score " , " English Score " , " Hindi Score " , " Social Science Score"} ;
        tableModel = new DefaultTableModel(columnNames , 0 );
        table = new JTable(tableModel) ;
        // Configuring the table header to distinguish from table data by setting the font style to bold
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(tableHeader.getFont().deriveFont(Font.BOLD)); // Optional: Customize the font style
        JScrollPane scrollPane = new JScrollPane(table) ;



        // configuring the back button
        back = new JButton("Home");
        back.setFocusable(false);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window.cardLayout.show(Window.base, "home");
            }
        });
        back.setPreferredSize(new Dimension(200, 35));
        reload = new JButton("Reload Data");
        reload.setFocusable(false);
        displayRecordOnTable();
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTable() ;
                try {
                    displayRecordOnTable();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        reload.setPreferredSize(new Dimension(200, 35));

        // creating a button panel to hold button reload data and home
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER    ));
        buttonPanel.setSize(1148 , 50) ;
        buttonPanel.add(reload) ;
        buttonPanel.add(back) ;

        // Creating parentPanel to hold table contents
        displayPanel = new JPanel() ;
        displayPanel.setLayout(new BoxLayout(displayPanel , BoxLayout.PAGE_AXIS)) ;
        displayPanel.setSize(1148,657) ;
        displayPanel.add(scrollPane) ;
//        displayPanel.add(back) ;
//        displayPanel.add(reload) ;
        displayPanel.add(buttonPanel) ;

    }

    private void displayRecordOnTable() throws SQLException, ClassNotFoundException {
        BackendDB.connect() ;
        // Capturing all records
        List<List<String>> records ;
        records = retrieveAllRecords() ;
        // iterating the list to enter records into the database
        for(List<String> row : records) {
            String[] rowData = new String[8] ;
            int i= 0 ;
            for(String data : row) {
                rowData[i] = data;
                i++ ;
            }
            tableModel.addRow(rowData) ;
        }
    }
    private void resetTable() {
        tableModel.setRowCount(0);
    }
}
