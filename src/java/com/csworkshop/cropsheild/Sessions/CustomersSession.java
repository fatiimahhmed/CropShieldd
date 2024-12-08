/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.AdminsExceptions.EmailAlreadyExistException;
import com.csworkshop.cropsheild.AdminsExceptions.ImageFailedException;
import com.csworkshop.cropsheild.CustomersExceptions.CustomerListEmptyException;
import com.csworkshop.cropsheild.CustomersExceptions.CustomerNotFoundException;
import com.csworkshop.cropsheild.CustomersExceptions.CustomerTypeNullException;
import com.csworkshop.cropsheild.Entity.CustomersEntity;
import com.csworkshop.cropsheild.Entity.UsersEntity;
import com.csworkshop.cropsheild.UsersExceptions.DateofBirthNullException;
import com.csworkshop.cropsheild.UsersExceptions.EmailNullException;
import com.csworkshop.cropsheild.UsersExceptions.FirstNameNullException;
import com.csworkshop.cropsheild.UsersExceptions.GenderNullException;
import com.csworkshop.cropsheild.UsersExceptions.PasswordNullException;
import com.csworkshop.cropsheild.UsersExceptions.PhoneNumberNullException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author syeda
 */
@Stateless
public class CustomersSession implements CustomersSessionRemote {

    @PersistenceContext(unitName = "CropSheildPU")
    private EntityManager em;

    @Override
    public void addCustomer(String firstName, String secondName, String gender, Date dateOfBirth, String email, String phone, String password, String customerType, String address, byte[] imageBytes)
            throws FirstNameNullException, GenderNullException, DateofBirthNullException, EmailNullException, PhoneNumberNullException, PasswordNullException, CustomerTypeNullException,
            EmailAlreadyExistException, ImageFailedException {

        // Validate inputs
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
        TypedQuery<UsersEntity> userQuery = em.createQuery(
                "SELECT u FROM UsersEntity u WHERE u.email = :email", UsersEntity.class
        );
        userQuery.setParameter("email", email);
        System.out.println("Email being checked: " + email);

        List<UsersEntity> users = userQuery.getResultList();

        if (users.isEmpty()) {
            users = null; // No user found
        }
        if (users != null) {
            throw new EmailAlreadyExistException("Email Is Already Registered Try Again With Other...!");
        }
        if (phone == null || phone.isEmpty()) {
            throw new PhoneNumberNullException("Phone Number Cannot Be Empty...!");
        }
        if (password == null || password.isEmpty()) {
            throw new PasswordNullException("Password Cannot Be Empty...!");
        }
        if (customerType == null || customerType.isEmpty()) {
            throw new CustomerTypeNullException("Customer Type Cannot Be Null or Empty...!");
        }

        // Create a new user entity
        UsersEntity user = new UsersEntity();
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setGender(gender);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password); // You may want to hash the password here
        try {
            user.setUserImage(imageBytes); // Set the user image
        } catch (Exception e) {
            throw new ImageFailedException("Failed to convert image bytes to Blob");
        }
        // Persist the user
        em.persist(user);

        // Create a new customer entity
        CustomersEntity customer = new CustomersEntity();
        customer.setUserId(user); // Use the newly created user
        customer.setCustomerType(customerType);
        customer.setAddress(address);

        // Persist the customer
        em.persist(customer);
    }

    @Override
    public CustomersEntity getCustomerById(int customerId) throws CustomerNotFoundException {
        CustomersEntity customer = em.find(CustomersEntity.class, customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
        }
        return customer;
    }

    @Override
    public List<CustomersEntity> getAllCustomers() throws CustomerListEmptyException {
        Query query = em.createNamedQuery("CustomersEntity.findAll", CustomersEntity.class);
        List<CustomersEntity> customerList = query.getResultList();
        if (customerList.isEmpty()) {
            throw new CustomerListEmptyException("Customer list is empty...!");
        }
        return customerList;
    }

    @Override
    public List<CustomersEntity> getCustomersByType(String customerType) throws CustomerListEmptyException {
        TypedQuery<CustomersEntity> query = em.createNamedQuery("CustomersEntity.findByType", CustomersEntity.class);
        query.setParameter("customerType", customerType);
        List<CustomersEntity> customerList = query.getResultList();
        if (customerList.isEmpty()) {
            throw new CustomerListEmptyException("No customers found for the specified type.");
        }
        return customerList;
    }

    @Override
    public void updateCustomer(int customerId, String customerType, String address, String firstName, String secondName, String gender, Date dateOfBirth, String email, String phone, String password)
            throws CustomerNotFoundException, FirstNameNullException, GenderNullException, DateofBirthNullException, EmailNullException, PhoneNumberNullException, PasswordNullException, CustomerTypeNullException {

        // Find the customer
        CustomersEntity customer = em.find(CustomersEntity.class, customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
        }

        // Update customer details
        if (customerType != null && !customerType.isEmpty()) {
            customer.setCustomerType(customerType);
        }
        if (address != null && !address.isEmpty()) {
            customer.setAddress(address);
        }

        // Update related user details
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
        UsersEntity user = customer.getUserId();
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setGender(gender);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);

        // Persist changes
        em.merge(customer); // Update Customer
        em.merge(user);     // Update User
    }

    @Override
    public void deleteCustomer(int customerId) throws CustomerNotFoundException {
        CustomersEntity customer = em.find(CustomersEntity.class, customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
        }

        // Fetch the associated user
        UsersEntity user = customer.getUserId();

        // Delete the customer
        em.remove(customer);

        // Delete the user
        if (user != null) {
            em.remove(user);
        }
    }

    @Override
    public CustomersEntity getCustomerByEmail(String email, String password) throws CustomerNotFoundException, EmailNullException {
        if (email == null || email.isEmpty()) {
            throw new EmailNullException("Email cannot be null or empty.");
        }

        // Step 1: Fetch the user by email
        TypedQuery<UsersEntity> userQuery = em.createQuery(
                "SELECT u FROM UsersEntity u WHERE u.email = :email and u.password = :password", UsersEntity.class
        );
        userQuery.setParameter("email", email);
        userQuery.setParameter("password", password);

        UsersEntity user;
        try {
            user = userQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new CustomerNotFoundException("User with email " + email + " not found.");
        }

        // Step 2: Fetch the customer by user entity
        TypedQuery<CustomersEntity> customerQuery = em.createQuery(
                "SELECT c FROM CustomersEntity c WHERE c.userId = :user", CustomersEntity.class
        );
        customerQuery.setParameter("user", user);

        List<CustomersEntity> customers = customerQuery.getResultList();
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("Customer for email " + email + " not found.");
        }

        return customers.get(0);
    }

    @Override
    public long countAllCustomers() throws CustomerNotFoundException {
        try {
            return em.createQuery("SELECT COUNT(c) FROM CustomersEntity c", Long.class).getSingleResult();
        }
        catch(Exception e)
        {
            throw new CustomerNotFoundException("Error Finding In Total Customers...!");              
        }

    }

    public void persist(Object object) {
        em.persist(object);
    }

}
