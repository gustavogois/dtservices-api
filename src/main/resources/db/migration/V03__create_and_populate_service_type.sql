CREATE TABLE service_type (
     id				      BIGINT(20)      PRIMARY KEY AUTO_INCREMENT,
     name	          VARCHAR(30)		  NOT NULL,
     default_value	DECIMAL(8,2)		NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO service_type (name, default_value) values ('Arrombamento de porta', 10.00);
INSERT INTO service_type (name, default_value) values ('Pintura (m2)', 5.50);
INSERT INTO service_type (name, default_value) values ('Limpeza de terreno (m2)', 20.00);