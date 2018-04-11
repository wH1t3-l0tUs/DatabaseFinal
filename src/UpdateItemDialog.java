import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateItemDialog extends JDialog {
    Connection conn;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable itemTable;
    private JButton modifyButton;
    private JButton addButton;

    public UpdateItemDialog(Connection conn) {
        this.conn = conn;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddItemDialog.createAndShowDialog(conn);
                updateTable();
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void updateTable() {
        int rowSize = 0;
        String[] columnNames = {"ID", "Name", "Amount in stock", "Unit Price", "Unit"};// column headers
        Statement stmt;
        ResultSet rs;
        String[][] itemData = null; // using a 2D array of strings to store data

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM ITEM");
            rs.next();
            rowSize = rs.getInt(1);
            rs = stmt.executeQuery("SELECT * FROM ITEM");
            itemData = new String[rowSize][5];
            for (int i=0;i<rowSize;i++) {
                rs.next();
                itemData[i][0] = rs.getString(1);
                itemData[i][1] = rs.getString(2);
                itemData[i][2] = Integer.toString(rs.getInt(3));
                itemData[i][3] = Integer.toString(rs.getInt(4));
                itemData[i][4] = rs.getString(5);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DefaultTableModel model = new DefaultTableModel(itemData,columnNames) { //load data from 2D array to a table model
            public boolean isCellEditable(int row, int column){
                return false;
            } // disable cell editing
        };
        itemTable.setModel(model); // set the model of the JTable
        itemTable.getColumnModel().getColumn(0).setPreferredWidth(60);  // adjust column size
        itemTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        itemTable.getColumnModel().getColumn(2).setPreferredWidth(65);
        itemTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        itemTable.getColumnModel().getColumn(4).setPreferredWidth(60);

    }

    private void createUIComponents() {
        itemTable = new JTable(); // initialize the JTable
        updateTable(); // refresh table
        itemTable.getTableHeader().setReorderingAllowed(false);
    }

    public static void createAndShowDialog(Connection conn) {
        UpdateItemDialog dialog = new UpdateItemDialog(conn);
        dialog.pack();
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setSize(500,400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
