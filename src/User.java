// Name: Sarah Crotty
// Date: 19/02/2026
// Purpose: user class

public class User
{
    // Fields representing user data
    private int userID;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String role;
    private String createdOn;
    private boolean isActive;

    // Returns the user ID
    public int getUserID()
    {
        return userID;
    }

    // Sets the user ID
    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    // Returns the user's email address
    public String getEmail()
    {
        return email;
    }

    // Sets the user's email address
    public void setEmail(String email)
    {
        this.email = email;
    }

    // Returns the hashed password for the user
    public String getPasswordHash()
    {
        return passwordHash;
    }

    // Sets the hashed password for the user
    public void setPasswordHash(String passwordHash)
    {
        this.passwordHash = passwordHash;
    }

    // Returns the user's first name
    public String getFirstName()
    {
        return firstName;
    }

    // Sets the user's first name
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    // Returns the user's last name
    public String getLastName()
    {
        return lastName;
    }

    // Sets the user's last name
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    // Returns the user's role (e.g. Customer, Staff, Admin)
    public String getRole()
    {
        return role;
    }

    // Sets the user's role
    public void setRole(String role)
    {
        this.role = role;
    }

    // Returns the date the user account was created
    public String getCreatedOn()
    {
        return createdOn;
    }

    // Sets the date the user account was created
    public void setCreatedOn(String createdOn)
    {
        this.createdOn = createdOn;
    }

    // Returns whether the user account is active
    public boolean isActive()
    {
        return isActive;
    }

    // Sets the active status of the user account
    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }
}
