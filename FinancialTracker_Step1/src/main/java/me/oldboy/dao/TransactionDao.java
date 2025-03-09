package me.oldboy.dao;

import me.oldboy.data_base.TransactionDB;
import me.oldboy.entity.Transaction;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) implementation of the CrudOperation for managing Transaction entities.
 */
public class TransactionDao implements CrudOperation<Long, Transaction>{

    private static TransactionDao INSTANCE;

    public static TransactionDao getInstance() {
        if(INSTANCE == null){
            INSTANCE = new TransactionDao();
        }
        return INSTANCE;
    }

    private TransactionDB transactionDB = TransactionDB.getInstance();

    @Override
    public List<Transaction> findAll() {
        return transactionDB.getTransactionsDb();
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        Optional<Transaction> mayBeTransaction =
                findAll().stream()
                         .filter(transaction -> transaction.getTransactionId().equals(id))
                         .findAny();
        return mayBeTransaction;
    }

    public List<Transaction> findTransactionByUserId(Long userId){
        List<Transaction> currentUserTransactionList = findAll().stream()
                                                                .filter(transaction -> transaction.getUserId().equals(userId))
                                                                .toList();

        return currentUserTransactionList;
    }

    @Override
    public boolean delete(Long id) {
        Optional<Transaction> mayBeTransaction = findAll().stream()
                                                          .filter(transaction -> transaction.getTransactionId().equals(id))
                                                          .findAny();
        if(mayBeTransaction.isPresent()){
            findAll().remove(mayBeTransaction.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void update(Transaction entity) {
        Optional<Transaction> mayBeTransaction = findById(entity.getTransactionId());

        if(mayBeTransaction.isPresent()){
            int index = findAll().indexOf(mayBeTransaction.get());
            findAll().set(index, entity);
        }
    }

    @Override
    public Transaction save(Transaction entity) {
        Long currentId = transactionDB.getCurrentIdCount();
        Long newUserId = currentId + 1L;

        findAll().add(transactionBuilder(newUserId, entity));

        return findById(newUserId).get();
    }

    private Transaction transactionBuilder(Long transactionId, Transaction entity){
        return Transaction.builder()
                .transactionId(transactionId)
                .userId(entity.getUserId())
                .finOperation(entity.getFinOperation())
                .date(entity.getDate())
                .description(entity.getDescription())
                .build();
    }
}