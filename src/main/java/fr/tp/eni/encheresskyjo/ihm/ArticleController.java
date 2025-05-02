package fr.tp.eni.encheresskyjo.ihm;

import fr.tp.eni.encheresskyjo.bll.ArticleService;
import fr.tp.eni.encheresskyjo.bo.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    //mapping

    @GetMapping("/")
    public String home(Model model) {
        List<Article> articles = articleService.getArticles();
        model.addAttribute("articles", articles);
        System.out.println("Page d'accueil");
        return "index";
    }

}
