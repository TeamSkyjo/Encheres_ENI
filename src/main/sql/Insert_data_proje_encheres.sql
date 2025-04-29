USE Projet_Encheres;
GO

-- Suppression des données dans l'ordre de dépendance
DELETE FROM ENCHERES;
DELETE FROM RETRAITS;
DELETE FROM ARTICLES;
DELETE FROM UTILISATEURS;
DELETE FROM CATEGORIES;

-- Insertion des catégories
SET IDENTITY_INSERT CATEGORIES ON;

INSERT INTO CATEGORIES (no_categorie, libelle) VALUES
(1, 'Informatique'),
(2, 'Ameublement'),
(3, 'Sport&Loisirs'),
(4, 'Vêtements');

SET IDENTITY_INSERT CATEGORIES OFF;

-- Insertion des utilisateurs (avec mots de passe hachés)
SET IDENTITY_INSERT UTILISATEURS ON;

INSERT INTO UTILISATEURS (no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES
(1, 'techguy', 'Martin', 'Lucas', 'lucas.martin@email.com', '0601020304', '12 rue de l''Info', '75001', 'Paris', 
 '$2a$10$kHbAVAt47pD.9mFChqZ1jOS3Cu9csyhKUk.ZqxShspqVEIQntFtfa', 1500, 0),
(2, 'meublequeen', 'Durand', 'Claire', 'claire.durand@email.com', '0602030405', '8 avenue des Meubles', '69000', 'Lyon', 
 '$2a$10$7VqBS8C0D1pR14zEV0cC4OExzDqS.5IdgoF1g8hSpIbEM4FhdwIyu', 1200, 0),
(3, 'sporty', 'Lemoine', 'Julien', 'julien.lemoine@email.com', NULL, '45 rue des Sports', '31000', 'Toulouse', 
 '$2a$10$HQrl0N0d9ktUkZC7v0X.ZOivvVDViQrHQGLZGuGb6I/NXko2bFVo2', 1000, 0),
(4, 'admin', 'Admin', 'Super', 'admin@site.com', '0600000000', '1 rue de l''Admin', '99999', 'Webtown', 
 '$2a$10$eEbzZpW94b5LqWZL2iHpiONOCgujj2crhTmwT1zAFmZYzLbwK3mx6', 9999, 1);

--Mots de passe : 
--techguy	pass123	$2a$10$kHbAVAt47pD.9mFChqZ1jOS3Cu9csyhKUk.ZqxShspqVEIQntFtfa
--meublequeen	secret	$2a$10$7VqBS8C0D1pR14zEV0cC4OExzDqS.5IdgoF1g8hSpIbEM4FhdwIyu
--sporty	foot2024	$2a$10$HQrl0N0d9ktUkZC7v0X.ZOivvVDViQrHQGLZGuGb6I/NXko2bFVo2
--admin	adminpass	$2a$10$eEbzZpW94b5LqWZL2iHpiONOCgujj2crhTmwT1zAFmZYzLbwK3mx6

SET IDENTITY_INSERT UTILISATEURS OFF;

-- Insertion des articles
SET IDENTITY_INSERT ARTICLES ON;

INSERT INTO ARTICLES (no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, url_image, no_utilisateur, no_categorie) VALUES
(1, 'Ordinateur portable', 'PC portable Lenovo, 16Go RAM, 512Go SSD', DATEADD(DAY,+1,GETDATE()), DATEADD(DAY,+3,GETDATE()), 500, NULL, 'https://example.com/pc.jpg', 1, 1),
(2, 'Canapé 3 places', 'Canapé en tissu gris, très confortable', DATEADD(DAY,-1,GETDATE()), DATEADD(DAY,+1,GETDATE()), 300, NULL, 'https://example.com/canape.jpg', 2, 2),
(3, 'Vélo de course', 'Vélo BTWIN, très bon état, taille M', DATEADD(DAY,-4,GETDATE()), GETDATE(), 250, NULL, 'https://example.com/velo.jpg', 3, 3),
(4, 'Veste en cuir', 'Veste cuir noir, taille L', DATEADD(DAY,-5,GETDATE()), DATEADD(DAY,-2,GETDATE()), 100, NULL, 'https://example.com/veste.jpg', 2, 4);

SET IDENTITY_INSERT ARTICLES OFF;

-- Insertion des retraits
INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES
(1, '12 rue de l''Info', '75001', 'Paris'),
(2, '8 avenue des Meubles', '69000', 'Lyon'),
(3, '45 rue des Sports', '31000', 'Toulouse'),
(4, '8 avenue des Meubles', '69000', 'Lyon');

-- Insertion des enchères
INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES
(3, 4, DATEADD(DAY,-5,GETDATE()), 110),
(1, 2, DATEADD(DAY,-1,GETDATE()), 320),
(2, 3, DATEADD(DAY,-2,GETDATE()), 260),
(1, 4, DATEADD(DAY,-3,GETDATE()), 150);

SELECT * FROM ARTICLES INNER JOIN ENCHERES ON ARTICLES.no_article = ENCHERES.no_article;