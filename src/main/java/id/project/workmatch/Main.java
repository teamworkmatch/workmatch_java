
package id.project.workmatch;

import javax.swing.SwingUtilities;
import id.project.workmatch.view.MasukFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MasukFrame().setVisible(true);
        });
    }
}
