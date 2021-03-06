package s09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import s08.Coder;

public class PreparedSelector {
    private static final String URL = "jdbc:mysql://localhost:3306/me?serverTimezone=Europe/Rome";
    private static final String USER = "me";
    private static final String PASSWORD = "password";

    private static final String CODERS_BY_SALARY = "SELECT first_name, last_name, salary FROM coders "
            + "WHERE salary >= ? ORDER BY 3 DESC";

    public List<Coder> getCodersBySalary(double lower) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); //
                PreparedStatement prepStmt = conn.prepareStatement(CODERS_BY_SALARY)) {
            prepStmt.setDouble(1, lower);

            System.out.println("I'm about to execute " + prepStmt);

            ResultSet rs = prepStmt.executeQuery();

            List<Coder> results = new ArrayList<>();
            while (rs.next()) {
                results.add(new Coder(rs.getString(1), rs.getString(2), rs.getInt(3)));
            }

            return results;
        }
    }

    public static void main(String[] args) {
        try {
            PreparedSelector sample = new PreparedSelector();
            System.out.println("Rich coders are: " + sample.getCodersBySalary(6000));
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }
}