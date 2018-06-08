package com.books;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class BookStorage extends ArrayList<Book> {

    public BookStorage filterByAuthorName(String name) {
        BookStorage result = new BookStorage();
        Iterator<Book> iterator = iterator();
        while (iterator.hasNext()) {
            Book currentBook = iterator.next();
            Set<Person> authors = currentBook.getAuthors();
            for (Person author : authors)
                if (author.getFirstName().contains(name)) {
                    result.add(currentBook);
                    break;
                }
        }
        return result;
    }

    @Override
    public String toString() {
        String result = "";
        Iterator<Book> iterator = iterator();
        if (iterator.hasNext()) {
            while (iterator.hasNext()) {
                Book book = iterator.next();
                result += book + "\n";
            }
        } else {
            result = "Storage is empty";
        }
        return result;
    }
}
