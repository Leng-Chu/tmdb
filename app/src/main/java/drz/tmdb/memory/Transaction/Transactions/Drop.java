package drz.tmdb.memory.Transaction.Transactions;

import net.sf.jsqlparser.statement.Statement;

import drz.tmdb.memory.Transaction.Transactions.Exception.TMDBException;

public interface Drop {
    boolean drop(Statement statement) throws TMDBException;
}
