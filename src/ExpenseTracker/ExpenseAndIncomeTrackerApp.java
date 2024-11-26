package ExpenseTracker;
import ExpenseTracker.src.Login;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class ExpenseAndIncomeTrackerApp {
    private JSpinner yearSpinner;
    private JSpinner monthSpinner;
    private JSpinner daySpinner;
    private JLabel incomeLabel;
    private JLabel expenseLabel;
    private JFrame frame;
    private JPanel titleBar;
    private JLabel titleLabel;
    private JLabel closeLabel;
    private JLabel minimizeLabel;
    private JPanel dashboardPanel;
    private JPanel buttonsPanel;
    private JButton addTransactionButton;
    private JButton removeTransactionButton;
    private JButton calculateMonthlyButton;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private double totalAmount = 0.0;
    private boolean isDragging = false;
    private Point mouseOffset;
    private JButton updateTransactionButton;
    private JButton logoutButton;

    public ExpenseAndIncomeTrackerApp() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(52, 73, 94)));

        titleBar = new JPanel();
        titleBar.setLayout(null);
        titleBar.setBackground(new Color(52, 73, 94));
        titleBar.setPreferredSize(new Dimension(frame.getWidth(), 30));
        frame.add(titleBar, BorderLayout.NORTH);

        titleLabel = new JLabel("AURA-THE EXPENSE TRACKER");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        titleLabel.setBounds(10, 0, 250, 30);
        titleBar.add(titleLabel);

        closeLabel = new JLabel("x");
        closeLabel.setForeground(Color.WHITE);
        closeLabel.setFont(new Font("Arial", Font.BOLD, 17));
        closeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        closeLabel.setBounds(frame.getWidth() - 50, 0, 30, 30);
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeLabel.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeLabel.setForeground(Color.white);
            }
        });

        titleBar.add(closeLabel);

        minimizeLabel = new JLabel("-");
        minimizeLabel.setForeground(Color.WHITE);
        minimizeLabel.setFont(new Font("Arial", Font.BOLD, 17));
        minimizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        minimizeLabel.setBounds(frame.getWidth() - 80, 0, 30, 30);
        minimizeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        minimizeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setState(JFrame.ICONIFIED);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                minimizeLabel.setForeground(Color.yellow);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                minimizeLabel.setForeground(Color.white);
            }
        });

        titleBar.add(minimizeLabel);

        titleBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isDragging = true;
                mouseOffset = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging = false;
            }
        });

        titleBar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    Point newLocation = e.getLocationOnScreen();
                    newLocation.translate(-mouseOffset.x, -mouseOffset.y);
                    frame.setLocation(newLocation);
                }
            }
        });

        // Create and set up the dashboard panel
        dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        dashboardPanel.setBackground(new Color(211, 211,        211));
        frame.add(dashboardPanel, BorderLayout.CENTER);

        // Create and set up buttons panel
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5, 1, 5, 5)); // Change to 5 rows to accommodate the new button
        
        addTransactionButton = new JButton("Add Transaction");
        addTransactionButton.setBackground(new Color(41, 128, 150));
        addTransactionButton.setForeground(Color.WHITE);
        addTransactionButton.setFocusPainted(false);
        addTransactionButton.setBorderPainted(false);
        addTransactionButton.setFont(new Font("Arial", Font.BOLD, 14));
        addTransactionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        removeTransactionButton = new JButton("Remove Transaction");
        removeTransactionButton.setBackground(new Color(231, 76, 60));
        removeTransactionButton.setForeground(Color.WHITE);
        removeTransactionButton.setFocusPainted(false);
        removeTransactionButton.setBorderPainted(false);
        removeTransactionButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeTransactionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Create the Update Transaction button
        updateTransactionButton = new JButton("Update Transaction");
        updateTransactionButton.setBackground(new Color(255, 192, 203)); // Pink color
        updateTransactionButton.setForeground(Color.WHITE);
        updateTransactionButton.setFocusPainted(false);
        updateTransactionButton.setBorderPainted(false);
        updateTransactionButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateTransactionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        calculateMonthlyButton = new JButton("Calculate Monthly Expense");
        calculateMonthlyButton.setBackground(new Color(46, 204, 113));
        calculateMonthlyButton.setForeground(Color.WHITE);
        calculateMonthlyButton.setFocusPainted(false);
        calculateMonthlyButton.setBorderPainted(false);
        calculateMonthlyButton.setFont(new Font("Arial", Font.BOLD, 14));
        calculateMonthlyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(128, 0, 128)); // Purple
        logoutButton.setForeground(Color.WHITE); // White text
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(true); // Enable border painting
        logoutButton.setBorder(BorderFactory.createLineBorder(Color.white, 2)); // Set border color and thickness
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add buttons to the panel
        buttonsPanel.add(addTransactionButton);
        buttonsPanel.add(removeTransactionButton);
        buttonsPanel.add(updateTransactionButton); // Add Update button here
        buttonsPanel.add(calculateMonthlyButton);
        buttonsPanel.add(logoutButton);
        dashboardPanel.add(buttonsPanel);
        String[] columnNames = {"ID", "Date", "Type", "Description", "Amount"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create the transaction table
        transactionTable = new JTable(tableModel);
        transactionTable.setBackground(Color.WHITE);
        transactionTable.setGridColor(new Color(200, 200, 200));
        transactionTable.setRowHeight(25);
        transactionTable.getTableHeader().setBackground(new Color(52, 73, 94));
        transactionTable.getTableHeader().setForeground(Color.WHITE);
        transactionTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // Add custom cell renderer for coloring based on type
        transactionTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String type = (String) table.getModel().getValueAt(row, 2); // Column 2 contains the type

                if (type.equals("Expense")) {
                    c.setForeground(new Color(231, 76, 60)); // Red color for expense
                } else if (type.equals("Income")) {
                    c.setForeground(new Color(46, 204, 113)); // Green color for income
                } else {
                    c.setForeground(Color.BLACK); // Default color
                }

                // Maintain selection highlighting
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                } else {
                    c.setBackground(table.getBackground());
                }

                return c;
            }
        });

        // Create scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        dashboardPanel.add(scrollPane);

        // Create total amount panel
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new GridLayout(3, 1)); // Change to GridLayout with 3 rows
        totalPanel.setBackground(new Color(211, 211, 211));

        // Total Balance Label
        totalLabel = new JLabel("Total Balance: ₹" + String.format("%.2f", totalAmount));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(new Color(52, 73, 94));
        totalPanel.add(totalLabel);

        // Total Income Label
        incomeLabel = new JLabel("Total Income: ₹0.00");
        incomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        incomeLabel.setForeground(new Color(46, 204, 113)); // Green color for income
        totalPanel.add(incomeLabel);

        // Total Expense Label
        expenseLabel = new JLabel("Total Expense: ₹0.00");
        expenseLabel.setFont(new Font("Arial", Font.BOLD, 16));
        expenseLabel.setForeground(new Color(231, 76, 60)); // Red color for expense
        totalPanel.add(expenseLabel);

        dashboardPanel.add(totalPanel);

        // Add action listeners for buttons
        addTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddTransactionDialog();
            }
        });
        updateTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateTransactionDialog();
            }
        });

        calculateMonthlyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMonthlyExpenseDialog();
            }
        });

        removeTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedTransaction();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to logout?",
                        "Logout Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    frame.dispose(); // Close the current window
                    new Login(); // Open the Login window
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
    private void removeSelectedTransaction() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow != -1) {
            // Confirm deletion
            int choice = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to delete this transaction?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);
    
            if (choice != JOptionPane.YES_OPTION) {
                return; // User chose not to delete
            }
    
            // Get the transaction details
            int transactionId = (int) tableModel.getValueAt(selectedRow, 0); // Assuming ID is in column 0
            String type = (String) tableModel.getValueAt(selectedRow, 2); // Assuming Type is in column 2
            String description = (String) tableModel.getValueAt(selectedRow, 3); // Assuming Description is in column 3
            double amount = Double.parseDouble((String) tableModel.getValueAt(selectedRow, 4)); // Assuming Amount is in column 4
            
            // Remove from the main transaction table
            try (Connection connection = DataBaseConnector.getConnection();
                 PreparedStatement psMain = connection.prepareStatement(
                         "DELETE FROM transaction_table WHERE ID = ?")) {
                psMain.setInt(1, transactionId);
                psMain.executeUpdate();
                System.out.println("Transaction removed successfully from the main table.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Error removing transaction from main table: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Remove from the category-specific table
            String categoryTableName = description.toLowerCase() + "_transactions"; // Construct table name
            try (Connection connection = DataBaseConnector.getConnection();
                 PreparedStatement psCategory = connection.prepareStatement(
                         "DELETE FROM " + categoryTableName + " WHERE ID = ?")) {
                psCategory.setInt(1, transactionId); // Use the same ID
                int rowsAffected = psCategory.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Transaction removed successfully from " + categoryTableName);
                } else {
                    JOptionPane.showMessageDialog(frame, "No transaction found in " + categoryTableName + " with ID: " + transactionId,
                            "Deletion Warning", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Error removing transaction from category table: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Update the total amount based on transaction type
            if (type.equals("Income")) {
                totalAmount -= amount; // Subtract income amount from total
                double currentIncome = Double.parseDouble(incomeLabel.getText().replace("Total Income: ₹", ""));
                currentIncome -= amount; // Subtract from total income
                incomeLabel.setText("Total Income: ₹" + String.format("%.2f", currentIncome));
            } else { // Expense
                totalAmount += amount; // Add expense amount back to total
                double currentExpense = Double.parseDouble(expenseLabel.getText().replace("Total Expense: ₹", ""));
                currentExpense -= amount; // Subtract from total expense
                expenseLabel.setText("Total Expense: ₹" + String.format("%.2f", currentExpense));
            }
    
            // Update the total balance label
            totalLabel.setText("Total Balance: ₹" + String.format("%.2f", totalAmount));
            
            // Remove the row from the table
            tableModel.removeRow(selectedRow);
    
            // Inform the user that the transaction was successfully removed
            JOptionPane.showMessageDialog(frame, "Transaction successfully removed.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a transaction to remove!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void showAddTransactionDialog() {
        JDialog dialog = new JDialog(frame, "Add Transaction", true);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(frame);

        String[] types = {"Expense", "Income"};
        JComboBox<String> typeCombo = new JComboBox<>(types);
        
        // JComboBox for predefined descriptions
        String[] descriptions = {"Medical", "Shopping", "Entertainment", "Transport", "Grocery", "Others"};
        JComboBox<String> descriptionCombo = new JComboBox<>(descriptions);
        
        JTextField amountField = new JTextField();
        
        // Create date spinners
        Calendar now = Calendar.getInstance();
        JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(now.get(Calendar.YEAR), 1900, 2100, 1));
        JSpinner monthSpinner = new JSpinner(new SpinnerNumberModel(now.get(Calendar.MONTH) + 1, 1, 12, 1));
        JSpinner daySpinner = new JSpinner(new SpinnerNumberModel(now.get(Calendar.DAY_OF_MONTH), 1, 31, 1));

        JSpinner.NumberEditor yearEditor = new JSpinner.NumberEditor(yearSpinner, "#");
        yearSpinner.setEditor(yearEditor);

        Dimension spinnerSize = new Dimension(60, 25);
        yearSpinner.setPreferredSize(spinnerSize);
        monthSpinner.setPreferredSize(new Dimension(45, 25));
        daySpinner.setPreferredSize(new Dimension(45, 25));

        JPanel datePanel = new JPanel();
        datePanel.add(yearSpinner);
        datePanel.add(new JLabel("-"));
        datePanel.add(monthSpinner);
        datePanel.add(new JLabel("-"));
        datePanel.add(daySpinner);
        
        JButton submitButton = new JButton("Add");

        dialog.add(new JLabel("Date:"));
        dialog.add(datePanel);
        dialog.add(new JLabel("Type:"));
        dialog.add(typeCombo);
        dialog.add(new JLabel("Description:")); 
        dialog.add(descriptionCombo);
        dialog.add(new JLabel("Amount:"));
        dialog.add(amountField);
        dialog.add(new JLabel(""));
        dialog.add(submitButton);
        
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Validate amount input
                    if (amountField.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "Please enter an amount!");
                        return;
                    }
                    
                    double amount = Double.parseDouble(amountField.getText());

                    // Validate description selection
                    String description = (String) descriptionCombo.getSelectedItem();
                    if (description == null || description.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "Please select a description!");
                        return;
                    }

                    // Get selected date
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, (Integer) yearSpinner.getValue());
                    calendar.set(Calendar.MONTH, (Integer) monthSpinner.getValue() - 1);
                    calendar.set(Calendar.DAY_OF_MONTH, (Integer) daySpinner.getValue());
                    Date selectedDate = calendar.getTime();

                    // Get transaction type
                    String type = (String) typeCombo.getSelectedItem();

                    // Update totals based on transaction type
                    if (type.equals("Income")) {
                        totalAmount += amount;
                        double currentIncome = Double.parseDouble(incomeLabel.getText().replace("Total Income: ₹", ""));
                        currentIncome += amount;
                        incomeLabel.setText("Total Income: ₹" + String.format("%.2f", currentIncome));
                    } else { // Expense
                        totalAmount -= amount;
                        double currentExpense = Double.parseDouble(expenseLabel.getText().replace("Total Expense: ₹", ""));
                        currentExpense += amount;
                        expenseLabel.setText("Total Expense: ₹" + String.format("%.2f", currentExpense));
                    }

                    totalLabel.setText("Total Balance: ₹" + String.format("%.2f", totalAmount));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = dateFormat.format(selectedDate);

                    // Store expenses as negative values
                    if (type.equals("Expense")) {
                        amount = -amount; // Store expenses as negative values in the database
                    }

                    // Add to database and get the new ID
                    int newId = addTransaction(typeCombo, descriptionCombo, amountField, yearSpinner, monthSpinner, daySpinner);

                    // Check if the transaction was added successfully
                    if (newId != -1) {
                        tableModel.addRow(new Object[]{newId, dateString, type, description, String.format("%.2f", Math.abs(amount))}); // Use absolute value for display
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Failed to add transaction.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    dialog.dispose(); // Close the dialog after processing
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Please enter a valid amount!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    // Catch any other exceptions that might occur
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "An unexpected error occurred: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.getRootPane().registerKeyboardAction(
            e -> dialog.dispose(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        dialog.setVisible(true); 
    }
     
   
    private void showUpdateTransactionDialog() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a transaction to update!", 
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        // Retrieve current transaction details
        int transactionId = (int) tableModel.getValueAt(selectedRow, 0); // Assuming ID is in column 0
        String currentDate = (String) tableModel.getValueAt(selectedRow, 1); // Assuming Date is in column 1
        String currentType = (String) tableModel.getValueAt(selectedRow, 2); // Assuming Type is in column 2
        String currentDescription = (String) tableModel.getValueAt(selectedRow, 3); // Assuming Description is in column 3
        double currentAmount = Double.parseDouble((String) tableModel.getValueAt(selectedRow, 4)); // Assuming Amount is in column 4
    
        JDialog dialog = new JDialog(frame, "Update Transaction", true);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(frame);
    
        // Fields for updating transaction
        JTextField dateField = new JTextField(currentDate);
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Expense", "Income"});
        typeCombo.setSelectedItem(currentType);
        String[] descriptions = {"Medical", "Shopping", "Entertainment", "Transport", "Grocery", "Others"};
        JComboBox<String> descriptionCombo = new JComboBox<>(descriptions);
        JTextField amountField = new JTextField(String.valueOf(currentAmount));
    
        dialog.add(new JLabel("Date:"));
        dialog.add(dateField);
        dialog.add(new JLabel("Type:"));
        dialog.add(typeCombo);
        dialog.add(new JLabel("Description:"));
        dialog.add(descriptionCombo);
        dialog.add(new JLabel("Amount:"));
        dialog.add(amountField);
    
        JButton updateButton = new JButton("Update");
        dialog.add(new JLabel(""));
        dialog.add(updateButton);
    
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String date = dateField.getText().trim();
                    String type = (String) typeCombo.getSelectedItem();
                    String description = (String) descriptionCombo.getSelectedItem();
                    double amount = Double.parseDouble(amountField.getText().trim());
        
                    // Create a Transaction object to update
                    Transaction transaction = new Transaction(transactionId, date, type, description, amount);
                    TransactionDAO transactionDAO = new TransactionDAO();
                    transactionDAO.updateTransaction(transaction); // Update in the database
        
                    // Update the table model
                    updateTableModel(transaction);
        
                    // Update total income, total expense, and total balance
                    updateTotals(transaction, currentAmount, type);
        
                    dialog.dispose(); // Close the dialog
                    JOptionPane.showMessageDialog(frame, "Transaction updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Please enter a valid amount!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    
        dialog.setVisible(true);
    }
     
  

    private void showMonthlyExpenseDialog() {
        JDialog dialog = new JDialog(frame, "Calculate Monthly Summary", true);
        dialog.setLayout(new GridLayout(3, 2, 10, 10));
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(frame);
    
        JComboBox<String> monthCombo = new JComboBox<>(new String[]{
            "January", "February", "March", "April", "May", "June", 
            "July", "August", "September", "October", "November", "December"
        });
        JTextField yearField = new JTextField();
        JButton calculateButton = new JButton("Calculate");
    
        dialog.add(new JLabel("Month:"));
        dialog.add(monthCombo);
        dialog.add(new JLabel("Year:"));
        dialog.add(yearField);
        dialog.add(new JLabel(""));
        dialog.add(calculateButton);
    
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int month = monthCombo.getSelectedIndex() + 1; // January is 0, so add 1
                    int year = Integer.parseInt(yearField.getText());
    
                    double monthlyExpense = calculateMonthlyExpense(month, year);
                    double monthlyIncome = calculateMonthlyIncome(month, year);
                    double netAmount = monthlyIncome - monthlyExpense;
    
                    String message = String.format("Monthly Summary for %s %d:\n\n" +
                                                   "Total Income: ₹%.2f\n" +
                                                   "Total Expense: ₹%.2f\n" +
                                                   "Net Amount: ₹%.2f",
                                                   monthCombo.getSelectedItem(), year,
                                                   monthlyIncome, monthlyExpense, netAmount);
    
                    JOptionPane.showMessageDialog(dialog, message, "Monthly Summary", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Please enter a valid year!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    
        dialog.setVisible(true);
    }
   



            private double calculateMonthlyExpense(int month, int year) {
        double totalExpense = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String dateStr = (String) tableModel.getValueAt(i, 1); // Assuming date is in column 1
            String type = (String) tableModel.getValueAt(i, 2);    // Assuming type is in column 2
            double amount = Double.parseDouble((String) tableModel.getValueAt(i, 4)); // Assuming amount is in column 4
    
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
    
                if (cal.get(Calendar.MONTH) + 1 == month && 
                    cal.get(Calendar.YEAR) == year && 
                    type.equals("Expense")) {
                    totalExpense += Math.abs(amount);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return totalExpense;
    }
    
    private double calculateMonthlyIncome(int month, int year) {
        double totalIncome = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String dateStr = (String) tableModel.getValueAt(i, 1); // Assuming date is in column 1
            String type = (String) tableModel.getValueAt(i, 2);    // Assuming type is in column 2
            double amount = Double.parseDouble((String) tableModel.getValueAt(i, 4)); // Assuming amount is in column 4
    
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
    
                if (cal.get(Calendar.MONTH) + 1 == month && 
                    cal.get(Calendar.YEAR) == year && 
                    type.equals("Income")) {
                    totalIncome += amount;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return totalIncome;
    }
    private int addTransaction(JComboBox<String> typeCombo, JComboBox<String> descriptionCombo, JTextField amountField, JSpinner yearSpinner, JSpinner monthSpinner, JSpinner daySpinner) {
        int newId = -1; // Initialize newId to -1, indicating failure by default
        String type = (String) typeCombo.getSelectedItem();
        String description = (String) descriptionCombo.getSelectedItem();
        double amount = Double.parseDouble(amountField.getText().trim());
    
        // Get selected date
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, (Integer) yearSpinner.getValue());
        calendar.set(Calendar.MONTH, (Integer) monthSpinner.getValue() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, (Integer) daySpinner.getValue());
        Date selectedDate = calendar.getTime();
    
        // Format date to string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(selectedDate);
    
        // Insert transaction into the main database
        try (Connection connection = DataBaseConnector.getConnection();
             PreparedStatement psMain = connection.prepareStatement(
                     "INSERT INTO transaction_table (Date, Type, Description, Amount) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
    
            // Insert into main transaction table
            psMain.setString(1, dateString);
            psMain.setString(2, type);
            psMain.setString(3, description);
            psMain.setDouble(4, amount);
    
            int rowsAffectedMain = psMain.executeUpdate();
            if (rowsAffectedMain > 0) {
                try (ResultSet generatedKeys = psMain.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        newId = generatedKeys.getInt(1); // Get the generated ID
                        System.out.println("Transaction inserted successfully with ID: " + newId);
                    }
                }
            } else {
                System.out.println("Failed to insert transaction into the main table.");
            }
    
            // Validate transaction type before inserting into category table
            if (type.equalsIgnoreCase("Expense") || type.equalsIgnoreCase("Income")) {
                String categoryTableName = description.toLowerCase() + "_transactions"; // Construct table name
                try (PreparedStatement psCategory = connection.prepareStatement(
                        "INSERT INTO " + categoryTableName + " (ID, Date, Description, Amount) VALUES (?, ?, ?, ?)")) {
                    
                    // Insert into the respective category table with the same ID
                    psCategory.setInt(1, newId); // Use the same ID
                    psCategory.setString(2, dateString);
                    psCategory.setString(3, description);
                    psCategory.setDouble(4, amount);
    
                    int rowsAffectedCategory = psCategory.executeUpdate(); // Execute the insert for the category table
                    if (rowsAffectedCategory > 0) {
                        System.out.println("Transaction inserted successfully into " + categoryTableName);
                    } else {
                        System.out.println("Failed to insert transaction into " + categoryTableName);
                    }
                } catch (SQLException e) {
                    System.out.println("Error inserting into category table: " + e.getMessage());
                }
            } else {
                System.out.println("Error - Invalid transaction type: " + type);
            }
        } catch (SQLException ex) {
            System.out.println("Error - Data not inserted: " + ex.getMessage());
            ex.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error - Invalid amount format: " + e.getMessage());
        }
    
        return newId;
    }
    private void updateTableModel(Transaction transaction) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((int) tableModel.getValueAt(i, 0) == transaction.getId()) { // Assuming the first column is the ID
                tableModel.setValueAt(transaction.getDate(), i, 1); // Update Date
                tableModel.setValueAt(transaction.getType(), i, 2); // Update Type
                tableModel.setValueAt(transaction.getDescription(), i, 3); // Update Description
                tableModel.setValueAt(String.format("%.2f", transaction.getAmount()), i, 4); // Update Amount (formatted)
                break;
            }
        }
    }
    private void updateTotals(Transaction transaction, double previousAmount, String type) {
        // Calculate the difference in amount
        double difference = transaction.getAmount() - previousAmount;
    
        // Update total amount based on the type
        if (type.equals("Income")) {
            totalAmount += difference; // Adjust total amount
            double currentIncome = Double.parseDouble(incomeLabel.getText().replace("Total Income: ₹", ""));
            currentIncome += difference; // Update total income
            incomeLabel.setText("Total Income: ₹" + String.format("%.2f", currentIncome));
        } else if (type.equals("Expense")) {
            totalAmount -= difference; // Adjust total amount
            double currentExpense = Double.parseDouble(expenseLabel.getText().replace("Total Expense: ₹", ""));
            currentExpense += difference; // Update total expense
            expenseLabel.setText("Total Expense: ₹" + String.format("%.2f", currentExpense));
        }
    
        // Update total balance label
        totalLabel.setText("Total Balance: ₹" + String.format("%.2f", totalAmount));
    }
     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}