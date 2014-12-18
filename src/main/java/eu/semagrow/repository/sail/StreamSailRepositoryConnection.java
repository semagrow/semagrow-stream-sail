package eu.semagrow.repository.sail;

import eu.semagrow.query.StreamSailTupleQuery;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.repository.RepositoryConnection;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface StreamSailRepositoryConnection extends RepositoryConnection {
    public StreamSailTupleQuery prepareStreamTupleQuery(QueryLanguage ql, String queryString, String baseURI) throws MalformedQueryException;
}
