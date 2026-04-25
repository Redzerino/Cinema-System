//Name: Sarah Crotty
//Date: 24/02/2026
//Purpose: Screan CRUD

import java.sql.*;
import java.util.ArrayList;

public class ScreenDAO
{
    //adds screen to database//
    public void addScreen(String screenType, int capacity)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO screens (screenType, capacity) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, screenType);
            ps.setInt(2, capacity);
            ps.executeUpdate();
            System.out.println("Screen added successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    //retrieves all screens//
    public ArrayList<String[]> getAllScreens()
    {
        ArrayList<String[]> screens = new ArrayList<>();
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM screens";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                String[] row =
                {
                    String.valueOf(rs.getInt("screenID")),
                    rs.getString("screenType"),
                    String.valueOf(rs.getInt("capacity"))
                };
                screens.add(row);
            }
        }
        catch(Exception e) { e.printStackTrace(); }
        return screens;
    }

    //updates screen details//
    public void updateScreen(int screenID, String screenType, int capacity)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE screens SET screenType=?, capacity=? WHERE screenID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, screenType);
            ps.setInt(2, capacity);
            ps.setInt(3, screenID);
            ps.executeUpdate();
            System.out.println("Screen updated successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    //deletes screen//
    public void deleteScreen(int screenID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM screens WHERE screenID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, screenID);
            ps.executeUpdate();
            System.out.println("Screen deleted successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }
}