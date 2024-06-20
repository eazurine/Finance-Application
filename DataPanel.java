import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class DataPanel extends BasePanel {
    public DataPanel(JFrame frame, DataItem cumulativeData) {
        super();
        this.frame = frame;
        setLayout(new BorderLayout());
        this.topButtonPanel(cumulativeData);

        // Set background color for the entire DataPanel
        this.setBackground(new java.awt.Color(255, 230, 230)); // Line 12

        JPanel dataPanel = new JPanel();
        dataPanel.setBackground(new java.awt.Color(255, 230, 230)); // Line 16
        dataPanel.setLayout(new GridLayout(1, 3)); // Modified to set 1 row and 3 columns

        // Create buttons for each data option
        JButton uploadingBudgetButton = new JButton("Uploading Budget");
        uploadingBudgetButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        uploadingBudgetButton.setContentAreaFilled(false);
        URL option1URL = BasePanel.class.getResource("icons/uploadBudget.png");
        uploadingBudgetButton.setIcon(new ImageIcon(option1URL, "Uploading Budget"));
        uploadingBudgetButton.setToolTipText("Upload a CSV file with Budget");
        uploadingBudgetButton.addActionListener(e -> {
            uploadBudgetData(cumulativeData);
        });
        dataPanel.add(uploadingBudgetButton);

        JButton uploadingExpensesButton = new JButton("Uploading Expenses");
        uploadingExpensesButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        uploadingExpensesButton.setContentAreaFilled(false);
        URL option2URL = BasePanel.class.getResource("icons/uploadExpenses.png");
        uploadingExpensesButton.setIcon(new ImageIcon(option2URL, "Uploading Expenses"));
        uploadingExpensesButton.setToolTipText("Upload a CSV file with Expenses");
        uploadingExpensesButton.addActionListener(e -> {
            uploadExpenseData(cumulativeData);
        });
        dataPanel.add(uploadingExpensesButton);

        JButton resetButton = new JButton("Data Reset");
        resetButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        resetButton.setContentAreaFilled(false);
        URL option3URL = BasePanel.class.getResource("icons/reset.png");
        resetButton.setIcon(new ImageIcon(option3URL, "Data Reset"));
        resetButton.setToolTipText("Reset Data");
        resetButton.addActionListener(e -> {
            cumulativeData.reset();
        });
        dataPanel.add(resetButton);

        add(dataPanel, BorderLayout.CENTER); // Changed from BorderLayout.SOUTH to BorderLayout.CENTER
    }

    @Override
    protected void updateStart() {
        // Implement the updateStart method as required
    }
}
