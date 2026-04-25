// Name: Sarah Crotty
// Date: 26/02/2026
// Purpose: Movie class

public class Movie
{
    // Movie details
    private int movieID;
    private String title;
    private String description;
    private String genre;
    private int duration;
    private String ageRating;
    private String language;
    private String releaseDate;
    private String status;

    // Gets movie ID
    public int getMovieID()
    {
        return movieID;
    }

    // Sets movie ID
    public void setMovieID(int movieID)
    {
        this.movieID = movieID;
    }

    // Gets movie title
    public String getTitle()
    {
        return title;
    }

    // Sets movie title
    public void setTitle(String title)
    {
        this.title = title;
    }

    // Gets movie description
    public String getDescription()
    {
        return description;
    }

    // Sets movie description
    public void setDescription(String description)
    {
        this.description = description;
    }

    // Gets movie genre
    public String getGenre()
    {
        return genre;
    }

    // Sets movie genre
    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    // Gets movie duration
    public int getDuration()
    {
        return duration;
    }

    // Sets movie duration
    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    // Gets age rating
    public String getAgeRating()
    {
        return ageRating;
    }

    // Sets age rating
    public void setAgeRating(String ageRating)
    {
        this.ageRating = ageRating;
    }

    // Gets language
    public String getLanguage()
    {
        return language;
    }

    // Sets language
    public void setLanguage(String language)
    {
        this.language = language;
    }

    // Gets release date
    public String getReleaseDate()
    {
        return releaseDate;
    }

    // Sets release date
    public void setReleaseDate(String releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    // Gets status
    public String getStatus()
    {
        return status;
    }

    // Sets status
    public void setStatus(String status)
    {
        this.status = status;
    }
}