package drz.tmdb.memory.Transaction.Transactions;

import drz.tmdb.memory.Transaction.Transactions.Exception.TMDBException;
import drz.tmdb.memory.Transaction.Transactions.utils.SelectResult;

public interface Select {
    SelectResult select(Object stmt) throws TMDBException;
}
