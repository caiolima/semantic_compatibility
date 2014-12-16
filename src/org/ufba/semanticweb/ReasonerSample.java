package org.ufba.semanticweb;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.ufba.semanticweb.base.KnoledgeBase;

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

	private static Scanner scan;
	
	public static void main(String[] args) {
		setupKnowledegeBase();
	    
	    scan = new Scanner(System.in);
	    
	    //Start asking for a product
	    System.out.println("Welcome to TechGuru, the system of computer parts compatibility!");
	    System.out.println("Insert commands below to consult our knowledge.");
	    String command = "";
	    
	    do {
	    	System.out.print("> ");
	    	command = scan.nextLine();
	    	proccessCommand(command);
	    } while(true);    
	}
	
	private static void setupKnowledegeBase() {
		OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		InputStream in = FileManager.get().open("/Users/caiolima/Desktop/tarbII_semantica/TwitterAnnotator/compatibility.xml");
	    
	    ontologyModel.read(in, "");
	     
	    Reasoner reasoner = PelletReasonerFactory.theInstance().create();
	    
	    //bind the reasoner to the ontology model
	    reasoner = reasoner.bindSchema(ontologyModel);
	    
	    //Bind the reasoner to the data model into a new Inferred model
	    Model infModel = ModelFactory.createInfModel(reasoner,ontologyModel);
	    
	    KnoledgeBase.getInstance().initKnoledgeBase(infModel);
	}

	private static void proccessCommand(String command) {
		switch(command) {
			case "compatibility":
				verifyCompatibility();
				break;
			case "complist":
				verifyCompatibilityList();
				break;
			case "exit":
				System.out.print("Finalizando programa...\n");
				System.exit(0);
				break;
			default:
				System.out.print("Comando invalido! \n");
		}
	}

	private static String pickProduct() {
		
		int i = 0;
		ArrayList<String> products =  KnoledgeBase.getInstance().getAllProducts();
		for(String p:products){
			System.out.print(i + " - " + p + "\n");
			i++;
		}
		
		System.out.print("(i) > ");
		
		String opt = scan.nextLine();
		int opt_n = Integer.parseInt(opt);
		
		String choice1 = products.get(opt_n);
		
		System.out.print("Your choice: " + choice1 + "\n");
				
		return choice1;
	}
	
	private static void verifyCompatibility() {
		System.out.print("Choose a product below\n");
		String choice1 = pickProduct();
		
		System.out.print("Choose another product below\n");
		String choice2 = pickProduct();
		
		ArrayList<String> compProducts = KnoledgeBase.getInstance().getCompatibilityProducts(choice1);
		if (compProducts.contains(choice2)) {
			System.out.println("The products are compatible");
		} else {
			System.out.println("The products are NOT compatible");
		}
		
	}
	
	private static void verifyCompatibilityList() {
		System.out.print("Choose a product below\n");
		String choice1 = pickProduct();
		
		ArrayList<String> compProducts = KnoledgeBase.getInstance().getCompatibilityProducts(choice1);
		
		System.out.print("This product is compatible with: \n");
		
		int i = 0;
		for(String p:compProducts){
			System.out.print(i + " - " + p + "\n");
			i++;
		}
	}
	
}
