
-- USER (password: jwtpass)
INSERT INTO app_user (id,first_name, last_name, password, username) VALUES ('1', 'John', 'Doe', '$2a$10$bj7qFEPpCcYOPqncrWQAnOGZ3onz78ipBoykaV9vidBFZxmhcfcHa', 'john.doe');
INSERT INTO app_user (id,first_name, last_name, password, username) VALUES ('2', 'Admin', 'Admin', '$2a$10$qtH0F1m488673KwgAfFXEOWxsoZSeHqqlB/8BTt3a6gsI5c2mdlfe', 'admin.admin');

-- SANDWICH
INSERT INTO sandwich(id,nom,pain,description,prix) VALUES ('77230e70-075c-4b51-aded-181717e7b08b', 'Jambon beurre','complet','Un classique',3);
INSERT INTO sandwich(id,nom,pain,description,prix) VALUES ('b8709805-b3df-4809-a3fb-f4ea64b593cb', 'Pain bagnat aux deux fromages et au jambon','seigle','Enorme',3);
INSERT INTO sandwich(id,nom,pain,description,prix) VALUES ('de5e445f-74c9-4457-9f51-ddb205598bc1', 'Végétarien','campagne','Il est vert',2);
INSERT INTO sandwich(id,nom,pain,description,prix) VALUES ('c5627308-0efc-443a-9346-7e6835e551e6', 'Thon','complet','On le retrouve en surface',4);
INSERT INTO sandwich(id,nom,pain,description,prix) VALUES ('03f7392a-4abe-47c4-a269-3112bce8e82b', 'Aux trois pestos','complet','Jolie à regarder',5);

-- CATEGORIE
INSERT INTO categorie(id,nom,description) VALUES ('3ce141a0-2354-49b5-9d26-66e198f8d486','Gourmand','Pour une grosse faim');



