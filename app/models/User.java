package models;

import play.db.jpa.Model;

import javax.persistence.Entity;

@Entity
public class User extends Model {
    public String username;
    public String password;
    public Boolean isAdmin;

    private User(String username, String password, boolean b) {
        this.username=username;
        this.password=password;
        this.isAdmin=b;
    }


    public static User createAdmin(String username, String password){
        User u=new User(username,password,true);
        return u;
    }

    public static User createUser(String username, String password){
        User u=new User(username,password,false);
        return u;
    }

    public static User authenticate(String username, String password) {
        return find("byUsernameAndPassword",username,password).first();
    }
}
