package com.example.demo;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * Data Access Object - provide some specific data operations without exposing details of the database
 * Access data for the Greeting entity.
 * Repository annotation allows Spring to find and configure the DAO.
 * Transactional annonation will cause Spring to call begin() and commit()
 * at the start/end of the method. If exception occurs it will also call rollback().
 */
@Repository
@Transactional
public class GreetingDao {

    //PersistenceContext annotation used to specify there is a database source.
    //EntityManager is used to create and remove persistent entity instances,
    //to find entities by their primary key, and to query over entities.
    @PersistenceContext
    private EntityManager entityManager;

    //Insert greeting into the database.
    public void create(Greeting greeting) {
        entityManager.persist(greeting);
        return;
    }

    //Return the greeting with the passed-in id.
    public Greeting getById(int id) {
        return entityManager.find(Greeting.class, id);
    }

    /**
     * Delete the user from the database.
     */
    /*
    public void delete(Greeting greeting) {
        if (entityManager.contains(greeting))
            entityManager.remove(greeting);
        else
            entityManager.remove(entityManager.merge(greeting));
        return;
    }
    */

    /**
     * Return all the users stored in the database.
     */
    /*
    @SuppressWarnings("unchecked")
    public List<Greeting> getAll() {
        return entityManager.createQuery("from User").getResultList();
    }
    */


    /**
     * Return the user having the passed email.
     */
    /*
    public Greeting getByEmail(String email) {
        return (Greeting) entityManager.createQuery(
                "from User where email = :email")
                .setParameter("email", email)
                .getSingleResult();
    }
    */

    /**
     * Update the passed user in the database.
     */
    /*
    public void update(Greeting student) {
        entityManager.merge(student);
        return;
    }
    */

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------


} // class UserDao
