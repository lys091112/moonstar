create table user
(
  `userid`   bigint(32)   not null auto_increment,
  `username` varchar(120) not null,
  `password` varchar(40)  not null,
  PRIMARY KEY (`userid`)
) ENGINE = InnoDB
  DEFAULT charset = utf8;


CREATE TABLE `user_log`
(
  `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id`       bigint(20) NOT NULL COMMENT '用户id',
  `operator_type` varchar(1024)       DEFAULT '' COMMENT '操作类型',
  `add_date`      timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入库时间',
  `update_date`   timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8 COMMENT ='用户操作记录表'

