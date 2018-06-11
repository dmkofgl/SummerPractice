package com.books;

import java.util.*;
import java.util.stream.Collectors;

public class BookFilter {

    public List<Book> filterByAuthorName(Collection<Book> books, String part) {
        return books.stream().filter((book -> book.getAuthors().stream()
                .map(author -> author.getFirstName() + " " + author.getLastName())
                .anyMatch(authorInfo -> authorInfo.contains(part)))).collect(Collectors.toList());

    }


}
