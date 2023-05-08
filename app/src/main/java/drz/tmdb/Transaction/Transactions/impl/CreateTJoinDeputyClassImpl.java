package drz.tmdb.Transaction.Transactions.impl;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SetOperationList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import drz.tmdb.Transaction.Transactions.Exception.TMDBException;
import drz.tmdb.Transaction.Transactions.utils.MemConnect;
import drz.tmdb.Transaction.Transactions.utils.SelectResult;

public class CreateTJoinDeputyClassImpl extends CreateDeputyClassImpl{
    private MemConnect memConnect;

    public CreateTJoinDeputyClassImpl(MemConnect memConnect) {
        super(memConnect);
        this.memConnect=memConnect;
    }

    public CreateTJoinDeputyClassImpl() {
    }

    @Override
    public boolean createDeputyClass(Statement stmt) throws TMDBException {
        return execute((net.sf.jsqlparser.statement.create.deputyclass.CreateTJoinDeputyClass) stmt);
    }

    private boolean execute(net.sf.jsqlparser.statement.create.deputyclass.CreateTJoinDeputyClass stmt) throws TMDBException {
        Table deputyClass = stmt.getTJoinDeputyClass();
        if(memConnect.getClassId(String.valueOf(deputyClass))!=-1){
            throw new TMDBException(deputyClass+" already exists");
        }
        Select select1 = stmt.getSelectFirst();
        Select select2 = stmt.getSelectEnd();
        SelectResult selectResult1 = getSelectResult(select1);
        SelectResult selectResult2 = getSelectResult(select2);
        TJoinSelect tJoinSelect=new TJoinSelect(memConnect);
        SelectResult selectResult = tJoinSelect.intersect(selectResult1, selectResult2);

        work(select1, deputyClass);
        work(select2, deputyClass);

        boolean help = super.help(selectResult, 5, String.valueOf(deputyClass));
        return help;
    }

    private SelectResult getSelectResult(Select select) throws TMDBException{
        TJoinSelect select1 = new TJoinSelect(this.memConnect);
        SelectResult selectResult=select1.select(select);
        return selectResult;
    }

    private void work(Select select, Table deputyClass) throws TMDBException{
        List<SelectBody> selects = ((SetOperationList) select.getSelectBody()).getSelects();
        String[] strings = new String[0];
        if (!selects.isEmpty()) {
            List<FromItem> list=new ArrayList<>();
            for (int i = 1; i < selects.size(); i++) {
                SelectBody selectBody = selects.get(i);
                list.add(((PlainSelect)selectBody).getFromItem());
            }
            strings = list.stream().map(FromItem::toString).toArray(String[]::new);
        }
        if (strings.length!=0) {
            insertElseDeputyTable(strings,5,deputyClass.toString());
        }
    }

    private void insertElseDeputyTable(String[] strings, int i, String deputyClass) throws TMDBException {
        int classId = memConnect.getClassId(deputyClass);
        super.insertDeputyTable(strings,i,classId);
    }


}
