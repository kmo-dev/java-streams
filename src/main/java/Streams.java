import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Streams {

    public static void main(String[] args) {
        final var author1 = new Author("author1", LocalDate.of(1988, Month.AUGUST, 11));
        final var author2 = new Author("author2", LocalDate.of(2002, Month.JANUARY, 1));
        final var author3 = new Author("author3", LocalDate.of(1956, Month.JUNE, 15));
        final var book1 = new Book("book1", Genre.COMEDY, 3.99, 42, 1_000_000_000_000L, List.of(author1, author2, author3));
        final var book2 = new Book("book2", Genre.DRAMA, 15, 317, 2_000_000_000_000L, List.of(author2, author3));
        final var book3 = new Book("book3", Genre.ACTION, 0.99, 4, 3_000_000_000_000L, List.of(author3));
        final var book4 = new Book("book4", Genre.ACTION, 0.99, 4, 4_000_000_000_000L, List.of(author3));

        final var books = List.of(book1, book2, book3, book4);

        final Map<Long, Book> bookByIsbn = books.stream()
                .collect(Collectors.toMap(Book::isbn, Function.identity()));

        final Map<Genre, Book> bookByGenre = books.stream()
                .collect(Collectors.toMap(Book::genre, Function.identity(), (b1, b2) -> b2));

        final Map<Genre, Set<Book>> booksByGenre = books.stream()
                .collect(Collectors.groupingBy(Book::genre, Collectors.toSet()));

        final Map<Genre, Set<Long>> isbnsByGenre = books.stream()
                .collect(Collectors.groupingBy(Book::genre, Collectors.mapping(Book::isbn, Collectors.toSet())));

        final Map<Genre, Set<Author>> authorsByGenre = books.stream()
                .collect(Collectors.groupingBy(Book::genre, Collectors.flatMapping(book -> book.authors().stream(), Collectors.toSet())));

        System.out.println();
    }


    record Book(String title,
                Genre genre,
                double price,
                int pages,
                long isbn,
                List<Author> authors) {
    }

    enum Genre {
        COMEDY, DRAMA, ACTION, THRILLER
    }

    record Author(String name,
                  LocalDate dateOfBirth) {
    }

}
