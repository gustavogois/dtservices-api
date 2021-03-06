CREATE TABLE requester (
                         id              BIGINT(20)  PRIMARY KEY AUTO_INCREMENT,
                         name            VARCHAR(50) NOT NULL,
                         acronym         VARCHAR(3)  NOT NULL,
                         data_billing    VARCHAR(200)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE process (
                       id				BIGINT(20)		PRIMARY KEY AUTO_INCREMENT,
                       external_code	VARCHAR(20)		NOT NULL,
                       internal_code	VARCHAR(7)		NOT NULL,
                       dt_creation		DATETIME		NOT NULL,
                       requester_id	  BIGINT(20)		NOT NULL,
                       FOREIGN KEY (requester_id) REFERENCES requester(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE service_type (
                            id				      BIGINT(20)      PRIMARY KEY AUTO_INCREMENT,
                            name	          VARCHAR(30)		  NOT NULL,
                            default_value	DECIMAL(8,2)		NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO requester (name, acronym, data_billing) values ('Banco Whitestar', 'WHT', '');
INSERT INTO requester (name, acronym, data_billing) values ('Banco Millenium', 'BCP', '');

INSERT INTO process (external_code, internal_code, dt_creation, requester_id) values
('ADWER234242', 'WHT0001', '2019-07-09', 1);
INSERT INTO process (external_code, internal_code, dt_creation, requester_id) values
('YTWETRE75454', 'WHT0002', '2019-07-09', 1);
INSERT INTO process (external_code, internal_code, dt_creation, requester_id) values
('IOUOIUO67657', 'BCP0001', '2019-07-09', 2);
INSERT INTO process (external_code, internal_code, dt_creation, requester_id) values
('CXVCXVXV876876876', 'BCP0002', '2019-07-09', 2);

INSERT INTO service_type (name, default_value) values ('Arrombamento de porta', 10.00);
INSERT INTO service_type (name, default_value) values ('Pintura (m2)', 5.50);
INSERT INTO service_type (name, default_value) values ('Limpeza de terreno (m2)', 20.00);