CREATE DATABASE DiarioGratidao;


USE DiarioGratidao;


CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE EntradaGratidao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    texto TEXT NOT NULL,
    data DATE NOT NULL,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id) ON DELETE CASCADE
);

INSERT INTO Usuario (nome, email, senha) VALUES
('Rodrigo Pinheiro', 'rodrigo@example.com', 'senha123'),
('Fernanda Dias', 'fernanda@example.com', 'senha456');


INSERT INTO EntradaGratidao (texto, data, usuario_id) VALUES
('Sou grato pela minha família.', '2024-05-01', 1),
('Grato pelo trabalho que amo.', '2024-05-02', 1),
('Agradecido pela saúde e bem-estar.', '2024-05-03', 2);
