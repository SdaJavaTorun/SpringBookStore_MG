package com.example.bookstore.model;

import java.util.Objects;

public class BookDetailsDto {
    private final String title;
    private final String author;
    private final String description;

    public BookDetailsDto(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public static BookDetailsDto toDto(BookDetails bookDetails) {
        return new BookDetailsDto(
                bookDetails.getTitle(),
                bookDetails.getAuthor(),
                bookDetails.getDescription()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDetailsDto that = (BookDetailsDto) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(author, that.author) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, description);
    }

    @Override
    public String toString() {
        return "BookDetailsDto{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
