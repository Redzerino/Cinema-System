// Name: Sarah Crotty
// Date: 27/02/2026
// Purpose: Showtime class

public class Showtime
{
    // Fields representing showtime data
    private int showtimeID;
    private int movieID;
    private int screenID;
    private String startTime;
    private String endTime;
    private String status;

    // Returns the showtime ID
    public int getShowtimeID()
    {
        return showtimeID;
    }

    // Sets the showtime ID
    public void setShowtimeID(int showtimeID)
    {
        this.showtimeID = showtimeID;
    }

    // Returns the movie ID linked to this showtime
    public int getMovieID()
    {
        return movieID;
    }

    // Sets the movie ID linked to this showtime
    public void setMovieID(int movieID)
    {
        this.movieID = movieID;
    }

    // Returns the screen ID where the showtime takes place
    public int getScreenID()
    {
        return screenID;
    }

    // Sets the screen ID where the showtime takes place
    public void setScreenID(int screenID)
    {
        this.screenID = screenID;
    }

    // Returns the start time of the showtime
    public String getStartTime()
    {
        return startTime;
    }

    // Sets the start time of the showtime
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    // Returns the end time of the showtime
    public String getEndTime()
    {
        return endTime;
    }

    // Sets the end time of the showtime
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    // Returns the current status of the showtime (e.g. Scheduled, Cancelled)
    public String getStatus()
    {
        return status;
    }

    // Sets the current status of the showtime
    public void setStatus(String status)
    {
        this.status = status;
    }
}
