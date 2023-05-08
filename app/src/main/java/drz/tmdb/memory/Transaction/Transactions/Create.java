package drz.tmdb.memory.Transaction.Transactions;

import net.sf.jsqlparser.statement.Statement;

import drz.tmdb.memory.Transaction.Transactions.Exception.TMDBException;

public interface Create {
    boolean create(Statement stmt) throws TMDBException;

}
