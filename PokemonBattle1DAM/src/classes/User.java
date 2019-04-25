/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import classes.Pokemon;
import exceptions.InvalidGenreException;
import java.time.LocalDateTime;

/**
 *
 * @author cenec
 */
public class User extends LivingBeing {
     private String password;
     private LocalDateTime lastConnection;
     private int timesLoggedIn;
     private AccessLevel accessLevel;
     private Pokemon pokemon;
     
     public enum AccessLevel{
            PREMIUM,
            BASIC
     }

    public User(String n,char g,String d,String password,  AccessLevel accessLevel, Pokemon pokemon) throws InvalidGenreException {
        super(n, g, d);
        this.password = password;
        this.lastConnection = null;
        this.timesLoggedIn = 0;
        this.accessLevel = accessLevel;
        this.pokemon = pokemon;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(LocalDateTime lastConnection) {
        this.lastConnection = lastConnection;
    }

    public int getTimesLoggedIn() {
        return timesLoggedIn;
    }

    public void setTimesLoggedIn(int timesLoggedIn) {
        this.timesLoggedIn = timesLoggedIn;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }
    
    
     
     
     
     
}
