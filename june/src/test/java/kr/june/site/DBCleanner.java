package kr.june.site;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.kernel.AbstractGraphDatabase;

public class DBCleanner {

	 private GraphDatabaseService service;
	    
	    public DBCleanner(GraphDatabaseService service) {
	    	this.service = service;
	    }

	    public Map<String, Object> cleanDb() {
	        Map<String, Object> result = new HashMap<String, Object>();
	        Transaction tx = service.beginTx();
	        try {
	            removeNodes(result);
	            clearIndex(result);
	            tx.success();
	        } finally {
	            tx.finish();
	        }
	        return result;
	    }

	    private void removeNodes(Map<String, Object> result) {
	        Node refNode = service.getReferenceNode();
	        int nodes = 0, relationships = 0;
	        for (Node node : service.getAllNodes()) {
	            for (Relationship rel : node.getRelationships(Direction.OUTGOING)) {
	                rel.delete();
	                relationships++;
	            }
	            for (Relationship rel : node.getRelationships(Direction.INCOMING)) {
	            	
	                rel.delete();
	                relationships++;
	            }
	            
	            if (!refNode.equals(node)) {
	                node.delete();
	                nodes++;
	            }
	        }
	        result.put("nodes", nodes);
	        result.put("relationships", relationships);

	    }

	    private void clearIndex(Map<String, Object> result) {
	        IndexManager indexManager = service.index();
	        result.put("node-indexes", Arrays.asList(indexManager.nodeIndexNames()));
	        result.put("relationship-indexes", Arrays.asList(indexManager.relationshipIndexNames()));
	        for (String ix : indexManager.nodeIndexNames()) {
	            indexManager.forNodes(ix).delete();
	        }
	        for (String ix : indexManager.relationshipIndexNames()) {
	            indexManager.forRelationships(ix).delete();
	        }
	    }
}
