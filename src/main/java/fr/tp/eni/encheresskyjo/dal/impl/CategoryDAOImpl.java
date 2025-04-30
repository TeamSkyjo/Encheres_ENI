package fr.tp.eni.encheresskyjo.dal.impl;

import fr.tp.eni.encheresskyjo.bo.Category;
import fr.tp.eni.encheresskyjo.dal.CategoryDAO;
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

/**
 * @author TeamSkyjo
 * @version 1.0
 */
@Repository
public class CategoryDAOImpl implements CategoryDAO {

    private static final String SELECT_ALL = "SELECT no_categorie, libelle FROM CATEGORIES;";
    private static final String SELECT_BY_ID = "SELECT no_categorie, libelle FROM CATEGORIES WHERE no_categorie = :categoryId;";
    private static final String INSERT = "INSERT INTO CATEGORIES VALUES (:label);";
    private static final String UPDATE = "UPDATE CATEGORIES SET libelle = :label WHERE no_categorie = :categoryId;";
    private static final String DELETE = "DELETE FROM CATEGORIES WHERE no_categorie = :categoryId;";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public CategoryDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Category category) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("label", category.getLabel());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                INSERT,
                mapSqlParameterSource,
                keyHolder
        );

        category.setCategoryId(keyHolder.getKey().intValue());
    }

    @Override
    public List<Category> readAll() {

        return jdbcTemplate.query(
                SELECT_ALL,
                new CategoryRowMapper()
        );
    }

    @Override
    public Category read(int categoryId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("categoryId", categoryId);

        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ID,
                mapSqlParameterSource,
                new CategoryRowMapper()
        );
    }

    @Override
    public void delete(int categoryId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("categoryId", categoryId);

        namedParameterJdbcTemplate.update(
                DELETE,
                mapSqlParameterSource
        );
    }

    @Override
    public void update(Category category) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("label", category.getLabel());
        mapSqlParameterSource.addValue("categoryId", category.getCategoryId());

        namedParameterJdbcTemplate.update(
                UPDATE,
                mapSqlParameterSource
        );
    }
}

class CategoryRowMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category();
        category.setCategoryId(rs.getInt("no_categorie"));
        category.setLabel(rs.getString("libelle"));

        return category;
    }
}