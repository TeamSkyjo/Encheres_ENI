package fr.tp.eni.encheresskyjo.ihm;

import fr.tp.eni.encheresskyjo.bll.UserService;
import fr.tp.eni.encheresskyjo.dto.UserGeneralDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    /**
     * map to another user's profile WHEN USER IS LOGGED IN.
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/profil")
    public String displayUserProfile(
            @RequestParam(name="username", required=true) String username,
            Model model) {
        UserGeneralDTO user = this.userService.loadUser(username);
        model.addAttribute("user", user);

        return "/user/profile";
    }
}
