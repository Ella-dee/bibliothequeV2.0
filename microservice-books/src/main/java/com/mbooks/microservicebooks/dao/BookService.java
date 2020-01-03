package com.mbooks.microservicebooks.dao;

import com.mbooks.microservicebooks.model.Book;
import com.mbooks.microservicebooks.model.Borrowing;
import com.mbooks.microservicebooks.model.WaitingList;
import com.mbooks.microservicebooks.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mbooks.microservicebooks.proxies.MicroserviceMailingProxy;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BookService {
    @Autowired
    private BorrowingDao borrowingDao;
    @Autowired
    private WaitingListDao waitingListDao;
    @Autowired
    private MicroserviceMailingProxy mailingProxy;

    public void setBookAvailability(Book book){
        boolean availableOrNot = true;
        String bookRef = book.getRef();
        int notReturnedYet = 0;
        int booksAvailable = book.getNbr();
        int available = booksAvailable;
        //list all borrowings for this book
        List<Borrowing> borrowingsByBook = borrowingDao.findBorrowingByBook_Id(book.getId());
        //if there is at least one borrowing with this book ref
        if (borrowingsByBook.size() > 0) {
            //notReturnedYet occurences where the book is not returned yet
            for (Borrowing borrowing : borrowingsByBook) {
                if (borrowing.getReturned() == null) {
                    notReturnedYet++;
                }
            }
            available=booksAvailable - notReturnedYet;
            //if all borrowed books with this ref have not been returned, then this book is not available
            if (available == 0) {
                availableOrNot=false;
                //looks for the closest return date possible:
                //1 - Get all string dates in date array
                ArrayList<LocalDate> allDates = new ArrayList<>();
                for (Borrowing borrowing: borrowingsByBook){
                    //If the borrowing has not been returned yet
                    if(borrowing.getReturned() == null) {
                        Date stringToDate = DateUtils.convertStringToDateFormat(borrowing.getLimitDate());
                        allDates.add(DateUtils.convertToLocalDateViaInstant(stringToDate));
                    }
                }
                //2 - Sort dates in order and get first date
                Collections.sort(allDates);
                LocalDate closestReturnDate = allDates.get(0);
                //3 - Set closestReturnDate to book
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                book.setClosestReturnDate(closestReturnDate.format(format));

            }
            //if at least one has been returned: check if there is a waiting list to set priority for user waiting
            else {
                List<WaitingList> waitingLists = book.getWaitingList();
                if (!waitingLists.isEmpty()) {
                    availableOrNot = false;
                    System.out.println(book.getTitle()+" a une waiting list: available " + availableOrNot);
                } else {
                    availableOrNot = true;
                    System.out.println(book.getTitle()+" a pas de waiting list: available " + availableOrNot);
                }
            }
        }
        //if no borrowings for this book
        else {
                availableOrNot=true;
        }
        book.setAvailable(availableOrNot);
        book.setAvailableBooksNbr(available);
    }

    /**
     * <h2>After book is returned, checks if people are waiting for it</h2>
     * @param book
     */
    public void checkForWaitingList(Book book){
        //check if there's a waiting list for that book
        Integer bookReturnedWaitingList = book.getWaitingList().size();
        //if there is send an email
        if (bookReturnedWaitingList>0){
            List<WaitingList> list = book.getWaitingList();
            ArrayList<Integer> idList = new ArrayList<>();
            for (WaitingList item: list){
                idList.add(item.getId());
            }
            Collections.sort(idList);
            WaitingList waitingList = waitingListDao.getOne(idList.get(0));
            //TODO status 504 gateaway timeout dans postman / mail envoyé quand même
            mailingProxy.sendNotifWhenAwaitedBookIsReturned(waitingList.getUserId(), waitingList.getBook().getId());
            System.out.println("userid "+waitingList.getUserId()+" bookid "+waitingList.getBook().getId());
        }
    }
}
