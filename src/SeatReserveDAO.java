// Name: Sarah Crotty
// Date: 26/02/2026
// Purpose: CRUD for reserved seats

import java.sql.*;
import java.util.ArrayList;

public class SeatReserveDAO
{
    // Inserts a new reservation record with auto-set reserve and expire timestamps
    public void addReservation(int showtimeID, int seatID, int userID, String guestSessionID, String status)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            // reserveAt is set to NOW() and expireAt is set 15 minutes from now
            String sql = "INSERT INTO seatreserve (showtimeID, seatID, user_ID, guestSessionID, reserveAt, expireAt, status) " +
                "VALUES (?, ?, ?, ?, NOW(), DATE_ADD(NOW(), INTERVAL 15 MINUTE), ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, showtimeID);
            ps.setInt(2, seatID);
            // If no user is logged in, set user_ID to NULL
            if(userID == 0)
                ps.setNull(3, java.sql.Types.INTEGER);
            else
                ps.setInt(3, userID);
            ps.setString(4, guestSessionID);
            ps.setString(5, status);
            ps.executeUpdate();
            System.out.println("Reservation added successfully");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // Marks all active reservations older than 15 minutes as Expired
    public void expireOldReservations()
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE seatreserve SET status='Expired' WHERE status='Active' AND reserveAt < DATE_SUB(NOW(), INTERVAL 15 MINUTE)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // Returns all reservation records from the database
    public ArrayList<String[]> getAllReservations()
    {
        ArrayList<String[]> reservations = new ArrayList<>();
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM seatreserve";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                String[] row = {
                    String.valueOf(rs.getInt("reserveID")),
                    String.valueOf(rs.getInt("showtimeID")),
                    String.valueOf(rs.getInt("seatID")),
                    String.valueOf(rs.getInt("user_ID")),
                    rs.getString("guestSessionID"),
                    rs.getString("reserveAt"),
                    rs.getString("expireAt"),
                    rs.getString("status")
                };
                reservations.add(row);
            }
        }
        catch(Exception e) { e.printStackTrace(); }
        return reservations;
    }

    // Updates the status and expiry time of an existing reservation by ID
    public void updateReservation(int reserveID, String status, String expireAt)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE seatreserve SET status=?, expireAt=? WHERE reserveID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, expireAt);
            ps.setInt(3, reserveID);
            ps.executeUpdate();
            System.out.println("Reservation updated successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    // Permanently deletes a reservation record by ID
    public void deleteReservation(int reserveID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM seatreserve WHERE reserveID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, reserveID);
            ps.executeUpdate();
            System.out.println("Reservation deleted successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }
}
