package drz.tmdb.memory.Transaction.Transactions;

import net.sf.jsqlparser.statement.Statement;

import java.util.ArrayList;

import drz.tmdb.memory.Transaction.Transactions.Exception.TMDBException;

public interface Insert {
    ArrayList<Integer> insert(Statement stmt) throws TMDBException;
}
