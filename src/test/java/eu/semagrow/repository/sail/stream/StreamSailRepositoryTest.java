package eu.semagrow.repository.sail.stream;

import eu.semagrow.repository.sail.stream.StreamSailRepositoryImpl;
import eu.semagrow.query.StreamSailTupleQuery;
import eu.semagrow.repository.sail.StreamSailRepository;
import eu.semagrow.repository.sail.StreamSailRepositoryConnection;
import eu.semagrow.sail.StreamSail;
import eu.semagrow.sail.stream.StreamSailConnectionImpl;
import eu.semagrow.sail.stream.StreamSailImpl;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;

/**
 *
 * @author turnguard
 */
public class StreamSailRepositoryTest {
    
    public StreamSailRepositoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSequential() throws RepositoryException, MalformedQueryException, QueryEvaluationException, IOException, RDFParseException{
        StreamSail streamSail = new StreamSailImpl();
        StreamSailRepository streamSailRepo = new StreamSailRepositoryImpl(streamSail);
                             streamSailRepo.initialize();
                                 
        StreamSailRepositoryConnection streamSailRepoCon = streamSailRepo.getStreamSailRepositoryConnection();
                                       streamSailRepoCon.add(StreamSailConnectionImpl.class.getResourceAsStream("/testdata.ttl"), "", RDFFormat.TURTLE);
        long start = System.currentTimeMillis();        
        TupleQueryResult streamSailTupleQueryResult = streamSailRepoCon.prepareTupleQuery(QueryLanguage.SPARQL, "SELECT ?s ?o ?a ?b WHERE { ?s <http://rdfs.org/sioc/ns#parent_of> ?o . ?o ?a ?b }",null).evaluate();
        while(streamSailTupleQueryResult.hasNext()){
            System.out.println(streamSailTupleQueryResult.next());
        }
        System.out.println(System.currentTimeMillis()-start);
    }
    
    //@Test
    public void testStreaming() throws RepositoryException, MalformedQueryException, QueryEvaluationException, IOException, RDFParseException {
        StreamSail streamSail = new StreamSailImpl();
        StreamSailRepository streamSailRepo = new StreamSailRepositoryImpl(streamSail);
                             streamSailRepo.initialize();

        StreamSailRepositoryConnection streamSailRepoCon = streamSailRepo.getStreamSailRepositoryConnection();        
                                       streamSailRepoCon.add(StreamSailConnectionImpl.class.getResourceAsStream("/testdata.ttl"), "", RDFFormat.TURTLE);            
        long start = System.currentTimeMillis();        
        StreamSailTupleQuery streamSailTupleQuery = streamSailRepoCon.prepareStreamTupleQuery(QueryLanguage.SPARQL, "SELECT ?s WHERE { ?s ?p ?o }",null);
                             streamSailTupleQuery.streamEvaluation().forEach(
                                         (b)->{                                             
                                             System.out.println((System.currentTimeMillis()-start)+" "+b);
                                         }
                                 );
    }
    
    @Test
    public void test3() throws RepositoryException, MalformedQueryException, QueryEvaluationException, IOException, RDFParseException {
        StreamSail streamSail = new StreamSailImpl();
        StreamSailRepository streamSailRepo = new StreamSailRepositoryImpl(streamSail);
                             streamSailRepo.initialize();
                                 
        StreamSailRepositoryConnection streamSailRepoCon = streamSailRepo.getStreamSailRepositoryConnection(); 
                                       streamSailRepoCon.add(StreamSailConnectionImpl.class.getResourceAsStream("/testdata.ttl"), "", RDFFormat.TURTLE);    
        long start = System.currentTimeMillis();        
        StreamSailTupleQuery streamSailTupleQuery = streamSailRepoCon.prepareStreamTupleQuery(QueryLanguage.SPARQL, "SELECT ?s ?o ?a ?b WHERE { ?s <http://rdfs.org/sioc/ns#parent_of> ?o . ?o ?a ?b }",null);
                             streamSailTupleQuery.streamEvaluation().forEach(
                                         (b)->{                                             
                                             System.out.println(b);
                                         }
                                 );
        System.out.println(System.currentTimeMillis()-start);                         
    }    
}
