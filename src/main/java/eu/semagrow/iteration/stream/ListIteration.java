package eu.semagrow.iteration.stream;

import info.aduna.iteration.CloseableIteration;
import java.util.List;
import org.openrdf.model.Statement;

/**
 *
 * @author http://www.turnguard.com/turnguard
 * @param <T>
 */
public class ListIteration<T extends Exception> implements CloseableIteration<Statement, T> {

    private final List<Statement> list;
    
    public ListIteration(List<Statement> list){
        this.list = list;    
    }
    
    public List<Statement> getList(){ return this.list; }
       
    @Override
    public void close() throws T {
        this.list.clear();
    }

    @Override
    public boolean hasNext() throws T {
        return !this.list.isEmpty();
    }

    @Override
    public Statement next() throws T {
        return this.list.remove(0);
    }

    @Override
    public void remove() throws T {
        this.list.remove(0);
    }

}
