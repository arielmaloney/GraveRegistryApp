package com.example.ariel.graveregistryapp;
/**
 * Created by Holly Calloway on 2/28/2018.
 */

public class Graves
{
    private String firstName, lastName, conflict, cemetery;

    public Graves()
    {

    }

    public Graves(String firstName, String lastName, String conflict, String cemetery)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.conflict = conflict;
        this.cemetery = cemetery;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getConflict()
    {
        return conflict;
    }

    public void setConflict(String conflict)
    {
        this.conflict = conflict;
    }

    public String getCemetery()
    {
        return cemetery;
    }

    public void setCemetery(String cemetery)
    {
        this.cemetery = cemetery;
    }
}
