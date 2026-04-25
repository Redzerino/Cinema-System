// Name: Sarah Crotty
// Date: 19/02/2026
// Purpose: user CRUD

import java.sql.*;
import java.util.ArrayList;

public class UserDAO
{
    // Inserts a new user record into the database
    public void addUser(String email, String passwordHash, String firstName, String lastName, String role, boolean isActive)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO users (email, password_hash, firstName, lastName, role, isActive) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, passwordHash);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            ps.setString(5, role);
            ps.setBoolean(6, isActive);
            ps.executeUpdate();
            System.out.println("User added successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    // Returns all user records from the database
    public ArrayList<String[]> getAllUsers()
    {
        ArrayList<String[]> users = new ArrayList<>();
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM users";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                String[] row = {
                    String.valueOf(rs.getInt("user_id")),
                    rs.getString("email"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("role"),
                    String.valueOf(rs.getBoolean("isActive"))
                };
                users.add(row);
            }
        }
        catch(Exception e) { e.printStackTrace(); }
        return users;
    }

    // Updates an existing user record by ID (password not updated here)
    public void updateUser(int userID, String email, String firstName, String lastName, String role, boolean isActive)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE users SET email=?, firstName=?, lastName=?, role=?, isActive=? WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, role);
            ps.setBoolean(5, isActive);
            ps.setInt(6, userID);
            ps.executeUpdate();
            System.out.println("User updated successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    // Soft deletes a user by setting their isActive flag to 0
    public void deleteUser(int userID)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "Update users SET isActive=0 WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userID);
            ps.executeUpdate();
            System.out.println("User deleted successfully");
        }
        catch(Exception e) { e.printStackTrace(); }
    }
}
