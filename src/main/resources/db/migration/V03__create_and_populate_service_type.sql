CREATE TABLE service_type (
     id				      BIGINT(20)      PRIMARY KEY AUTO_INCREMENT,
     name	          VARCHAR(30)		  NOT NULL,
     default_value	DECIMAL(8,2)		NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;