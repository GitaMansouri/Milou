create table users(
    id int primary key auto_increment ,
    name nvarchar(50) not null ,
    email varchar(100) not null ,
    password varchar(255) not null
);