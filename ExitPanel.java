import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitPanel extends JDialog {

    public ExitPanel(JFrame frame) {
        super(frame, "Exit Confirmation", true);
        setLayout(new BorderLayout());

        JLabel message = new JLabel("Are you sure you want to exit?");
        message.setHorizontalAlignment(SwingConstants.CENTER);
        add(message, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton exitButton = new JButton("Exit");
        JButton cancelButton = new JButton("Cancel");

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Close the dialog
            }
        });

        buttonPanel.add(exitButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(300, 150);
        setLocationRelativeTo(frame); // Center the dialog relative to the parent frame
    }
}
