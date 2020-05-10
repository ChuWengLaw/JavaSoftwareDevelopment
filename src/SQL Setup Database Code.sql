drop table billboards;
CREATE TABLE billboards (
    billboardname varchar(20) PRIMARY KEY,
    textcolour varchar(20),
    backgroundcolour varchar(20),
    message varchar(20) NOT NULL,
    image varchar(20) NOT NULL,
    information varchar(20) NOT NULL,
    author NOT NULL FOREIGN KEY REFERENCES users(userName)
    );
    
    INSERT INTO billboards (billboardname, textcolour, message, image, information)
VALUES ('KFC', 'REDHEXI', 'Chicken', 'URL of Chicken', 'Fast Food');

    INSERT INTO billboards
VALUES ('Coles', 'red', 'white', 'fresh food', 'URL coleslogo', 'Cheap');

    INSERT INTO billboards
VALUES ('Callum', 'Blue', 'green', 'yes', 'URL', 'yes');

    INSERT INTO billboards
VALUES ('ABC Cafe', 'RED', 'BLACK', 'RED', 'URL REDBLACK', 'BLACK');

    INSERT INTO billboards
VALUES ('Diet Givers', 'orange', 'blue', 'Lose Weight', 'URL gym', 'Fast');

CREATE TABLE users (
userName varchar(20) PRIMARY KEY;
);