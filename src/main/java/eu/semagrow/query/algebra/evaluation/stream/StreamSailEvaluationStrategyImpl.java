package eu.semagrow.query.algebra.evaluation.stream;

import eu.semagrow.query.algebra.evaluation.StreamSailEvaluationStrategy;
import eu.semagrow.query.algebra.evaluation.StreamSailTripleSource;
import eu.semagrow.sail.StreamSailConnection;
import info.aduna.iteration.CloseableIteration;
import java.util.stream.Stream;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.algebra.Join;
import org.openrdf.query.algebra.LeftJoin;
import org.openrdf.query.algebra.Projection;
import org.openrdf.query.algebra.ProjectionElem;
import org.openrdf.query.algebra.Slice;
import org.openrdf.query.algebra.StatementPattern;
import org.openrdf.query.algebra.TupleExpr;
import org.openrdf.query.algebra.evaluation.QueryBindingSet;
import org.openrdf.query.algebra.evaluation.impl.EvaluationStrategyImpl;
import org.openrdf.sail.SailException;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class StreamSailEvaluationStrategyImpl extends EvaluationStrategyImpl implements StreamSailEvaluationStrategy {

    private final StreamSailConnection sailCon;

    public StreamSailEvaluationStrategyImpl(
            StreamSailTripleSource streamSailTripleSource,
            StreamSailConnection sailCon) {
        super(streamSailTripleSource);
        this.sailCon = sailCon;
    }

    @Override
    public Stream<BindingSet> streamEvaluation(TupleExpr expr, BindingSet bindings) throws QueryEvaluationException {
        //System.out.println(expr);
        try {
            if (expr instanceof StatementPattern) {
                return stream((StatementPattern) expr, bindings);
            } else if (expr instanceof Projection) {
                return stream((Projection) expr, bindings);
            } else if (expr instanceof Join) {
                return stream((Join) expr, bindings);
            } else if (expr instanceof Slice) {
                return stream((Slice) expr, bindings);
            }
        } catch (SailException e) {
            throw new QueryEvaluationException(e);
        }
        return null;
    }

    private Stream<BindingSet> stream(Projection projection, BindingSet bindings) throws QueryEvaluationException {
        return this
                .streamEvaluation(projection.getArg(), bindings)
                .map((bs) -> {
                    QueryBindingSet resultBindings = new QueryBindingSet(bindings);
                    for (ProjectionElem pe : projection.getProjectionElemList().getElements()) {
                        Value targetValue = bs.getValue(pe.getSourceName());
                        if (targetValue != null) {
                            resultBindings.setBinding(pe.getTargetName(), targetValue);
                        }
                    }
                    return resultBindings;
                });
    }

    private Stream<BindingSet> stream(Join join, BindingSet bindings) throws QueryEvaluationException {
        return this
                .streamEvaluation(join.getLeftArg(), bindings)
                .parallel()
                .flatMap((leftBindingSet) -> {
                    try {
                        return this.streamEvaluation(join.getRightArg(), leftBindingSet)
                                   .map((rightBindingSet) -> {
                                            ((QueryBindingSet) rightBindingSet).addAll(leftBindingSet);
                                            return rightBindingSet;
                                       });
                    } catch (QueryEvaluationException qee) {
                        return Stream.empty();
                    }
                });
    }

    private Stream<BindingSet> stream(Slice slice, BindingSet bindings) throws QueryEvaluationException {
        if (slice.getLimit() > 0 && slice.getOffset() > 0) {
            return this.streamEvaluation(slice.getArg(), bindings)
                    .skip(slice.getOffset())
                    .limit(slice.getLimit());
        } else {
            if(slice.getLimit()>0){
                return this.streamEvaluation(slice.getArg(), bindings)
                                    .limit(slice.getLimit());            
            } else {
               return this.streamEvaluation(slice.getArg(), bindings)
                                    .skip(slice.getOffset());
            }            
        }
    }

    private Stream<BindingSet> stream(StatementPattern sp, BindingSet bindings) throws SailException {
        Resource subj = (Resource) getVarValue(sp.getSubjectVar(), bindings);
        URI pred = (URI) getVarValue(sp.getPredicateVar(), bindings);
        Value obj = getVarValue(sp.getObjectVar(), bindings);
        return this.sailCon.streamStatements(subj, pred, obj, true).map(
                (s) -> {
                    QueryBindingSet result = new QueryBindingSet(bindings);
                    if (sp.getSubjectVar() != null && !result.hasBinding(sp.getSubjectVar().getName())) {
                        result.addBinding(sp.getSubjectVar().getName(), s.getSubject());
                    }
                    if (sp.getPredicateVar() != null && !result.hasBinding(sp.getPredicateVar().getName())) {
                        result.addBinding(sp.getPredicateVar().getName(), s.getPredicate());
                    }
                    if (sp.getObjectVar() != null && !result.hasBinding(sp.getObjectVar().getName())) {
                        result.addBinding(sp.getObjectVar().getName(), s.getObject());
                    }
                    return result;
                }
        );
    }

    @Override
    public CloseableIteration<BindingSet, QueryEvaluationException> evaluate(Join join, BindingSet bindings) throws QueryEvaluationException {
        System.out.println("evaluating join");
        return super.evaluate(join, bindings); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CloseableIteration<BindingSet, QueryEvaluationException> evaluate(LeftJoin leftJoin, BindingSet bindings) throws QueryEvaluationException {
        System.out.println("evaluating leftjoin");
        return super.evaluate(leftJoin, bindings); //To change body of generated methods, choose Tools | Templates.
    }
}
