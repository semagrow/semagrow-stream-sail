package eu.semagrow.repository.sail.stream;

import eu.semagrow.query.StreamSailTupleQuery;
import eu.semagrow.repository.sail.StreamSailRepository;
import eu.semagrow.repository.sail.StreamSailRepositoryConnection;
import eu.semagrow.sail.StreamSail;
import eu.semagrow.sail.stream.StreamSailConnectionImpl;
import eu.semagrow.sail.stream.StreamSailImpl;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openrdf.model.Literal;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;

/**
 *
 * @author turnguard
 */
public class EvaluationStrategyImplTest {
    static final StreamSail streamSail = new StreamSailImpl();
    static StreamSailRepository streamSailRepo = new StreamSailRepositoryImpl(streamSail);
    
    public EvaluationStrategyImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws RepositoryException, IOException, RDFParseException {
        System.out.println("SETUP CLASS");
        streamSailRepo.initialize();  
        StreamSailRepositoryConnection streamSailRepoCon = null;
        try {
            streamSailRepoCon = streamSailRepo.getStreamSailRepositoryConnection();
            System.out.println("IMPORT INITIALIZED " + streamSailRepoCon.size());            
            streamSailRepoCon.add(StreamSailConnectionImpl.class.getResourceAsStream("/testdata.ttl"), "", RDFFormat.TURTLE);    
            System.out.println("IMPORT COMPLETE " + streamSailRepoCon.size());
        } finally {
            if(streamSailRepoCon!=null){
                streamSailRepoCon.close();
            }
        }
    }
    
