package com.rntgroup.collections.entity;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class Book {

    String title;
    int numberOfPages;
    List<Author> authors = new ArrayList<>();

    public Book(String title, int numberOfPages, Author... authors) {
        this.title = title;
        this.numberOfPages = numberOfPages;
        for (Author author : authors) {
            if(!this.authors.contains(author)){
                this.authors.add(author);
            }
        }
    }

}
