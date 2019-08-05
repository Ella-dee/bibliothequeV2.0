package com.mclientui.microserviceclientui.proxies;

import com.mclientui.microserviceclientui.beans.*;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <h2>Proxy links clientui to microservice-books</h2>
 */
@FeignClient(name = "zuul-server")
@RibbonClient(name = "microservice-books")
public interface MicroserviceBooksProxy {

    @GetMapping(value = "microservice-books/Livres")
    List<BookBean> listBooks();

    @GetMapping( value = "microservice-books/Livres/{id}")
    BookBean showBook(@PathVariable("id") Integer id);

    @GetMapping(value = "microservice-books/Auteurs")
    List<AuthorBean> listAuthors();

    @GetMapping( value = "microservice-books/Auteurs/{id}")
    AuthorBean showAuthor(@PathVariable("id") Integer id);

    @GetMapping(value = "microservice-books/Genres")
    List<CategoryBean> listCategories();

    @GetMapping( value = "microservice-books/Genres/{id}")
    CategoryBean showCategory(@PathVariable("id") Integer id);

    @GetMapping(value = "microservice-books/Prets")
    List<BorrowingBean> listBorrowings();

    @GetMapping( value = "microservice-books/Prets/{id}")
    BorrowingBean showBorrowing(@PathVariable("id") Integer id);



}