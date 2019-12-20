package com.mbooks.microservicebooks.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class BookTest {

    private Book book;
    private Author author;
    private Category category;
    private Category category2;
    private Borrowing borrowing;
    private WaitingList waitingList;

    @Before
    public void setUp() throws Exception {
        /* jeu de données */
        author=new Author();
        author.setId(2);
        author.setFirstName("Newton");
        author.setLastName("Scamender");
        author.setBirthDate("13/12/1924");
        author.setNationality("Anglaise");

        category=new Category();
        category.setName("Categorie de Test");
        category.setId(22);

        category2=new Category();
        category2.setName("Encore du Test");
        category2.setId(23);

        book = new Book();
        book.setTitle("Les animaux fantastiques");
        book.setSynopsis("Niffler arrête de chipper!");
        book.setUsersWaiting(2);
        book.setClosestReturnDate("24/12/2019");
        book.setAuthor(author);
        book.setBookCover("book cover image stored online with url");
        book.setAvailableBooksNbr(0);
        book.setAvailable(false);
        book.setNbr(6);
        book.setEditionHouse("Edition Fait Maison");
        book.setRef("AF2578953HY");
        book.setReleaseDate("21/12/2017");
        book.setPages(432);
        book.setCategory(category);

        List<WaitingList> waitingLists = new ArrayList<>();
        waitingList = new WaitingList();
        waitingList.setBook(book);
        waitingList.setUserPos(4);
        waitingList.setUserId(6);
        waitingList.setId(8);
        waitingLists.add(waitingList);

        List<Borrowing> borrowingList = new ArrayList<>();
        borrowing = new Borrowing();
        borrowing.setBook(book);
        borrowing.setBorrowed("21/10/2018");
        borrowing.setIdUser(3);
        borrowingList.add(borrowing);

        book.setWaitingList(waitingLists);
        book.setBorrowingList(borrowingList);
    }

    @Test(expected = Test.None.class)
    public void getUsersWaiting() {
        Assert.assertEquals((Integer)2, book.getUsersWaiting());
        Assert.assertNotEquals((Integer)4, book.getUsersWaiting());
        book.setUsersWaiting(4);
        Assert.assertEquals((Integer)4, book.getUsersWaiting());
        Assert.assertNotEquals((Integer)2, book.getUsersWaiting());
    }

    @Test(expected = Test.None.class)
    public void getClosestReturnDate() {
        Assert.assertEquals("24/12/2019", book.getClosestReturnDate());
        Assert.assertNotEquals("30/06/2020", book.getClosestReturnDate());
        book.setClosestReturnDate("30/06/2020");
        Assert.assertEquals("30/06/2020", book.getClosestReturnDate());
        Assert.assertNotEquals("24/12/2019", book.getClosestReturnDate());
    }

    @Test(expected = Test.None.class)
    public void getWaitingList() {
        Assert.assertEquals("[WaitingList{id=8, book=Book{id=null, ref='AF2578953HY', title='Les animaux fantastiques'}, userId=6, userPos=4}]", book.getWaitingList().toString());
        Assert.assertNotEquals(null, book.getWaitingList());
        List<WaitingList> waitingLists = book.getWaitingList();
        WaitingList waitingList2 = new WaitingList();
        waitingList2.setBook(book);
        waitingList2.setUserPos(1);
        waitingList2.setUserId(2);
        waitingList2.setId(3);
        waitingLists.add(waitingList2);
        Assert.assertEquals(2, book.getWaitingList().size());
        Assert.assertNotEquals(1, book.getWaitingList().size());
    }

    @Test(expected = Test.None.class)
    public void getAvailable() {
        Assert.assertEquals(false, book.getAvailable());
        Assert.assertNotEquals(true, book.getAvailable());
        book.setAvailable(true);
        Assert.assertEquals(true, book.getAvailable());
        Assert.assertNotEquals(false, book.getAvailable());
    }

    @Test(expected = Test.None.class)
    public void getNbr() {
        Assert.assertEquals((Integer)6, book.getNbr());
        Assert.assertNotEquals((Integer)0, book.getNbr());
        book.setNbr(1);
        Assert.assertEquals((Integer)1, book.getNbr());
        Assert.assertNotEquals((Integer)6, book.getNbr());
    }

    @Test(expected = Test.None.class)
    public void getAvailableBooksNbr() {
        Assert.assertEquals(0, book.getAvailableBooksNbr());
        Assert.assertNotEquals(8, book.getAvailableBooksNbr());
        book.setAvailableBooksNbr(1);
        Assert.assertEquals(1, book.getAvailableBooksNbr());
        Assert.assertNotEquals(6, book.getAvailableBooksNbr());
    }

    @Test(expected = Test.None.class)
    public void getBorrowingList() {
        Assert.assertEquals("[Borrowing{id=null, borrowed=21/10/2018, returned=null}]", book.getBorrowingList().toString());
        Assert.assertNotEquals(null, book.getBorrowingList());
        List<Borrowing> borrowingList = book.getBorrowingList();
        Borrowing borrowing2 = new Borrowing();
        borrowing2.setBook(book);
        borrowing2.setBorrowed("23/08/2019");
        borrowing2.setIdUser(2);
        borrowingList.add(borrowing2);
        Assert.assertEquals(2, book.getBorrowingList().size());
        Assert.assertNotEquals(1, book.getBorrowingList().size());
    }

    @Test(expected = Test.None.class)
    public void getSynopsis() {
        Assert.assertEquals("Niffler arrête de chipper!", book.getSynopsis());
        Assert.assertNotEquals("La vie est un long fleuve tranquille sur un océan d'ennui", book.getSynopsis());
        book.setSynopsis("C'est un beau roman c'est une belle histoire");
        Assert.assertEquals("C'est un beau roman c'est une belle histoire", book.getSynopsis());
        Assert.assertNotEquals("A long time ago in a galaxy far far away...", book.getSynopsis());

    }

    @Test(expected = Test.None.class)
    public void getBookCover() {
        Assert.assertEquals("book cover image stored online with url", book.getBookCover());
        Assert.assertNotEquals("null null null", book.getBookCover());
        book.setBookCover("https://gph.is/SkI3MM");
        Assert.assertEquals("https://gph.is/SkI3MM", book.getBookCover());
        Assert.assertNotEquals("book cover image stored online with url", book.getBookCover());
    }

    @Test(expected = Test.None.class)
    public void getRef() {
        Assert.assertEquals("AF2578953HY", book.getRef());
        Assert.assertNotEquals("ref ref ref", book.getRef());
        book.setRef("PR2019NEW");
        Assert.assertEquals("PR2019NEW", book.getRef());
        Assert.assertNotEquals("AF2578953HY", book.getRef());
    }

    @Test(expected = Test.None.class)
    public void getTitle() {
        Assert.assertEquals("Les animaux fantastiques", book.getTitle());
        Assert.assertNotEquals("The rise of skywalker", book.getTitle());
        book.setTitle("The rise of skywalker");
        Assert.assertEquals("The rise of skywalker", book.getTitle());
        Assert.assertNotEquals("pew pew pew", book.getTitle());
    }

    @Test(expected = Test.None.class)
    public void getEditionHouse() {
        Assert.assertEquals("Edition Fait Maison", book.getEditionHouse());
        Assert.assertNotEquals("Penguin", book.getEditionHouse());
        book.setEditionHouse("Albinos Miguel");
        Assert.assertEquals("Albinos Miguel", book.getEditionHouse());
        Assert.assertNotEquals("Folio", book.getEditionHouse());
    }

    @Test(expected = Test.None.class)
    public void getReleaseDate() {
        Assert.assertEquals("21/12/2017", book.getReleaseDate());
        Assert.assertNotEquals("30/06/2020", book.getReleaseDate());
        book.setReleaseDate("30/06/2020");
        Assert.assertEquals("30/06/2020", book.getReleaseDate());
        Assert.assertNotEquals("24/12/2019", book.getReleaseDate());
    }

    @Test(expected = Test.None.class)
    public void getPages() {
        Assert.assertEquals((Integer)432, book.getPages());
        Assert.assertNotEquals((Integer)8, book.getPages());
        book.setPages(1);
        Assert.assertEquals((Integer)1, book.getPages());
        Assert.assertNotEquals((Integer)432, book.getPages());
    }

    @Test(expected = Test.None.class)
    public void getCategory() {
        Assert.assertEquals(category, book.getCategory());
        Assert.assertNotEquals(category2, book.getCategory());
        book.setCategory(category2);
        Assert.assertEquals(category2, book.getCategory());
        Assert.assertNotEquals(category, book.getCategory());
    }

    @Test(expected = Test.None.class)
    public void getAuthor() {
        Assert.assertEquals(author, book.getAuthor());
        Assert.assertNotEquals("Luke", book.getAuthor().getFirstName());
        Author author2=new Author();
        author2.setFirstName("Luke");
        book.setAuthor(author2);
        Assert.assertEquals(author2, book.getAuthor());
        Assert.assertNotEquals(author, book.getAuthor());
    }

    @Test(expected = Test.None.class)
    public void toStringT() {
        Assert.assertEquals("Book{id=null, ref='AF2578953HY', title='Les animaux fantastiques'}", book.toString());
        Assert.assertNotEquals("Book{id=null, ref='JU873HH', title='Le dernier Jedi'}", book.toString());
    }
}