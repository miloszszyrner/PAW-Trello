ALTER TABLE BOARD
ADD VISIBILITY varchar(8) NOT NULL;

UPDATE BOARD
SET VISIBILITY = 'PRIVATE';

COMMIT;