INSERT INTO requester (name, acronym, data_billing) values ('Banco Whitestar', 'WHT', '');
INSERT INTO requester (name, acronym, data_billing) values ('Banco Millenium', 'BCP', '');

INSERT INTO process (external_code, internal_code, dt_creation, requester_id) values
('ADWER234242', 'WHT0001', '2019-07-09 20:12:00', 1);
INSERT INTO process (external_code, internal_code, dt_creation, requester_id) values
('YTWETRE75454', 'WHT0002', '2019-07-09 20:13:00', 1);
INSERT INTO process (external_code, internal_code, dt_creation, requester_id) values
('IOUOIUO67657', 'BCP0001', '2019-07-09 20:14:00', 2);
INSERT INTO process (external_code, internal_code, dt_creation, requester_id) values
('CXVCXVXV876876876', 'BCP0002', '2019-07-09 20:15:00', 2);

INSERT INTO service_type (name, default_value) values ('Arrombamento de porta', 10.00);
INSERT INTO service_type (name, default_value) values ('Pintura (m2)', 5.50);
INSERT INTO service_type (name, default_value) values ('Limpeza de terreno (m2)', 20.00);