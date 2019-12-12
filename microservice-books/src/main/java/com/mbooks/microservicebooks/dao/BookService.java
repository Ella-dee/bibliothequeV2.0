package com.mbooks.microservicebooks.dao;

import com.mbooks.microservicebooks.model.Book;
import com.mbooks.microservicebooks.model.Borrowing;
import com.mbooks.microservicebooks.model.WaitingList;
import com.mbooks.microservicebooks.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proxies.MicroserviceMailingProxy;
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
            //available books minus those borrowed that have not been returned
            booksAvailable -= notReturnedYet;
            //if all borrowed books with this ref have not been returned, then this book is not available
            if (notReturnedYet >= borrowingsByBook.size()) {
                availableOrNot=false;
                //looks for the closest return date possible:
                //1 - Get all string dates in date array
                ArrayList<LocalDate> allDates = new ArrayList<>();
                for (Borrowing borrowing: borrowingsByBook){
                    Date stringToDate = DateUtils.convertStringToDateFormat(borrowing.getLimitDate());
                    allDates.add(DateUtils.convertToLocalDateViaInstant(stringToDate));
                }
                //2 - Sort dates in order and get first date
                Collections.sort(allDates);
                LocalDate closestReturnDate = allDates.get(0);
                //3 - Set closestReturnDate to book
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                book.setClosestReturnDate(closestReturnDate.format(format));

            }
        } else {
            availableOrNot=true;
        }
        book.setAvailable(availableOrNot);
        book.setAvailableBooksNbr(booksAvailable);
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
            mailingProxy.sendNotifWhenAwaitedBookIsReturned(waitingList.getIdUser(), waitingList.getBook().getId());
        }
    }
}
