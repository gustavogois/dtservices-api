CREATE TABLE process (
                         id				BIGINT(20)		PRIMARY KEY AUTO_INCREMENT,
                         external_code	VARCHAR(20)		NOT NULL,
                         internal_code	VARCHAR(7)		NOT NULL,
                         dt_creation		DATETIME		NOT NULL,
                         requester_id	  BIGINT(20)		NOT NULL,
                         FOREIGN KEY (requester_id) REFERENCES requester(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;