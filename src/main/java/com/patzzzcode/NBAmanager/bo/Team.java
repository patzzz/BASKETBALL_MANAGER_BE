package com.patzzzcode.NBAmanager.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEAMS")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private int noOfPLayers;
    private int noOfGames;
    private int noOfWins;
    private int noOfLoses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getNoOfPLayers() {
        return noOfPLayers;
    }

    public void setNoOfPLayers(int noOfPLayers) {
        this.noOfPLayers = noOfPLayers;
    }

    public int getNoOfGames() {
        return noOfGames;
    }

    public void setNoOfGames(int noOfGames) {
        this.noOfGames = noOfGames;
    }

    public int getNoOfWins() {
        return noOfWins;
    }

    public void setNoOfWins(int noOfWins) {
        this.noOfWins = noOfWins;
    }

    public int getNoOfLoses() {
        return noOfLoses;
    }

    public void setNoOfLoses(int noOfLoses) {
        this.noOfLoses = noOfLoses;
    }

    public Team(Long id, String name, String country, int noOfPLayers, int noOfGames, int noOfWins, int noOfLoses) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.noOfPLayers = noOfPLayers;
        this.noOfGames = noOfGames;
        this.noOfWins = noOfWins;
        this.noOfLoses = noOfLoses;
    }

    public Team() {
    }
}
