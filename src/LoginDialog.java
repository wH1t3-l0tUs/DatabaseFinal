import sun.applet.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;


public class LoginDialog extends JDialog {
    private static String DB_URL = "jdbc:sqlserver://localhost;"
            + "databaseName=DB_SQL;"
            + "integratedSecurity=true";

    private JPanel contentPane;
    private JButton buttonOK;
    private JPasswordField passwordField;
    private JTextField usernameField;

    public LoginDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

    }

    private void onOK() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, usernameField.getText(), new String(passwordField.getPassword()));
            MainUI.createAndShowUI(conn);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Incorrect username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void createAndShowDialog() {
        LoginDialog dialog = new LoginDialog();
        dialog.setTitle("Database login");
        dialog.pack();
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        dialog.addWindowListener(new WindowAdapter() {
            @Override public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
