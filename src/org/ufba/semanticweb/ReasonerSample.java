package org.ufba.semanticweb;

import java.io.InputStream;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.util.FileManager;

public class ReasonerSample {

	public static void main(String[] args) {
		OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		InputStream in = FileManager.get().open("/Users/caiolima/Desktop/tarbII_semantica/TwitterAnnotator/compatibility.xml");
	    
	    ontologyModel.read(in, "");
	     
	    Reasoner reasoner = PelletReasonerFactory.theInstance().create();
	    
	    //bind the reasoner to the ontology model
	    reasoner = reasoner.bindSchema(ontologyModel);
	    
	    //Bind the reasoner to the data model into a new Inferred model
	    Model infModel = ModelFactory.createInfModel(reasoner,ontologyModel);
	    
		// Create a new query
		String queryString =    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
								"PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
								"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
								"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
								"PREFIX comp: <http://www.semanticweb.org/ygor/ontologies/2014/10/compatibility#>" +
								"SELECT * WHERE { ?p1 comp:productCompatibleWith comp:HP_Deskjet_4200_Series }";
                                        		
		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, infModel);
		//futebol.write(System.out);
		ResultSet results = qe.execSelect();
		
		// Output query results
		ResultSetFormatter.out(System.out, results, query);

		// Important - free up resources used running the query
		qe.close();	    
	}
	
}
