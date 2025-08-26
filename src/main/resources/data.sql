INSERT INTO users (name, login, password, email, usertype, avatar, active) VALUES
('Wile E. Coyote', 'coyote', 'senha123', 'wile@acme.com', 'ADMIN', 'https://s3.amazonaws.com/comicgeeks/characters/avatars/10927.jpg', true),
('Road Runner', 'runner', 'senah123', 'roadrunner@acme.com', 'ADMIN', 'https://pa1.aminoapps.com/7235/7f28023b3169637b8d520f0e88bcf01cbb4e3baar1-266-234_00.gif', true),
('Daffy Duck', 'duck', 'senha123','daffy@acme.com', 'ADMIN', 'https://www.gravatar.com/avatar/daf4b5b81342fe587d5043b3e58a0180?s=200', true),
('Bugs Bunny', 'bunny', 'senha123','bugs@acme.com', 'FUNCIONARIO', 'https://www.gravatar.com/avatar/9f0c2cb2d5f8264e2c35d92f3655f2b3?s=200', true),
('Elmer Fudd', 'fudd', 'senha123', 'elmer@acme.com', 'CLIENTE', 'https://www.gravatar.com/avatar/6be1f3c98be3886b5473b7b947fe82ad?s=200', true);

INSERT INTO product (name, description, unit_Price, quantity_estoque, active) VALUES
('Papel Foto', 'Papel Gloss Foto', 5, 10, true );

INSERT INTO service_variation (id, name, active) VALUES
(1,'Xerox P&B', true),
(2,'Xerox Cor', true);

-- INSERT INTO service (name, description, price, active) VALUES
-- ('Xerox', 'copias', 0.5, true),
-- ('Impressão', 'impressão colorida', 2, true);
--
