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

-- suppression des donn�es ajout�es en testant
DELETE FROM RETRAITS WHERE no_article > 4;
DELETE FROM ARTICLES WHERE no_article > 4;

-- apr�s le DELETE, pour repartir � auto-incr�mentation de l'id � partir du jeu de donn�es initial
-- la prochaine donn�e ins�r�e aura l'id 5
DBCC CHECKIDENT ('ARTICLES', RESEED, 4);
