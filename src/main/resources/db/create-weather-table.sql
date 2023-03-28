create sequence seq_weather as BIGINT
start with 10
increment by 50
minvalue 10
cycle
cache 20;

create table weather(
id int not null,
temperature varchar(10),
city_name varchar(120),
primary key(id)
);