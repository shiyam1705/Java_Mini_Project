package ExpenseTracker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public static List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = DataBaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM transaction_table");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String date = rs.getString("date");
                String type = rs.getString("transaction_type");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");

                // Ensure Transaction constructor and getters are correctly defined
                transactions.add(new Transaction(id, date, type, description, amount));
            }
        } catch (SQLException ex) {
            System.out.println("TransactionDAO - Error: " + ex.getMessage());
        }
        return transactions;
    }
    

    public static void removeTransaction(int transactionId) {
        System.out.println("Attempting to remove transaction with ID: " + transactionId);
        try (Connection connection = DataBaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM transaction_table WHERE Id = ?")) {
    
            ps.setInt(1, transactionId);
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Transaction Removed");
            } else {
                System.out.println("No transaction found with ID: " + transactionId);
            }
        } catch (SQLException ex) {
            System.out.println("TransactionDAO - Error: " + ex.getMessage());
        }
    }
    public static void addTransaction(Transaction transaction) {
        try (Connection connection = DataBaseConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO transaction_table (date, transaction_type, description, amount) VALUES (?, ?, ?, ?)")) {

            ps.setString(1, transaction.getDate());
            ps.setString(2, transaction.getType());
            ps.setString(3, transaction.getDescription());
            ps.setDouble(4, transaction.getAmount());
            ps.executeUpdate();
            System.out.println("Transaction inserted successfully.");
        } catch (SQLException ex) {
            System.out.println("TransactionDAO - Error: " + ex.getMessage());
        }
    }
    public void updateTransaction(Transaction transaction) {
        // SQL statement for updating the main transaction table
        String sqlMain = "UPDATE transaction_table SET date = ?, type = ?, description = ?, amount = ? WHERE id = ?";
        String categoryTableName = transaction.getDescription().toLowerCase() + "_transactions"; // Construct table name for category
    
        try (Connection connection = DataBaseConnector.getConnection();
             PreparedStatement psMain = connection.prepareStatement(sqlMain);
             PreparedStatement psCategory = connection.prepareStatement("UPDATE " + categoryTableName + " SET Date = ?, Description = ?, Amount = ? WHERE ID = ?")) {
    
            // Update main transaction table
            psMain.setString(1, transaction.getDate());
            psMain.setString(2, transaction.getType()); // Ensure this matches the actual column name in the database
            psMain.setString(3, transaction.getDescription());
            psMain.setDouble(4, transaction.getAmount());
            psMain.setInt(5, transaction.getId());
            int rowsAffectedMain = psMain.executeUpdate();
    
            if (rowsAffectedMain > 0) {
                System.out.println("Main transaction updated successfully.");
            } else {
                System.out.println("Failed to update transaction in the main table.");
            }
    
            // Update category-specific table
            psCategory.setString(1, transaction.getDate());
            psCategory.setString(2, transaction.getDescription());
            psCategory.setDouble(3, transaction.getAmount());
            psCategory.setInt(4, transaction.getId());
            int rowsAffectedCategory = psCategory.executeUpdate();
    
            if (rowsAffectedCategory > 0) {
                System.out.println("Transaction updated successfully in " + categoryTableName);
            } else {
                System.out.println("Failed to update transaction in " + categoryTableName);
            }
        } catch (SQLException e) {
            System.out.println("Error updating transaction: " + e.getMessage());
        }
    }
}