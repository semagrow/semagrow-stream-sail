package eu.semagrow.query;

import java.util.stream.Stream;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQuery;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface StreamSailTupleQuery extends TupleQuery {
    public Stream<BindingSet> streamEvaluation() throws QueryEvaluationException;
}
