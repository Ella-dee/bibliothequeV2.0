package com.mclientui.microserviceclientui.proxies;

import com.mclientui.microserviceclientui.beans.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <h2>Proxy links clientui to microservice-books</h2>
 */
@FeignClient(name = "microservice-books", url = "localhost:9001")
public interface MicroserviceBooksProxy {

    @GetMapping(value = "/Livres")
    List<BookBean> listBooks();

    @GetMapping( value = "/Livres/{id}")
    BookBean showBook(@PathVariable("id") Integer id);

    @GetMapping(value = "/Auteurs")
    List<AuthorBean> listAuthors();

    @GetMapping( value = "/Auteurs/{id}")
    AuthorBean showAuthor(@PathVariable("id") Integer id);

    @GetMapping(value = "/Genres")
    List<CategoryBean> listCategories();

    @GetMapping( value = "/Auteurs/{id}")
    CategoryBean showCategory(@PathVariable("id") Integer id);

    @GetMapping(value = "/Prets")
    List<BorrowingBean> listBorrowings();

    @GetMapping( value = "/Prets/{id}")
    BorrowingBean showBorrowing(@PathVariable("id") Integer id);



}