// Name: Sarah Crotty
// Date: 22/03/2026
// Purpose:launches the Login GUI

import javax.swing.SwingUtilities;

public class Main
{
    // Starts the application on the Swing event thread
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            LoginGUI login = new LoginGUI();
            login.setVisible(true);
        });
    }
}