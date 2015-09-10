import lotus.domino.NotesThread;

import org.riverframework.River;
import org.riverframework.core.DefaultDatabase;
import org.riverframework.core.Document;
import org.riverframework.core.Session;

/**
 * Getting Started with IBM Notes
 * ==============================
 * 
 * This is a basic script that opens a session, creates a test database,
 * creates a document, set a simple value and retrieves it again. Also, 
 * says "Hello" :-)
 * 
 * To compile it, just use this command:
 * 
 * javac -cp .\*;C:\IBM\Notes\jvm\lib\ext\Notes.jar GettingStarted.java
 * 
 * To execute it:
 * 
 * java -cp .;.\*;C:\IBM\Notes\jvm\lib\ext\Notes.jar GettingStarted mypassword
 * 
 * Of course, you must change the location of Notes.jar, depending where you 
 * installed IBM Notes. Also, mypassword is the password that you are using to 
 * open IBM Notes. More exactly, the password to open your ID file.  
 * 
 * 
 * @author mario.sotil
 *
 */
public class GettingStarted {

	public static void main(String[] args) {
		// This is an arbitrary name for the test database. 
		final String filepath = "river-getting-started.nsf";
		
		// The password from your current ID is the only parameter. 
		// It must be the same password that you use when you open IBM Notes client in this computer.
		String password = "";
		if (args.length == 1) {
			password = args[0];
		} else {
			System.out.println("It was expected: java -cp classpath GettingStarted mypassword");
			return;
		}

		// Initializing the Notes Thread (required for stand-alone Java applications)
		NotesThread.sinitThread();

		// Opening a IBM Notes local session
		Session session = null;
		try {
			session = River.getSession(River.LOTUS_DOMINO, null, null, password);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return;
		}
		System.out.println("Session opened: " + session.getUserName());

		// Checking if the test database exists
		DefaultDatabase database = session.getDatabase(DefaultDatabase.class, "", filepath);

		// If not, then we create a new one
		if (!database.isOpen()) {
			database = session.createDatabase(DefaultDatabase.class, "", filepath);
		}
		System.out.println("Database opened: " + database.getName());

		// Saving a new document
		Document doc = database.createDocument().setField("Greetings", "Hello World!").save();
		
		// Retrieving its id 		
		String id = doc.getObjectId();
		System.out.println("Document created");
		
		// Getting the document again with this id...
		Document doc2 = database.getDocument(id);
		// ... and we say hello :-)
		System.out.println("*** River Framework says " + doc2.getFieldAsString("Greetings"));

		// *** Closing the session
		River.closeSession(River.LOTUS_DOMINO);
		NotesThread.stermThread();

		System.out.println("Done.");
	}
}
