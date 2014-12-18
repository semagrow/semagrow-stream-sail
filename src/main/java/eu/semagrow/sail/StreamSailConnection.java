package eu.semagrow.sail;

import info.aduna.iteration.CloseableIteration;
import java.util.stream.Stream;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.Dataset;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.algebra.TupleExpr;
import org.openrdf.sail.SailConnection;
import org.openrdf.sail.SailException;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface StreamSailConnection extends SailConnection {
    public Stream<BindingSet> streamEvaluation(TupleExpr te, Dataset dtst, BindingSet bs, boolean bln) throws SailException;
    public Stream<Statement> streamStatements(Resource rsrc, URI uri, Value value, boolean bln, Resource... rsrcs) throws SailException;    
}
