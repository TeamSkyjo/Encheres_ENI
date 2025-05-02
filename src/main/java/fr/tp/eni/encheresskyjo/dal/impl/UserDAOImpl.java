package fr.tp.eni.encheresskyjo.dal.impl;

import fr.tp.eni.encheresskyjo.bo.User;
import fr.tp.eni.encheresskyjo.dal.UserDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author TeamSkyjo
 * @Version 1.0
 * Class to connect the database to the "User" object.
 */

@Repository
public class UserDAOImpl implements UserDAO {

    //DEPENDENCIES INJECTION
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }

    //SQL REQUESTS
    private static final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)" +
            "VALUES (:pseudo, :nom, :prenom, :email, :telephone, :rue, :code_postal, :ville, :mot_de_passe, :credit, :administrateur);";
    private static final String SELECT_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = :no_utilisateur;";
    private static final String SELECT_BY_USERNAME = "SELECT * FROM UTILISATEURS WHERE pseudo = :pseudo;";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM UTILISATEURS WHERE email = :email;";
    private static final String UPDATE_USER = "UPDATE UTILISATEURS SET pseudo = :pseudo, nom = :nom, prenom=:prenom, email=:email, telephone = :telephone, rue=:rue, code_postal=:code_postal, ville=:ville, mot_de_passe=:mot_de_passe, credit= :credit, administrateur=:administrateur\n" +
            "WHERE no_utilisateur = :no_utilisateur;";
    private static final String UPDATE_PASSWORD = "UPDATE UTILISATEURS SET mot_de_passe=:mot_de_passe WHERE email=:email;";
    private static final String DELETE = "DELETE FROM UTILISATEURS WHERE no_utilisateur=:no_utilisateur;";

    //IMPLEMENTATION

    /**
     * Method to create a new user in the database.
     *
     * @param user
     */
    @Override
    public void create(User user) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("pseudo", user.getUsername());
        mapSqlParameterSource.addValue("nom", user.getLastName());
        mapSqlParameterSource.addValue("prenom", user.getFirstName());
        mapSqlParameterSource.addValue("email", user.getEmail());
        mapSqlParameterSource.addValue("telephone", user.getTelephone());
        mapSqlParameterSource.addValue("rue", user.getStreet());
        mapSqlParameterSource.addValue("code_postal", user.getZip());
        mapSqlParameterSource.addValue("ville", user.getCity());
        mapSqlParameterSource.addValue("mot_de_passe", user.getPassword());
        mapSqlParameterSource.addValue("credit", user.getCredit());
        mapSqlParameterSource.addValue("administrateur", user.isAdmin());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                INSERT,
                mapSqlParameterSource,
                keyHolder,
                new String[]{"no_utilisateur"}
        );
        user.setUserId(keyHolder.getKey().intValue());
    }

    /**
     * Method to search for a user in database from an id
     *
     * @param userId
     * @return User
     */
    @Override
    public User readById(int userId) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("no_utilisateur", userId);
        mapSqlParameterSource.addValue("no_utilisateur", userId);
        User user = namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ID,
                mapSqlParameterSource,
                new UserRowMapper()
        );
        return user;
    }

    /**
     * Method to search for a user in database from a username
     *
     * @param username
     * @return User
     */
    @Override
    public User readByUsername(String username) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("pseudo", username);
        mapSqlParameterSource.addValue("pseudo", username);
        User user = namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_USERNAME,
                mapSqlParameterSource,
                new UserRowMapper()
        );
        return user;
    }

    /**
     * Method to search for a user in database from an email
     *
     * @param email
     * @return User
     */
    @Override
    public User readByEmail(String email) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("email", email);
        mapSqlParameterSource.addValue("email", email);
        User user = namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_EMAIL,
                mapSqlParameterSource,
                new UserRowMapper()
        );
        return user;
    }

    /**
     * Method to update fields from a user, except id.
     *
     * @param user
     */
    @Override
    public void updateAll(User user) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_utilisateur", user.getUserId());
        mapSqlParameterSource.addValue("pseudo", user.getUsername());
        mapSqlParameterSource.addValue("nom", user.getLastName());
        mapSqlParameterSource.addValue("prenom", user.getFirstName());
        mapSqlParameterSource.addValue("email", user.getEmail());
        mapSqlParameterSource.addValue("telephone", user.getTelephone());
        mapSqlParameterSource.addValue("rue", user.getStreet());
        mapSqlParameterSource.addValue("code_postal", user.getZip());
        mapSqlParameterSource.addValue("ville", user.getCity());
        mapSqlParameterSource.addValue("mot_de_passe", user.getPassword());
        mapSqlParameterSource.addValue("credit", user.getCredit());
        mapSqlParameterSource.addValue("administrateur", user.isAdmin());

        namedParameterJdbcTemplate.update(
                UPDATE_USER,
                mapSqlParameterSource
        );
    }

    /**
     * Method to update the password from e-mail
     *
     * @param email
     * @param newPassword
     */
    @Override
    public void updatePassword(String email, String newPassword) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("email", email);
        mapSqlParameterSource.addValue("mot_de_passe", newPassword);
        namedParameterJdbcTemplate.update(
                UPDATE_PASSWORD,
                mapSqlParameterSource
        );
    }

    /**
     * Method to delete a user in the database.
     *
     * @param userId
     */
    @Override
    public void delete(int userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_utilisateur", userId);
        namedParameterJdbcTemplate.update(
                DELETE,
                mapSqlParameterSource
        );
    }
}

/**
 * @Author TeamSkyjo
 * @Version 1.0
 * Class to RowMap Users from the database (in French) to User object.
 */
class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("no_utilisateur"));
        user.setUsername(rs.getString("pseudo"));
        user.setLastName(rs.getString("nom"));
        user.setFirstName(rs.getString("prenom"));
        user.setEmail(rs.getString("email"));
        user.setTelephone(rs.getString("telephone"));
        user.setStreet(rs.getString("rue"));
        user.setZip(rs.getString("code_postal"));
        user.setCity(rs.getString("ville"));
        user.setCredit(rs.getInt("credit"));
        user.setAdmin(rs.getBoolean("administrateur"));
        return user;
    }
}
