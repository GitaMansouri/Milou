create table recipients(
    id int primary key auto_increment ,
    senderEmail nvarchar(100) ,
    recipientEmail nvarchar(100) ,
    isRead boolean default false
);