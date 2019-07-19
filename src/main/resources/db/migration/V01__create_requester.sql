CREATE TABLE requester (
    id              BIGINT(20)  PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(50) NOT NULL,
    acronym         VARCHAR(3)  NOT NULL,
    data_billing    VARCHAR(200)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;