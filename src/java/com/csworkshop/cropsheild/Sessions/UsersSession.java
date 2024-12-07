/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.AdminsExceptions.ImageFailedException;
import com.csworkshop.cropsheild.Entity.UsersEntity;
import com.csworkshop.cropsheild.UsersExceptions.DateofBirthNullException;
import com.csworkshop.cropsheild.UsersExceptions.EmailNullException;
import com.csworkshop.cropsheild.UsersExceptions.FirstNameNullException;
import com.csworkshop.cropsheild.UsersExceptions.GenderNullException;
import com.csworkshop.cropsheild.UsersExceptions.PasswordNullException;
import com.csworkshop.cropsheild.UsersExceptions.PhoneNumberNullException;
import com.csworkshop.cropsheild.UsersExceptions.UserNotFoundException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author syeda
 */
@Stateless
public class UsersSession implements UsersSessionRemote {

    @PersistenceContext(unitName = "CropSheildPU")
    private EntityManager em;

    @Override
    public void addUser(String firstName, String secondName, String gender, Date dateOfBirth, String email, String phone, String password , byte[] imageBytes)
            throws FirstNameNullException, GenderNullException, DateofBirthNullException, EmailNullException, PhoneNumberNullException, PasswordNullException,
            ImageFailedException
            {
        if (firstName == null || firstName.isEmpty() || firstName.length() < 3) {
            throw new FirstNameNullException("Invalid First Name. Try Again...!");
        }
        if (gender == null) {
            throw new GenderNullException("Select Your Gender, Please...!");
        }
        if (dateOfBirth == null) {
            throw new DateofBirthNullException("Invalid Date Of Birth. Try Again...!");
        }
        if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new EmailNullException("Please Enter Email...!");
        }
        if (phone == null || phone.isEmpty()) {
            throw new PhoneNumberNullException("Phone Number Cannot Be Empty...!");
        }
        if (password == null || password.isEmpty()) {
            throw new PasswordNullException("Password Cannot Be Empty...!");
        }
        
        UsersEntity user = new UsersEntity();
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setGender(gender);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setUserImage(imageBytes);
        em.persist(user);

    }

    @Override
    public List<UsersEntity> getAllUsers() {
        return em.createNamedQuery("UsersEntity.findAll", UsersEntity.class).getResultList();
    }

    @Override
    public UsersEntity getUserById(int id)
            throws UserNotFoundException {
        UsersEntity User;
        User = em.find(UsersEntity.class, id);
        if (User == null) {
            throw new UserNotFoundException("User with ID " + id + " does not exist.");
        }
        return User;
    }

    @Override
    public List<UsersEntity> getUserByFirstName(String firstName) throws FirstNameNullException, UserNotFoundException {
        if (firstName == null || firstName.isEmpty()) {
            throw new FirstNameNullException("First name cannot be null or empty.");
        }

        Query qry = em.createNamedQuery("UsersEntity.findByFirstName", UsersEntity.class);
        qry.setParameter("firstName", firstName);
        List<UsersEntity> users = qry.getResultList();

        if (users.isEmpty()) {
            throw new UserNotFoundException("No user found with the first name: " + firstName);
        }

        return users;
    }

    @Override
    public UsersEntity getUserByEmail(String email) throws EmailNullException, UserNotFoundException {
        if (email == null || email.isEmpty()) {
            throw new EmailNullException("Email cannot be null or empty.");
        }

        // Create and set parameter for named query
        Query qry = em.createNamedQuery("UsersEntity.findByEmail", UsersEntity.class);
        qry.setParameter("email", email); // Set the email parameter

        // Execute the query and get a single result
        UsersEntity users = (UsersEntity) qry.getSingleResult();
        if (users == null) {
            throw new UserNotFoundException("No user found with the email: " + email);
        }
        return users;
    }

    @Override
    public UsersEntity getUserByPhone(String phone) throws PhoneNumberNullException, UserNotFoundException {
        if (phone == null || phone.isEmpty()) {
            throw new PhoneNumberNullException("Phone number cannot be null or empty.");
        }

        // Create and set parameter for named query
        Query qry = em.createNamedQuery("UsersEntity.findByPhone", UsersEntity.class);
        qry.setParameter("phone", phone); // Set the phone parameter

        // Execute the query and get a single result
        UsersEntity users = (UsersEntity) qry.getSingleResult();
        if (users == null) {
            throw new UserNotFoundException("No user found with the email: " + phone);
        }
        return users;
    }

    @Override
    public void updateUser(int userId, String firstName, String secondName, String gender, Date dateOfBirth, String email, String phone, String password)
            throws UserNotFoundException, FirstNameNullException, GenderNullException, DateofBirthNullException, EmailNullException, PhoneNumberNullException, PasswordNullException {

        // Fetch the user by ID
        UsersEntity user = em.find(UsersEntity.class, userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }

        // Validate and update fields
        if (firstName == null || firstName.isEmpty() || firstName.length() < 3) {
            throw new FirstNameNullException("Invalid First Name. Try Again...!");
        }
        if (gender == null) {
            throw new GenderNullException("Select Your Gender, Please...!");
        }
        if (dateOfBirth == null) {
            throw new DateofBirthNullException("Invalid Date Of Birth. Try Again...!");
        }
        if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new EmailNullException("Please Enter Email...!");
        }
        if (phone == null || phone.isEmpty()) {
            throw new PhoneNumberNullException("Phone Number Cannot Be Empty...!");
        }
        if (password == null || password.isEmpty()) {
            throw new PasswordNullException("Password Cannot Be Empty...!");
        }
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setGender(gender);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);

        // Merge the updated entity back into the persistence context
        em.merge(user);
    }

    @Override
    public void deleteUser(int userId) throws UserNotFoundException {
        // Fetch the user by ID
        UsersEntity user = em.find(UsersEntity.class, userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }

        // Remove the user
        em.remove(user);
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
