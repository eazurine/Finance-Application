import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

public class SpendingAnalysisPanel extends BasePanel {
    private DataItem cumulativeData;
    private CategoryChart barChart;
    private XChartPanel<CategoryChart> chartPanel;
    private JRadioButton budgetRadioButton;
    private JRadioButton yearlyRadioButton;

    public SpendingAnalysisPanel(JFrame frame, DataItem cumulativeData) {
        super();
        this.frame = frame;
        this.cumulativeData = cumulativeData;
        setLayout(new BorderLayout()); // Use BorderLayout for the SpendingAnalysisPanel
        this.topButtonPanel(cumulativeData);


        // Create the radio panel and add radio buttons to it
        JPanel radioPanel = new JPanel();
        budgetRadioButton = new JRadioButton("Budget Allocation");
        yearlyRadioButton = new JRadioButton("Yearly Expenses");
        budgetRadioButton.setSelected(true);

        // Group the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(budgetRadioButton);
        group.add(yearlyRadioButton);

        // Add action listeners to the radio buttons
        budgetRadioButton.addActionListener(e -> updateBarChart("Budget Allocation"));
        yearlyRadioButton.addActionListener(e -> updateBarChart("Yearly Expenses"));

        // Add radio buttons to the radio panel
        radioPanel.add(budgetRadioButton);
        radioPanel.add(yearlyRadioButton);

        // Add the radio panel to the north position
            add(radioPanel, BorderLayout.SOUTH);

        // Initialize and set up the bar chart
        barChart = new CategoryChartBuilder().width(800).height(600).title("Spending Analysis").xAxisTitle("Category").yAxisTitle("Amount ($)").build();
        barChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        barChart.getStyler().setAvailableSpaceFill(0.9);
        barChart.getStyler().setToolTipsEnabled(true);
        barChart.getStyler().setToolTipType(Styler.ToolTipType.xAndYLabels);

        chartPanel = new XChartPanel<>(barChart);
        updateBarChart("Budget Allocation"); // Initialize the chart with data

        chartPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });

        // Add the chart panel to the center position
        add(chartPanel, BorderLayout.CENTER);
    }

    private void updateBarChart(String chartType) {
        barChart.getSeriesMap().clear();
        if ("Budget Allocation".equals(chartType)) {
            String[] budgetCategories = new String[cumulativeData.getBudget().length];
            Number[] budgetValues = new Number[cumulativeData.getBudget().length];

            for (int i = 0; i < cumulativeData.getBudget().length; i++) {
                budgetCategories[i] = (String) cumulativeData.getBudget()[i][0];
                budgetValues[i] = Integer.parseInt(cumulativeData.getBudget()[i][1].toString());
            }

            barChart.addSeries("Budget Allocation", Arrays.asList(budgetCategories), Arrays.asList(budgetValues));
            Color[] barColors = new Color[]{
                    new Color(255, 0, 0),  // Red
                    new Color(0, 255, 0),  // Green
                    new Color(0, 0, 255),  // Blue
                    new Color(255, 255, 0),  // Yellow
                    new Color(255, 165, 0)  // Orange
            };
            List<Color> colorList = Arrays.asList(barColors);
            barChart.getStyler().setSeriesColors(colorList.toArray(new Color[0]));
        } else if ("Yearly Expenses".equals(chartType)) {
            Object[][] yearlyExpensesData = cumulativeData.getYearlyExpenses(); // Retrieve yearly expenses data
            String[] expenseCategories = new String[yearlyExpensesData.length];
            Number[] expenseValues = new Number[yearlyExpensesData.length];

            for (int i = 0; i < yearlyExpensesData.length; i++) {
                expenseCategories[i] = yearlyExpensesData[i][0].toString();
                expenseValues[i] = Integer.parseInt(yearlyExpensesData[i][1].toString());
            }

            barChart.addSeries("Yearly Expenses", Arrays.asList(expenseCategories), Arrays.asList(expenseValues));
            Color[] barColors = new Color[]{
                    new Color(255, 105, 180),  // Hot Pink
                    new Color(75, 0, 130),  // Indigo
                    new Color(0, 128, 128),  // Teal
                    new Color(255, 69, 0),  // Orange Red
                    new Color(128, 0, 0)  // Maroon
            };
            List<Color> colorList = Arrays.asList(barColors);
            barChart.getStyler().setSeriesColors(colorList.toArray(new Color[0]));
        }
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    private void handleMouseClick(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        List<?> categories = (List<?>) barChart.getSeriesMap().get("Expenses").getXData();
        double barWidth = chartPanel.getWidth() / (double) categories.size();

        int clickedBarIndex = (int) (x / barWidth - 0.5);
        if (clickedBarIndex >= 0 && clickedBarIndex < categories.size()) {
            String category = categories.get(clickedBarIndex).toString();
            Object yValue = barChart.getSeriesMap().get("Expenses").getYData();
            Double value;
            if (yValue instanceof List<?>) {
                value = ((Number) barChart.getSeriesMap().get("Expenses").getYData().toArray()[clickedBarIndex]).doubleValue();
            } else {
                value = 0.0; // Default value
            }

            JOptionPane.showMessageDialog(chartPanel, "Category: " + category + "\nValue: " + value, "Bar Clicked", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    protected void updateStart() {
        updateBarChart("Budget Allocation");
    }
}
