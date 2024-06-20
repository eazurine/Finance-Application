import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class LoadingPanel extends JPanel {
    private ImageIcon icon;
    private Timer timer;
    private JLabel label;

    public LoadingPanel(JFrame frame, DataItem cumulativeData) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.GRAY);

        URL imageURL = getClass().getResource("icons/loadingIcon.gif");
        ImageIcon icon = new ImageIcon(imageURL);
        label = new JLabel(icon);
        add(label, BorderLayout.CENTER);

        // Start the timer to delay the transition
        timer = new Timer(6000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Stop the timer
                timer.stop();
                // Remove the loading panel from its parent container
                Container parent = getParent();
                parent.remove(LoadingPanel.this);
                // Add the new panel (replace this with your desired panel)
                frame.add(new MenuPanel(frame, cumulativeData));
                // Revalidate and repaint the parent container to reflect the changes
                parent.revalidate();
                parent.repaint();
            }
        });
        timer.setRepeats(false); // Set the timer to only fire once
        timer.start(); // Start the timer
    }
}
