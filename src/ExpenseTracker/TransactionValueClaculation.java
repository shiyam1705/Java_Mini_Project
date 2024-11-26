package ExpenseTracker;

import java.util.List;

public class TransactionValueClaculation {

    public static double getTotalIncomes(List<Transaction> transactions) {
        double totalIncome = 0.0;
        for (Transaction transaction : transactions) {
            if ("Income".equals(transaction.getType())) {
                totalIncome += transaction.getAmount();
            }
        }
        return totalIncome;
    }

    public static double getTotalExpenses(List<Transaction> transactions) {
        double totalExpenses = 0.0;
        for (Transaction transaction : transactions) {
            if ("Expense".equals(transaction.getType())) {
                totalExpenses += transaction.getAmount();
            }
        }
        return totalExpenses;
    }

    public static double getTotalValue(List<Transaction> transactions) {
        double totalIncome = getTotalIncomes(transactions);
        double totalExpenses = getTotalExpenses(transactions);
        return totalIncome - totalExpenses;
    }
}