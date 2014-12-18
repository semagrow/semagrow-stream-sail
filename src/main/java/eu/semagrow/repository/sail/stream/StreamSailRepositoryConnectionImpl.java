package eu.semagrow.repository.sail.stream;

import eu.semagrow.query.StreamSailTupleQuery;
import eu.semagrow.repository.sail.StreamSailRepositoryConnection;
import eu.semagrow.sail.StreamSailConnection;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.parser.ParsedTupleQuery;
import org.openrdf.query.parser.QueryParserUtil;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.sail.SailRepositoryConnection;
import org.openrdf.repository.sail.SailTupleQuery;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class StreamSailRepositoryConnectionImpl extends SailRepositoryConnection implements StreamSailRepositoryConnection {

    public StreamSailRepositoryConnectionImpl(SailRepository repository, StreamSailConnection sailConnection) {
        super(repository, sailConnection);        
    }

    @Override
    public StreamSailTupleQuery prepareStreamTupleQuery(QueryLanguage ql, String queryString, String baseURI) throws MalformedQueryException {
        ParsedTupleQuery parsedQuery = QueryParserUtil.parseTupleQuery(ql, queryString, baseURI);        
        return new StreamSailTupleQueryImpl(parsedQuery, this);        
    }
    
    @Override
    public SailTupleQuery prepareTupleQuery(QueryLanguage ql, String queryString, String baseURI) throws MalformedQueryException {
        ParsedTupleQuery parsedQuery = QueryParserUtil.parseTupleQuery(ql, queryString, baseURI);        
        return new StreamSailTupleQueryImpl(parsedQuery, this);
    }

}
