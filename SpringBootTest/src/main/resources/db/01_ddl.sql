create table if not exists t_user
(
    `id`    int   PRIMARY KEY    NOT NULL AUTO_INCREMENT,
    `name`  varchar(200) NOT NULL,
    `sex`   varchar(10)     NOT NULL DEFAULT '男',
    `pwd`   varchar(50),
    `email` varchar(50)
);
