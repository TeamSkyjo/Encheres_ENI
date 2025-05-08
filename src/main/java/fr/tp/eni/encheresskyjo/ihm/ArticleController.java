package fr.tp.eni.encheresskyjo.ihm;

import fr.tp.eni.encheresskyjo.bll.ArticleService;
import fr.tp.eni.encheresskyjo.bll.CategoryService;
import fr.tp.eni.encheresskyjo.bll.BidService;
import fr.tp.eni.encheresskyjo.bll.CategoryService;
import fr.tp.eni.encheresskyjo.bll.UserService;
import fr.tp.eni.encheresskyjo.bo.*;
import fr.tp.eni.encheresskyjo.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author TeamSkyjo
 * @Version 1.0
 * Class to control map the views for the article webpages
 */
@Controller
public class ArticleController {

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

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

    // To do in every Controller
    @ModelAttribute("loggedUser")
    public User loggedUser(Principal principal) {
        if (principal != null) {
            return userService.getByUsername(principal.getName());
        }
        return null;
    }

    @GetMapping("/")
    public String redirectHome() {
        return "redirect:/encheres";
    }

    @GetMapping("/encheres")
    public String home(Model model) {
        List<Article> articles = articleService.getArticles().stream()
                .filter(a->a.readStatus().equals(ArticleStatus.ONGOING)).collect(Collectors.toList());
        model.addAttribute("articles", articles);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/rechercheUtilisateur")
    public String searchByBidsOrSales(@RequestParam(required = false) String bidsorsales,
                                      @RequestParam(required = false) String sales,
                                      @RequestParam(required = false) String bids,
                                      Principal principal,
                                      Model model) {
        List<Article> articles = new ArrayList<>();
        List<Article> filteredArticles = new ArrayList<>();
        User user = userService.getByUsername(principal.getName());
        if (bidsorsales.equals("bids")) {
            if (bids == null || bids.isBlank()) {
                bids = "ongoing";
            }
            if ("all".equals(bids)) {
                articles = articleService.getArticles();
                filteredArticles = articles.stream()
                        .filter(a->a.readStatus().equals(ArticleStatus.ONGOING))
                        .collect(Collectors.toList());
            }
            else if ("win".equals(bids)) {
                filteredArticles = bidService.getBidsWonByUser(user.getUserId());
            }
            else {
                articles = bidService.getBidsByUser(user.getUserId());
                filteredArticles = articles.stream()
                        .filter(a->a.readStatus().equals(ArticleStatus.ONGOING))
                        .collect(Collectors.toList());
            }
        }
        else if (bidsorsales.equals("sales")) {
            articles = articleService.getArticles().stream()
                    .filter(a->a.getSeller().equals(user))
                    .collect(Collectors.toList());
            if (sales == null || sales.isBlank()) {
                sales = "ongoing"; // valeur par défaut
            }
            if ("ended".equals(sales)) {
                filteredArticles=articles.stream()
                        .filter(a->a.readStatus().equals(ArticleStatus.ENDED))
                        .collect(Collectors.toList());
            }
            else if ("not_started".equals(sales)) {
                filteredArticles=articles.stream()
                        .filter(a->a.readStatus().equals(ArticleStatus.NOT_STARTED))
                        .collect(Collectors.toList());
            }
            else {
                filteredArticles=articles.stream()
                        .filter(a->a.readStatus().equals(ArticleStatus.ONGOING))
                        .collect(Collectors.toList());
            }
        }
        else {
            articles = articleService.getArticles();
            filteredArticles = articles.stream()
                    .filter(a->a.readStatus().equals(ArticleStatus.ONGOING))
                    .collect(Collectors.toList());
        }
        model.addAttribute("bidsorsales", bidsorsales);
        model.addAttribute("bids", bids);
        model.addAttribute("sales", sales);
        model.addAttribute("articles", filteredArticles);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/rechercheArticle")
    public String searchByArticles(@RequestParam(required = false) String pattern,
                                   @RequestParam(required = false) String categoryId,
                                   Model model) {
        List<Article> articles = new ArrayList<>();
        if (categoryId != null && categoryId.equals("0")) {
            articles = articleService.getFilteredArticles(pattern, null);
        } else {
            Category category = categoryService.getCategoryById(Integer.parseInt(categoryId));
            articles = articleService.getFilteredArticles(pattern, category);
        }
        List<Article> filteredArticles = articles.stream()
                .filter(a->a.readStatus().equals(ArticleStatus.ONGOING)).collect(Collectors.toList());
        model.addAttribute("articles", filteredArticles);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/article/details")
    public String displayArticle(
            @RequestParam(name = "id", required = true) int id,
            Principal principal,
            Model model
    ) {
        User user = userService.getByUsername(principal.getName());
        Article article = this.articleService.getArticleById(id);
        Bid bestBid = bidService.getBestBid(article);
        model.addAttribute("article", article);
        if (article.getSeller().equals(user)) {
            return "article/seller-bid-ended";
        }
        if (article.getEndDate().isBefore(LocalDate.now()) && user.getUserId() == bestBid.getBuyer().getUserId())
        {
            return "article/winning-bid";
        }
        return "article/details";
    }

    @PostMapping("/article/details")
    public String placeBid(
            @ModelAttribute Article article,
            @RequestParam(value = "bidPrice", required = false) Integer bidPrice,
            @RequestParam(value = "articleId", required = false) Integer articleId,
            @RequestParam String action,
            BindingResult bindingResult,
            Model model,
            Principal principal
    ){
        article = articleService.getArticleById(articleId);
        User user = userService.getByUsername(principal.getName());
        if ("seller".equals(action)) {
            try {
                articleService.deleteArticle(article, user);
                return "redirect:/encheres";
            } catch (BusinessException e) {
                e.getKeys().forEach(key -> {
                    ObjectError error = new ObjectError("globalError", key);
                    bindingResult.addError(error);
                });
            }
        }
        else if ("buyer".equals(action)) {
            try {
                bidService.closeBid(article);
                return "redirect:/encheres";
            } catch (BusinessException e) {
                e.getKeys().forEach(key -> {
                    ObjectError error = new ObjectError("globalError", key);
                    bindingResult.addError(error);
                });
            }
        }

        else {
            try {
                bidService.createBid(user, article, bidPrice);
                model.addAttribute("bidPrice", bidPrice);
                return "redirect:/encheres";

            } catch (BusinessException e) {
                e.getKeys().forEach(key -> {
                    ObjectError error = new ObjectError("globalError", key);
                    bindingResult.addError(error);
                });
            }
        }
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
            // ajout
            article.setEndDate(article.getEndDate());

            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("article", article);
            return "article/create";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/article/creer")
    public String createArticle (
            @RequestParam("imageFile") MultipartFile file,
            @Valid @ModelAttribute("article") Article article,
            BindingResult bindingResult,
            Model model,
            Principal principal
    ) throws IOException {
        if (!bindingResult.hasErrors()) {
            try {
                StringBuilder fileNames = new StringBuilder();
                Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
                fileNames.append(file.getOriginalFilename());
                Files.write(fileNameAndPath, file.getBytes());
//                model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
                article.setImageUrl("/uploads/" + fileNames);
                article.setSeller(userService.getByUsername(principal.getName()));
                System.out.println(article);
                articleService.createArticle(article);
            } catch (BusinessException exception) {
                exception.getKeys().forEach(key -> {
                    ObjectError error = new ObjectError("globalError", key);
                    bindingResult.addError(error);
                });
                List<Category> categories = categoryService.getAllCategories();
                model.addAttribute("categories", categories);
                System.out.println(exception.getKeys());
                return "article/create";
            }
        } else {
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            return "article/create";
        }
        return "redirect:/encheres";
    }


}
