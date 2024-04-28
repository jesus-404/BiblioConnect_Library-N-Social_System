package org.example.librarySystem;
import java.time.LocalDate;

public class Transaction {
    private int transactionId;
    private Patron patron;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean returned;

    public Transaction(int transactionId, Patron patron, Book book) {
        this.transactionId = transactionId;
        this.patron = patron;
        this.book = book;
        this.borrowDate = LocalDate.now();
        this.returned = false;
    }

    public int getTransactionId() {
        return transactionId;
    }
    public Patron getPatron() {
        return patron;
    }
    public Book getBook() {
        return book;
    }
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public boolean isReturned() {
        return returned;
    }
    public void markAsReturned() {
        this.returned = true;
        this.returnDate = LocalDate.now();
    }
}