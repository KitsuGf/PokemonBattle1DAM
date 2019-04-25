package classes;

import exceptions.InvalidGenreException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kitsu
 */
public class Pokemon extends LivingBeing {
    private int id;
    private PokemonType type;
    private String species;
    private byte level;
    private short lifePoints;
    private int experiencePoints;

    public enum PokemonType{
        FIRE,
        WATER,
        PLANT
    };

    /**
     *  Pokemon basic constructor with all data
     * @param id numeric id
     * @param n name
     * @param g gender
     * @param d description
     * @param type type of PokemonType enum
     * @param lifePoints 0 - 100
     * @param sp species
     * @throws InvalidGenreException 
     */
    public Pokemon(int id, String n, char g, String d,PokemonType type, short lifePoints,String sp) throws InvalidGenreException {
        super(n, g, d);
        this.id=id;
        this.species=sp;
        this.type = type;
        this.level = 1;
        this.experiencePoints=0;
        this.lifePoints = lifePoints;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public short getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(short lifePoints) {
        this.lifePoints = lifePoints;
    }
    
    
    
            }
