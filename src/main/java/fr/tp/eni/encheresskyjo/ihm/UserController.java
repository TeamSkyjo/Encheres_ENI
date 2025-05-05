package fr.tp.eni.encheresskyjo.ihm;

import fr.tp.eni.encheresskyjo.bll.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/inscription")
    public String displayRegistrationForm(Model model) {

        return "/register";
    }

    @GetMapping("/profil")
    public String displayUserProfile(Model model) {

        return "/user/profile";
    }
}
