package org.example.pojo;

public class User1 {
    private String username;
    private String password;

    public String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }
    public void setUsername(String username)
    {
        this.username=username;
    }
    public void setPassword(String password)
    {
        this.password=password;

    }


    @Override
    public String toString()
    {
        return "{username="+username+",password="+password+"}";
    }
}
