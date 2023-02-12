CREATE TABLE Computer (
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  brand VARCHAR(50) NOT NULL,
  version VARCHAR(50) NOT NULL,
  description VARCHAR(255),
  price DOUBLE NOT NULL,
  UNIQUE(brand, version)
);

CREATE TABLE ComputerStore (
  computer_id INT UNSIGNED NOT NULL PRIMARY KEY,
  stock INT NOT NULL DEFAULT 0,
  last_provision_date Date,
  FOREIGN KEY (computer_id) REFERENCES Computer(id)
);

CREATE TRIGGER ComputerStoreTrigger AFTER INSERT ON Computer FOR EACH ROW INSERT INTO ComputerStore(computer_id) VALUES (NEW.id);

CREATE TABLE Sale (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  client_name VARCHAR(255),
  computer_id INT UNSIGNED NOT NULL,
  quantity INT NOT NULL DEFAULT 0,
  saleDate Date,
  FOREIGN KEY (computer_id) REFERENCES Computer(id)
);
INSERT INTO Computer (brand, version, description, price) VALUES
	 ('Windows','Windows 11','Released on 06/28/21',139.99),
	 ('Ubuntu','Jammy Jellyfish','04/21/2022',0),
	 ('Mac','MacOS 13 Ventura','Released on 10/24/2022',29.99);

ALTER TABLE Computer AUTO_INCREMENT = 1;
SELECT * FROM Computer;
SELECT * FROM ComputerStore;
SELECT * FROM Sale;

DELETE FROM computer;
DELETE FROM computerstore;

 DROP TABLE Sale;
 DROP TABLE ComputerStore;
 DROP TABLE Computer;
 DROP TABLE ComputerVersion;

