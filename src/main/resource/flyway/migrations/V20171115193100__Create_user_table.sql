CREATE TABLE USER(
ID int NOT NULL AUTO_INCREMENT,
USERNAME varchar(50) NOT NULL,
PASSWORD varchar(255) NOT NULL,
PRIMARY KEY(ID)
);

ALTER TABLE USER AUTO_INCREMENT=10000;

COMMIT;