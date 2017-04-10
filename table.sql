CREATE TABLE `user_chat_info` (
  `ip` varchar(40) DEFAULT NULL COMMENT 'ip',
  `user_type` char(4) DEFAULT NULL COMMENT '用户类型',
  `name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `content` varchar(400) DEFAULT NULL COMMENT '消息内定',
  `msg_desc` varchar(40) DEFAULT NULL COMMENT '消息简介'
) ENGINE=InnoDB DEFAULT CHARSET=utf8
