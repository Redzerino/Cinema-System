// Name: Sarah Crotty
// Date: 27/02/2026
// Purpose: CRUD for movie

import java.sql.*;
import java.util.ArrayList;

public class MovieDAO
{

    // Adds a new movie to the database
    public void addMovie(String title, String description, String genre, int duration,
                         String ageRating, String language, String releaseDate, String status)
    {
        try
        {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO movies (Title, Description, Genre, Duration, AgeRating, Language, ReleaseDate, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, genre);
            ps.setInt(4, duration);
            ps.setString(5, ageRating);
            ps.setString(6, language);
            ps.setString(7, releaseDate);
            ps.setString(8, status);

            ps.executeUpdate();

            System.out.println("Movie added successfully");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // Retrieves all movies from the database
    public ArrayList<Movie> getAllMovies()
    {
        ArrayList<Movie> movies = new ArrayList<>();

        try
        {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM movies";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                Movie m = new Movie();

                m.setMovieID(rs.getInt("MovieID"));
                m.setTitle(rs.getString("Title"));
                m.setDescription(rs.getString("Description"));
                m.setGenre(rs.getString("Genre"));
                m.setDuration(rs.getInt("Duration"));
                m.setAgeRating(rs.getString("AgeRating"));
                m.setLanguage(rs.getString("Language"));
                m.setReleaseDate(rs.getString("ReleaseDate"));
                m.setStatus(rs.getString("Status"));

                movies.add(m);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return movies;
    }

    // Updates an existing movie record
    public void updateMovie(int movieID, String title, String description, String genre,
                            int duration, String ageRating, String language,
                            String releaseDate, String status)
    {
        try
        {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE movies SET Title=?, Description=?, Genre=?, Duration=?, AgeRating=?, Language=?, ReleaseDate=?, Status=? WHERE MovieID=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, genre);
            ps.setInt(4, duration);
            ps.setString(5, ageRating);
            ps.setString(6, language);
            ps.setString(7, releaseDate);
            ps.setString(8, status);
            ps.setInt(9, movieID);

            ps.executeUpdate();

            System.out.println("Movie updated successfully");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // Marks a movie as inactive instead of deleting it
    public void deleteMovie(int movieID)
    {
        try
        {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE movies SET status='Inactive' WHERE MovieID=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, movieID);

            ps.executeUpdate();

            System.out.println("Movie deleted successfully");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}