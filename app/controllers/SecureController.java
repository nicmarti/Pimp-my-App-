package controllers;


import models.User;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;

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

    public static void authenticate(@Required(message = "username est obligatoire") String username,
                                    @Required(message = "mot de passe est obligatoire") String password) {
        if (validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request
            login();
        }

        User u = User.authenticate(username, password);
        if (u == null) {
            flash.error("Utilisateur non trouvé ou mot de passe incorrect");
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
