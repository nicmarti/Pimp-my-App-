package models;

import play.db.jpa.Model;
import play.libs.Codec;

import javax.persistence.Entity;

@Entity
public class User extends Model {
    public String username;
    public String encryptedPassword;
    public Boolean isAdmin;

    private User(String username, String encryptedPassword, boolean b) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.isAdmin = b;
    }

    public static User createAdmin(String username, String password) {
        User u = new User(username, password, true);
        return u;
    }

    public static User createUser(String username, String password) {
        User u = new User(username, password, false);
        return u;
    }

    public static User authenticate(String username, String password) {
        if (username == null) return null;
        if (password == null) return null;

        User u = find("byUsername",username.trim()).first();
        if (u == null) {
            return null;
        }

        // Return true if the encrypted password equals the password encoded with SHA1
        if(u.encryptedPassword.equals(Codec.hexSHA1(password.trim()))){
            return u;
        }
        return null;
    }
}
