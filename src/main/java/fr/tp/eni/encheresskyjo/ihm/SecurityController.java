package fr.tp.eni.encheresskyjo.ihm;

import fr.tp.eni.encheresskyjo.bll.UserService;
import fr.tp.eni.encheresskyjo.dto.UserCreateDTO;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import fr.tp.eni.encheresskyjo.bll.UserService;
import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;

@Controller
//@SessionAttributes({"membreSession"})
public class SecurityController {
    private final UserService userService;


    public SecurityController(
            UserService userService
    ) {
        this.userService = userService;
    }

    // Add loggedUser in the Model
    // To do in every Controller
    @ModelAttribute("loggedUser")
    public User loggedUser(Principal principal) {
        if (principal != null) {
            return userService.getByUsername(principal.getName());
        }
        return null;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login_success")
    public String loginSuccess() {
        return "redirect:/";
    }

    @GetMapping("/inscription")
    public String displayRegistrationForm(Model model) {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        model.addAttribute("userCreateDTO", userCreateDTO);
        return "/register";
    }

    @PostMapping("/inscription")
    public String register(
            @ModelAttribute("userCreateDTO") UserCreateDTO userCreateDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (!bindingResult.hasErrors()) {
            try {
                userService.createUser(userCreateDTO);
                return "redirect:/login";
            } catch (BusinessException exception) {
                exception.getKeys().forEach(key -> {
                    ObjectError error = new ObjectError("globalError", key);
                    bindingResult.addError(error);
                });
                System.out.println(exception.getKeys());
                return "/register";
            }
        } else {
            return "/register";
        }
    }
}