    @AfterClass
    public static void tearDownClass() throws RepositoryException {
        System.out.println("TEAR DOWN CLASS");
        streamSailRepo.shutDown();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void simpleQuery() throws RepositoryException, MalformedQueryException, QueryEvaluationException{
        System.out.println("simpleQuery");        
        StreamSailRepositoryConnection streamSailRepoCon = null;
        StreamSailTupleQuery streamSailTupleQuery;
        try {
            streamSailRepoCon = streamSailRepo.getStreamSailRepositoryConnection();
            streamSailTupleQuery = streamSailRepoCon
                    .prepareStreamTupleQuery(
                            QueryLanguage.SPARQL, 
                            "SELECT ?s ?p ?o WHERE { ?s ?p ?o }",
                            null
                    );   
            Assert.assertEquals("Must have 25 bindings", 25, streamSailTupleQuery.streamEvaluation().count());
                    
        } finally {
            if(streamSailRepoCon!=null){
                streamSailRepoCon.close();
            }
        }        
    }
    @Test
    public void simpleQueryWithLimit() throws RepositoryException, MalformedQueryException, QueryEvaluationException{
        System.out.println("simpleQueryWithLimit");
        StreamSailRepositoryConnection streamSailRepoCon = null;
        StreamSailTupleQuery streamSailTupleQuery;
        try {
            streamSailRepoCon = streamSailRepo.getStreamSailRepositoryConnection();
            streamSailTupleQuery = streamSailRepoCon
                    .prepareStreamTupleQuery(
                            QueryLanguage.SPARQL, 
                            "SELECT ?s ?p ?o WHERE { ?s ?p ?o } LIMIT 10",
                            null
                    );
            Assert.assertEquals("Must have 10 bindings", 10, streamSailTupleQuery.streamEvaluation().count());

        } finally {
            if(streamSailRepoCon!=null){
                streamSailRepoCon.close();
            }
        }        
    }
    
    @Test
    public void simpleQueryWithOffset() throws RepositoryException, MalformedQueryException, QueryEvaluationException{
        System.out.println("simpleQueryWithOffset");        
        StreamSailRepositoryConnection streamSailRepoCon = null;
        StreamSailTupleQuery streamSailTupleQuery;
        try {
            streamSailRepoCon = streamSailRepo.getStreamSailRepositoryConnection();
            streamSailTupleQuery = streamSailRepoCon
                    .prepareStreamTupleQuery(
                            QueryLanguage.SPARQL, 
                            "SELECT ?s ?p ?o WHERE { ?s ?p ?o } OFFSET 10",
                            null
                    );
            Assert.assertEquals("Must have 15 bindings", 15, streamSailTupleQuery.streamEvaluation().count());

        } finally {
            if(streamSailRepoCon!=null){
                streamSailRepoCon.close();
            }
        }        
    }  
    
    @Test
    public void simpleQueryWithLimitAndOffset() throws RepositoryException, MalformedQueryException, QueryEvaluationException{
        System.out.println("simpleQueryWithLimitAndOffset");
        StreamSailRepositoryConnection streamSailRepoCon = null;
        StreamSailTupleQuery streamSailTupleQuery;
        try {
            streamSailRepoCon = streamSailRepo.getStreamSailRepositoryConnection();
            streamSailTupleQuery = streamSailRepoCon
                    .prepareStreamTupleQuery(
                            QueryLanguage.SPARQL, 
                            "SELECT ?s ?p ?o WHERE { ?s ?p ?o } LIMIT 10 OFFSET 10",
                            null
                    );
            Assert.assertEquals("Must have 10 bindings", 10, streamSailTupleQuery.streamEvaluation().count());

        } finally {
            if(streamSailRepoCon!=null){
                streamSailRepoCon.close();
            }
        }        
    }      
    
    @Test
    public void simpleQueryWithRegexFilter() throws RepositoryException, MalformedQueryException, QueryEvaluationException{
        System.out.println("simpleQueryWithRegexFilter");
        StreamSailRepositoryConnection streamSailRepoCon = null;
        StreamSailTupleQuery streamSailTupleQuery;
        try {
            streamSailRepoCon = streamSailRepo.getStreamSailRepositoryConnection();
            streamSailTupleQuery = streamSailRepoCon
                    .prepareStreamTupleQuery(
                            QueryLanguage.SPARQL, 
                            "SELECT ?s ?p ?o WHERE { ?s ?p ?o FILTER(regex(str(?o),'OpenDirectory','i'))}",
                            null
                    );
            Assert.assertEquals("Must have 2 bindings", 2, streamSailTupleQuery.streamEvaluation().count());

        } finally {
            if(streamSailRepoCon!=null){
                streamSailRepoCon.close();
            }
        }        
    }
    
    @Test
    public void simpleQueryWithURIFilter() throws RepositoryException, MalformedQueryException, QueryEvaluationException{
        System.out.println("simpleQueryWithURIFilter");
        StreamSailRepositoryConnection streamSailRepoCon = null;
        StreamSailTupleQuery streamSailTupleQuery;
        try {
            streamSailRepoCon = streamSailRepo.getStreamSailRepositoryConnection();
            streamSailTupleQuery = streamSailRepoCon
                    .prepareStreamTupleQuery(
                            QueryLanguage.SPARQL, 
                            "SELECT ?s ?p ?o WHERE { ?s ?p ?o FILTER(?o=<http://www.turnguard.com/turnguard>)}",
                            null
                    );
            Assert.assertEquals("Must have 6 bindings", 6, streamSailTupleQuery.streamEvaluation().count());

        } finally {
            if(streamSailRepoCon!=null){
                streamSailRepoCon.close();
            }
        }        
    }  
    
    @Test
    public void simpleQueryWithSelectAs() throws RepositoryException, MalformedQueryException, QueryEvaluationException{
        System.out.println("simpleQueryWithSelectAs");
        StreamSailRepositoryConnection streamSailRepoCon = null;
        StreamSailTupleQuery streamSailTupleQuery;
        try {
            streamSailRepoCon = streamSailRepo.getStreamSailRepositoryConnection();
            streamSailTupleQuery = streamSailRepoCon
                    .prepareStreamTupleQuery(
                            QueryLanguage.SPARQL, 
                            "SELECT ?s ?p (?o AS ?XX) WHERE { ?s ?p ?o }",
                            null
                    );
            Assert.assertEquals("Must have 25 bindings", 25, streamSailTupleQuery.streamEvaluation().filter((b)->b.hasBinding("XX")).count());

        } finally {
            if(streamSailRepoCon!=null){
                streamSailRepoCon.close();
            }
        }        
    }      
    
    @Test
    public void simpleQueryWithJoin() throws RepositoryException, MalformedQueryException, QueryEvaluationException{
        System.out.println("simpleQueryWithJoin");        
        StreamSailRepositoryConnection streamSailRepoCon = null;
        StreamSailTupleQuery streamSailTupleQuery;
        try {
            streamSailRepoCon = streamSailRepo.getStreamSailRepositoryConnection();
            streamSailTupleQuery = streamSailRepoCon
                    .prepareStreamTupleQuery(
                            QueryLanguage.SPARQL, 
                            "SELECT ?s ?o ?a ?b WHERE { ?s <http://rdfs.org/sioc/ns#parent_of> ?o . ?o ?a ?b }",null
                    );    
            Assert.assertEquals("Must have 16 bindings", 16, streamSailTupleQuery.streamEvaluation().count());

        } finally {
            if(streamSailRepoCon!=null){
                streamSailRepoCon.close();
            }
        }        
    }
    
    @Test
    public void simpleQueryWithDistinct() throws RepositoryException, MalformedQueryException, QueryEvaluationException{
        System.out.println("simpleQueryWithDistinct");        
        StreamSailRepositoryConnection streamSailRepoCon = null;
        StreamSailTupleQuery streamSailTupleQuery;
        try {
            streamSailRepoCon = streamSailRepo.getStreamSailRepositoryConnection();
            streamSailTupleQuery = streamSailRepoCon
                    .prepareStreamTupleQuery(
                            QueryLanguage.SPARQL, 
                            "SELECT DISTINCT ?p ?o WHERE { ?s ?p ?o }",null
                    );    
            Assert.assertEquals("Must have 13 bindings", 13, streamSailTupleQuery.streamEvaluation().count());
            
            //System.out.println(streamSailTupleQuery);
            //streamSailTupleQuery.streamEvaluation().forEach((b)->{ System.out.println(b);});
        } finally {
            if(streamSailRepoCon!=null){
                streamSailRepoCon.close();
            }
        }        
    } 
    
    @Test
    public void simpleQueryWithBindLiteral() throws RepositoryException, MalformedQueryException, QueryEvaluationException{
        System.out.println("simpleQueryWithBindLiteral");        
        StreamSailRepositoryConnection streamSailRepoCon = null;
        StreamSailTupleQuery streamSailTupleQuery;
        try {
            streamSailRepoCon = streamSailRepo.getStreamSailRepositoryConnection();
            streamSailTupleQuery = streamSailRepoCon
                    .prepareStreamTupleQuery(
                            QueryLanguage.SPARQL, 
                            "SELECT DISTINCT * WHERE { ?s ?p ?o . BIND(\"XX\" AS ?XX) } LIMIT 1",null
                    );    
            Assert.assertEquals("Must have 1 bindingSet and XX as binding", 1, streamSailTupleQuery.streamEvaluation().filter((b)->b.hasBinding("XX")).count());            
        } finally {
            if(streamSailRepoCon!=null){
                streamSailRepoCon.close();
            }
        }        
    }  
    
    @Test
    public void simpleQueryWithBindTypedLiteral() throws RepositoryException, MalformedQueryException, QueryEvaluationException{
        System.out.println("simpleQueryWithBindTypedLiteral");        
        StreamSailRepositoryConnection streamSailRepoCon = null;
        StreamSailTupleQuery streamSailTupleQuery;
        try {
            streamSailRepoCon = streamSailRepo.getStreamSailRepositoryConnection();
            streamSailTupleQuery = streamSailRepoCon
                    .prepareStreamTupleQuery(
                            QueryLanguage.SPARQL, 
                            "SELECT DISTINCT (?one + ?two AS ?XX) WHERE { ?s ?p ?o . BIND(1 AS ?one) BIND(2 AS ?two) } LIMIT 1",null
                    );    
            Assert.assertEquals("Must have 1 bindingSet and XX as binding", 1, streamSailTupleQuery.streamEvaluation().filter((b)->b.hasBinding("XX") && ((Literal)b.getValue("XX")).intValue()==3 ).count());            
        } finally {
            if(streamSailRepoCon!=null){
                streamSailRepoCon.close();
            }
        }        
    }      
}
