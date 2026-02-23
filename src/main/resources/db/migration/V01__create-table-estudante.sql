CREATE TABLE estudantes (
  id INT NOT NULL,
  nome VARCHAR(45) NOT NULL,
  data_de_nascimento DATE  NOT NULL,
  area_de_estudo VARCHAR(45) NOT NULL,
  email VARCHAR(255),
  login VARCHAR(255),
  senha VARCHAR(255),
  PRIMARY KEY (id)
  );
