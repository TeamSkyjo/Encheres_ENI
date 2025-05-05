package fr.tp.eni.encheresskyjo.ihm;

import fr.tp.eni.encheresskyjo.bll.ArticleService;
import fr.tp.eni.encheresskyjo.bll.BidService;
import fr.tp.eni.encheresskyjo.bll.CategoryService;
import fr.tp.eni.encheresskyjo.bll.UserService;
import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.ArticleStatus;
import fr.tp.eni.encheresskyjo.bo.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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
    private CategoryService categoryService;
    private BidService bidService;
    private UserService userService;

    public ArticleController(ArticleService articleService, CategoryService categoryService, BidService bidService, UserService userService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.bidService = bidService;
        this.userService = userService;
    }

    //mapping
    @GetMapping("/")
    public String home(Model model) {
        List<Article> articles = articleService.getArticles();
        model.addAttribute("articles", articles);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/searchBidsOrSales")
    public String searchByBidsOrSales(@RequestParam(required = false) String bidsorsales,
                                      Model model) {
        model.addAttribute("bidsorsales", bidsorsales);
        List<Article> articles = articleService.getArticles();
        model.addAttribute("articles", articles);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/searchArticles")
    public String searchByArticles(@RequestParam(required = false) String pattern,
                                   @RequestParam(required = false) String categoryId,
                                   Model model) {
        List<Article> articles = new ArrayList<>();
        if (categoryId != null && categoryId.equals("0")) {
            articles = articleService.getFilteredArticles(pattern,null);
        }
        else {
            Category category = categoryService.getCategoryById(Integer.parseInt(categoryId));
            articles = articleService.getFilteredArticles(pattern, category);
        }
        model.addAttribute("articles", articles);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
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
