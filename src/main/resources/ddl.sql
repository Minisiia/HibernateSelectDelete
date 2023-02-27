USE library;


DROP TABLE book_delete;
DROP TABLE author_delete;
CREATE TABLE author_delete
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(45)
);
CREATE TABLE book_delete (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(256),
    author_id int-- ,
    -- FOREIGN KEY (author_id) REFERENCES author(id)
);

