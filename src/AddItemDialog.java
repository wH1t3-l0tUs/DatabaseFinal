import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

public class AddItemDialog extends JDialog {
    Connection conn;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField idField;
    private JTextField nameField;
    private JTextField amountField;
    private JTextField priceField;
    private JTextField unitField;

    public AddItemDialog(Connection conn) {
        this.conn = conn;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

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
    }

    private boolean fieldsEmpty() {
        return idField.getText().isEmpty() || nameField.getText().isEmpty() || amountField.getText().isEmpty()
                || priceField.getText().isEmpty() || unitField.getText().isEmpty();
    }

    private void onOK() {
        if (fieldsEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "All fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
        }

        String SQL = "INSERT INTO Item(IdItem, NameItem, AmountInStock, UnitPrice, Unit)" +
                     "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement insertStmt = conn.prepareStatement(SQL);
            insertStmt.setString(1, idField.getText());
            insertStmt.setString(2, nameField.getText());
            insertStmt.setInt(3, Integer.parseInt(amountField.getText()));
            insertStmt.setInt(4, Integer.parseInt(priceField.getText()));
            insertStmt.setString(5, unitField.getText());
            insertStmt.execute();
        } catch (SQLException e){
            e.printStackTrace();
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null,
                    "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void createAndShowDialog(Connection conn) {
        AddItemDialog dialog = new AddItemDialog(conn);
        dialog.pack();
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setSize(500,400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void createUIComponents() {
        amountField = new JNumberTextField();
        priceField = new JNumberTextField();
    }
}
