package eu.semagrow.repository.sail;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface StreamSailRepository extends Repository {
    public StreamSailRepositoryConnection getStreamSailRepositoryConnection() throws RepositoryException;
}
