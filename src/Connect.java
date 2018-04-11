import java.sql.*;

public class Connect {
    public static void GetStudentInfo(Connection conn, int id){
        try {
            String sql = "EXEC Get_Student_Info @Id = ?";
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
            stmt.close();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void PrintInfo(Connection conn) {
        try {
            String insert = "SELECT StudentID, Name, Phone FROM Students\n" +
                    "WHERE StudentID = ?";
            PreparedStatement stmt = conn.prepareStatement(insert);
            // get data from table 'student'
            int id = 4;
            String name = "duc";
            String phone = "01639967568";

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
            // show data

            // close connection
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
