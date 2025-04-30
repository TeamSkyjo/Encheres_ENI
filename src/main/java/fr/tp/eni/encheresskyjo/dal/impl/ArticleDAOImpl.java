package fr.tp.eni.encheresskyjo.dal.impl;

import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Category;
import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dal.ArticleDAO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

    private static final String INSERT =
            "INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, url_image, no_utilisateur, no_categorie) " +
                    "VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :url_image, :no_utilisateur, :no_categorie);";

    private static final String READ_BY_ID =
            "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, \n" +
                    "a.prix_initial, a.prix_vente, a.url_image, \n" +
                    "u.no_utilisateur, u.pseudo AS username, \n" +
                    "c.no_categorie, c.libelle \n" +
                    "FROM ARTICLES a\n" +
                    "INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur\n" +
                    "INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie\n" +
                    "WHERE no_article = :no_article;";

    private static final String READ_ALL =
            "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, \n" +
                    "a.prix_initial, a.prix_vente, a.url_image, \n" +
                    "u.no_utilisateur, u.pseudo AS username, \n" +
                    "c.no_categorie, c.libelle \n" +
                    "FROM ARTICLES a\n" +
                    "INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur\n" +
                    "INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie;";


    private static final String READ_BY_NAME =
            "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, \n" +
                    "a.prix_initial, a.prix_vente, a.url_image, \n" +
                    "u.no_utilisateur, u.pseudo AS username, \n" +
                    "c.no_categorie, c.libelle \n" +
                    "FROM ARTICLES a\n" +
                    "INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur\n" +
                    "INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie\n" +
                    "WHERE a.nom_article LIKE :pattern";

    private static final String READ_BY_CATEGORY =
            "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, \n" +
                    "a.prix_initial, a.prix_vente, a.url_image, \n" +
                    "u.no_utilisateur, u.pseudo AS username, \n" +
                    "c.no_categorie, c.libelle \n" +
                    "FROM ARTICLES a\n" +
                    "INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur\n" +
                    "INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie\n" +
                    "WHERE c.libelle LIKE :libelle";

    // sans le sellingPrice
    private static final String UPDATE =
            "UPDATE ARTICLES \n" +
                    "SET nom_article = :nom_article," +
                    "description = :description, " +
                    "date_debut_encheres = :date_debut_encheres, " +
                    "date_fin_encheres = :date_fin_encheres, " +
                    "prix_initial = :prix_initial, " +
                    "prix_vente = :prix_vente, \n" +
                    "url_image = :url_image " +
                    "WHERE no_article = :no_article";


    private static final String DELETE =
            "DELETE FROM ARTICLES \n" +
                    "WHERE no_article = :no_article";

    private static final String READ_BY_NAME_USER_ID =
            "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.url_image, u.no_utilisateur, u.pseudo AS username, c.no_categorie, c.libelle FROM ARTICLES a INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie WHERE a.no_utilisateur = :userId AND a.nom_article = :articleName;";


    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ArticleDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void create(Article article) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("nom_article", article.getArticleName());
        mapSqlParameterSource.addValue("description", article.getDescription());
        mapSqlParameterSource.addValue("date_debut_encheres", article.getStartDate());
        mapSqlParameterSource.addValue("date_fin_encheres", article.getEndDate());
        mapSqlParameterSource.addValue("prix_initial", article.getStartingPrice());
        mapSqlParameterSource.addValue("prix_vente", article.getSellingPrice());
        mapSqlParameterSource.addValue("url_image", article.getImageUrl());
        mapSqlParameterSource.addValue("no_utilisateur", article.getSeller().getUserId());
        mapSqlParameterSource.addValue("no_categorie", article.getCategory().getCategoryId());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                INSERT,
                mapSqlParameterSource,
                keyHolder,
                new String[]{"no_article"}
        );

        article.setArticleId(keyHolder.getKey().intValue());
    }


    @Override
    public Article readByID(int articleId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article", articleId);


        Article article = namedParameterJdbcTemplate.queryForObject(
                READ_BY_ID,
                mapSqlParameterSource,
                new ArticleRowMapper()
        );

        return article;
    }

    @Override
    public List<Article> readAll() {
        List<Article> articles = jdbcTemplate.query(READ_ALL, new ArticleRowMapper());
        return articles;
    }

    @Override
    public List<Article> readByName(String pattern) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("pattern", "%" + pattern + "%");

        List<Article> articles = namedParameterJdbcTemplate.query(READ_BY_NAME, mapSqlParameterSource, new ArticleRowMapper());
        return articles;
    }


    @Override
    public List<Article> readByCategory(String libelle) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("libelle", "%" + libelle + "%");

        List<Article> articles = namedParameterJdbcTemplate.query(READ_BY_CATEGORY, mapSqlParameterSource, new ArticleRowMapper());
        return articles;

    }

    @Override
    public void update(Article article) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article", article.getArticleId());
        mapSqlParameterSource.addValue("nom_article", article.getArticleName());
        mapSqlParameterSource.addValue("description", article.getDescription());
        mapSqlParameterSource.addValue("date_debut_encheres", article.getStartDate());
        mapSqlParameterSource.addValue("date_fin_encheres", article.getEndDate());
        mapSqlParameterSource.addValue("prix_initial", article.getStartingPrice());
        mapSqlParameterSource.addValue("prix_vente", article.getSellingPrice());
        mapSqlParameterSource.addValue("url_image", article.getImageUrl());

        namedParameterJdbcTemplate.update(UPDATE, mapSqlParameterSource);

    }

    @Override
    public void delete(int articleId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article", articleId);

        namedParameterJdbcTemplate.update(DELETE, mapSqlParameterSource);

    }

    @Override
    public boolean isArticleUnique(Article article) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", article.getSeller().getUserId());
        mapSqlParameterSource.addValue("articleName", article.getArticleName());

        List<Article> articles = namedParameterJdbcTemplate.query(
                READ_BY_NAME_USER_ID,
                mapSqlParameterSource,
                new ArticleRowMapper()
        );

        return articles.isEmpty();
    }
}

class ArticleRowMapper implements RowMapper<Article> {

    @Override
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Article article = new Article();
        article.setArticleId(rs.getInt("no_article"));
        article.setArticleName(rs.getString("nom_article"));
        article.setDescription(rs.getString("description"));
        article.setStartDate(rs.getDate("date_debut_encheres").toLocalDate());
        article.setEndDate(rs.getDate("date_fin_encheres").toLocalDate());
        article.setStartingPrice(rs.getInt("prix_initial"));
        article.setSellingPrice(rs.getInt("prix_vente"));
        article.setImageUrl(rs.getString("url_image"));

        User seller = new User();
        seller.setUserId(rs.getInt("no_utilisateur"));
        seller.setUsername(rs.getString("username"));
        article.setSeller(seller);

        Category category = new Category();
        category.setCategoryId(rs.getInt("no_categorie"));
        category.setLabel(rs.getString("libelle"));
        article.setCategory(category);

        return article;
    }
}
