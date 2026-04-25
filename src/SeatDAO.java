// Name: Sarah Crotty
// Date: 27/02/2026
// Purpose: DAO class for performing CRUD operations on the seats table

import java.sql.*;
import java.util.ArrayList;

public class SeatDAO
{
    // Inserts a new seat record into the database
    public void addSeat(int screenID, String seatRow, int seatNumber, String seatType, boolean isWorking)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO seats (screenID, seatRow, seatNumber, seatType, isWorking) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, screenID);
            ps.setString(2, seatRow);
            ps.setInt(3, seatNumber);
            ps.setString(4, seatType);
            ps.setBoolean(5, isWorking);
            ps.executeUpdate();
            System.out.println("Seat added successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    // Returns all seat records from the database
    public ArrayList<String[]> getAllSeats()
    {
        ArrayList<String[]> seats = new ArrayList<>();
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM seats";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                String[] row = {
                    String.valueOf(rs.getInt("seatID")),
                    String.valueOf(rs.getInt("screenID")),
                    rs.getString("seatRow"),
                    String.valueOf(rs.getInt("seatNumber")),
                    rs.getString("seatType"),
                    String.valueOf(rs.getBoolean("isWorking"))
                };
                seats.add(row);
            }
        }
        catch(Exception e) { e.printStackTrace(); }
        return seats;
    }

    // Updates an existing seat record by ID
    public void updateSeat(int seatID, int screenID, String seatRow, int seatNumber, String seatType, boolean isWorking)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE seats SET screenID=?, seatRow=?, seatNumber=?, seatType=?, isWorking=? WHERE seatID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, screenID);
            ps.setString(2, seatRow);
            ps.setInt(3, seatNumber);
            ps.setString(4, seatType);
            ps.setBoolean(5, isWorking);
            ps.setInt(6, seatID);
            ps.executeUpdate();
            System.out.println("Seat updated successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    // Soft deletes a seat by setting isWorking to 0
    public void deleteSeat(int seatID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE seats SET isWorking=0 WHERE seatID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, seatID);
            ps.executeUpdate();
            System.out.println("Seat deleted successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }
}
