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

package controllers;


import models.User;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;

/**
 * A Simple controller to demonstrate how to protect a part of your site.
 */
public class SecureController extends Controller {

    @Before(unless = {"login", "logout", "authenticate"})
    static void checkLogin() {
        if (!session.contains("user.id")) {
            login();
        }
    }

    public static void login() {
        render();
    }

    public static void logout() {
        session.clear();
        login();
    }

    public static void authenticate(@Required(message = "username is mandatory") String username,
                                    @Required(message = "password is mandatory") String password) {
        if (validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request
            login();
        }

        User u = User.authenticate(username, password);
        if (u == null) {
            flash.error("User not found or incorrect password");
            login();
        }
        session.put("user.id", u.id);
        protectedPage();
    }

    public static void protectedPage() {
        Long id = Long.parseLong(session.get("user.id"));
        User user = User.findById(id);
        render(user);
    }

}
