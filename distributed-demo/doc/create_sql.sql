# 用户表 User
create table if not exists `user` (
	`user_id` varchar(32) not null comment '用户id',
	`username` varchar(128) not null comment '用户名',
	`password` varchar(128) not null comment '密码',
	primary key(`user_id`)
) default charset=utf8 comment '用户表';

# 预先插入的用户数据
insert into `user` values('uid_000000000001', 'zc', 'zc123');commit;
insert into `user` values('uid_000000000002', 'admin', 'admin123');commit;