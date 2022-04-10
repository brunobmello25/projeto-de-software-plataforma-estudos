-- CRIA O BANCO ----------

CREATE DATABASE plataforma_estudos;



-- CRIA AS TABELAS ----------

CREATE TABLE plataforma_estudos.product (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  price DECIMAL(8, 2) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8mb4;



-- INSERE DADOS NO BANCO ----------

INSERT INTO plataforma_estudos.product(name, price) VALUES ('Produto Base', 20.00);
UPDATE plataforma_estudos.product SET name='Produto Base' WHERE id=1;

-- CONFIGURANDO LOCK OTIMISTA ----------

ALTER TABLE plataforma_estudos.product ADD COLUMN (
    version INT NOT NULL DEFAULT 0
    )