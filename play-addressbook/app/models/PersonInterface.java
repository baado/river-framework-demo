package models;

import java.util.List;

/**
 * Created by mario.sotil on 10/27/2015.
 */
public interface PersonInterface {

    public List<Person> listAll();

    public Person findById(String id);

    public void save();

    public void remove();

    public String toString();
}
