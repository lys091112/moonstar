create table user (
    `userid` bigint(32) not null auto_increment,
    `username` varchar(120) not null,
    `password` varchar(40) not null,
    PRIMARY KEY (`userid`)
)ENGINE=InnoDB DEFAULT charset = utf8;
