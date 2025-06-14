import GUI.InterestManagementFrame;

import javax.swing.SwingUtilities;

public class Start {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterestManagementFrame().setVisible(true));
    }
}