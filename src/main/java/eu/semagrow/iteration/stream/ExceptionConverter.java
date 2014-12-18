package eu.semagrow.iteration.stream;

import info.aduna.iteration.CloseableIteration;
import org.openrdf.model.Statement;

/**
 *
 * @author http://www.turnguard.com/turnguard
 * @param <E1>
 * @param <E2>
 */
public interface ExceptionConverter<E1 extends Exception,E2 extends Exception> extends CloseableIteration<Statement, E2>{

    

}
