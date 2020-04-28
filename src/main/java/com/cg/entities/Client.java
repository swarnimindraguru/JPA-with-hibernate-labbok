package com.cg.jpastart.entities;

import javax.persistence.*;
import java.util.List;

public class Client {
    EntityManagerFactory sig;

    public static void main(String[] args) {
        Client client = new Client();
        client.execute();
    }

    void execute() {
        //entity manager factory created
        sig = Persistence.createEntityManagerFactory("author-mgt");
        Author author = createAuthor();
        int id = author.getAuthorId();
        Author found = findAuthorById(id);
        print(found);

        found.setFirstName("Swarnim");
        updateAuthor(found);

        print(found);

        List<Author> employees=findAllEmployees();

        //removeEmployeeById(id); //uncomment if you want to remove author
        System.out.println("printing all Authors");
        print(employees);
        sig.close();
    }

    void print(Author author){
        System.out.println("employee id="+author.getAuthorId()+" firstname="+author.getFirstName()+" middlename="+author.getMiddleName()+" lastname="+author.getLastName()+" phonenumber="+author.getPhoneNo());

    }
    void print(List<Author>authors){
        for (Author author:authors){
            print(author);
        }
    }

    Author findAuthorById(int authorid) {
        EntityManager em = sig.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Author a = em.find(Author.class, authorid);
        transaction.commit();
        em.close();
        return a;
    }

    List<Author> findAllEmployees(){
        EntityManager em = sig.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query =em.createQuery("from Author");
        List<Author>authors=query.getResultList();
        return authors;
    }

    Author updateAuthor(Author author){
        EntityManager em = sig.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        author=em.merge(author);
        transaction.commit();
        em.close();
        return author;
    }

    void removeAuthorById(int authorid){
        EntityManager em = sig.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Author author=em.find(Author.class,authorid);
        em.remove(author);
        transaction.commit();
        em.close();
        System.out.println("Authors removed with id="+authorid);
    }


    Author createAuthor() {
        //
        //creating entity manager
        //
        EntityManager em = sig.createEntityManager();
        //
        //starting transaction
        //
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Author author = new Author();
        author.setFirstName("Vivek");
        author.setMiddleName("Indra");
        author.setLastName("Guru");
        author.setPhoneNo(882470175);

        em.persist(author);
        //
        //comitting transaction after operations
        //
        transaction.commit();

        System.out.println("Added one Author, with  Authorid=" + author.getAuthorId());
        //
        // closing entity manager after operation
        //
        em.close();
        return author;
    }
}
