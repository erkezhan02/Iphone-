import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseConnection {
   public Connection connect_to_db(String dbname, String user, String pass) {
      Connection conn = null;
      try {
         Class.forName("org.postgresql.Driver");
         conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
         if (conn != null) {
            System.out.println("Connection Established");
         } else {
            System.out.println("Connection failed");
         }
      } catch (Exception e) {
         System.out.println(e);
      }
      return conn;
   }

   public void createTable(Connection conn, String table_name) {
      String query = "CREATE TABLE IF NOT EXISTS " + table_name + " (iphoneid SERIAL, " + "name VARCHAR(200), "
              + "price DECIMAL(10, 2), " + "PRIMARY KEY (iphoneid)" + ");";
      try (PreparedStatement statement = conn.prepareStatement(query)) {
         statement.execute();
         System.out.println("Table created");
      } catch (SQLException e) {
         System.out.println(e);
      }
   }

   public void insertRow(Connection conn, String table_name, String name, String price) {
      String query = "INSERT INTO " + table_name + "(name, price) VALUES (?, ?)";
      try (PreparedStatement statement = conn.prepareStatement(query)) {
         statement.setString(1, name);
         statement.setBigDecimal(2, new BigDecimal(price));
         statement.executeUpdate();
         System.out.println("Row Inserted");
      } catch (SQLException e) {
         System.out.println(e);
      }
   }

   public void displayRows(Connection conn, String table_name) {
      String query = "SELECT * FROM " + table_name;
      try (PreparedStatement statement = conn.prepareStatement(query);
           ResultSet resultSet = statement.executeQuery()) {
         while (resultSet.next()) {
            int id = resultSet.getInt("iphoneid");
            String name = resultSet.getString("name");
            String price = resultSet.getString("price");
            System.out.println("ID: " + id + ", Name: " + name + ", Price: " + price);
         }
      } catch (SQLException e) {
         System.out.println(e);
      }
   }

   public void deleteRow(Connection conn, String table_name, int id) {
      String query = "DELETE FROM " + table_name + " WHERE iphoneid = ?";
      try (PreparedStatement statement = conn.prepareStatement(query)) {
         statement.setInt(1, id);
         int rowsDeleted = statement.executeUpdate();
         if (rowsDeleted > 0) {
            System.out.println("Row deleted successfully");
         } else {
            System.out.println("Row with specified ID not found");
         }
      } catch (SQLException e) {
         System.out.println(e);
      }
   }

   public void updateRow(Connection conn, String table_name, int id, String name, String price) {
      String query = "UPDATE " + table_name + " SET name = ?, price = ? WHERE iphoneid = ?";
      try (PreparedStatement statement = conn.prepareStatement(query)) {
         statement.setString(1, name);
         statement.setBigDecimal(2, new BigDecimal(price));
         statement.setInt(3, id);
         int rowsUpdated = statement.executeUpdate();
         if (rowsUpdated > 0) {
            System.out.println("Row updated successfully");
         } else {
            System.out.println("Row with specified ID not found");
         }
      } catch (SQLException e) {
         System.out.println(e);
      }
   }
}
