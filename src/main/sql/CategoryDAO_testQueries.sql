USE Projet_Encheres;
GO

SELECT * FROM ARTICLES;
SELECT * FROM RETRAITS;
SELECT * FROM CATEGORIES;

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

-- suppression des données ajoutées en testant
DELETE FROM RETRAITS WHERE no_article > 4;
DELETE FROM ARTICLES WHERE no_article > 4;

-- après le DELETE, pour repartir à auto-incrémentation de l'id à partir du jeu de données initial
-- la prochaine donnée insérée aura l'id 5
DBCC CHECKIDENT ('ARTICLES', RESEED, 4);
