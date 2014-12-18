package eu.semagrow.iteration.stream;

import eu.semagrow.iteration.stream.ExceptionConverter;
import org.openrdf.model.Statement;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.sail.SailException;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class ExceptionConvertingListIteration implements ExceptionConverter<SailException,QueryEvaluationException>{

    private final ListIteration<SailException> listIteration;
    
    public ExceptionConvertingListIteration(ListIteration<SailException> listIteration) {
        this.listIteration = listIteration;
    }

    @Override
    public void close() throws QueryEvaluationException {
        try {
            this.listIteration.close();
        } catch (SailException ex) {
            throw new QueryEvaluationException(ex);
        }
    }

    @Override
    public boolean hasNext() throws QueryEvaluationException {
        try {
            return this.listIteration.hasNext();
        } catch (SailException ex) {
            throw new QueryEvaluationException(ex);
        }
    }

    @Override
    public Statement next() throws QueryEvaluationException {
        try {
            return this.listIteration.next();
        } catch (SailException ex) {
            throw new QueryEvaluationException(ex);
        }
    }

    @Override
    public void remove() throws QueryEvaluationException {
        try {
            this.listIteration.remove();
        } catch (SailException ex) {
            throw new QueryEvaluationException(ex);
        }
    }

}
