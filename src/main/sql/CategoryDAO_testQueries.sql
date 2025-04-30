USE Projet_Encheres;
GO

-- supprimer les données ajoutées lors des tests
DELETE FROM CATEGORIES WHERE no_categorie > 4;

-- private static final String SELECT_ALL = "SELECT no_categorie, libelle FROM CATEGORIES;";
SELECT no_categorie, libelle FROM CATEGORIES;

-- private static final String SELECT_BY_ID = "SELECT no_categorie, libelle FROM CATEGORIES WHERE no_categorie = :categoryId;";
SELECT no_categorie, libelle FROM CATEGORIES WHERE no_categorie = 3;

-- private static final String INSERT = "INSERT INTO CATEGORIES VALUES (:label);";
INSERT INTO CATEGORIES VALUES ('Voitures');

-- private static final String UPDATE = "UPDATE CATEGORIES SET libelle = :label WHERE no_categorie = :categoryId;";
UPDATE CATEGORIES SET libelle = 'Véhicules' WHERE no_categorie = 10;

-- private static final String DELETE = "DELETE FROM CATEGORIES WHERE no_categorie = :categoryId;";
DELETE FROM CATEGORIES WHERE no_categorie = 8;
