package fr.tp.eni.encheresskyjo.ihm;

import fr.tp.eni.encheresskyjo.bll.UserService;
import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.converter.UserToUserUpdateDtoConverter;
import fr.tp.eni.encheresskyjo.dto.UserGeneralDTO;
import fr.tp.eni.encheresskyjo.dto.UserUpdateDTO;
import fr.tp.eni.encheresskyjo.exception.BusinessError;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;
    private UserToUserUpdateDtoConverter userToUserUpdateDtoConverter;

    public UserController(UserService userService, UserToUserUpdateDtoConverter userToUserUpdateDtoConverter) {
        this.userService = userService;
        this.userToUserUpdateDtoConverter = userToUserUpdateDtoConverter;
    }

    // To do in every Controller
    @ModelAttribute("loggedUser")
    public User loggedUser(Principal principal) {
        if (principal != null) {
            return userService.getByUsername(principal.getName());
        }
        return null;
    }

    @ModelAttribute("user")
    public UserUpdateDTO initializeUserDTO(Principal principal) {
        User user = userService.getByUsername(principal.getName());
        return userToUserUpdateDtoConverter.convert(user);
    }

    @GetMapping("/profil")
    public String displayUserProfile(
            @RequestParam(name = "username", required = false) String username,
            Principal principal,
            Model model) {
        if (username == null || username.isBlank()) {
            username = principal.getName();
        }

        UserGeneralDTO userDto = userService.loadUser(username);
        model.addAttribute("user", userDto);

        if (principal.getName().equals(username)) {
            model.addAttribute("action", "my-profile");
            return "/user/my-profile";
        }

        model.addAttribute("action", "profile");
        return "/user/profile";
    }

    @GetMapping("/profil/modifier")
    public String showUpdateForm(
            Principal principal,
            Model model
    ) {
        User user = userService.getByUsername(principal.getName());
        UserUpdateDTO userUpdateDTO = userToUserUpdateDtoConverter.convert(user);
        model.addAttribute("user", userUpdateDTO);
        model.addAttribute("action", "update");
        return "/user/update";
    }


    @PostMapping("/profil/modifier")
    public String updateProfile(
            @ModelAttribute("user") UserUpdateDTO userUpdateDTO,
            BindingResult bindingResult,
 //           @RequestParam(value = "action", required = false) String action,
            Principal principal,
            Model model,
            HttpServletRequest request
    ) {
        System.out.println("<<<<<<<<<<<<<<<<<<");

        if (bindingResult.hasErrors()) {
            System.out.println("Erreur de validation : " + bindingResult.getAllErrors());
            model.addAttribute("action", "update");
            return "/user/update";
        }

        try {
            userService.updateUser(userUpdateDTO);
            System.out.println("update successful");

            // invalidate session if new username
            if (!principal.getName().equals(userUpdateDTO.getUsername())) {
                request.getSession().invalidate();
                return "redirect:/login?usernameChanged=true";
            }

            // invalidate session if newPassword
            if (userUpdateDTO.getNewPassword() != null && !userUpdateDTO.getNewPassword().isEmpty()) {
                request.getSession().invalidate();
                return "redirect:/login?passwordChanged=true";
            }

            // reload updated user
            User updatedUser = userService.getByUsername(userUpdateDTO.getUsername());
            model.addAttribute("user", updatedUser);

            return "redirect:/profil";
        } catch (BusinessException exception) {

            for (BusinessError error : exception.getErrors()) {
                if (error.getField() == null) {
                    bindingResult.addError(new ObjectError("globalError", error.getMessageCode()));
                } else {
                    bindingResult.addError(new FieldError("user", error.getField(), error.getMessageCode()));
                }
            }

            System.out.println("Error while updating : " + exception.getKeys());
            model.addAttribute("action", "update");
            model.addAttribute("user", userUpdateDTO);
            return "/user/update";
        }
    }
}
