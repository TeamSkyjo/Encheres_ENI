-- Script de création de la base de données ENCHERES
--   type :      SQL Server 2012
--
USE Projet_Encheres;
GO

DROP TABLE IF EXISTS ENCHERES;
DROP TABLE IF EXISTS RETRAITS;
DROP TABLE IF EXISTS ARTICLES;
DROP TABLE IF EXISTS UTILISATEURS;
DROP TABLE IF EXISTS CATEGORIES;

CREATE TABLE CATEGORIES (
    no_categorie   INTEGER IDENTITY(1,1) NOT NULL,
    libelle        VARCHAR(30) NOT NULL
);

ALTER TABLE CATEGORIES ADD constraint categorie_pk PRIMARY KEY (no_categorie);

CREATE TABLE ENCHERES (
    no_utilisateur   INTEGER NOT NULL,
    no_article       INTEGER NOT NULL,
    date_enchere     datetime2 NOT NULL,
	montant_enchere  INTEGER NOT NULL
);

ALTER TABLE ENCHERES ADD constraint enchere_pk PRIMARY KEY (no_utilisateur, no_article);

CREATE TABLE RETRAITS (
	no_article       INTEGER NOT NULL,
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(15) NOT NULL,
    ville            VARCHAR(30) NOT NULL
);

ALTER TABLE RETRAITS ADD constraint retrait_pk PRIMARY KEY (no_article);

CREATE TABLE UTILISATEURS (
    no_utilisateur   INTEGER IDENTITY(1,1) NOT NULL,
    pseudo           VARCHAR(30) NOT NULL,
    nom              VARCHAR(30) NOT NULL,
    prenom           VARCHAR(30) NOT NULL,
    email            VARCHAR(50) NOT NULL,
    telephone        VARCHAR(15),
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(10) NOT NULL,
    ville            VARCHAR(30) NOT NULL,
    mot_de_passe     VARCHAR(250) NOT NULL,
    credit           INTEGER NOT NULL,
    administrateur   bit NOT NULL
);

ALTER TABLE UTILISATEURS ADD constraint utilisateur_pk PRIMARY KEY (no_utilisateur);


CREATE TABLE ARTICLES (
    no_article                    INTEGER IDENTITY(1,1) NOT NULL,
    nom_article                   VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
	date_debut_encheres           DATE NOT NULL,
    date_fin_encheres             DATE NOT NULL,
    prix_initial                  INTEGER,
    prix_vente                    INTEGER,
	url_image					  VARCHAR(300),
    no_utilisateur                INTEGER NOT NULL,
    no_categorie                  INTEGER NOT NULL
);

ALTER TABLE ARTICLES ADD constraint articles_pk PRIMARY KEY (no_article);

-- Foreign keys
ALTER TABLE ARTICLES
    ADD CONSTRAINT articles_vendus_categories_fk FOREIGN KEY ( no_categorie )
        REFERENCES CATEGORIES ( no_categorie )
ON DELETE NO ACTION 
    ON UPDATE no action;

ALTER TABLE ARTICLES
    ADD CONSTRAINT ventes_utilisateur_fk FOREIGN KEY ( no_utilisateur )
        REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action;

ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_utilisateur_fk FOREIGN KEY ( no_utilisateur )
		REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action;

ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action;

ALTER TABLE RETRAITS
    ADD CONSTRAINT retraits_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action;

-- Table des rôles
-- Si UTILISATEURS.administrateur = 1 : ROLE_ADMIN et ROLE_MEMBRE
-- Sinon : ROLE_MEMBRE
DROP TABLE IF EXISTS ROLES;

CREATE TABLE ROLES(	
	role NVARCHAR(50) NOT NULL, 
	is_admin int NOT NULL,
	PRIMARY KEY (ROLE, IS_ADMIN)
);
