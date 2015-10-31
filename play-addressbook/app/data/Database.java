package data;

import org.riverframework.core.AbstractIndexedDatabase;
import org.riverframework.core.DefaultCounter;
import org.riverframework.core.Session;
import play.Logger;

public class Database extends AbstractIndexedDatabase<Database> {
	protected Database(Session session, org.riverframework.wrapper.Database<?> _database) {
		super(session, _database);

		/*
		 * Take note of this lines. They let the framework to know what 
		 * document classes will be controlled for this database.
		 */
		registerDocumentClass(DefaultCounter.class);
		registerDocumentClass(Person.class);
	}

	@Override
	protected Database getThis() {
		return this;
	}

	public void reset() {
		System.out.println("Deleting old documents...");
		getAllDocuments().deleteAll();

		// Creating five hundred Person documents
		System.out.println("Creating documents with random data...");
		for (int i = 0; i < 50; i++) {

			//Getting some random values
			String name = RandomValuesHelper.getAFirstName();
			String lastName = RandomValuesHelper.getALastName();
			String domain = RandomValuesHelper.getADomain();

			//Saving as a new document
			createDocument(Person.class)
					.setField("FirstName", name)
					.setField("LastName", lastName)
					.setField("FullName", name + " " + lastName)
					.setField("InternetAddress", (name + "." + lastName + "@" + domain).toLowerCase())
					.generateId()
					.save();
		}

		Logger.info("Documents created.");

	}
}
