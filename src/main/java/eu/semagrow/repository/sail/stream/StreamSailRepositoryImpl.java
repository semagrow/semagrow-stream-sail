package eu.semagrow.repository.sail.stream;

import eu.semagrow.repository.sail.StreamSailRepository;
import eu.semagrow.repository.sail.StreamSailRepositoryConnection;
import eu.semagrow.sail.StreamSail;
import eu.semagrow.sail.StreamSailConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.sail.SailRepositoryConnection;
import org.openrdf.sail.Sail;
import org.openrdf.sail.SailException;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class StreamSailRepositoryImpl extends SailRepository implements StreamSailRepository {

    public StreamSailRepositoryImpl(StreamSail sail) {
        super(sail);
    }

    @Override
    public StreamSailRepositoryConnection getStreamSailRepositoryConnection() throws RepositoryException {
        try {
            return new StreamSailRepositoryConnectionImpl(this, (StreamSailConnection)super.getSail().getConnection());
        } catch (SailException ex) {
            throw new RepositoryException(ex);
        }
    }
    
    @Override
    public SailRepositoryConnection getConnection() throws RepositoryException {
        try {
            return new StreamSailRepositoryConnectionImpl(this, (StreamSailConnection)super.getSail().getConnection());
        } catch (SailException ex) {
            throw new RepositoryException(ex);
        }
    }

}
