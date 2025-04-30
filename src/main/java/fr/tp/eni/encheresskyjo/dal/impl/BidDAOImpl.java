package fr.tp.eni.encheresskyjo.dal.impl;

import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Bid;
import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dal.BidDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class BidDAOImpl implements BidDAO {

    private static final String SELECT_ALL = "SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM ENCHERES;";
    private static final String SELECT_BY_ARTICLE_ID = "SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM ENCHERES WHERE no_article = :articleId;";
    private static final String SELECT_BY_USER_ID = "SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM ENCHERES WHERE no_utilisateur = :userId;";
    private static final String INSERT = "INSERT INTO ENCHERES VALUES (:buyerId, :articleId, :bidDate, :bidPrice);";
    private static final String DELETE = "DELETE FROM ENCHERES WHERE no_utilisateur = :userId AND no_article = :articleId;";


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BidDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void create(Bid bid) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("buyerId", bid.getBuyer().getUserId());
        mapSqlParameterSource.addValue("articleId", bid.getArticle().getArticleId());
        mapSqlParameterSource.addValue("bidDate", bid.getBidDate());
        mapSqlParameterSource.addValue("bidPrice", bid.getBidPrice());

        namedParameterJdbcTemplate.update(
                INSERT,
                mapSqlParameterSource
        );
    }

    @Override
    public List<Bid> readAll() {
        return namedParameterJdbcTemplate.query(
                SELECT_ALL,
                new BidRowMapper()
        );
    }

    @Override
    public List<Bid> readByArticle(int articleId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("articleId", articleId);

        return namedParameterJdbcTemplate.query(
                SELECT_BY_ARTICLE_ID,
                mapSqlParameterSource,
                new BidRowMapper()
        );
    }

    @Override
    public List<Bid> readByUser(int userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);

        return namedParameterJdbcTemplate.query(
                SELECT_BY_USER_ID,
                mapSqlParameterSource,
                new BidRowMapper()
        );
    }

    @Override
    public void delete(Bid bid) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", bid.getBuyer().getUserId());
        mapSqlParameterSource.addValue("articleId", bid.getArticle().getArticleId());

        namedParameterJdbcTemplate.update(
                DELETE,
                mapSqlParameterSource
        );
    }
}

class BidRowMapper implements RowMapper<Bid> {
    @Override
    public Bid mapRow(ResultSet rs, int rowNum) throws SQLException {
        Bid bid = new Bid();
        bid.setBidDate(rs.getObject("date_enchere", LocalDate.class));
        bid.setBidPrice(rs.getInt("montant_enchere"));

        // TODO : See Proposition_ENI for association in BidService
        // Link with User/buyer
        User user = new User();
        user.setUserId(rs.getInt("no_utilisateur"));
        bid.setBuyer(user);

        // Link with Article
        Article article = new Article();
        article.setArticleId(rs.getInt("no_article"));
        bid.setArticle(article);

        return bid;
    }

//    /**
//     * Méthode privée pour centraliser l'association entre un film et son genre et
//     * réalisateur
//     *
//     * @param film
//     */
//    private void chargerGenreEtRealisateur1Film(Film f) {
//        Participant realisateur = participantDAO.read(f.getRealisateur().getId());
//        f.setRealisateur(realisateur);
//        Genre genre = genreDAO.read(f.getGenre().getId());
//        f.setGenre(genre);
//    }
}
