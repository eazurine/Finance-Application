import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileWriter;
import java.io.IOException;

public class BudgetingPanel extends BasePanel {
    private JTable incomeTable, allocationTable, overallStatusTable, remainingTable;
    private DefaultTableModel incomeModel, allocationModel, statusModel, remainingModel;
    private JPanel cards;
    private DataItem cumulativeData;

    public BudgetingPanel(JFrame frame, DataItem cumulativeData) {
        super();
        this.frame = frame;
        this.cumulativeData = cumulativeData;
        setLayout(new BorderLayout());
        this.topButtonPanel(cumulativeData);


        // Create tables
        String[] columnNames = {"Category", "Amount"};
        Object[][] sourceOfIncome = cumulativeData.getSourceOfIncome();
        Object[][] budgetAllocation = cumulativeData.getBudgetAllocation();
        Object[][] overallStatusData = cumulativeData.getOverallStatus();
        Object[][] allocationData = cumulativeData.getAllocationStatus();

        incomeModel = new DefaultTableModel(sourceOfIncome, columnNames);
        allocationModel = new DefaultTableModel(budgetAllocation, columnNames);
        statusModel = new DefaultTableModel(overallStatusData, columnNames);
        remainingModel = new DefaultTableModel(allocationData, columnNames);

        incomeTable = new JTable(incomeModel);
        allocationTable = new JTable(allocationModel);
        overallStatusTable = new JTable(statusModel);
        remainingTable = new JTable(remainingModel);

        // Budget Cards
        JPanel comboBoxPane = new JPanel();
        comboBoxPane.setBorder(BorderFactory.createTitledBorder("Budget"));
        String comboBoxItems[] = {"Set Budget", "Budget Status"};
        JComboBox<String> cb = new JComboBox<>(comboBoxItems);
        cb.setEditable(false);
        comboBoxPane.add(cb);

        // Add tables to the set budget panel
        JPanel setBudgetPanel = new JPanel();
        setBudgetPanel.setLayout(new BoxLayout(setBudgetPanel, BoxLayout.Y_AXIS));
        setBudgetPanel.add(new JLabel("Source of Income", SwingConstants.CENTER));
        setBudgetPanel.add(new JScrollPane(incomeTable));
        setBudgetPanel.add(new JLabel("Budget Allocation", SwingConstants.CENTER));
        setBudgetPanel.add(new JScrollPane(allocationTable));

        // Add tables to the budget status panel
        JPanel budgetStatusPanel = new JPanel(new GridLayout(4, 1));
        budgetStatusPanel.add(new JLabel("Overview"));
        budgetStatusPanel.add(new JScrollPane(overallStatusTable));
        budgetStatusPanel.add(new JLabel("Remaining by Category"));
        budgetStatusPanel.add(new JScrollPane(remainingTable));

        // Create a panel with CardLayout
        cards = new JPanel(new CardLayout());
        cards.add(setBudgetPanel, "Set Budget");
        cards.add(budgetStatusPanel, "Budget Status");

        // Add ItemListener to JComboBox to switch cards and update data
        cb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    CardLayout cl = (CardLayout) (cards.getLayout());
                    cl.show(cards, (String) e.getItem());
                    if ("Budget Status".equals(e.getItem())) {
                        updateStatusTables();
                    }
                }
            }
        });

        // Add components to the main panel
        add(comboBoxPane, BorderLayout.SOUTH);
        add(cards, BorderLayout.CENTER);
    }

    private void updateStatusTables() {
        Object[][] overallStatusData = cumulativeData.getOverallStatus();
        Object[][] allocationData = cumulativeData.getAllocationStatus();

        statusModel.setDataVector(overallStatusData, new String[]{"Category", "Amount"});
        remainingModel.setDataVector(allocationData, new String[]{"Category", "Amount"});
    }



    @Override
    public void updateData(DataItem cumulativeDataFull) {
        super.updateData(cumulativeDataFull);
        Object[][] budgetData = cumulativeDataFull.getBudget();
        int rowCount = incomeTable.getRowCount();
        int columnNumber = 1;
        for (int i = 0; i < rowCount; i++) {
            budgetData[i][columnNumber] = incomeTable.getValueAt(i, 1);
        }
        rowCount = allocationTable.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            budgetData[i + 4][columnNumber] = allocationTable.getValueAt(i, 1);
        }
        cumulativeDataFull.setBudget(budgetData);
        this.cumulativeData = cumulativeDataFull;
        updateStatusTables();  // Ensure the status tables are updated with new data
    }

    @Override
    public void saveDataToCSV(DataItem cumulativeDataFull) {
        super.saveDataToCSV(cumulativeDataFull);
        Object[][] budgetData = cumulativeDataFull.getBudget();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save CSV File");

        // Set default directory to user's home directory
        fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));

        // Set file filter to show only CSV files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(frame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Check if file extension is provided
            if (!filePath.toLowerCase().endsWith(".csv")) {
                filePath += ".csv"; // Append .csv extension if not provided
            }

            try (FileWriter writer = new FileWriter(filePath)) {
                String[] columnNames = {"Category", "Amount"};
                DefaultTableModel budgetModel = new DefaultTableModel(budgetData, columnNames);
                // Write cumulative data to CSV file
                writer.write("Category,Amount");
                writer.write("\n");
                for (int i = 0; i < budgetModel.getRowCount(); i++) {
                    String category = (String) budgetModel.getValueAt(i, 0);
                    String value = String.valueOf(budgetModel.getValueAt(i, 1));
                    writer.write(category + "," + value);
                    writer.write("\n");
                }
                writer.close();
                JOptionPane.showMessageDialog(this, "Data saved to budget.csv");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving data to budget.csv");
            }
        }
    }

    @Override
    protected void updateStart() {
        // Implement the updateStart method as required
    }
}
