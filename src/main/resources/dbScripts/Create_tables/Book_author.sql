CREATE TABLE bookapp.book_author (
  `author_id` int NOT NULL,
  `book_id` int NOT NULL,
  PRIMARY KEY (`author_id`,book_id),
  foreign key (author_id) references authors(id),
    foreign key (book_id) references books(id)
);

