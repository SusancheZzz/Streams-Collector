package com.rntgroup.collections;

import com.rntgroup.collections.entity.Author;
import com.rntgroup.collections.entity.Book;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StreamDAO {

    private final Book[] books;
    public static final Comparator<Map.Entry<Integer, List<Book>>> PAGES_COMPARATOR =
        Comparator.comparingInt(Map.Entry::getKey);

    public StreamDAO(Book[] books) {
        this.books = books;
    }

    // получение стрима
    public Stream<Book> getBooksStream() {
        return Arrays.stream(books);
    }

    // проверьте, не содержит ли какая-либо книга (или все книги) более 200 страниц
    public boolean ifExistBookWithSingleAuthor() {
        return getBooksStream()
            .anyMatch(book -> book.getNumberOfPages() > 200);
    }

    // найти книгу, в которой 200 страниц
    public Book getBookWithSingleAuthor() {
        return getBooksStream()
            .filter(book -> book.getNumberOfPages() == 200)
            .findFirst().orElseThrow(NoSuchElementException::new);
    }

    // Вспомогалка
    public List<Book> findBooksWithExtremeNumberOfPages(boolean findMax) {
        return getBooksStream()
            .collect(Collectors.groupingBy(Book::getNumberOfPages))
            .entrySet().stream()
            .max(findMax ? PAGES_COMPARATOR : PAGES_COMPARATOR.reversed())
            .map(Map.Entry::getValue)
            .orElseGet(List::of);
    }

    // найти книги с MAX количеством страниц
    public List<Book> findBooksWithMaximumNumberOfPages() {
        return findBooksWithExtremeNumberOfPages(true);
    }

    // найти книги с MIN количеством страниц
    public List<Book> findBooksWithMinimumNumberOfPages() {
        return findBooksWithExtremeNumberOfPages(false);
    }

    // отфильтруйте книги, в которых указан только один автор
    public List<Book> filterBookWhereSingleAuthor() {
        return getBooksStream()
            .filter(b -> b.getAuthors().size() == 1)
            .toList();
    }

    // отсортируйте книги по количеству страниц/названию
    public List<Book> sortBookOfNumberPagesAndTitle() {
        return getBooksStream()
            .sorted(Comparator.comparingInt(Book::getNumberOfPages).thenComparing(Book::getTitle))
            .toList();
    }

    // получите список всех названий книг
    public Set<String> getAllUniqueTitle() {
        return getBooksStream()
            .map(Book::getTitle)
            .collect(Collectors.toSet());
    }

    // выведите в консоль список всех названий книг
    public void printAllUniqueTitle() {
        getBooksStream()
            .map(Book::getTitle)
            .forEach(System.out::println);
    }

    // получите список всех авторов
    public Set<Author> getAllAuthors() {
        return getBooksStream()
            .flatMap(b -> b.getAuthors().stream())
            .collect(Collectors.toSet());
    }

    // Определите названия книги с MAX количеством страниц для каждого автора
    public Map<Author, String> findBookWithMaxNumberOfPagesForEveryoneAuthors() {
        return getBooksStream()
            .flatMap(book -> book.getAuthors().stream()
                .map(author -> Map.entry(author, book))) // <Author, Book>
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (book1, book2) -> book1.getNumberOfPages() >= book2.getNumberOfPages() ? book1 : book2
            ))
            .entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().getTitle() // Берём только название книги
            ));
    }
}
