package org.ufba.semanticweb.base;

import java.util.ArrayList;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class KnoledgeBase {

	private Model model;
	private static KnoledgeBase instance;

	public static KnoledgeBase getInstance() {
		if (instance == null)
			instance = new KnoledgeBase();
		return instance;
	}

	private KnoledgeBase() {
	}

	public void initKnoledgeBase(Model model) {
		this.model = model;
	}

	public ArrayList<String> getAllProducts() {
		if (this.model == null)
			return null;

		 String queryString =
				 "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
				 "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
				 "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
				 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
				 "PREFIX comp: <http://www.semanticweb.org/ygor/ontologies/2014/10/compatibility#>" +
				 "SELECT ?p WHERE {?p rdf:type comp:ProductSpecs}";
		
		 Query query = QueryFactory.create(queryString);
		
		 // Execute the query and obtain results
		 QueryExecution qe = QueryExecutionFactory.create(query, this.model);
		 //futebol.write(System.out);
		 ResultSet results = qe.execSelect();
		 
		 ArrayList<String> products = new ArrayList<String>();
		 
		 
		 while(results.hasNext()) {
			 QuerySolution r = results.next();
			 RDFNode node = r.get("p");
			 products.add(node.toString().replace("http://www.semanticweb.org/ygor/ontologies/2014/10/compatibility#", ""));
		 }
		
		 // Important - free up resources used running the query
		 qe.close();
		 
		 return products;
		 
	}

	public ArrayList<String> getCompatibilityProducts(String p) {
		if (this.model == null)
			return null;

		 String queryString =
				 "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
				 "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
				 "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
				 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
				 "PREFIX comp: <http://www.semanticweb.org/ygor/ontologies/2014/10/compatibility#>" +
				 "SELECT ?p WHERE {comp:" + p + " comp:productCompatibleWith ?p}";
		
		 Query query = QueryFactory.create(queryString);
		
		 // Execute the query and obtain results
		 QueryExecution qe = QueryExecutionFactory.create(query, this.model);
		 //futebol.write(System.out);
		 ResultSet results = qe.execSelect();
		 
		 ArrayList<String> products = new ArrayList<String>();
		 
		 while(results.hasNext()) {
			 QuerySolution r = results.next();
			 RDFNode node = r.get("p");
			 products.add(node.toString().replace("http://www.semanticweb.org/ygor/ontologies/2014/10/compatibility#", ""));
		 }
		
		 // Important - free up resources used running the query
		 qe.close();
		 
		 return products;
		 
	}
	
}
