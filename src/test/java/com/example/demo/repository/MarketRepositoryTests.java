package com.example.demo.repository;
import com.example.demo.entity.dao.AccountDAO;
import com.example.demo.entity.dao.BookDAO;
import com.example.demo.entity.dao.TransferAccount;
import com.example.demo.exceptions.AmountOutOfStorageException;
import com.example.demo.exceptions.BalanceToLowException;
import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.repo.MarketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class MarketeRepositoryTests {

    @Mock
    private AccountDAO account;

    @Mock
    private Map<Integer, BookDAO> books;

    @InjectMocks
    private MarketRepository repository;
    private final LinkedHashMap<String, BookDAO> accountDaoData = new LinkedHashMap<>();
    private final Map<Integer, BookDAO> booksData = new HashMap<>();

    @BeforeAll
    void setUp() {
        accountDaoData.put("Совершенный код", new BookDAO("Стив Макконелл", "Совершенный код", 100, 7));
        accountDaoData.put("Effective Java", new BookDAO("Joshua Bloch", "Effective Java", 2500, 10));
        accountDaoData.put("Философия Java", new BookDAO("Брюс Эккель", "Философия Java", 1500, 15));
        booksData.put(0, new BookDAO("Стив Макконелл", "Совершенный код", 100, 7));
        booksData.put(1, new BookDAO("Joshua Bloch", "Effective Java", 2500, 10));
        booksData.put(2, new BookDAO("Брюс Эккель", "Философия Java", 1500, 15));
    }

    @Test
    void getAccountShouldReturnValidTransferAccount() {
        TransferAccount expected = new TransferAccount(1000, new ArrayList<>(Arrays.asList(
                new BookDAO("Стив Макконелл", "Совершенный код", 100, 7),
                new BookDAO("Joshua Bloch", "Effective Java", 2500, 10),
                new BookDAO("Брюс Эккель", "Философия Java", 1500, 15))
        ));
        Mockito.when(account.getMoney()).thenReturn(1000);
        Mockito.when(account.getBooks()).thenReturn(accountDaoData);
        assertEquals(expected, repository.getAccount());

        Mockito.when(account.getBooks()).thenReturn(null);
        expected = new TransferAccount(1000, new ArrayList<>());
        assertEquals(expected, repository.getAccount());
    }

    @Test
    void getBooksShouldReturnBookList() {
        List<BookDAO> expected = new ArrayList<>(
                Arrays.asList(
                        new BookDAO("Стив Макконелл", "Совершенный код", 100, 7),
                        new BookDAO("Joshua Bloch", "Effective Java", 2500, 10),
                        new BookDAO("Брюс Эккель", "Философия Java", 1500, 15)
                )
        );
        Mockito.when(books.entrySet()).thenReturn(booksData.entrySet());
        System.out.println(books.entrySet());
        Mockito.when(books.size()).thenReturn(3);
        assertEquals(expected, repository.getBooks());

        expected.remove(2);
        booksData.remove(2);

        Mockito.when(books.entrySet()).thenReturn(booksData.entrySet());
        Mockito.when(books.size()).thenReturn(2);
        assertEquals(expected, repository.getBooks());
    }

    @Test
    void tryToBuyBookShouldBuyBook() {
        Mockito.when(books.containsKey(0)).thenReturn(true);
        Mockito.when(books.get(0)).thenReturn(new BookDAO("Стив Макконелл", "Совершенный код", 1000, 7));
        Mockito.when(account.getMoney()).thenReturn(10000);
        repository.makeDeal(0, 5);
    }

    @Test
    void tryToBuyBookShouldNotFoundBookExceptionThrows() {
        Mockito.when(books.containsKey(4)).thenReturn(false);
        Assertions.assertThrows(BookNotFoundException.class, () -> repository.makeDeal(4, 5));
    }

    @Test
    void tryToBuyBookAmountTooLargeExceptionThrows() {
        Mockito.when(books.containsKey(4)).thenReturn(true);
        Mockito.when(books.get(4)).thenReturn(new BookDAO("Стив Макконелл", "Совершенный код", 1000, 7));
        Assertions.assertThrows(AmountOutOfStorageException.class, () -> repository.makeDeal(4, 10));
    }

    @Test
    void tryToBuyBookAccountMoneyLessThenBooksCost() {
        Mockito.when(books.containsKey(4)).thenReturn(true);
        Mockito.when(account.getMoney()).thenReturn(5000);
        Mockito.when(books.get(4)).thenReturn(new BookDAO("Стив Макконелл", "Совершенный код", 1000, 7));
        Assertions.assertThrows(BalanceToLowException.class, () -> repository.makeDeal(4, 6));
    }

}

