package eu.semagrow.sail.stream;

import eu.semagrow.sail.StreamSail;
import eu.semagrow.sail.StreamSailConnection;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.model.vocabulary.SKOS;
import org.openrdf.sail.SailException;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public class StreamSailImpl implements StreamSail {
    
    Map<String,String> namespaces = new HashMap<>();
    protected final ValueFactory valueFactory = ValueFactoryImpl.getInstance();
    
    @Override
    public void setDataDir(File file) {}
    @Override
    public File getDataDir() { return null; }
    @Override
    public void initialize() throws SailException {
        namespaces.put("sioc", "http://rdfs.org/sioc/ns#");
        namespaces.put("rdf", RDF.NAMESPACE);
        namespaces.put("rdfs", RDFS.NAMESPACE);
        namespaces.put("skos", SKOS.NAMESPACE);
        namespaces.put("dcterms", DCTERMS.NAMESPACE);
    }

    @Override
    public void shutDown() throws SailException {
    }

    @Override
    public boolean isWritable() throws SailException {
        return false;
    }

    @Override
    public StreamSailConnection getConnection() throws SailException {
        return new StreamSailConnectionImpl(this);
    }

    @Override
    public ValueFactory getValueFactory() {
        return this.valueFactory;
    }

    public Map<String,String> getNamespaces(){
        return this.namespaces;
    }
}
