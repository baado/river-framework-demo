/*
 * Getting Started with River Framework and IBM Notes
 * ==================================================
 * 
 * This is a basic program that opens a session, creates a test database,
 * creates a document, set a simple value and retrieves it again. Also, 
 * says "Hello" :-)
 * 
 * The instructions are in https://github.com/mariosotil/river-framework/wiki/Getting-Started 
 * 
 */
 
import lotus.domino.NotesThread;

import org.riverframework.River;
import org.riverframework.core.DefaultDatabase;
import org.riverframework.core.Session;

public class GettingStarted {

	public static void main(String[] args) {
		// This is an arbitrary name for the test database. 
		final String filepath = "river-getting-started.nsf";
		
		// The password from your current ID is the only command line parameter. 
		String password = args[0];

		// Opening a IBM Notes local session
		NotesThread.sinitThread();
		Session session = River.getSession(River.LOTUS_DOMINO, null, null, password);
		System.out.println("Session opened for " + session.getUserName());

		// Checking if the test database exists
		DefaultDatabase database = session.getDatabase(DefaultDatabase.class, "", filepath);
		// If not exist, we create a new one
		if (!database.isOpen()) database = session.createDatabase(DefaultDatabase.class, "", filepath);
		System.out.println("Database opened with the name " + database.getName());

		// Creating a new document. Saving its ID.
		String id = database.createDocument().setField("Greetings", "Hello World!").save().getObjectId();
		System.out.println("Document created");
		
		// Retrieving the document again with this id and say hello :-)
		System.out.println("River Framework says " + database.getDocument(id).getFieldAsString("Greetings"));

		// *** Closing the session
		River.closeSession(River.LOTUS_DOMINO);
		NotesThread.stermThread();
		System.out.println("Done.");
	}
}