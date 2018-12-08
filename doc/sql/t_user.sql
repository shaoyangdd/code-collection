create table t_user
(
  user_id   int         not null
    primary key,
  user_name varchar(20) null,
  password  varchar(20) null,
  phone     varchar(15) null
)
  comment '用户表';
