import javax.swing.*;
import java.awt.*;

public class AppDriver {
    // Cumulative data structure
    private static DataItem cumulativeData = new DataItem();

    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }
    public static void main(String[] args) {
//        setUIFont (new javax.swing.plaf.FontUIResource("Serif",Font.ROMAN_BASELINE,14));
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("FinApp");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.getLayeredPane().setBackground(new java.awt.Color(255, 194, 194));

            // Show the home panel initially
            frame.add(new HomePanel(frame, cumulativeData));
            frame.setVisible(true);
        });
    }
}
