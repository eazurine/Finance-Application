import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MenuPanel extends BasePanel {
    private CardLayout cardLayout;

    public MenuPanel(JFrame frame, DataItem cumulativeData) {
        super();
        this.frame = frame;
        setLayout(new BorderLayout());
        this.topButtonPanel(cumulativeData);

        // Set background color for the entire MenuPanel
        this.setBackground(new java.awt.Color(255, 230, 230)); // Line 12

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new java.awt.Color(255, 230, 230)); // Line 16
        menuPanel.setLayout(new GridLayout(1, 3)); // Modified to set 1 row and 3 columns

        // Create buttons for each menu option
        JButton budgetingButton = new JButton("Budgeting");
        budgetingButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        budgetingButton.setContentAreaFilled(false);
        URL budgetURL = BasePanel.class.getResource("icons/budgetIcon.png");
        budgetingButton.setIcon(new ImageIcon(budgetURL, "Budgeting"));
        budgetingButton.setToolTipText("Set and track your budget");
        budgetingButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(new BudgetingPanel(frame, cumulativeData));
            frame.revalidate();
            frame.repaint();
        });
        menuPanel.add(budgetingButton);

        JButton expensesButton = new JButton("Expenses");
        expensesButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        expensesButton.setContentAreaFilled(false);
        URL expenseURL = BasePanel.class.getResource("icons/spendingIcon.png");
        expensesButton.setIcon(new ImageIcon(expenseURL, "Expense"));
        expensesButton.setToolTipText("Keep track of your spending");
        expensesButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(new ExpensesPanel(frame, cumulativeData));
            frame.revalidate();
            frame.repaint();
        });
        menuPanel.add(expensesButton);

        JButton spendingAnalysisButton = new JButton("Spending Analysis");
        spendingAnalysisButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        spendingAnalysisButton.setContentAreaFilled(false);
        URL spendingURL = BasePanel.class.getResource("icons/accountingIcon.png");
        spendingAnalysisButton.setIcon(new ImageIcon(spendingURL, "Expense"));
        spendingAnalysisButton.setToolTipText("Keep track of your spending");

        spendingAnalysisButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(new SpendingAnalysisPanel(frame, cumulativeData));
            frame.revalidate();
            frame.repaint();
        });
        menuPanel.add(spendingAnalysisButton);

        add(menuPanel, BorderLayout.CENTER); // Changed from BorderLayout.SOUTH to BorderLayout.CENTER
    }

    @Override
    protected void updateStart() {
        // Implementation for the updateStart method
    }
}
