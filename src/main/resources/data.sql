drop table if exists `boot_backend`;
create table boot_backend (
    `id` int primary key auto_increment,
    `name` varchar(20),
    `create_time` datetime default null
);
insert into boot_backend(id, name, create_time) values(1, 'shenyun', SYSDATE());