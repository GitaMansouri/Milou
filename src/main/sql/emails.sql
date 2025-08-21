create table emails(
    id int primary key auto_increment ,
    code varchar(6) primary key ,
    senderEmail nvarchar(100) ,
    recipientEmil nvarchar(100) ,
    subject nvarchar(255) ,
    emailBody nvarchar(4000) ,
    date datetime not null ,
    isReply boolean default false ,
    isForward boolean default false
);