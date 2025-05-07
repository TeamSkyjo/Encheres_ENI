package fr.tp.eni.encheresskyjo.ihm;

import fr.tp.eni.encheresskyjo.bll.UserService;
import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dto.UserGeneralDTO;
import fr.tp.eni.encheresskyjo.dto.UserUpdateDTO;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // To do in every Controller
    @ModelAttribute("loggedUser")
    public User loggedUser(Principal principal) {
        if (principal != null) {
            return userService.getByUsername(principal.getName());
        }
        return null;
    }

    @GetMapping("/profil")
    public String displayUserProfile(
            @RequestParam(name="username", required=false) String username,
            Principal principal,
            Model model) {
        try {
            UserGeneralDTO user = userService.loadUser(username);
            model.addAttribute("user", user);
            return "user/profile";
        } catch (Exception e) {
            UserGeneralDTO user = userService.loadUser(principal.getName());

            model.addAttribute("user", user);
            return "user/my-profile";
        }
    }

    @PostMapping("/profil")
    public String updateProfile(
            @ModelAttribute("user")UserUpdateDTO userUpdateDTO,
            BindingResult bindingResult,
            Principal principal,
            Model model
    ) {

        User user = new User();
        user = userService.getByUsername(principal.getName());
        userUpdateDTO.setUserId(user.getUserId());
        System.out.println("-------------------------");
        System.out.println(user);
        System.out.println(principal);
        System.out.println(userUpdateDTO.getUserId());
        if (!bindingResult.hasErrors()) {
            try {

                userService.updateUser(userUpdateDTO);
                System.out.println("update successful");
                return "redirect:/profil?success";
            } catch (BusinessException exception) {
                exception.getKeys().forEach(key -> {
                    ObjectError error = new ObjectError("globalError", key);
                    bindingResult.addError(error);
                });

                System.out.println(exception.getKeys());
                model.addAttribute("profile");
                return "user/profile";
            }
        } else {
            return "user/profile";
        }

    }

}
