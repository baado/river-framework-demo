package org.riverframework.domino.demo;

import lotus.domino.NotesThread;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.riverframework.River;
import org.riverframework.core.Document;
import org.riverframework.core.DocumentIterator;
import org.riverframework.core.IndexedDatabase;
import org.riverframework.core.Session;

/**
 * To run this demo, you will need:
 * 
 * - IBM Notes installed in your computer
 * - Eclipse with maven support
 *    
 */

public class AddressBookDemo {
	private static final String filepath = "RiverFramework_Test_PAB_5.nsf";

	public static void main(String[] args) {
		// *** Getting the password
		Options options = new Options();
		options.addOption("password", true, "the password of the last active id file on IBM Notes");

		CommandLineParser parser = new DefaultParser();
		CommandLine line = null;
		try {
			line = parser.parse( options, args );
		}
		catch( ParseException exp ) {
			System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
		}

		String password = line.getOptionValue("password");

		// *** Initializing the Notes Thread (required for stand-alone Java applications)
		NotesThread.sinitThread();

		// *** Opening a IBM Notes Session
		Session session = River.getSession(River.LOTUS_DOMINO, null, null, password);
		System.out.println("User=" + session.getUserName());

		// *** Opening/creating the test database

		// Checking if the test database exists
		IndexedDatabase database = session.getDatabase(AddressBook.class, "", filepath);

		// If not, then we create a new one
		if (!database.isOpen()) {
			database = session.createDatabase(AddressBook.class, "", filepath);

			// If it could not be created, then we finish the demo
			if (!database.isOpen()) {
				System.out.println("I could not create the test database at the local client, with the name '" + filepath + "'");
				return;
			}
		}
		System.out.println("Database=" + database.getName());
	
		// *** Deleting everything
		database.getAllDocuments().deleteAll();

		// *** Creating a five hundred of persons
		for (int i = 0; i < 500; i++) {

			//Getting some random values 
			String name = RandomValues.getAFirstName();
			String lastName = RandomValues.getALastName();
			String domain = RandomValues.getADomain();

			//Saving as a new document
			database.createDocument(Person.class)
			.setField("FirstName", name)
			.setField("LastName", lastName)
			.setField("FullName", name + " " + lastName)
			.setField("InternetAddress", (name + "." + lastName + "@" + domain).toLowerCase())
			.generateId()
			.save();
		}

		// *** Creating the search index 
		database.refreshSearchIndex(true);

		// *** Searching for people with a last name 'Thomas'
		String query = "Thomas";

		System.out.println("");
		System.out.println("Searching for '" + query + "'...");
		DocumentIterator it = database.search(query);

		// Printing the results
		System.out.println("Found:");

		for(Document doc : it) {
			Person p = doc.getAs(Person.class);
			System.out.println("* " + p.getFieldAsString("FullName") + ": " + p.getFieldAsString("InternetAddress"));
		}

		// *** Finding who has an specific id		
		final String lookingForTheId = "P10B7";
		
		System.out.println("");		
		System.out.println("Looking for the id " + lookingForTheId + "...");

		Document p = database.getDocument(Person.class, lookingForTheId);
		
		if (p.isOpen()) {
			System.out.println("His/her name is " + p.getFieldAsString("FullName") + " and his email address is " + p.getFieldAsString("InternetAddress"));
		} else {
			System.out.println("Not found.");
		}

		// *** Closing the session
		River.closeSession(River.LOTUS_DOMINO);
		NotesThread.stermThread();

		System.out.println("Done.");
	}
}
