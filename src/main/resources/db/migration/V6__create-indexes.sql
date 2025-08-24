-- Índices para a tabela 'topicos'
CREATE INDEX idx_topicos_autor_id ON topicos(autor_id);
CREATE INDEX idx_topicos_curso ON topicos(curso);
CREATE INDEX idx_topicos_data_criacao ON topicos(data_de_criacao);
-- Índice composto para busca por autor + data
CREATE INDEX idx_topicos_autor_data ON topicos(autor_id, data_de_criacao);

-- Índice para a tabela 'usuarios'
CREATE INDEX idx_usuarios_login ON usuarios(login);
CREATE INDEX idx_usuarios_nome ON usuarios(nome);

-- Índices para a tabela 'respostas'
CREATE INDEX idx_respostas_topico_id ON respostas(topico_id);
CREATE INDEX idx_respostas_autor_id ON respostas(autor_id);
CREATE INDEX idx_respostas_data_criacao ON respostas(data_criacao);
-- Índice composto para respostas por tópico + data
CREATE INDEX idx_respostas_topico_data ON respostas(topico_id, data_criacao);
-- Índice para marcar soluções
CREATE INDEX idx_respostas_solucao ON respostas(solucao) WHERE solucao = true;