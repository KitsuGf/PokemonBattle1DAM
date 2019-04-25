/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import exceptions.InvalidGenreException;

/**
 *
 * @author Kitsu
 */
public class LivingBeing {
        private String name;
        private boolean genre; //Female is represented by true, Male by false
        private String Description;

    public LivingBeing(String name, char genre, String Description) throws InvalidGenreException {
        this.name = name;
        this.setGenre(genre);
        this.Description = Description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGenre() {
        if(this.genre){
            return 'f';
        }else{
            return 'm';
        }
    }

    public final void setGenre(char genre) throws InvalidGenreException{
        if(genre=='f'||genre=='F'){
            this.genre=true;
        }else if (genre=='m'||genre=='M'){
            this.genre=false;
        }else{
            throw new InvalidGenreException(genre+" is not"
                    + "a valid genre. Only m and f are accepted.");
        }
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
    
    
        
        
}
