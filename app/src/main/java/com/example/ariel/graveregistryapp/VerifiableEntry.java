package com.example.ariel.graveregistryapp;

/**
 *  A class to hold the List View data that will be displayed in the ListView of the Verifiable Activity
 * Created by Ariel on 3/17/2018.
 */

public class VerifiableEntry {

    public String id;
    public String name;
    public String cemetery;
    public String conflict;

    public VerifiableEntry() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCemetery() {
        return cemetery;
    }

    public void setCemetery(String cemetery) {
        this.cemetery = cemetery;
    }

    public String getConflict() {
        return conflict;
    }

    public void setConflict(String conflict) {
        this.conflict = conflict;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
