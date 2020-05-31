--inserts for dictionary tables
INSERT INTO category (name) VALUES('Deser'), ('Zupa'), ('Sałatka'), ('Danie główne'), ('danie proste'), ('Koktajl');
INSERT INTO unit (name) VALUES ('łyżka'), ('łyżeczka'), ('szklanka'), ('kostka'), ('opakowanie'), ('szt'), ('g'), ('dag'), ('kg'), ('ml'), ('l');
INSERT INTO diet (name) VALUES ('wegetariańska'), ('wegańska'), ('bez laktozy'), ('bez glutenu'), ('białkowa');
INSERT INTO difficulty (name) VALUES ('łatwy'), ('średni'), ('trudny'), ('ekspert');

--inserts for operation tables
INSERT INTO recipe (title, description, PREPARATION_TIME_IN_MINUTES, category_id, diet_id, difficulty_id) VALUES ('Tort czekoladowy', 'Upieczony biszkopt przełóż masą czekoladową', 60, SELECT id FROM category WHERE name = 'Deser', SELECT id FROM diet WHERE name = 'wegetariańska', SELECT id FROM difficulty WHERE name = 'średni');
INSERT INTO recipe (title, description, PREPARATION_TIME_IN_MINUTES, category_id, diet_id, difficulty_id) VALUES ('Tort malinowy', 'Upieczony biszkopt przełóż masą malinową', 120, SELECT id FROM category WHERE name = 'Deser', SELECT id FROM diet WHERE name = 'wegetariańska', SELECT id FROM difficulty WHERE name = 'ekspert');
INSERT INTO recipe (title, description, PREPARATION_TIME_IN_MINUTES, category_id, diet_id, difficulty_id) VALUES ('Sałatka grecka', 'Warzywa pokroić', 15, SELECT id FROM category WHERE name = 'Sałatka', SELECT id FROM diet WHERE name = 'wegetariańska', SELECT id FROM difficulty WHERE name = 'łatwy');
INSERT INTO recipe (title, description, PREPARATION_TIME_IN_MINUTES, category_id, diet_id, difficulty_id) VALUES ('Kotlet schabowy', 'Przygotowane mięso usmażyć', 45, SELECT id FROM category WHERE name = 'Danie główne', SELECT id FROM diet WHERE name = 'białkowa', SELECT id FROM difficulty WHERE name = 'trudny');
INSERT INTO recipe (title, description, PREPARATION_TIME_IN_MINUTES, category_id, diet_id, difficulty_id) VALUES ('Makaron z sosem', 'Makaron ugotować', 30, SELECT id FROM category WHERE name = 'danie proste', SELECT id FROM diet WHERE name = 'wegańska', SELECT id FROM difficulty WHERE name = 'trudny');

INSERT INTO recipe_ingredient (name, amount, unit_id, recipe_id) VALUES ('cukier', 2, SELECT id FROM unit WHERE name = 'szklanka', SELECT id FROM recipe WHERE title = 'Tort czekoladowy');
INSERT INTO recipe_ingredient (name, amount, unit_id, recipe_id) VALUES ('mąka', 1, SELECT id FROM unit WHERE name = 'szklanka', SELECT id FROM recipe WHERE title = 'Tort czekoladowy');
INSERT INTO recipe_ingredient (name, amount, unit_id, recipe_id) VALUES ('maliny', 500, SELECT id FROM unit WHERE name = 'g', SELECT id FROM recipe WHERE title = 'Tort malinowy');
INSERT INTO recipe_ingredient (name, amount, unit_id, recipe_id) VALUES ('masło', 1, SELECT id FROM unit WHERE name = 'kostka', SELECT id FROM recipe WHERE title = 'Tort czekoladowy');
INSERT INTO recipe_ingredient (name, amount, unit_id, recipe_id) VALUES ('pomidor', 2, SELECT id FROM unit WHERE name = 'szt', SELECT id FROM recipe WHERE title = 'Sałatka grecka');
INSERT INTO recipe_ingredient (name, amount, unit_id, recipe_id) VALUES ('sałata', 1, SELECT id FROM unit WHERE name = 'szt', SELECT id FROM recipe WHERE title = 'Sałatka grecka');
INSERT INTO recipe_ingredient (name, amount, unit_id, recipe_id) VALUES ('oliwki', 300, SELECT id FROM unit WHERE name = 'g', SELECT id FROM recipe WHERE title = 'Sałatka grecka');
INSERT INTO recipe_ingredient (name, amount, unit_id, recipe_id) VALUES ('ser feta', 200, SELECT id FROM unit WHERE name = 'g', SELECT id FROM recipe WHERE title = 'Sałatka grecka');
INSERT INTO recipe_ingredient (name, amount, unit_id, recipe_id) VALUES ('masło', 0.5, SELECT id FROM unit WHERE name = 'kostka', SELECT id FROM recipe WHERE title = 'Tort malinowy');