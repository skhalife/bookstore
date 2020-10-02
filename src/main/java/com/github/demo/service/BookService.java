package com.github.demo.service;

import com.github.demo.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    private static final String API_TOKEN = "AIzaSyAQfxPJiounkhOjODEO5ZieffeBv6yft2Q";

    private IBookDatabase booksDatabase;

    public BookService() {
        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl == null) {
            databaseUrl = "jdbc:sqlite::memory:";
        }

        String databaseUser = System.getenv("DATABASE_USER");
        String databasePassword = System.getenv("DATABASE_PASSWORD");

        booksDatabase = new BookDatabaseImpl(databaseUrl, databaseUser, databasePassword);

        String noDbInit = System.getenv("DB_NO_INIT");
        if (noDbInit == null) {
            List<Book> books = new ArrayList<Book>(6);

            books.add(new Book("Jeff Sutherland","Scrum: The Art of Doing Twice the Work in Half the Time", "scrum.jpg"));
            books.add(new Book("Eric Ries","The Lean Startup: How Constant Innovation Creates Radically Successful Businesses", "lean.jpg"));
            books.add(new Book("Geoffrey A. Moore","Crossing the Chasm", "chasm.jpg"));
            books.add(new Book("David Thomas","The Pragmatic Programmer: From Journeyman to Master", "pragmatic.jpg"));
            books.add(new Book("Frederick P. Brooks Jr.", "The Mythical Man-Month: Essays on Software Engineering", "month.jpg"));
            books.add(new Book("Steve Krug","Don't Make Me Think, Revisited: A Common Sense Approach to Web Usability", "think.jpg"));
            
            booksDatabase.populate(books);
        }
    }

    public List<Book> getBooks() {
        return this.booksDatabase.getAll();
    }

    public List<Book> searchBooks(String name) {
        return this.booksDatabase.getBooksByTitle(name);
    }
}