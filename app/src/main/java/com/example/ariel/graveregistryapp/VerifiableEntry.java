package com.example.ariel.graveregistryapp;

/**
 *  A class to hold the List View data that will be displayed in the ListView of the Verifiable Activity
 * Created by Ariel on 3/17/2018.
 */

public class VerifiableEntry {

    // String variables for the entry
    public String id;
    public String name;
    public String cemetery;
    public String conflict;

    /**
     * Empty Constructor for VerifiableEntry
     */
    public VerifiableEntry() {

    }

    /**
     * Get grave marker name
     * @return grave marker name
     */
    public String getName() {
        return name;
    }

    /**
     * Set grave marker name
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get grave marker cemetery
     * @return grave marker cemetery
     */
    public String getCemetery() {
        return cemetery;
    }

    /**
     * Set grave marker cemetery
     * @param cemetery the cemetery
     */
    public void setCemetery(String cemetery) {
        this.cemetery = cemetery;
    }

    /**
     * Get grave marker conflict
     * @return grave marker conflict
     */
    public String getConflict() {
        return conflict;
    }

    /**
     * Set grave marker conflict
     * @param conflict the conflict
     */
    public void setConflict(String conflict) {
        this.conflict = conflict;
    }

    /**
     * Get grave marker ID
     * @return grave marker ID
     */
    public String getId() {
        return id;
    }

    /**
     * Set grave marker ID
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }
}
