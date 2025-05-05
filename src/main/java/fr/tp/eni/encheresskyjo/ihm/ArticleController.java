package fr.tp.eni.encheresskyjo.ihm;

import fr.tp.eni.encheresskyjo.bll.ArticleService;
import fr.tp.eni.encheresskyjo.bll.UserService;
import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.ArticleStatus;
import fr.tp.eni.encheresskyjo.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
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

    //mapping
    @GetMapping("/")
    public String home(Model model) {
        List<Article> articles = articleService.getArticles();
        model.addAttribute("articles", articles);
        System.out.println("Page d'accueil");
        return "index";
    }

    @GetMapping("/article")
    public String displayArticle(
            @RequestParam(name="id", required = true) int id,
            Model model
    ) {
        Article article =  this.articleService.getArticleById(id);
        model.addAttribute("article", article);
        return "article/details";
    }

}
