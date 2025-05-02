USE Projet_Encheres;
GO

SELECT * FROM ARTICLES;
SELECT * FROM RETRAITS;

SELECT nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, url_image, u.pseudo, c.libelle
FROM ARTICLES a
INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur
INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie;


--Update article
UPDATE ARTICLES
SET prix_vente = 200
WHERE no_article = 4;
SELECT * FROM ARTICLES WHERE no_article = 4;

SELECT nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, url_image, u.pseudo, c.libelle
FROM ARTICLES a
INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur
INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie
WHERE a.nom_article LIKE '%Ordinateur%';

DELETE FROM ARTICLES
WHERE no_article = 6;
SELECT * FROM ARTICLES;

SELECT * FROM CATEGORIES;
