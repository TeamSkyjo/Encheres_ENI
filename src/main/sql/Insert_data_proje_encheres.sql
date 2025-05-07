USE Projet_Encheres;
GO

-- Suppression des données dans l'ordre de dépendance
DELETE FROM ENCHERES;
DELETE FROM RETRAITS;
DELETE FROM ARTICLES;
DELETE FROM UTILISATEURS;
DELETE FROM CATEGORIES;
DELETE FROM ROLES;

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
 '{bcrypt}$2a$12$mZmizxD.C0Y55LaOudITNuKrOmROLrUBLxSOo9j733ghXAKyzi.lW', 1500, 0),
(2, 'meublequeen', 'Durand', 'Claire', 'claire.durand@email.com', '0602030405', '8 avenue des Meubles', '69000', 'Lyon', 
 '{bcrypt}$2a$12$wrGC3yx6Lvo1wvZvonfdJOR14TsJFT5dHMxlXpdbtbtoBiQ.NjK8m', 1200, 0),
(3, 'sporty', 'Lemoine', 'Julien', 'julien.lemoine@email.com', NULL, '45 rue des Sports', '31000', 'Toulouse', 
 '{bcrypt}$2a$12$NHhiOwfP8ZMs9d9r./ED0eFk4BTADsCOleJsuqB3SK/skdKoKMuUm', 1000, 0),
(4, 'admin', 'Admin', 'Super', 'admin@site.com', '0600000000', '1 rue de l''Admin', '99999', 'Webtown', 
 '{bcrypt}$2a$12$oKuuPMKR9mXQZWHQEGKYBO46LKkrQx0el0Ahlhetvs9tp2Q9rtp1q', 9999, 1);

--Mots de passe : 
--techguy		pass123		$2a$12$mZmizxD.C0Y55LaOudITNuKrOmROLrUBLxSOo9j733ghXAKyzi.lW
--meublequeen	secret		$2a$12$wrGC3yx6Lvo1wvZvonfdJOR14TsJFT5dHMxlXpdbtbtoBiQ.NjK8m
--sporty		foot2024	$2a$12$NHhiOwfP8ZMs9d9r./ED0eFk4BTADsCOleJsuqB3SK/skdKoKMuUm
--admin			adminpass	$2a$12$oKuuPMKR9mXQZWHQEGKYBO46LKkrQx0el0Ahlhetvs9tp2Q9rtp1q

SET IDENTITY_INSERT UTILISATEURS OFF;

-- Insertion des articles
SET IDENTITY_INSERT ARTICLES ON;

INSERT INTO ARTICLES (no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, url_image, no_utilisateur, no_categorie) VALUES
(1, 'Ordinateur portable', 'PC portable Lenovo, 16Go RAM, 512Go SSD', DATEADD(DAY,+1,GETDATE()), DATEADD(DAY,+3,GETDATE()), 500, NULL, '/uploads/PC-Portable-Lenovo.jpg', 1, 1),
(2, 'Canapé 3 places', 'Canapé en tissu gris, très confortable', DATEADD(DAY,-1,GETDATE()), DATEADD(DAY,+1,GETDATE()), 300, NULL, '/uploads/canape.jpg', 2, 2),
(3, 'Vélo de course', 'Vélo BTWIN, très bon état, taille M', DATEADD(DAY,-4,GETDATE()), GETDATE(), 250, NULL, '/uploads/velo-btwin.jpeg', 3, 3),
(4, 'Veste en cuir', 'Veste cuir noir, taille L', DATEADD(DAY,-5,GETDATE()), DATEADD(DAY,-2,GETDATE()), 100, NULL, '/uploads/veste-cuir-noir.jpg', 2, 4),

