package org.riverframework.domino.demo;

import org.riverframework.core.AbstractIndexedDatabase;
import org.riverframework.core.DefaultCounter;
import org.riverframework.core.Session;

public class AddressBook extends AbstractIndexedDatabase<AddressBook> {
	protected AddressBook(Session session, org.riverframework.wrapper.Database<?> _database) {
		super(session, _database);

		registerDocumentClass(DefaultCounter.class);
		registerDocumentClass(Person.class);
	}

	@Override
	protected AddressBook getThis() {
		return this;
	}

}
