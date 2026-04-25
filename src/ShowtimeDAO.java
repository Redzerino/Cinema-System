// Name: Sarah Crotty
// Date: 28/02/2026
// Purpose: showtime CRUD

import java.sql.*;
import java.util.ArrayList;

public class ShowtimeDAO
{
    // Inserts a new showtime record into the database
    public void addShowtime(int movieID, int screenID, String startTime, String endTime, String status)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO showtimes (movieID, screenID, startTime, endTime, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, movieID);
            ps.setInt(2, screenID);
            ps.setString(3, startTime);
            ps.setString(4, endTime);
            ps.setString(5, status);
            ps.executeUpdate();
            System.out.println("Showtime added successfully");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // Returns all showtimes joined with movie titles from the database
    public ArrayList<String[]> getAllShowtimes()
    {
        ArrayList<String[]> showtimes = new ArrayList<>();
        try
        {
            Connection con = DBConnection.getConnection();
            // Join with movies table to display movie title instead of ID
            String sql = "SELECT s.showtimeID, m.title, s.screenID, s.startTime, s.endTime, s.status " +
                        "FROM showtimes s INNER JOIN movies m ON s.movieID = m.movieID";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                String[] row = {
                    String.valueOf(rs.getInt("showtimeID")),
                    rs.getString("title"),
                    String.valueOf(rs.getInt("screenID")),
                    rs.getString("startTime"),
                    rs.getString("endTime"),
                    rs.getString("status")
                };
                showtimes.add(row);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return showtimes;
    }

    // Updates an existing showtime record by ID
    public void updateShowtime(int showtimeID, int movieID, int screenID, String startTime, String endTime, String status)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE showtimes SET movieID=?, screenID=?, startTime=?, endTime=?, status=? WHERE showtimeID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, movieID);
            ps.setInt(2, screenID);
            ps.setString(3, startTime);
            ps.setString(4, endTime);
            ps.setString(5, status);
            ps.setInt(6, showtimeID);
            ps.executeUpdate();
            System.out.println("Showtime updated successfully");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // Soft deletes a showtime by setting its status to Cancelled
    public void deleteShowtime(int showtimeID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE showtimes SET status='Cancelled' WHERE showtimeID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, showtimeID);
            ps.executeUpdate();
            System.out.println("Showtime cancelled successfully");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
