drop table BidList;
drop table Trade;
drop table CurvePoint;
drop table Rating;
drop table RuleName;
drop table Users;

CREATE TABLE BidList (
                         BidListId INT NOT NULL AUTO_INCREMENT,
                         account VARCHAR(30) NOT NULL,
                         type VARCHAR(30) NOT NULL,
                         bidQuantity DOUBLE,
                         askQuantity DOUBLE,
                         bid DOUBLE ,
                         ask DOUBLE,
                         benchmark VARCHAR(125),
                         bidListDate TIMESTAMP,
                         commentary VARCHAR(125),
                         security VARCHAR(125),
                         status VARCHAR(10),
                         trader VARCHAR(125),
                         book VARCHAR(125),
                         creationName VARCHAR(125),
                         creationDate TIMESTAMP ,
                         revisionName VARCHAR(125),
                         revisionDate TIMESTAMP ,
                         dealName VARCHAR(125),
                         dealType VARCHAR(125),
                         sourceListId VARCHAR(125),
                         side VARCHAR(125),

                         PRIMARY KEY (BidListId)
);

CREATE TABLE Trade (
                       TradeId INT NOT NULL AUTO_INCREMENT,
                       account VARCHAR(30) NOT NULL,
                       type VARCHAR(30) NOT NULL,
                       buyQuantity DOUBLE,
                       sellQuantity DOUBLE,
                       buyPrice DOUBLE ,
                       sellPrice DOUBLE,
                       tradeDate TIMESTAMP,
                       security VARCHAR(125),
                       status VARCHAR(10),
                       trader VARCHAR(125),
                       benchmark VARCHAR(125),
                       book VARCHAR(125),
                       creationName VARCHAR(125),
                       creationDate TIMESTAMP ,
                       revisionName VARCHAR(125),
                       revisionDate TIMESTAMP ,
                       dealName VARCHAR(125),
                       dealType VARCHAR(125),
                       sourceListId VARCHAR(125),
                       side VARCHAR(125),

                       PRIMARY KEY (TradeId)
);

CREATE TABLE CurvePoint (
                            Id INT NOT NULL AUTO_INCREMENT,
                            CurveId tinyint,
                            asOfDate TIMESTAMP,
                            term DOUBLE ,
                            value DOUBLE ,
                            creationDate TIMESTAMP ,

                            PRIMARY KEY (Id)
);

CREATE TABLE Rating (
                        Id INT NOT NULL AUTO_INCREMENT,
                        moodysRating VARCHAR(125),
                        sandPRating VARCHAR(125),
                        fitchRating VARCHAR(125),
                        orderNumber tinyint,

                        PRIMARY KEY (Id)
);

CREATE TABLE RuleName (
                          Id INT NOT NULL AUTO_INCREMENT,
                          name VARCHAR(125),
                          description VARCHAR(125),
                          json VARCHAR(125),
                          template VARCHAR(512),
                          sqlStr VARCHAR(125),
                          sqlPart VARCHAR(125),

                          PRIMARY KEY (Id)
);

CREATE TABLE Users (
                       Id INT NOT NULL AUTO_INCREMENT,
                       username VARCHAR(125),
                       password VARCHAR(125),
                       fullname VARCHAR(125),
                       role VARCHAR(125),

                       PRIMARY KEY (Id)
);

insert into Users(fullname, username, password, role) values('Administrator', 'admin', '$2a$12$7prg56oOXF9J6rQ0tHz9JeUjGG2Wp8/7THvSuSPmoSmsoLaOOjnou', 'ADMIN');
insert into Users(fullname, username, password, role) values('User', 'user', '$2a$12$7prg56oOXF9J6rQ0tHz9JeUjGG2Wp8/7THvSuSPmoSmsoLaOOjnou', 'USER');