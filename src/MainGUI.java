// Name: Sarah Crotty
// Date: 22/03/2026
// Purpose: Main application GUI has tabbed styling

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI extends JFrame
{
    private Color darkNavy = new Color(13, 13, 43);

    // Sets up main system window and loads all panels
    public MainGUI()
    {
        setTitle("NovaPlex Cinema System");
        setSize(1100, 950);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Sets window icon
        try
        {
            ImageIcon icon = new ImageIcon("images/Logo.png");
            setIconImage(icon.getImage());
        }
        catch(Exception e)
        {
            System.out.println("Logo not found");
        }

        setLayout(new BorderLayout());

        // Banner section
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setBackground(darkNavy);

        try
        {
            ImageIcon bannerIcon = new ImageIcon("images/Banner.png");
            Image bannerImg = bannerIcon.getImage().getScaledInstance(1100, 80, Image.SCALE_SMOOTH);
            JLabel bannerLabel = new JLabel(new ImageIcon(bannerImg));
            bannerPanel.add(bannerLabel, BorderLayout.CENTER);
        }
        catch(Exception e)
        {
            JLabel fallback = new JLabel("NovaPlex Cinema System", SwingConstants.CENTER);
            fallback.setFont(new Font("Arial", Font.BOLD, 28));
            fallback.setForeground(Color.WHITE);
            fallback.setPreferredSize(new Dimension(1100, 80));
            bannerPanel.add(fallback, BorderLayout.CENTER);
        }

        add(bannerPanel, BorderLayout.NORTH);

        // Tabbed navigation for all system modules
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.setBackground(darkNavy);
        tabbedPane.setForeground(Color.WHITE);

        tabbedPane.addTab("Movies", new MoviePanel().createPanel());
        tabbedPane.addTab("Showtimes", new ShowtimePanel().createPanel());
        tabbedPane.addTab("Bookings", new BookingPanel().createPanel());
        tabbedPane.addTab("Screens", new ScreenPanel().createPanel());
        tabbedPane.addTab("Seats", new SeatPanel().createPanel());
        tabbedPane.addTab("Users", new UserPanel().createPanel());
        tabbedPane.addTab("Payments", new PaymentPanel().createPanel());
        tabbedPane.addTab("Tickets", new TicketPanel().createPanel());
        tabbedPane.addTab("Seat Bookings", new SeatBookingPanel().createPanel());
        tabbedPane.addTab("Seat Reserves", new SeatReservePanel().createPanel());
        tabbedPane.addTab("Reports", new ReportPanel().createPanel());

        add(tabbedPane, BorderLayout.CENTER);

        // Timer to automatically expire old seat reservations every minute
        javax.swing.Timer autoExpireTimer = new javax.swing.Timer(60000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new SeatReserveDAO().expireOldReservations();
                System.out.println("Checked for expired reservations");
            }
        });
        autoExpireTimer.start();
    }
}