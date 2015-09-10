import lotus.domino.NotesThread;

import org.riverframework.River;
import org.riverframework.core.DefaultDatabase;
import org.riverframework.core.Document;
import org.riverframework.core.Session;

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
		System.out.println("*** River says " + doc2.getFieldAsString("Greetings"));

		// *** Closing the session
		River.closeSession(River.LOTUS_DOMINO);
		NotesThread.stermThread();

		System.out.println("Done.");
	}
}
