// Name: Sarah Crotty
// Date: 26/03/2026
// Purpose: Helper class for GUI styling and validation methods

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class GUIHelper
{
    // Colours used across the GUI
    public static Color darkNavy = new Color(13, 13, 43);
    public static Color addGreen = new Color(46, 139, 87);
    public static Color updateBlue = new Color(30, 100, 200);
    public static Color deleteRed = new Color(180, 30, 30);
    public static Color refreshGray = new Color(100, 100, 100);
    public static Color tableHeader = new Color(30, 30, 80);

    // makng a styled button
    public static JButton createButton(String text, Color color)
    {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 35));
        return button;
    }

    // Styles a table with fonts, colours and alternating rows so the colour changes through rows
    public static void styleTable(JTable table)
    {
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setGridColor(new Color(200, 200, 200));
        table.setSelectionBackground(new Color(100, 100, 200));
        table.setSelectionForeground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setBackground(tableHeader);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 12));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column)
            {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if(!isSelected)
                {
                    if(row % 2 == 0)
                        setBackground(Color.WHITE);
                    else
                        setBackground(new Color(240, 240, 255));
                }
                return this;
            }
        });
    }

    // Checks if a movie exists
    public static boolean movieExists(int movieID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT movieID FROM movies WHERE movieID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, movieID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks if a screen exists
    public static boolean screenExists(int screenID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT screenID FROM screens WHERE screenID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, screenID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks if a showtime exists
    public static boolean showtimeExists(int showtimeID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT showtimeID FROM showtimes WHERE showtimeID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, showtimeID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks if a seat exists
    public static boolean seatExists(int seatID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT seatID FROM seats WHERE seatID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, seatID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks if a booking exists
    public static boolean bookingExists(int bookingID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT bookingID FROM bookings WHERE bookingID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookingID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks if a user exists
    public static boolean userExists(int userID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT user_id FROM users WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks if a seat booking exists
    public static boolean seatBookingExists(int seatBookingID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT seatBookingID FROM seatbooking WHERE seatBookingID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, seatBookingID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks if a seat is already booked for a showtime
    public static boolean isSeatAlreadyBooked(int seatID, int showtimeID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT sb.seatBookingID FROM seatbooking sb " +
            "INNER JOIN bookings b ON sb.bookingID = b.bookingID " +
            "WHERE sb.seatID=? AND sb.showtimeID=? AND b.status != 'Cancelled'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, seatID);
            ps.setInt(2, showtimeID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Gets the duration of a movie
    public static int getMovieDuration(int movieID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT duration FROM movies WHERE movieID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, movieID);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return rs.getInt("duration");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    // Checks for duplicate movie
    public static boolean duplicateMovie(String title, String releaseDate)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT movieID FROM movies WHERE title=? AND releaseDate=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, releaseDate);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks for duplicate ticket
    public static boolean duplicateTicket(int seatBookingID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT ticketID FROM tickets WHERE seatBookingID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, seatBookingID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks for duplicate email
    public static boolean duplicateEmail(String email)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT user_id FROM users WHERE email=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks for duplicate booking reference
    public static boolean duplicateBookingReference(String reference)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT bookingID FROM bookings WHERE bookingReference=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, reference);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks for duplicate seat
    public static boolean duplicateSeat(int screenID, String seatRow, int seatNumber)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT seatID FROM seats WHERE screenID=? AND seatRow=? AND seatNumber=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, screenID);
            ps.setString(2, seatRow);
            ps.setInt(3, seatNumber);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks for duplicate showtime
    public static boolean duplicateShowtime(int movieID, int screenID, String startTime)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT showtimeID FROM showtimes WHERE movieID=? AND screenID=? AND startTime=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, movieID);
            ps.setInt(2, screenID);
            ps.setString(3, startTime);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks for duplicate payment reference
    public static boolean duplicatePaymentReference(String reference)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT paymentID FROM payments WHERE reference=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, reference);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks if a movie is inactive
    public static boolean isMovieRetired(int movieID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT movieID FROM movies WHERE movieID=? AND status='Inactive'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, movieID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks if a movie has upcoming showtimes
    public static boolean hasUpcomingShowtime(int movieID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT showtimeID FROM showtimes WHERE movieID=? AND startTime > NOW() AND status='Scheduled'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, movieID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Checks if a seat is broken
    public static boolean isSeatBroken(int seatID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT seatID FROM seats WHERE seatID=? AND isWorking=0";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, seatID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}