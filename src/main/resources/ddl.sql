USE library;

DROP TABLE book;
DROP TABLE author;
CREATE TABLE author
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(45)
);
CREATE TABLE book (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(256),
    author_id int-- ,
    -- FOREIGN KEY (author_id) REFERENCES author(id)
);

