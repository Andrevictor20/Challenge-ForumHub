CREATE TABLE respostas (
    id BIGSERIAL PRIMARY KEY,
    mensagem TEXT NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    solucao BOOLEAN NOT NULL DEFAULT FALSE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    topico_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,

    CONSTRAINT fk_respostas_topico_id FOREIGN KEY(topico_id) REFERENCES topicos(id),
    CONSTRAINT fk_respostas_autor_id FOREIGN KEY(autor_id) REFERENCES usuarios(id)
);