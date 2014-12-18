package eu.semagrow.repository.sail.stream;

import eu.semagrow.query.StreamSailTupleQuery;
import eu.semagrow.sail.StreamSailConnection;
import java.util.stream.Stream;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.parser.ParsedTupleQuery;
import org.openrdf.repository.sail.SailRepositoryConnection;
import org.openrdf.repository.sail.SailTupleQuery;
import org.openrdf.sail.SailException;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class StreamSailTupleQueryImpl extends SailTupleQuery implements StreamSailTupleQuery {

    public StreamSailTupleQueryImpl(ParsedTupleQuery tupleQuery, SailRepositoryConnection sailConnection) {
        super(tupleQuery, sailConnection);
    }

    @Override
    public Stream<BindingSet> streamEvaluation() throws QueryEvaluationException {
        try {
            return ((StreamSailConnection)super.getConnection().getSailConnection()).streamEvaluation(getParsedQuery().getTupleExpr(), getActiveDataset(), getBindings(), getIncludeInferred());
        } catch (SailException ex) {
            throw new QueryEvaluationException(ex);
        }
    }
}
