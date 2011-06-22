/*
 * Copyright (C) 2011 Nicolas Martignole
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
