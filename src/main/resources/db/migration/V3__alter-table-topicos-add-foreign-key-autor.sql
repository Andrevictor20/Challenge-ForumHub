INSERT INTO usuarios (id, nome, login, senha) VALUES (1, 'Andre Victor', 'andre@email.com', '$2a$12$CbqXkA9yKnm/MRmiRb4ME.HCP1yH2lAwiKbs5ZFixNT6BNKtQY2JW');
ALTER TABLE topicos ADD COLUMN autor_id BIGINT;

UPDATE topicos SET autor_id = 1;

ALTER TABLE topicos ALTER COLUMN autor_id SET NOT NULL;

ALTER TABLE topicos ADD CONSTRAINT fk_topicos_autor_id
FOREIGN KEY (autor_id) REFERENCES usuarios(id);

ALTER TABLE topicos DROP COLUMN autor;