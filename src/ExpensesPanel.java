import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class ExpensesPanel extends BasePanel {
    private JTable expensesTable;
    private PieChart pieChart;
    private XChartPanel<PieChart> pieChartPanel;
    private JButton selectedButton; // Variable to store the currently selected button


    public ExpensesPanel(JFrame frame, DataItem cumulativeData) {
        super();
        this.frame = frame;
        setLayout(new BorderLayout());
        this.topButtonPanel(cumulativeData);


        // Create table with rows and default values
        String[] columnNames = {"Category", "Amount"};
        Object[][] monthlyCost = cumulativeData.getExpenseRow();
        DefaultTableModel expenseModel = new DefaultTableModel(monthlyCost, columnNames);
        expensesTable = new JTable(expenseModel);
        JScrollPane tableScrollPane = new JScrollPane(expensesTable);
        tableScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Put the expenses"));
        add(tableScrollPane, BorderLayout.CENTER);

        // Create pie chart and pie chart panel
        pieChart = new PieChartBuilder().width(400).height(300).title("Expenses Distribution").build();
        updateData(cumulativeData); // Update pie chart with initial data
        pieChartPanel = new XChartPanel<>(pieChart);
        add(pieChartPanel, BorderLayout.EAST);



        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(inputPanel, BorderLayout.SOUTH);

        // Add buttons for January to December
        JPanel monthButtonPanel = new JPanel(new GridLayout(2, 6));
        for (int i = 1; i <= 12; i++) {
            JButton monthButton = new JButton("Month " + i);
            int monthIndex = i;
            monthButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectButton(monthButton); // Select the clicked button
                    resetTableAndChart(Integer.parseInt(monthButton.getText().split(" ")[1]), cumulativeData); // Reset the table and pie chart
                }
            });
            monthButtonPanel.add(monthButton);
            if (i == 1) { // Initially select the "Month 1" button
                selectButton(monthButton);
                resetTableAndChart(Integer.parseInt(monthButton.getText().split(" ")[1]), cumulativeData); // Reset the table and pie chart
            }
        }
        add(monthButtonPanel, BorderLayout.SOUTH);

    }

    // Method to update pie chart based on data in the table
    public void updateData(DataItem cumulativeDataFull) {
        super.updateData(cumulativeDataFull);
        Object[][] expenseData = cumulativeDataFull.getExpense();
        pieChart.getSeriesMap().clear();
        int rowCount = expensesTable.getRowCount();
        int columnNumber = 0;
        if (selectedButton != null) {
            columnNumber = Integer.parseInt(selectedButton.getText().split(" ")[1]);
        }
        for (int i = 0; i < rowCount; i++) {
            String category = (String) expensesTable.getValueAt(i, 0);
            String valueString = expensesTable.getValueAt(i, 1).toString();
            expenseData[i][columnNumber] = expensesTable.getValueAt(i, 1);
            expenseData[i][0] = expensesTable.getValueAt(i, 0);
            double value = Double.parseDouble(valueString);
            pieChart.addSeries(category, value);
        }
        if (pieChartPanel != null) {
            pieChartPanel.revalidate();
            pieChartPanel.repaint();
        }
        cumulativeDataFull.setExpense(expenseData);
    }

    // Method to save data from the expenses table to a CSV file
    public void saveDataToCSV(DataItem cumulativeDataFull) {
        super.saveDataToCSV(cumulativeDataFull);
        Object[][] expenseData = cumulativeDataFull.getExpense();
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
                DefaultTableModel expenseModel = (DefaultTableModel) expensesTable.getModel();
                // Write cumulative data to CSV file
                writer.write("Category,January,February,March,April,May,June,July,August,September,October,November,December");
                writer.write("\n");
                for (int i = 0; i < expenseModel.getRowCount(); i++) {
                    String category = (String) expenseModel.getValueAt(i, 0);
                    writer.write(category + ",");
                    for (int j = 1; j < expenseData[i].length; j++) {
                        writer.write(expenseData[i][j].toString());
                        if (j < expenseData[i].length - 1) {
                            writer.write(",");
                        }
                    }
                    writer.write("\n");
                }
                writer.close();
                JOptionPane.showMessageDialog(this, "Data saved to expenses.csv");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving data to expenses.csv");
            }
        }
    }
    // Method to select a button and update its appearance
    private void selectButton(JButton button) {
        if (selectedButton != null) {
            selectedButton.setBackground(null); // Reset background color of previously selected button
        }
        selectedButton = button; // Update reference to selected button
        selectedButton.setOpaque(true);
        selectedButton.setBackground(Color.GREEN); // Set background color of selected button
    }
    // Method to reset the table and pie chart
    private void resetTableAndChart(int month, DataItem cumulativeDataFull) {
        Object[][] expenseData = cumulativeDataFull.getExpense();
        DefaultTableModel expenseModel = (DefaultTableModel) expensesTable.getModel();
        int rowCount = expenseModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            expenseModel.setValueAt(expenseData[i][month], i, 1); // Reset values to 0
        }
        updateData(cumulativeDataFull); // Update pie chart with reset data
    }

    @Override
    protected void updateStart() {

    }
}