-- Informatique
(5, 'Écran 27 pouces', 'Écran IPS Full HD, parfait pour le télétravail', DATEADD(DAY,-2,GETDATE()), DATEADD(DAY,+1,GETDATE()), 120, NULL, '/uploads/ecran-ips.jpg', 1, 1),
(6, 'Clavier mécanique', 'Clavier RGB switches bleus, AZERTY', DATEADD(DAY,+2,GETDATE()), DATEADD(DAY,+5,GETDATE()), 60, NULL, '/uploads/clavier-switch-bleu.jpg', 3, 1),
-- Catégorie 2 : Ameublement
(7, 'Chaise de bureau ergonomique', 'Chaise réglable, très bon état', DATEADD(DAY,-3,GETDATE()), DATEADD(DAY,-1,GETDATE()), 80, NULL, '/uploads/chaise-ergonomique.jpg', 2, 2),
(8, 'Table basse bois massif', 'Table en chêne 100x60 cm', DATEADD(DAY,-1,GETDATE()), DATEADD(DAY,+2,GETDATE()), 90, NULL, '/uploads/table-basse.jpg', 1, 2),

-- Catégorie 3 : Sport & Loisirs
(9, 'Tapis de yoga', 'Tapis antidérapant, 6 mm, violet', DATEADD(DAY,+1,GETDATE()), DATEADD(DAY,+4,GETDATE()), 25, NULL, '/uploads/tapis-de-yoga.jpg', 3, 3),
(10, 'Raquette de tennis', 'Raquette Babolat Pure Drive, bon état', DATEADD(DAY,-6,GETDATE()), DATEADD(DAY,-2,GETDATE()), 70, NULL, '/uploads/raquette.jpg', 2, 3),

-- Catégorie 4 : Vêtements
(11, 'Jean slim homme', 'Taille 42, couleur bleu foncé', DATEADD(DAY,-2,GETDATE()), DATEADD(DAY,+1,GETDATE()), 30, NULL, '/uploads/jeans-slim-bleu-fonce.jpg', 1, 4),
(12, 'Robe été fleurie', 'Robe légère, taille M, comme neuve', DATEADD(DAY,-5,GETDATE()), DATEADD(DAY,-1,GETDATE()), 45, NULL, '/uploads/robe.jpg', 3, 4);

SET IDENTITY_INSERT ARTICLES OFF;

-- Insertion des retraits
INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES
(1, '12 rue de l''Info', '75001', 'Paris'),        -- Utilisateur 1
(2, '8 avenue des Meubles', '69000', 'Lyon'),      -- Utilisateur 2
(3, '45 rue des Sports', '31000', 'Toulouse'),     -- Utilisateur 3
(4, '10 rue du Parc', '33000', 'Bordeaux'),        -- Utilisateur 2
(5, '12 rue de l''Info', '75001', 'Paris'),        -- Utilisateur 1
(6, '45 rue des Sports', '31000', 'Toulouse'),     -- Utilisateur 3
(7, '8 avenue des Meubles', '69000', 'Lyon'),      -- Utilisateur 2
(8, '12 rue de l''Info', '75001', 'Paris'),        -- Utilisateur 1
(9, '45 rue des Sports', '31000', 'Toulouse'),     -- Utilisateur 3
(10, '8 avenue des Meubles', '69000', 'Lyon'),     -- Utilisateur 2
(11, '12 rue de l''Info', '75001', 'Paris'),       -- Utilisateur 1
(12, '45 rue des Sports', '31000', 'Toulouse');    -- Utilisateur 3


-- Insertion des enchères
INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES
(3, 4, DATEADD(DAY,-5,GETDATE()), 110),
(1, 2, DATEADD(DAY,-1,GETDATE()), 320),
(2, 3, DATEADD(DAY,-2,GETDATE()), 260),
(1, 4, DATEADD(DAY,-3,GETDATE()), 150);


-- Insertion des rôles
INSERT INTO ROLES (ROLE, IS_ADMIN) VALUES ('ROLE_MEMBRE',0);
INSERT INTO ROLES (ROLE, IS_ADMIN) VALUES ('ROLE_MEMBRE',1);
INSERT INTO ROLES (ROLE, IS_ADMIN) VALUES ('ROLE_ADMIN',1);


-- Scénarios
-- Article 1 = enchère à venir- article sans enchère
-- article 2 = enchère en cours - article avec une enchère
-- article 3 = article avec plusieurs enchères
-- article 4 = enchère terminée avec une enchère