import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public abstract class BasePanel extends JPanel {

    protected JFrame frame;
    protected JButton selectedButton; // Variable to store the currently selected button

    public BasePanel() {
        this.frame = frame;
    }

    protected abstract void updateStart();

    protected void topButtonPanel(DataItem cumulativeData) {
        // Add "Home" buttons
        JButton homeButton = new JButton();
        homeButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        homeButton.setContentAreaFilled(false);
        URL homeURL = BasePanel.class.getResource("icons/homeicon.png");
        homeButton.setIcon(new ImageIcon(homeURL, "Home"));
        homeButton.setToolTipText("Home");
        homeButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(new HomePanel(frame, cumulativeData));
            frame.revalidate();
            frame.repaint();
        });

        // Add "Menu" buttons
        JButton menuButton = new JButton();
        menuButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        menuButton.setContentAreaFilled(false);
        URL menuURL = BasePanel.class.getResource("icons/menuicon.png");
        menuButton.setIcon(new ImageIcon(menuURL, "Menu"));
        menuButton.setToolTipText("Menu");
        menuButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(new MenuPanel(frame, cumulativeData));
            frame.revalidate();
            frame.repaint();
        });

        // Add "Save" buttons
        JButton saveButton = new JButton();
        saveButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        saveButton.setContentAreaFilled(false);
        URL saveURL = BasePanel.class.getResource("icons/saveIcon.png");
        saveButton.setIcon(new ImageIcon(saveURL, "Save"));
        saveButton.setToolTipText("Save data in two CSV files.");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDataToCSV(cumulativeData);
            }
        });

        // Add "Data Processing" button
        JButton dataProcessingButton = new JButton();
        dataProcessingButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        dataProcessingButton.setContentAreaFilled(false);
        URL dataProcessingURL = BasePanel.class.getResource("icons/dataProcessingIcon.png");
        dataProcessingButton.setIcon(new ImageIcon(dataProcessingURL, "Data Processing"));
        dataProcessingButton.setToolTipText("Load the old data or reset the data.");
        dataProcessingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(new DataPanel(frame, cumulativeData));
                frame.revalidate();
                frame.repaint();
            }
        });

        // Add "Upload Expense" button
        JButton uploadExpenseButton = new JButton();
        uploadExpenseButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        uploadExpenseButton.setContentAreaFilled(false);
        URL uploadExpenseURL = BasePanel.class.getResource("icons/uploadExpensesIcon.png");
        uploadExpenseButton.setIcon(new ImageIcon(uploadExpenseURL, "Upload Expense"));
        uploadExpenseButton.setToolTipText("Upload expense data from CSV file.");
        uploadExpenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadExpenseData(cumulativeData);
            }
        });

        // Add "Upload Budget" button
        JButton uploadBudgetButton = new JButton();
        uploadBudgetButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        uploadBudgetButton.setContentAreaFilled(false);
        URL uploadBudgetURL = BasePanel.class.getResource("icons/uploadBudgetIcon.png");
        uploadBudgetButton.setIcon(new ImageIcon(uploadBudgetURL, "Upload Budget"));
        uploadBudgetButton.setToolTipText("Upload budget data from CSV file.");
        uploadBudgetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadBudgetData(cumulativeData);
            }
        });

        // Add "Exit" button
        JButton exitButton = new JButton();
        exitButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        exitButton.setContentAreaFilled(false);
        URL exitURL = BasePanel.class.getResource("icons/exitIcon.png");
        exitButton.setIcon(new ImageIcon(exitURL, "Exit"));
        exitButton.setToolTipText("Exit the application.");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExitPanel exitPanel = new ExitPanel(frame);
                exitPanel.setVisible(true);
            }
        });

        // Add button to update the chart
        JButton updateDataButton = new JButton();
        updateDataButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        updateDataButton.setContentAreaFilled(false);
        URL updateURL = BasePanel.class.getResource("icons/updateIcon.png");
        updateDataButton.setIcon(new ImageIcon(updateURL, "Update Data"));
        updateDataButton.setToolTipText("Update data at regular interval.");
        updateDataButton.addActionListener(e -> updateData(cumulativeData));

        // Add panel
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topButtonPanel.setBackground(new java.awt.Color(245, 230, 230));
        topButtonPanel.add(homeButton);
        topButtonPanel.add(menuButton);
        topButtonPanel.add(dataProcessingButton);
//        topButtonPanel.add(uploadExpenseButton);
//        topButtonPanel.add(uploadBudgetButton);
        topButtonPanel.add(saveButton);
        topButtonPanel.add(updateDataButton);
        topButtonPanel.add(exitButton);
        add(topButtonPanel, BorderLayout.NORTH);
    }

    protected void uploadExpenseData(DataItem cumulativeData) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                cumulativeData.updateExpenseData(br);
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error uploading expense data from CSV file.");
            }
        }
    }

    protected void uploadBudgetData(DataItem cumulativeData) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                cumulativeData.updateBudgetData(br);
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error uploading budget data from CSV file.");
            }
        }
    }

    // Method to update pie chart based on data in the table
    protected void updateData(DataItem cumulativeData) {
    }

    protected void saveDataToCSV(DataItem cumulativeData) {
    }

}
