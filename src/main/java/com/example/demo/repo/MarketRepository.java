package com.example.demo.repo;

import com.example.demo.entity.dao.AccountDAO;
import com.example.demo.entity.dao.BookDAO;
import com.example.demo.entity.dao.DataDAO;
import com.example.demo.entity.dao.TransferAccount;
import com.example.demo.exceptions.AmountOutOfStorageException;
import com.example.demo.exceptions.BalanceToLowException;
import com.example.demo.exceptions.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
public class MarketRepository implements AbstractRepo<DataDAO> {

    private AccountDAO account;

    private Map<Integer, BookDAO> books;

    public MarketRepository() {
        books = new LinkedHashMap<>();
    }

    public TransferAccount getAccount() {
        List<BookDAO> copied = new ArrayList<>();
        if (account.getBooks() == null) {
            copied = new ArrayList<>();
        } else {
            for (Map.Entry<String, BookDAO> e : account.getBooks().entrySet()) {
                BookDAO b = e.getValue();
                copied.add(new BookDAO(b.getAuthor(), b.getName(), b.getPrice(), b.getAmount()));
            }
        }
        return new TransferAccount(account.getMoney(), copied);
    }

    public List<BookDAO> getBooks() {
        List<BookDAO> copied = new ArrayList<>(books.size());
        for (Map.Entry<Integer, BookDAO> e : books.entrySet()) {
            BookDAO b = e.getValue();
            copied.add(new BookDAO(b.getAuthor(), b.getName(), b.getPrice(), b.getAmount()));
        }
        return copied;
    }

    public void makeDeal(Integer bookId, Integer amount) {
        if (!books.containsKey(bookId)) throw new BookNotFoundException();
        purchase(bookId, amount);
    }

    private void purchase(Integer bookId, Integer amount) {
        BookDAO book = books.get(bookId);
        if (book.getAmount() < amount) {
            throw new AmountOutOfStorageException();
        }
        int cost = calculateCost(book.getPrice(), amount);
        if (account.getMoney() < cost) {
            throw new BalanceToLowException(String.format("Account hasn't got money to purchase the book: %s with amount: %s", book.getName(), amount));
        }
        tryTuBuyBook(bookId, cost, amount);
    }

    private void tryTuBuyBook(Integer bookId, Integer cost, Integer amount) {
        BookDAO book = books.get(bookId);

        if (amount.equals(book.getAmount())) {
            books.remove(bookId);
        } else {
            book.setAmount(book.getAmount() - amount);
        }
        BookDAO cp = account.getBooks().getOrDefault(book.getName(), new BookDAO(book.getAuthor(), book.getName(), book.getPrice(), 0));
        cp.setAmount(cp.getAmount() + amount);
        account.setMoney(account.getMoney() - cost);
        if (account.getBooks().containsKey(book.getName())) {
            account.getBooks().replace(book.getName(), cp);
        } else {
            account.getBooks().put(book.getName(), cp);
        }

        log.info(String.format("Account bought books: [%s by %s] in amount: %s", book.getName(), book.getAuthor(), amount));
        log.info(String.format("Money on the account after transaction: %s", account.getMoney()));
    }


    private Integer calculateCost(Integer getPrice, Integer amount) {
        return getPrice * amount;
    }

    @Override
    public void fill(DataDAO entity) {
        this.account = new AccountDAO(entity.getAccount().getMoney());
        for (int i = 0; i < entity.getBooks().size(); i++) {
            books.put(i, entity.getBooks().get(i));
        }
        log.info("Products data loaded successfully");
    }
}
