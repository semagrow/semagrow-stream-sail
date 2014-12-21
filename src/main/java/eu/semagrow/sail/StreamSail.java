package eu.semagrow.sail;

import java.util.List;
import org.openrdf.model.Statement;
import org.openrdf.sail.Sail;

/**
 *
 * @author http://www.turnguard.com/turnguard
 */
public interface StreamSail extends Sail {
    public List<Statement> getStatements();
}
