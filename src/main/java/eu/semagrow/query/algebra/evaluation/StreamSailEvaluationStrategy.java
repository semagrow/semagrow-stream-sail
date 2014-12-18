package eu.semagrow.query.algebra.evaluation;

import info.aduna.iteration.CloseableIteration;
import java.util.stream.Stream;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.algebra.TupleExpr;
import org.openrdf.query.algebra.evaluation.EvaluationStrategy;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface StreamSailEvaluationStrategy extends EvaluationStrategy {
    
    public Stream<BindingSet> streamEvaluation(TupleExpr te, BindingSet bs) throws QueryEvaluationException;

}
