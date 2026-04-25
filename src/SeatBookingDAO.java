// Name: Sarah Crotty
// Date: 28/02/2026
// Purpose: seatbooking CRUD

import java.sql.*;
import java.util.ArrayList;

public class SeatBookingDAO
{
    // Inserts a new seat booking record into the database
    public void addSeatBooking(int bookingID, int showtimeID, int seatID, double pricePaid)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO seatbooking (bookingID, showtimeID, seatID, pricePaid) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookingID);
            ps.setInt(2, showtimeID);
            ps.setInt(3, seatID);
            ps.setDouble(4, pricePaid);
            ps.executeUpdate();
            System.out.println("SeatBooking added successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    // Returns all seat booking records from the database
    public ArrayList<String[]> getAllSeatBookings()
    {
        ArrayList<String[]> seatBookings = new ArrayList<>();
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM seatbooking";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                String[] row = {
                    String.valueOf(rs.getInt("seatBookingID")),
                    String.valueOf(rs.getInt("bookingID")),
                    String.valueOf(rs.getInt("showtimeID")),
                    String.valueOf(rs.getInt("seatID")),
                    String.valueOf(rs.getDouble("pricePaid"))
                };
                seatBookings.add(row);
            }
        }
        catch(Exception e) { e.printStackTrace(); }
        return seatBookings;
    }

    // Updates an existing seat booking record by ID
    public void updateSeatBooking(int seatBookingID, int bookingID, int showtimeID, int seatID, double pricePaid)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE seatbooking SET bookingID=?, showtimeID=?, seatID=?, pricePaid=? WHERE seatBookingID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookingID);
            ps.setInt(2, showtimeID);
            ps.setInt(3, seatID);
            ps.setDouble(4, pricePaid);
            ps.setInt(5, seatBookingID);
            ps.executeUpdate();
            System.out.println("SeatBooking updated successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    // Permanently deletes a seat booking record by ID
    public void deleteSeatBooking(int seatBookingID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM seatbooking WHERE seatBookingID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, seatBookingID);
            ps.executeUpdate();
            System.out.println("SeatBooking deleted successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }
}
