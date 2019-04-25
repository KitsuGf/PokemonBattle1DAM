/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import classes.User;
import classes.User.AccessLevel;
import static classes.User.AccessLevel.BASIC;
import static classes.User.AccessLevel.PREMIUM;
import exceptions.InvalidGenreException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kitsu
 */
public class PokemonGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Variables needed to work
        Scanner sc=new Scanner(System.in);
        Connection connection=null;
                try {
             connection=DriverManager.getConnection(
                    "jdbc:mysql://85.214.120.213:3306/pokemonbattle1dam"
                    ,"1dam","1dam");
        } catch (SQLException ex) {
                    System.err.println("La conexion  a bd ha fallado");
        }
        
        String menu="\n\nChoose action by number:"
                +"\n\t0 - Exit game"
                +"\n\t1 - Register new User"
                +"\n\t2 - Login User";
        int action=0;
        User user=null;
        do{
            if(user!=null){
                    menu="\n\nHello, "+user.getName()+". Choose action by number:"
                +"\n\t0 - Exit game";
                    if(user.getPokemon()==null){
                        menu+="\n\t1 - Register new pokemon";
                    }else{
                        //11 - Concatenate another option 1 - Show pokemon
                        //12- Modify case 1 on the switch, and if it finds that user!=null&&user.getPokemon()!=null execute a new fucntion in pokemon class: public String print() , that returns all the pokemon's data.
                    }
            }
            System.out.println(menu);
            action=Integer.parseInt(sc.nextLine());
            switch(action){
                case 0:
                    System.out.println("Bye");
                    break;
                case 1:
                    //if user is still null, the user hasn't been registered nor logged in
                    if(user==null){
                        user=registerUser(sc,connection);
                    }else{
                        System.out.println("Let's register your pokemon!");
                    }
                    break;
                case 2:
                    user=loginUser(sc,connection);
                 break;
                default:
                    System.out.println("Invalid option choosen");
                    break;
            }
        }while(action!=0);
    }
    
    public static void registerPokemon(Scanner sc, Connection conn, User user){
            //1 - Ask for all of pokemon data via Scanner
             //2 - Create an statement object and insert into pokemon table.  (you can skip id , as it is auto-incremented)
            //3 - Query the newly created pokemon id (query max id from pokemon table)
            //4 - Create a java Pokemon object with the read data and the queried id
           //5 - User setPokemon in User class to Link the pokemon to the user in java
            //6 - Reuse Statement or create a new one to insert into pokemonUser table, where you link pokemon and user.
            
    }
    
    public static User registerUser(Scanner sc, Connection conn){
         try {
         System.out.println("Tell me your username:");
        String username=sc.nextLine();
        System.out.println("Choose your Password:");
        String password=sc.nextLine();
        System.out.println("Choose your genre (m/f)");
        char genreChar=sc.nextLine().charAt(0);
        System.out.println("Tell me about you:");
        String description=sc.nextLine();
        
       
            
            User actual=new User(username,genreChar,
                    description,password,AccessLevel.BASIC,null);
            //we're goint to persist the user in the database
            //so we can log in as him/her later
            Statement registerStatement=conn.createStatement();
            registerStatement.executeUpdate(
                    "insert into user (name,genre,description,password,"
                            + "lastConnection,accessLevel) values('"+username+"',"
                            + "'"+genreChar+"','"+description+
                            "','"+password+"',"+
                            //Abbreviated if. This means:
                            //(condition?executesIfTrue:executesifFalse)
                            // ? and : are just separators
                            (actual.getLastConnection()!=null?
                            "'"+actual.getLastConnection()+"'":
                            "null")
                            +",'BASIC')");
            registerStatement.close();
            return actual;
         } catch (InvalidGenreException ex) {
                System.err.println(ex.getMessage());
                registerUser(sc,conn);
        } catch (SQLException ex) {
             System.err.println("SQL exception");
             ex.printStackTrace();
        }
         return null;
    }
    
    public static User loginUser(Scanner sc, Connection conn){
        try {
            System.out.println("What's your username?");
            String username=sc.nextLine();
            System.out.println("And your password?");
            String password=sc.nextLine();
            //First thing to do would be to retrieve the whole
            //user info from db.
             //We're using the wildcard (something you can replace
            //by anything) ? in the following statement. the String 
            //array is used to give values to the wildcards as they
            //appear (position 0 replaces the first ? , position 1 replaces
            //the second ? and so on...
            PreparedStatement loginStatement=
                    conn.prepareStatement(
                            "select * from user where name=? "
                                    + "and password=? ");
                            loginStatement.setString(1, username);
                            loginStatement.setString(2, password);
            ResultSet foundUser=loginStatement.executeQuery();

            if(foundUser.next()){ //User is found
                 System.out.println("Login succesful!");            
                    //we get our recovered user and put it into a Java Object
                AccessLevel al=null;
                if(foundUser.getString("accessLevel")
                        .equals("BASIC")){
                    al=BASIC;
                }else if (foundUser.getString("accessLevel")
                        .equals("PREMIUM")){
                    al=PREMIUM;
                }else{
                    System.err.println("Invalid Access Level on Database");
                }
               //7 - Create or reuse a Statement to query the pokemon linked to the user
                // (select pokemonId from pokemonUser where user= ?)
                //8 - Create or reuse a Statement to query all pokemon data for the recovered id
                // (select * from pokemon where id= ?)
                //9 - Create a Pokemon java object with all recovered data from the previous step (or null if the user doesn't have any)
                //10 - (only if step 8 didn't return null) Use the newly created Pokemon variable in the User's constructor last argument  
                User actual=new User(foundUser.getString("name"),
                foundUser.getString("genre").charAt(0),
                foundUser.getString("description"),
                foundUser.getString("password"),
                al,null);
                foundUser.close();
                System.out.println("Hello, "+actual.getName()+", login successful!");
               
               return actual;
            }else{ //User is not found
                    System.err.println("Invalid username and password");
                    loginUser(sc,conn);
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception");
            ex.printStackTrace();
        } catch (InvalidGenreException ex) {
            //This should never happen because
            //we make sure when we register the user
            //that genre can only have m or f
            System.err.println(ex.getMessage());
            System.err.println("Holy Shit! This should have never happened!");
            //We use an assert because this is a theoretically impossible situation
            assert true: "Holy Shit! This should have never happened!";
        }
        //If login is incorrect, return null user
        return null;
    }
    
}
