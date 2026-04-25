// Name: Sarah Crotty
// Date: 28/02/2026
// Purpose: DAO class for handling booking CRUD

import java.sql.*;
import java.util.ArrayList;

public class BookingDAO
{
    // Adds a new booking to the database
    public void addBooking(String bookingReference, String guestEmail, String guestName, int showtimeID, double totalAmount, String status)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO bookings (bookingReference, guestEmail, guestName, showtimeID, totalAmount, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, bookingReference);
            ps.setString(2, guestEmail);
            ps.setString(3, guestName);
            ps.setInt(4, showtimeID);
            ps.setDouble(5, totalAmount);
            ps.setString(6, status);
            ps.executeUpdate();
            System.out.println("Booking added successfully");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // Retrieves all bookings with related movie and showtime details
    public ArrayList<String[]> getAllBookings()
    {
        ArrayList<String[]> bookings = new ArrayList<>();
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT b.bookingID, b.bookingReference, b.guestName, b.guestEmail, m.title, s.startTime, b.totalAmount, b.status " +
                        "FROM bookings b " +
                        "INNER JOIN showtimes s ON b.showtimeID = s.showtimeID " +
                        "INNER JOIN movies m ON s.movieID = m.movieID";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                String[] row = {
                    String.valueOf(rs.getInt("bookingID")),
                    rs.getString("bookingReference"),
                    rs.getString("guestName"),
                    rs.getString("guestEmail"),
                    rs.getString("title"),
                    rs.getString("startTime"),
                    String.valueOf(rs.getDouble("totalAmount")),
                    rs.getString("status")
                };
                bookings.add(row);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return bookings;
    }

    // Updates an existing booking's details
    public void updateBooking(int bookingID, String guestName, String guestEmail, double totalAmount, String status)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE bookings SET guestName=?, guestEmail=?, totalAmount=?, status=? WHERE bookingID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, guestName);
            ps.setString(2, guestEmail);
            ps.setDouble(3, totalAmount);
            ps.setString(4, status);
            ps.setInt(5, bookingID);
            ps.executeUpdate();
            System.out.println("Booking updated successfully");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // Marks a booking as cancelled
    public void deleteBooking(int bookingID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE bookings SET Status='Cancelled' WHERE bookingID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookingID);
            ps.executeUpdate();
            System.out.println("Booking deleted successfully");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}