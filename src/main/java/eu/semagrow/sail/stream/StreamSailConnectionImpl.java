package eu.semagrow.sail.stream;

import eu.semagrow.sail.StreamSailConnection;
import eu.semagrow.query.algebra.evaluation.stream.StreamSailEvaluationStrategyImpl;
import eu.semagrow.iteration.stream.ListIteration;
import eu.semagrow.sail.StreamSail;
import info.aduna.iteration.CloseableIteration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.openrdf.model.Namespace;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.query.BindingSet;
import org.openrdf.query.Dataset;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.algebra.TupleExpr;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.RDFHandlerBase;
import org.openrdf.sail.SailException;
import org.openrdf.sail.UnknownSailTransactionStateException;
import org.openrdf.sail.UpdateContext;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class StreamSailConnectionImpl implements StreamSailConnection {
        
    List<Statement> states = new ArrayList<>();
    
    private final StreamSailEvaluationStrategyImpl federationSailEvaluationStrategyImpl;
    private final StreamSail streamSail;
    
    public StreamSailConnectionImpl(final StreamSail streamSail){    
        this.streamSail = streamSail;
        this.federationSailEvaluationStrategyImpl = new StreamSailEvaluationStrategyImpl(
                new StreamSailTripleSourceImpl(this),
                this);        
    }
    
    @Override
    public boolean isOpen() throws SailException { return true; }
    @Override
    public void close() throws SailException {}    

    @Override
    public Stream<BindingSet> streamEvaluation(TupleExpr te, Dataset dtst, BindingSet bs, boolean bln) throws SailException {
        try {        
            return this.federationSailEvaluationStrategyImpl.streamEvaluation(te, bs);
        } catch (QueryEvaluationException ex) {
            throw new SailException(ex);
        }
    }
    

    @Override
    public Stream<Statement> streamStatements(Resource subj, URI pred, Value obj, boolean bln, Resource... rsrcs) throws SailException {        
        /*
        return Stream.generate(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {}
            return createRandomStatement(rsrc,uri); 
        }).limit(10);
        */
        if(subj==null && pred==null && obj==null){
            return this.states.stream();
        } else {
            return this.states
                        .stream()
                        .filter(
                            (s)->(subj==null || subj.equals(s.getSubject())) &&
                                 (pred==null || pred.equals(s.getPredicate())) &&
                                 (obj==null || obj.equals(s.getObject()))
                        );

        }        
    }
    
    @Override
    public CloseableIteration<? extends BindingSet, QueryEvaluationException> evaluate(TupleExpr te, Dataset dtst, BindingSet bs, boolean bln) throws SailException {
        try {
            return this.federationSailEvaluationStrategyImpl.evaluate(te, bs);            
        } catch (QueryEvaluationException ex) {
            throw new SailException(ex);
        }
    }
        
    @Override
    public CloseableIteration<? extends Statement, SailException> getStatements(Resource rsrc, URI uri, Value value, boolean bln, Resource... rsrcs) throws SailException {
        return new ListIteration<>(this.streamStatements(rsrc, uri, value, bln, rsrcs).collect(Collectors.toList()));
    }
    
    @Override
    public CloseableIteration<? extends Resource, SailException> getContextIDs() throws SailException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long size(Resource... rsrcs) throws SailException {
        return this.states.size();
    }

    @Override
    public void begin() throws SailException {}

    @Override
    public void prepare() throws SailException {}

    @Override
    public void commit() throws SailException {}

    @Override
    public void rollback() throws SailException {}

    @Override
    public boolean isActive() throws UnknownSailTransactionStateException {
        return true;
    }

    @Override
    public void addStatement(Resource rsrc, URI uri, Value value, Resource... rsrcs) throws SailException {
        this.states.add(new StatementImpl(rsrc,uri,value));
    }

    @Override
    public void removeStatements(Resource rsrc, URI uri, Value value, Resource... rsrcs) throws SailException {
        this.states.remove(new StatementImpl(rsrc,uri,value));
    }

    @Override
    public void startUpdate(UpdateContext uc) throws SailException {}

    @Override
    public void addStatement(UpdateContext uc, Resource rsrc, URI uri, Value value, Resource... rsrcs) throws SailException {}

    @Override
    public void removeStatement(UpdateContext uc, Resource rsrc, URI uri, Value value, Resource... rsrcs) throws SailException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void endUpdate(UpdateContext uc) throws SailException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear(Resource... rsrcs) throws SailException {
        this.states.clear();
    }

    @Override
    public CloseableIteration<? extends Namespace, SailException> getNamespaces() throws SailException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNamespace(String string) throws SailException {
        return ((StreamSailImpl)this.streamSail).getNamespaces().get(string);
    }

    @Override
    public void setNamespace(String string, String string1) throws SailException {
        ((StreamSailImpl)this.streamSail).getNamespaces().put(string, string1);
    }

    @Override
    public void removeNamespace(String string) throws SailException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clearNamespaces() throws SailException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
