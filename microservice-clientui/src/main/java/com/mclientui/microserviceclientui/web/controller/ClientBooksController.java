package com.mclientui.microserviceclientui.web.controller;

import com.mclientui.microserviceclientui.beans.*;
import com.mclientui.microserviceclientui.proxies.MicroserviceBooksProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <h2>Controller linking with microservice-books</h2>
 */
@Controller
public class ClientBooksController {

    @Autowired
    private MicroserviceBooksProxy booksProxy;

    /**
     * <p>Lists all books by categories</p>
     * @param model
     * @return books.html
     */
    @RequestMapping("/Livres")
    public String listBooks(Model model){
        List<CategoryBean> categories = booksProxy.listCategories();
        for (CategoryBean c: categories){
            for (BookBean b:c.getBooks()){
                b.setAvailable(booksProxy.showBook(b.getId()).getAvailable());
            }
        }
        model.addAttribute("categories", categories);
        return "books";
    }

    /**
     * <p>Lists all books by titre like %</p>
     * @param model
     * @return books.html
     */
    @RequestMapping("/Livres/rechercheParTitre")
    public String searchBooksByTitle(@ModelAttribute("book") BookBean bookBean, Model model){
        List<BookBean> books = booksProxy.searchBooksByTitle(bookBean.getTitle());
        model.addAttribute("books", books);
        return "booksByTitle";
    }

    /**
     * shows details of particular book with its id
     * @param bookId
     * @param model
     * @return book-details.html
     */
    @RequestMapping("/Livres/{bookId}")
    public String bookDetails(@PathVariable Integer bookId, Model model,HttpServletRequest request){
        BookBean book = booksProxy.showBook(bookId);
        HttpSession session = request.getSession();
        Boolean sessionExists = false;
        Boolean hasBorrowingRunning = false;
        Boolean isOnUserWaitingList = false;
        if(session.getAttribute("loggedInUserId")!=null) {
            sessionExists = true;
            Integer userId = (Integer) session.getAttribute("loggedInUserId");
            //get user waiting lists
            List<WaitingListBean> waitingListBeanList = booksProxy.showUserWaitingList(userId);
            if(!waitingListBeanList.isEmpty()) {
                System.out.println("waiting list for this book exists");
                for (WaitingListBean w : waitingListBeanList) {
                    if (w.getUserId() == userId) {
                        if (w.getBook().getId() == bookId) {
                            isOnUserWaitingList = true;
                        }
                    }
                }
            }
            //get user borrowings
            List<BorrowingBean> borrowingBeanList=booksProxy.listBorrowings();
            for(BorrowingBean borrowingBean:borrowingBeanList){
                if(borrowingBean.getIdUser() == userId){
                    if (borrowingBean.getBook().getId()==bookId){
                        hasBorrowingRunning = true;
                    }
                }
            }
        }

        model.addAttribute("hasBorrowingRunning", hasBorrowingRunning);
        model.addAttribute("isOnUserWaitingList", isOnUserWaitingList);
        model.addAttribute("sessionExists", sessionExists);
        model.addAttribute("book", book);
        return "book-details";
    }

    @RequestMapping(value = "/Reservations/Livre/{bookId}/utilisateur-sur-liste")
    public String addUserToWaitingList(@PathVariable Integer bookId, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer userId = (Integer)session.getAttribute("loggedInUserId");
        //try {
            booksProxy.addUserToWaitingList(userId, bookId);
       /* }catch (Exception e){
            if(e instanceof CannotAddException){
                model.addAttribute("errorMessage", e.getMessage());
            }
        }*/
        return "redirect:/Livres/"+bookId;
    }

    /**
     * <p>Lists all authors</p>
     * @param model
     * @return authors.html
     */
    @RequestMapping("/Auteurs")
    public String listAuthors(Model model){
        List<AuthorBean> authors = booksProxy.listAuthors();
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
     * shows details (list of books) of particular category with its id
     * @param catId
     * @param model
     * @return category-details.html
     */
    @RequestMapping( value = "/Genres/{catId}")
    public String showCategory(@PathVariable Integer catId, Model model){
        CategoryBean cat = booksProxy.showCategory(catId);
        model.addAttribute("cat", cat);
        List<BookBean> books = booksProxy.listBooks();
        model.addAttribute("books", books);
        return "category-details";
    }

    /**
     * renews a borrowing if possible
     * @param borrowId id of the borrowing
     * @return profile page of borrowing's user
     */
    @RequestMapping(value = "/Prets/{borrowId}/renew")
        public String renewBorrowing (@PathVariable Integer borrowId){
        BorrowingBean borrowingBean = booksProxy.renewBorrowing(borrowId);
        return "redirect:/Utilisateurs/MonProfil/"+borrowingBean.getIdUser();
        }
}