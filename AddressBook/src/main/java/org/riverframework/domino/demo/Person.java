package org.riverframework.domino.demo;

import org.riverframework.core.AbstractIndexedDocument;
import org.riverframework.core.Database;

class Person extends AbstractIndexedDocument<Person> {
	protected Person(Database database, org.riverframework.wrapper.Document<?> _doc) {
		super(database, _doc);
	}

	@Override
	public String getIdField() {
		/*
		 * Required. This is the field name whose value will be used as key in each 
		 * document and the index.
		 */
		return "fi_ShortName";
	}

	@Override
	public String getIndexName() {
		/*
		 * Required. This is the index name that will be used. 
		 */
		return "vi_persons";
	}

	@Override
	public String getBinder() {
		/*
		 * Required. This is the value that will group all the documents. In
		 * IBM Notes terms, this is the value of the Form field.
		 */
		return "fo_person";
	}

	@Override
	protected String[] getParametersToCreateIndex() {
		/*
		 * Required. If the index could not be found, this method returns the 
		 * parameters to create it. Take note of the query to select only the 
		 * Person documents. 
		 */
		return new String[] {getIndexName(), "Form=\"" + getBinder() + "\""};
	}

	@Override
	public Person afterCreate() {
		/* 
		 * This method is optional. It's useful for do something every time
		 * a document is created. In this demo, we will set a 'location' field
		 * to be sure that always be set. 
		 */
		_doc.setField("fi_Location", "NY");

		return getThis();
	}

	@Override
	public Person generateId() {
		/* 
		 * This method is optional. You can set the Id using setId(), 
		 * but you can use this method to calculate it any time.  
		 */

		// This values are only to generate the id as an hex number
		final int BASE_COUNTER = 16;
		final int LENGTH_COUNTER = 4;
		final long MIN_COUNTER = (long) Math.pow(BASE_COUNTER, LENGTH_COUNTER - 1) - 1;

		// If there is no an id defined, then we generate it
		if (getId().equals("")) {
			/* Getting a number from the counter using as key the string "KEY_PERSON"
			 * You can use any key to be sure that you will always get a unique number
			 * for the different situations 
			 */
			long id = getDatabase().getCounter("KEY_PERSON").getCount();

			// The count will start in MIN_COUNTER
			id += MIN_COUNTER;

			// Setting the index field. e.g. P10F7
			setId("P" + Long.toString(id, BASE_COUNTER).toUpperCase());
		}
		return getThis();
	}

	@Override
	protected Person getThis() {
		/*
		 *  Required. Used for method chaining
		 */
		return this;
	}

}
