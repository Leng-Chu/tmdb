package drz.tmdb.memory.Transaction.Transactions;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.statement.Statement;

import java.util.ArrayList;

import drz.tmdb.memory.Transaction.Transactions.Exception.TMDBException;

public interface Update {
    ArrayList<Integer> update(Statement stmt) throws JSQLParserException, TMDBException;
}
