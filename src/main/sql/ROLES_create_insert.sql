USE Projet_Encheres;
GO

-- Table des rôles
-- Si UTILISATEURS.administrateur = 1 : ROLE_ADMIN et ROLE_MEMBRE
-- Sinon : ROLE_MEMBRE
DROP TABLE IF EXISTS ROLES;

CREATE TABLE ROLES(	
	role NVARCHAR(50) NOT NULL, 
	is_admin int NOT NULL,
	PRIMARY KEY (ROLE, IS_ADMIN)
);

INSERT INTO ROLES (ROLE, IS_ADMIN) VALUES ('ROLE_MEMBRE',0);
INSERT INTO ROLES (ROLE, IS_ADMIN) VALUES ('ROLE_MEMBRE',1);
INSERT INTO ROLES (ROLE, IS_ADMIN) VALUES ('ROLE_ADMIN',1);

-- Requête pour récupérer les identifiants
-- SELECT pseudo, password, 1 AS enabled FROM UTILISATEURS WHERE pseudo = ?;
SELECT pseudo, mot_de_passe, 1 AS enabled FROM UTILISATEURS WHERE pseudo = 'techguy';

SELECT * FROM UTILISATEURS;

-- Récupération des rôles
-- SELECT pseudo, ROLE FROM UTILISATEURS INNER JOIN ROLES ON administrateur = is_admin WHERE pseudo = ?;
SELECT pseudo, ROLE FROM UTILISATEURS
INNER JOIN ROLES ON administrateur = is_admin WHERE pseudo = 'techguy';
SELECT pseudo, ROLE FROM UTILISATEURS
INNER JOIN ROLES ON administrateur = is_admin WHERE pseudo = 'admin';