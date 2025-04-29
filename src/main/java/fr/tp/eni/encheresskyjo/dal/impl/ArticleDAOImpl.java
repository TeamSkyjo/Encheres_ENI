package fr.tp.eni.encheresskyjo.dal.impl;

import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Category;
import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dal.ArticleDAO;
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

@Repository
public class ArticleDAOImpl implements ArticleDAO {

    // sans url_image
    private static final String INSERT =
            "INSERT INTO ARTICLES (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) " +
                    "VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie);";

    private static final String SELECT_BY_ID =
            "SELECT nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, url_image, u.pseudo, c.libelle\n" +
                    "FROM ARTICLES a\n" +
                    "INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur\n" +
                    "INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie\n" +
                    "WHERE no_article = :no_article;";

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
        mapSqlParameterSource.addValue("date_debut_encheres", article.getBidStartDate());
        mapSqlParameterSource.addValue("date_fin_encheres", article.getBidEndDate());
        mapSqlParameterSource.addValue("prix_initial", article.getStartingPrice());
        mapSqlParameterSource.addValue("prix_vente", article.getSellingPrice());
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
    public void update(int articleId, Article article) {

    }

    @Override
    public Article read(int articleId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article", articleId);


        Article article = namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ID,
                mapSqlParameterSource,
                new ArticleRowMapper()
        );

        return article;

    }

    @Override
    public List<Article> readAll() {
        return List.of();
    }

    @Override
    public void delete() {

    }

    @Override
    public void updateSellingPrice() {

    }

    @Override
    public void readSalesInProgress() {

    }

    @Override
    public void readWaitingSales() {

    }

    @Override
    public void readEndedSales() {

    }

    @Override
    public void addImage() {

    }

    @Override
    public void readByCategory() {

    }


    class ArticleRowMapper implements RowMapper<Article> {

        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            Article article = new Article();
            article.setArticleId(rs.getInt("no_article"));
            article.setArticleName(rs.getString("nom_article"));
            article.setDescription(rs.getString("description"));
            article.setBidStartDate(rs.getDate("date_debut_encheres").toLocalDate());
            article.setBidEndDate(rs.getDate("date_fin_encheres").toLocalDate());
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
}
