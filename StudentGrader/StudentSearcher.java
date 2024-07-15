package StudentGrader;
import javax.swing.* ;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.* ;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

import static StudentGrader.BackendDB.retrieveRecord;


public class StudentSearcher implements ActionListener{
    JPanel base , p1 , outputPanel;
    JTextField rollno_input ;

    JButton search, reset;
    static JFrame frame ;
    JScrollPane scrollPane ;
    JTable table ;
    DefaultTableModel tableModel ;
    StudentSearcher() {
        // Creating heading label
        JLabel heading = new JLabel("SEARCH STUDENT RECORD." , SwingConstants.CENTER);
        heading.setFont(new Font("MV Boli" , Font.BOLD , 35));


        // creating a panel to consist of a label to display "Rollno" , textArea , button to display record, reset button , and a label to display whether a record is prsent or not
        p1 = new JPanel(new FlowLayout()) ;
        // creating panel components
        // 1) rollno label
        JLabel rollno = new JLabel("Roll no: ");
        rollno.setFont(new Font("MV Boli" , Font.BOLD , 25));
        // 2) textArea to take rollno input from user
        rollno_input = new JTextField(25) ;
        // 3) search button
        search = new JButton("Search Student");
        search.setFocusable(false) ;
        search.addActionListener(this) ;
        search.setPreferredSize(new Dimension(200 , 30));
        //4) Reset button to reset the row data
        reset =  new JButton("Reset Table");
        reset.setFocusable(false) ;
        reset.addActionListener(this) ;
        reset.setPreferredSize(new Dimension(200, 30)) ;
        p1.add(rollno) ;
        p1.add(rollno_input) ;
        p1.add(search) ;
        p1.add(reset) ;

        // Creating a table to display the search records
        Object[] columnNames = {" Roll Number " , " Name " , " Age " , " Mathematics Score " , " Science Score " , " English Score " , " Hindi Score " , " Social Science Score"} ;
        tableModel = new DefaultTableModel(columnNames , 0 );
        table = new JTable(tableModel) ;
        // Configuring the table header to distinguish from table data by setting the font style to bold
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(tableHeader.getFont().deriveFont(Font.BOLD)); // Optional: Customize the font style

        // creating output panel for displaying records
        outputPanel = new JPanel() ;
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.PAGE_AXIS)) ;
        outputPanel.setPreferredSize(new Dimension(1000 , 400));
        // adding the table to scroll pane to make it scrollable
        scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeaderView(tableHeader);
        scrollPane.setPreferredSize(new Dimension(1000 , 400));
        // Adding the scroll pane to the outputPanel
        outputPanel.add(scrollPane) ;

        // Configure base panel
        base = new JPanel(new GridLayout(3, 1)) ;
        base.setSize(500, 400) ;
        base.add(heading) ;
        base.add(p1) ;
        base.add(outputPanel) ;
        // creating a frame
        frame = new JFrame("Student Searcher") ;
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE) ;
        frame.setLayout(new BorderLayout());
        frame.add(base , BorderLayout.CENTER) ;
        frame.setSize(1100 , 400) ;
        // adding window listener to check whether the window is closed or not
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Window.searchStudentRecord.setEnabled(true) ;
            }
        });
        frame.setVisible(true) ;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            // display on output label
//            System.out.println("Records Displayed.");
            // run a retrieve function and capture data into a new list
            List<String> searchResult;
            try {
                searchResult = retrieveRecord(rollno_input.getText());
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            if(!searchResult.isEmpty()) {
                String[] tableData = new String[8] ;
                int i = 0 ;
                for(String fields : searchResult) {
                    tableData[i] = fields ;
                    i++ ;
                }
                displayRecordOnTable(tableData);
            }
            else {
                JOptionPane.showMessageDialog(frame, "No Records Found", "Information", JOptionPane.INFORMATION_MESSAGE);

            }

        }
        // Configuring the functionality of reset button
        if(e.getSource() == reset) {
            tableModel.setRowCount(0);
        }
    }
    private void displayRecordOnTable(String[] tableData) {
        tableModel.addRow(tableData);
    }

}



