package data;

import org.riverframework.core.AbstractIndexedDatabase;
import org.riverframework.core.DefaultCounter;
import org.riverframework.core.Session;

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

}
