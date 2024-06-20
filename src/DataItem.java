import java.io.BufferedReader;
import java.io.IOException;

public class DataItem {
    private Object[][] cumulativeExpense;
    private Object[][] expenseData;
    private Object[][] budgetData;

    public DataItem() {
        reset();
    }

    public void reset() {
        this.cumulativeExpense = new Object[][]{
                {"Utilities", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {"Housing", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {"Entertainment", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {"Personal Shopping", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {"Memberships", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {"Emergency Funds", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {"Healthcare", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {"Transportation", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {"Food", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {"Pets", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {"Children", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        this.expenseData = new Object[][]{
                {"Utilities", 0},
                {"Housing", 0},
                {"Entertainment", 0},
                {"Personal Shopping", 0},
                {"Memberships", 0},
                {"Emergency Funds", 0},
                {"Healthcare", 0},
                {"Transportation", 0},
                {"Food", 0},
                {"Pets", 0},
                {"Children", 0}
        };
        this.budgetData = new Object[][]{
                {"Source 1", 0},
                {"Source 2", 0},
                {"Source 3", 0},
                {"Others", 0},
                {"Utilities", 0},
                {"Housing", 0},
                {"Entertainment", 0},
                {"Personal Shopping", 0},
                {"Memberships", 0},
                {"Emergency Funds", 0},
                {"Healthcare", 0},
                {"Transportation", 0},
                {"Food", 0},
                {"Pets", 0},
                {"Children", 0}
        };
    }

    public Object[][] getExpense() {
        return cumulativeExpense;
    }

    public Object[][] getExpenseRow() {
        return expenseData;
    }

    public void setExpense(Object[][] newCumulativeExpense) {
        this.cumulativeExpense = newCumulativeExpense;
    }

    public Object[][] getBudget() {
        return budgetData;
    }

    public Object[][] getSourceOfIncome() {
        Object[][] sourceOfIncomeData = new Object[][]{
                {"Source 1", 0},
                {"Source 2", 0},
                {"Source 3", 0},
                {"Others", 0}
        };
        for (int i = 0; i < 4; i++) {
            sourceOfIncomeData[i][1] = budgetData[i][1];
        }
        return sourceOfIncomeData;
    }

    public Object[][] getBudgetAllocation() {
        Object[][] allocationData = new Object[][]{
                {"Utilities", 0},
                {"Housing", 0},
                {"Entertainment", 0},
                {"Personal Shopping", 0},
                {"Memberships", 0},
                {"Emergency Funds", 0},
                {"Healthcare", 0},
                {"Transportation", 0},
                {"Food", 0},
                {"Pets", 0},
                {"Children", 0}
        };
        for (int i = 0; i < 11; i++) {
            allocationData[i][1] = budgetData[i + 4][1];
        }
        return allocationData;
    }

    public Object[][] getOverallStatus() {
        Object[][] overallStatusData = new Object[][]{
                {"Total Income", 0},
                {"Total Expenses", 0},
                {"Remaining Budget", 0}
        };
        int totalIncome = 0;
        for (int i = 0; i < 4; i++) {
            totalIncome += Integer.parseInt(budgetData[i][1].toString());
        }
        overallStatusData[0][1] = totalIncome;

        int totalExpense = 0;
        for (int i = 0; i < cumulativeExpense.length; i++) {
            for (int j = 1; j < cumulativeExpense[i].length; j++) {
                totalExpense += Integer.parseInt(cumulativeExpense[i][j].toString());
            }
        }
        overallStatusData[1][1] = totalExpense;
        overallStatusData[2][1] = totalIncome - totalExpense;

        return overallStatusData;
    }

    public Object[][] getAllocationStatus() {
        Object[][] budgetAllocation = this.getBudgetAllocation();

        Object[][] allocationStatusData = new Object[][]{
                {"Utilities", 0},
                {"Housing", 0},
                {"Entertainment", 0},
                {"Personal Shopping", 0},
                {"Memberships", 0},
                {"Emergency Funds", 0},
                {"Healthcare", 0},
                {"Transportation", 0},
                {"Food", 0},
                {"Pets", 0},
                {"Children", 0}
        };

        for (int i = 0; i < cumulativeExpense.length; i++) {
            int categoryExpense = 0;
            for (int j = 1; j < cumulativeExpense[i].length; j++) {
                categoryExpense += Integer.parseInt(cumulativeExpense[i][j].toString());
            }
            allocationStatusData[i][1] = Integer.parseInt(budgetAllocation[i][1].toString()) - categoryExpense;
        }
        return allocationStatusData;
    }

    public void setBudget(Object[][] newBudget) {
        this.budgetData = newBudget;
    }

    // New method to get yearly expenses
    public Object[][] getYearlyExpenses() {
        Object[][] yearlyExpensesData = new Object[cumulativeExpense.length][2];
        for (int i = 0; i < cumulativeExpense.length; i++) {
            yearlyExpensesData[i][0] = cumulativeExpense[i][0];
            int totalYearlyExpense = 0;
            for (int j = 1; j < cumulativeExpense[i].length; j++) {
                totalYearlyExpense += Integer.parseInt(cumulativeExpense[i][j].toString());
            }
            yearlyExpensesData[i][1] = totalYearlyExpense;
        }
        return yearlyExpensesData;
    }


    public void updateExpenseData(BufferedReader br) throws IOException {
        String line;
        int i = 0;
        while ((br.readLine()) != null) {
            while ((line = br.readLine()) != null) {
                this.cumulativeExpense[i][0]=line.split(",")[0];
                this.cumulativeExpense[i][1]=Integer.parseInt(line.split(",")[1]);
                this.cumulativeExpense[i][2]=Integer.parseInt(line.split(",")[2]);
                this.cumulativeExpense[i][3]=Integer.parseInt(line.split(",")[3]);
                this.cumulativeExpense[i][4]=Integer.parseInt(line.split(",")[4]);
                this.cumulativeExpense[i][5]=Integer.parseInt(line.split(",")[5]);
                this.cumulativeExpense[i][6]=Integer.parseInt(line.split(",")[6]);
                this.cumulativeExpense[i][7]=Integer.parseInt(line.split(",")[7]);
                this.cumulativeExpense[i][8]=Integer.parseInt(line.split(",")[8]);
                this.cumulativeExpense[i][9]=Integer.parseInt(line.split(",")[9]);
                this.cumulativeExpense[i][10]=Integer.parseInt(line.split(",")[10]);
                this.cumulativeExpense[i][11]=Integer.parseInt(line.split(",")[11]);
                this.cumulativeExpense[i][12]=Integer.parseInt(line.split(",")[12]);
                i++;
            }
        }

    }

    public void updateBudgetData(BufferedReader br) throws IOException {
        String line;
        int i = 0;
        while ((br.readLine()) != null) {
            while ((line = br.readLine()) != null) {
                this.budgetData[i][0]=line.split(",")[0];
                this.budgetData[i][1]=Integer.parseInt(line.split(",")[1]);
                i++;
            }
        }

    }
}