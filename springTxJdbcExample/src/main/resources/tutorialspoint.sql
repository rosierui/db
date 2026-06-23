drop table if exists `Student`;
CREATE TABLE Student(
   ID   INT NOT NULL AUTO_INCREMENT,
   NAME VARCHAR(30) NOT NULL,
   AGE  INT NOT NULL,
   PRIMARY KEY (ID)
);

drop table if exists `Marks`;
CREATE TABLE Marks(
   SID INT NOT NULL,
   MARKS  INT NOT NULL,
   YEAR   INT NOT NULL
);
