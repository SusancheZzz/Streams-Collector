package com.rntgroup.collections;

import com.rntgroup.collections.entity.Author;
import com.rntgroup.collections.entity.Book;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StreamDAOTest {

    Author[] authors;
    Book[] books;

    StreamDAO streamDAO;

    @Before
    public void init() {
        Author author1 = new Author("aut_1", (short) 10);
        Author author2 = new Author("aut_2", (short) 20);
        Author author3 = new Author("aut_3", (short) 30);
        Author author4 = new Author("aut_4", (short) 40);
        Author author5 = new Author("aut_5", (short) 50);

        Book book1 = new Book("title_1", 100,
            author1, author3, author5);
        Book book2 = new Book("title_2", 200,
            author2);
        Book book3 = new Book("title_3", 100,
            author2, author4);
        Book book4 = new Book("title_4", 500,
            author3, author4);
        Book book5 = new Book("title_5", 500,
            author2, author4, author5);

        authors = new Author[]{author1, author2, author3, author4, author5};
        books = new Book[]{book1, book2, book3, book4, book5};

        streamDAO = new StreamDAO(Arrays.stream(books));
    }

    @Test
    public void testIsBooksWithSingleAuthor() {
        assertTrue(streamDAO.isBooksWithSingleAuthor());
    }

    @Test
    public void testGetBooksWithSingleAuthor() {
        assertEquals(books[1], streamDAO.getBooksWithSingleAuthor());
    }

    @Test
    public void testFindBooksWithMaximumNumberOfPages() {
        assertEquals(List.of(books[3], books[4]), streamDAO.findBooksWithMaximumNumberOfPages());
    }

    @Test
    public void testFindBooksWithMinimumNumberOfPages() {
        assertEquals(List.of(books[0], books[2]), streamDAO.findBooksWithMinimumNumberOfPages());
    }

    @Test
    public void testFilterBookWhereSingleAuthor() {
        assertEquals(List.of(books[1]),
            streamDAO.filterBookWhereSingleAuthor());
    }

    @Test
    public void testSortBookOfNumberPagesAndTitle() {
        assertEquals(List.of(books[0], books[2], books[1], books[3], books[4]),
            streamDAO.sortBookOfNumberPagesAndTitle());
    }

    @Test
    public void testGetAllUniqueTitle() {
        assertEquals(Set.of(books[0].getTitle(), books[1].getTitle(), books[2].getTitle(), books[3].getTitle(), books[4].getTitle()),
            streamDAO.getAllUniqueTitle());
    }

    @Test
    public void testPrintAllUniqueTitle() {
        System.out.println("List-title:");
        streamDAO.printAllUniqueTitle();
    }

    @Test
    public void testGetAllAuthors() {
        assertEquals(Set.of(authors[0], authors[1], authors[2], authors[3], authors[4]),
            streamDAO.getAllAuthors());
    }

    @Test
    public void testFindBookWithMaxNumberOfPagesForEveryoneAuthors() {
        assertEquals(Map.of(
                authors[0], books[0].getTitle(),
                authors[1], books[4].getTitle(),
                authors[2], books[3].getTitle(),
                authors[3], books[3].getTitle(),
                authors[4], books[4].getTitle()
            ),
            streamDAO.findBookWithMaxNumberOfPagesForEveryoneAuthors());
    }
}