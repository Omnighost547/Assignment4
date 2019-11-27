import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DisplayQueryResults mainWindow = new DisplayQueryResults();
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // set window size and display window
            mainWindow.pack();
            mainWindow.setSize(mainWindow.getWidth(), mainWindow.getHeight());
            mainWindow.setVisible(true);

        });
    }
}
