USE Projet_Encheres;
GO

SELECT * FROM UTILISATEURS;
INSERT INTO UTILISATEURS VALUES ('test', 'test', 'test', 'test', 'test', 'test', 'test', 'test', 'test', 150, 0);

UPDATE UTILISATEURS SET pseudo = 'skyyyjo', nom ='VV', prenom='LL', email='laety@vrd.com', telephone = '0987654321', rue='test', code_postal='29200', ville='BRESTMEME', mot_de_passe='testestest', credit= 50, administrateur=1
WHERE no_utilisateur = 8;

UPDATE UTILISATEURS SET mot_de_passe='testestest' WHERE email='laety@vrd.fr';

DELETE FROM UTILISATEURS WHERE no_utilisateur=11;

SELECT * FROM RETRAITS ; 
SELECT * FROM ARTICLES ;

INSERT INTO ARTICLES VALUES ('Veste en jean', 'Veste jean, taille XS', DATEADD(DAY,-5,GETDATE()), DATEADD(DAY,+5,GETDATE()), 250, NULL, 'https://example.com/veste.jpg', 2, 4);
DELETE FROM RETRAITS WHERE no_article = 5 ;
INSERT INTO RETRAITS VALUES (5, 'rue des mouettes', '29000', 'Quimper');
UPDATE RETRAITS SET rue='5 all�e des mouettes', code_postal='29200', ville='Brest' WHERE no_article = 5;

SELECT * FROM RETRAITS WHERE no_article = 5;

--------------------------------------------------

SELECT * FROM UTILISATEURS;

-- private static final String INSERT
INSERT INTO UTILISATEURS 
VALUES ('movieFan', 'Almodovar', 'Pedro', 'p.almodovar@email.com', null, '5 somewhere', '67345', 'MadridInFrance', 'MotDePasse123!', 500, 0);

DELETE FROM UTILISATEURS WHERE no_utilisateur>4;

-- apr�s le DELETE, pour repartir � auto-incr�mentation de l'id � partir du jeu de donn�es initial
-- la prochaine donn�e ins�r�e aura l'id 5
DBCC CHECKIDENT ('UTILISATEURS', RESEED, 4);

SELECT * FROM UTILISATEURS;