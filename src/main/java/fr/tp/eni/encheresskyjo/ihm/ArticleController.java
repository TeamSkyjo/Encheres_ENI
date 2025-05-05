package fr.tp.eni.encheresskyjo.ihm;

import fr.tp.eni.encheresskyjo.bll.ArticleService;
import fr.tp.eni.encheresskyjo.bll.CategoryService;
import fr.tp.eni.encheresskyjo.bll.UserService;
import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.ArticleStatus;
import fr.tp.eni.encheresskyjo.bo.Category;
import fr.tp.eni.encheresskyjo.bo.User;
import jakarta.validation.Valid;
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


/**
 * @Author TeamSkyjo
 * @Version 1.0
 * Class to control map the views for the article webpages
 */


@Controller
public class ArticleController {

    //Dependencies injection
    private ArticleService articleService;
    private UserService userService;
    private CategoryService categoryService;

    public ArticleController(ArticleService articleService, UserService userService, CategoryService categoryService) {
        this.articleService = articleService;
        this.userService = userService;
        this.categoryService = categoryService;
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

    //mapping
    @GetMapping("/")
    public String home(Model model) {
        List<Article> articles = articleService.getArticles();
        model.addAttribute("articles", articles);
        System.out.println("Page d'accueil");
        return "index";
    }

    @GetMapping("/article/details")
    public String displayArticle(
            @RequestParam(name="id", required = true) int id,
            Model model
    ) {
        Article article =  this.articleService.getArticleById(id);
        model.addAttribute("article", article);
        return "article/details";
    }

    @GetMapping("/article/creer")
    public String displayArticleForm(
            Model model,
            Principal principal
    ) {
        if (principal != null) {
            Article article = new Article();
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("article", article);
            return "article/create";
        } else {
            return "redirect:/";
        }
    }

//    @PostMapping("/films/creer")
//    public String creerFilm(
//            @ModelAttribute("membreSession") Membre membreSession,
//            @Valid @ModelAttribute("film") Film film,
//            BindingResult bindingResult,
//            Model model
//    ) {
//        if (membreSession.isAdmin()) {
//            if (!bindingResult.hasErrors()) {
//                try {
//                    filmService.creerFilm(film, membreSession);
//                } catch (BusinessException exception) {
//                    exception.getKeys().forEach(key -> {
//                        ObjectError error = new ObjectError("globalError", key);
//                        bindingResult.addError(error);
//                    });
//                    List<Genre> genres = filmService.consulterGenres();
//                    List<Participant> participants = filmService.consulterParticipants();
//                    model.addAttribute("genres", genres);
//                    model.addAttribute("participants", participants);
//                    return "/film/creer";
//                }
//            } else {
//                List<Genre> genres = filmService.consulterGenres();
//                List<Participant> participants = filmService.consulterParticipants();
//                model.addAttribute("genres", genres);
//                model.addAttribute("participants", participants);
//                return "/film/creer";
//            }
//        } else {
//            logger.warn("Utilisateur non administrateur");
//        }
//
//        return "redirect:/films";
//    }
}
