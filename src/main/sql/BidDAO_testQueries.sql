USE Projet_Encheres;
GO

-- supprimer les données ajoutées lors des tests

-- private static final String SELECT_ALL = "SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM ENCHERES;";
SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM ENCHERES;

-- private static final String SELECT_BY_ARTICLE_ID = "SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM ENCHERES WHERE no_article = 3;";
SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM ENCHERES WHERE no_article = 3;

-- private static final String SELECT_BY_USER_ID = "SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM ENCHERES WHERE no_utilisateur = 3;";
SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM ENCHERES WHERE no_utilisateur = 3;

-- private static final String INSERT = "INSERT INTO ENCHERES VALUES (:buyerId, :articleId, :bidDate, :bidPrice);";
INSERT INTO ENCHERES VALUES (2, 4, GETDATE(), 350);

-- private static final String DELETE = "DELETE FROM ENCHERES WHERE no_utilisateur = 3 AND no_article = 4;";
DELETE FROM ENCHERES WHERE no_utilisateur = 3 AND no_article = 4;


SELECT * FROM ARTICLES WHERE no_utilisateur = 1 AND nom_article = 'Ordinateur portable';

SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres,
                    a.prix_initial, a.prix_vente, a.url_image,
                    u.no_utilisateur, u.pseudo AS username,
                    c.no_categorie, c.libelle
                    FROM ARTICLES a
                    INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur
                    INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie
                    WHERE a.no_utilisateur = 1 AND a.nom_article = 'Ordinateur portable';