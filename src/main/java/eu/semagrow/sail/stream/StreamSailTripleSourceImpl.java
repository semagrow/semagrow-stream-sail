package eu.semagrow.sail.stream;

import eu.semagrow.iteration.stream.ExceptionConvertingListIteration;
import eu.semagrow.iteration.stream.ListIteration;
import eu.semagrow.query.algebra.evaluation.StreamSailTripleSource;
import eu.semagrow.sail.StreamSail;
import eu.semagrow.sail.StreamSailConnection;
import info.aduna.iteration.CloseableIteration;
import info.aduna.iteration.ExceptionConvertingIteration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.sail.SailException;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class StreamSailTripleSourceImpl implements StreamSailTripleSource {

    private final StreamSailConnection federationSailConnection;
    public StreamSailTripleSourceImpl(StreamSailConnection federationSailConnection) {
        this.federationSailConnection = federationSailConnection;
    }

    @Override
    public CloseableIteration<? extends Statement, QueryEvaluationException> getStatements(Resource rsrc, URI uri, Value value, Resource... rsrcs) throws QueryEvaluationException {
        try {        
            return new ExceptionConvertingListIteration((ListIteration<SailException>) this.federationSailConnection.getStatements(rsrc, uri, value, true, rsrcs));
        } catch (SailException ex) {
            throw new QueryEvaluationException(ex);
        }
    }

    @Override
    public ValueFactory getValueFactory() {
        return ValueFactoryImpl.getInstance();
    }

}
