import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class HomePanel extends BasePanel {
    public HomePanel(JFrame frame, DataItem cumulativeData) {
        super();
        this.frame = frame;
        setLayout(new BorderLayout());
        this.topButtonPanel(cumulativeData);

        // Create a panel to hold the description and the start button
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new java.awt.Color(255, 230, 230));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Add software description
        JLabel descriptionLabel = new JLabel("<html>" +
                "<h1>Welcome to the Finance App</h1><br>" +
                "<p>Finance Application has a goal to help you organize all of your financial information in one place! " +
                "It helps you to track your expenses, set budgets, and analyze your spending patterns. Get started by clicking the button below!<br></p><br>" +
                "<p> When you reach the menu panel, first click on the budgeting icon!<br></p><br>" +
                "<p>Author: Emma Zurine <br>Contact: emma.zurine27@gmail.com</p>" +
                "</html>");
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add start button
        JButton startButton = new JButton();
        startButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        startButton.setContentAreaFilled(false);
        URL saveURL = BasePanel.class.getResource("icons/startIcon.gif");
        startButton.setIcon(new ImageIcon(saveURL, "Start"));
        startButton.setToolTipText("Please start here.");

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(new LoadingPanel(frame, cumulativeData));
//            frame.add(new MenuPanel(frame, cumulativeData));
            frame.revalidate();
            frame.repaint();
        });

        // Add components to the main panel
        mainPanel.add(descriptionLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add space between description and button
        mainPanel.add(startButton);

        // Add main panel to the center of the HomePanel
        add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    protected void updateStart() {

    }

    // For testing purposes
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Home Panel");
//        DataItem dataItem = new DataItem();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(new HomePanel(frame, dataItem));
//        frame.setSize(600, 400);
//        frame.setVisible(true);
//    }
}
