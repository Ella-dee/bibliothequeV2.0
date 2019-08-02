package com.mclientui.microserviceclientui.web.controller;

import com.mclientui.microserviceclientui.beans.AuthorBean;
import com.mclientui.microserviceclientui.beans.BookBean;
import com.mclientui.microserviceclientui.beans.CategoryBean;
import com.mclientui.microserviceclientui.proxies.MicroserviceBooksProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <h2>Controller linking with microservice-books</h2>
 */
@Controller
public class ClientBooksController {

    @Autowired
    private MicroserviceBooksProxy booksProxy;

    /**
     * <p>Lists all books</p>
     * @param model
     * @return books.html
     */
    @RequestMapping("/Livres")
    public String listBooks(Model model){
        List<BookBean> books =  booksProxy.listBooks();
        model.addAttribute("books", books);
        return "books";
    }

    /**
     * shows details of particular book with its id
     * @param bookId
     * @param model
     * @return book-details.html
     */
    @RequestMapping("/Livres/{bookId}")
    public String bookDetails(@PathVariable Integer bookId, Model model){
        BookBean book = booksProxy.showBook(bookId);
        model.addAttribute("book", book);
        return "book-details";
    }
    /**
     * <p>Lists all authors</p>
     * @param model
     * @return authors.html
     */
    @RequestMapping("/Auteurs")
    public String listAuthors(Model model){
        List<AuthorBean> authors = booksProxy.listAuthors();
        System.out.println(authors);
        model.addAttribute("authors", authors);
        return "authors";
    }
    /**
     * shows details of particular author with its id
     * @param authorId
     * @param model
     * @return author-details.html
     */
    @RequestMapping("/Auteurs/{authorId}")
    public String showAuthor(@PathVariable Integer authorId, Model model){
        AuthorBean author = booksProxy.showAuthor(authorId);
        model.addAttribute("author", author);
        return "author-details";
    }
    /**
     * <p>Lists all categories of books</p>
     * @param model
     * @return categories.html
     */
    @RequestMapping(value = "/Genres")
    public String listCategories(Model model){
        List<CategoryBean> categories = booksProxy.listCategories();
        model.addAttribute("categories", categories);
        return "categories";
    }
    /**
     * shows details (list of books) of particular category with its id
     * @param catId
     * @param model
     * @return category-details.html
     */
    @RequestMapping( value = "/Genres/{catId}")
    public String showCategory(@PathVariable Integer catId, Model model){
        CategoryBean cat = booksProxy.showCategory(catId);
        model.addAttribute("cat", cat);
        return "category-details";
    }
}