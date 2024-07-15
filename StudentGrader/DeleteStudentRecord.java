package StudentGrader;
import javax.swing.* ;
import java.awt.* ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

public class DeleteStudentRecord implements ActionListener{
    JPanel base , p1 , outputPanel;
    JTextField rollno_input ;

    JButton delete ;
    static JFrame frame ;
    DeleteStudentRecord() {
        // Creating heading label
        JLabel heading = new JLabel("DELETE STUDENT RECORD." , SwingConstants.CENTER);
        heading.setFont(new Font("MV Boli" , Font.BOLD , 35));


        // creating a panel to consist of a label to display "Rollno" , textArea , button to display record
        p1 = new JPanel(new FlowLayout()) ;
        // creating panel components
        // 1) rollno label
        JLabel rollno = new JLabel("Roll no: ");
        rollno.setFont(new Font("MV Boli" , Font.BOLD , 25));
        // 2) textArea to take rollno input from user
        rollno_input = new JTextField(25) ;
        // 3) search button
        delete = new JButton("Delete Record");
        delete.setFocusable(false) ;
        delete.addActionListener(this) ;
        delete.setPreferredSize(new Dimension(200 , 30));

        p1.add(rollno) ;
        p1.add(rollno_input) ;
        p1.add(delete) ;

        // creating output panel
        outputPanel = new JPanel() ;

        // Configure base panel
        base = new JPanel(new GridLayout(3 , 1)) ;
        base.setSize(1000 , 500) ;
        base.add(heading) ;
        base.add(p1) ;
        base.add(outputPanel) ;
        // creating a frame
        frame = new JFrame("Delete Records") ;
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE) ;
        frame.setLayout(new BorderLayout());
        frame.add(base , BorderLayout.CENTER) ;
        frame.pack() ;
        // adding window listener to check whether the window is closed or not
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Window.deleteStudentRecord.setEnabled(true) ;
            }
        });
        frame.setVisible(true) ;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // First retrieve info about the student to check whether the person is available or not
        List<String> data ;
        try {
            data = BackendDB.retrieveRecord(rollno_input.getText()) ;
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        if(!data.isEmpty()) {
            int options = JOptionPane.showConfirmDialog(frame, "Do you really want to Delete records ?", "Delete Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
            if (options == 0) {
                try {
                    BackendDB.deleteRecords(rollno_input.getText());
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(frame, "Records Deleted Succesfully.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }

        }
        else {
            JOptionPane.showMessageDialog(frame , "Student Does not Exists." ,"Information" ,JOptionPane.INFORMATION_MESSAGE);
        }


        // Confirm from user whether they want to delete the record of a particular student or not
        System.out.println("Records deleted successfully.");
    }
}
