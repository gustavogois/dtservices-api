CREATE TABLE process (
                         id				BIGINT(20)		PRIMARY KEY AUTO_INCREMENT,
                         external_code	VARCHAR(20)		NOT NULL,
                         internal_code	VARCHAR(7)		NOT NULL,
                         dt_creation		DATETIME		NOT NULL,
                         requester_id	  BIGINT(20)		NOT NULL,
                         FOREIGN KEY (requester_id) REFERENCES requester(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO process (external_code, internal_code, dt_creation, requester_id) values
('ADWER234242', 'WHT0001', '2019-07-09 20:12:00', 1);
INSERT INTO process (external_code, internal_code, dt_creation, requester_id) values
('YTWETRE75454', 'WHT0002', '2019-07-09 20:13:00', 1);
INSERT INTO process (external_code, internal_code, dt_creation, requester_id) values
('IOUOIUO67657', 'BCP0001', '2019-07-09 20:14:00', 2);
INSERT INTO process (external_code, internal_code, dt_creation, requester_id) values
('CXVCXVXV876876876', 'BCP0002', '2019-07-09 20:15:00', 2);