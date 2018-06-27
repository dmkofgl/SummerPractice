CREATE TABLE bookapp.books (
  `id` int not null auto_increment,
  `name` varchar(50) NOT NULL,
  `publisher_Id`int NULL,
  `bookdate` date NOT NULL,
  PRIMARY KEY (`id`)
);