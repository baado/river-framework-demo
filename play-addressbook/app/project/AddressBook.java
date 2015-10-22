package project;

import models.Person;
import org.riverframework.core.AbstractIndexedDatabase;
import org.riverframework.core.DefaultCounter;
import org.riverframework.core.Session;

public class AddressBook extends AbstractIndexedDatabase<AddressBook> {
	protected AddressBook(Session session, org.riverframework.wrapper.Database<?> _database) {
		super(session, _database);

		/*
		 * Take note of this lines. They let the framework to know what 
		 * document classes will be controlled for this database.
		 */
		registerDocumentClass(DefaultCounter.class);
		registerDocumentClass(Person.class);
	}

	@Override
	protected AddressBook getThis() {
		return this;
	}

}
