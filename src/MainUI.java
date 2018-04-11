import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class MainUI {
    Connection conn;

    private JPanel panel1;
    private JButton updateItemButton;

    public MainUI(Connection conn) {
        this.conn = conn;
        updateItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateItemDialog.createAndShowDialog(conn);
            }
        });
    }

    public static void createAndShowUI(Connection conn) {
        JFrame frame = new JFrame("MainUI");
        frame.setContentPane(new MainUI(conn).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400,200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

