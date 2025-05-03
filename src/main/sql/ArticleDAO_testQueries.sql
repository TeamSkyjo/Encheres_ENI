USE Projet_Encheres;
GO

-- supprimer les données ajoutées lors des tests


SELECT * FROM ARTICLES;
SELECT * FROM CATEGORIES;

--private static final String INSERT
INSERT INTO ARTICLES (nom_article, 
						description, 
						date_debut_encheres, 
						date_fin_encheres, 
						prix_initial, 
						prix_vente, 
						url_image, 
						no_utilisateur, 
						no_categorie) 
VALUES ('Ordinateur portable', 
		'PC portable Lenovo i15, 8Go RAM, 1To SSD',
		'2025-04-30', 
		'2025-05-07', 
		500, 
		0, 
		'https://example.com/images/lenovo.jpg', 
		3, 
		1);


-- private static final String READ_BY_ID
SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.url_image, 
	   u.no_utilisateur, u.pseudo AS username, 
	   c.no_categorie, c.libelle
FROM ARTICLES a
INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur
INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie
WHERE no_article = 1;


-- private static final String READ_BY_NAME
SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.url_image, 
	   u.no_utilisateur, u.pseudo AS username, 
	   c.no_categorie, c.libelle
FROM ARTICLES a
INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur
INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie
WHERE a.nom_article LIKE '%ordi%';


-- private static final String READ_BY_CATEGORY
SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.url_image, 
	   u.no_utilisateur, u.pseudo AS username, 
	   c.no_categorie, c.libelle
FROM ARTICLES a
INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur
INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie
WHERE c.libelle LIKE 'Informatique';


-- private static final String UPDATE 
UPDATE ARTICLES
SET nom_article = 'nouveau nom',
	description = 'une description quelconque',
	date_debut_encheres = '2025-04-30',
	date_fin_encheres = '2025-05-07',
	prix_initial = 500,
	prix_vente = 890,
	url_image = ''
WHERE no_article = 5;

-- private static final String READ_ALL
SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.url_image, 
		u.no_utilisateur, u.pseudo AS username, 
		c.no_categorie, c.libelle
FROM ARTICLES a
INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur
INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie;


-- private static final String DELETE
DELETE FROM ARTICLES 
WHERE no_article = 5;

-- après le DELETE, pour repartir à auto-incrémentation de l'id à partir du jeu de données initial
-- la prochaine donnée insérée aura l'id 5
DBCC CHECKIDENT ('ARTICLES', RESEED, 4);

SELECT * FROM ARTICLES;

