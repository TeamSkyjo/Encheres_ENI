USE Projet_Encheres;
GO

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