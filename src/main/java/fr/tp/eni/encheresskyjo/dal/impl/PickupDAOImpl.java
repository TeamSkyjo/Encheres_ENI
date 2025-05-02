package fr.tp.eni.encheresskyjo.dal.impl;

import fr.tp.eni.encheresskyjo.bo.Article;
import fr.tp.eni.encheresskyjo.bo.Pickup;
import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dal.PickupDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author TeamSkyjo
 * @Version 1.0
 * Class to connect the database to the "Pickup" object.
 */

@Repository
public class PickupDAOImpl implements PickupDAO {

    //DEPENDENCIES INJECTIONS
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PickupDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    //SQL REQUESTS
    private static final String CREATE = "INSERT INTO RETRAITS VALUES (:no_article, :rue, :code_postal, :ville);";
    private static final String UPDATE = "UPDATE RETRAITS SET rue=:rue, code_postal=:code_postal, ville=:ville WHERE no_article = :no_article;";
    private static final String READ = "SELECT * FROM RETRAITS WHERE no_article = :no_article;";

    @Override
    public void create(int articleId, Pickup pickup) {
        MapSqlParameterSource mapSqlParameterSource = getSource(articleId, pickup);
        namedParameterJdbcTemplate.update(CREATE, mapSqlParameterSource);
    }

    @Override
    public Pickup read(int articleId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article", articleId);
        Pickup pickup = namedParameterJdbcTemplate.queryForObject(
                READ,
                mapSqlParameterSource,
                new PickupRowMapper()
        );
        return pickup;
    }

    @Override
    public void update(int articleId, Pickup pickup) {
        MapSqlParameterSource mapSqlParameterSource = getSource(articleId, pickup);
        namedParameterJdbcTemplate.update(UPDATE, mapSqlParameterSource);
    }

    private static MapSqlParameterSource getSource(int articleId, Pickup pickup) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article", articleId);
        mapSqlParameterSource.addValue("rue", pickup.getStreet());
        mapSqlParameterSource.addValue("code_postal", pickup.getZip());
        mapSqlParameterSource.addValue("ville", pickup.getCity());
        return mapSqlParameterSource;
    }
}

class PickupRowMapper implements RowMapper<Pickup> {

    @Override
    public Pickup mapRow(ResultSet rs, int rowNum) throws SQLException {

        Pickup pickup = new Pickup();
        pickup.setStreet(rs.getString("rue"));
        pickup.setZip(rs.getString("code_postal"));
        pickup.setCity(rs.getString("ville"));

        Article article = new Article();
        article.setArticleId(rs.getInt("no_article"));
        article.setPickup(pickup);

        return pickup;
    }
}
