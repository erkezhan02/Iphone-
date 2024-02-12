import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();
        try (Scanner scanner = new Scanner(System.in)) {
            Connection conn = db.connect_to_db("Iphone", "postgres", "12345");
            db.createTable(conn, "Iphone");

            while (true) {
                System.out.println("Choose an option:");
                System.out.println("1. Insert new iPhone");
                System.out.println("2. Display all iPhones");
                System.out.println("3. Delete iPhone");
                System.out.println("4. Update iPhone");
                System.out.println("5. Exit");

                int option = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (option) {
                    case 1:
                        System.out.println("Enter iPhone name:");
                        String name = scanner.nextLine();
                        System.out.println("Enter iPhone price:");
                        String price = scanner.nextLine();
                        db.insertRow(conn, "Iphone", name, price);
                        break;
                    case 2:
                        System.out.println("All iPhones:");
                        db.displayRows(conn, "Iphone");
                        break;
                    case 3:
                        System.out.println("Enter iPhone ID to delete:");
                        int idToDelete = scanner.nextInt();
                        db.deleteRow(conn, "Iphone", idToDelete);
                        break;
                    case 4:
                        System.out.println("Enter iPhone ID to update:");
                        int idToUpdate = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.println("Enter new name for iPhone:");
                        String newName = scanner.nextLine();
                        System.out.println("Enter new price for iPhone:");
                        String newPrice = scanner.nextLine();
                        db.updateRow(conn, "Iphone", idToUpdate, newName, newPrice);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}