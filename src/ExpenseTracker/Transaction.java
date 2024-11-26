package ExpenseTracker;

public class Transaction {
    private int id;
    private String date;
    private String type;
    private String description;
    private double amount;

    // Constructor
    public Transaction(int id, String date, String type, String description, double amount) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.description = description;
        this.amount = amount;
    } 
// Getters
    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
  
}