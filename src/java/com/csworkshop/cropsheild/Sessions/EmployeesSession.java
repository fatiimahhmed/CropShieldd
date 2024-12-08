/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.AdminsExceptions.AdminListEmptyException;
import com.csworkshop.cropsheild.AdminsExceptions.AdminNotFoundException;
import com.csworkshop.cropsheild.AdminsExceptions.EmailAlreadyExistException;
import com.csworkshop.cropsheild.AdminsExceptions.EmployeeFetchException;
import com.csworkshop.cropsheild.AdminsExceptions.ImageFailedException;

import com.csworkshop.cropsheild.AdminsExceptions.PermissionNullException;
import com.csworkshop.cropsheild.Entity.EmployeeEntity;
import com.csworkshop.cropsheild.Entity.RolesEntity;

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
public class EmployeesSession implements EmployeesSessionRemote {

    @PersistenceContext(unitName = "CropSheildPU")
    private EntityManager em;

    @Override
    public void addAdmin(String firstName, String secondName, String gender, Date dateOfBirth, String email, String phone, String password, int role, byte[] imageBytes, String permissions)
            throws FirstNameNullException, GenderNullException, DateofBirthNullException, EmailNullException, PhoneNumberNullException, PasswordNullException, PermissionNullException,
            EmailAlreadyExistException,ImageFailedException {

        // Step 1: Validate inputs (as shown earlier)
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
        if (permissions == null || permissions.isEmpty()) {
            throw new PermissionNullException("Please specify the Permissions of the Admin...!");
        }

        // Step 2: Check if email already exists
        TypedQuery<UsersEntity> userQuery = em.createQuery(
                "SELECT u FROM UsersEntity u WHERE u.email = :email", UsersEntity.class
        );
        userQuery.setParameter("email", email);
        try {
            UsersEntity existingUser = userQuery.getSingleResult();
            if (existingUser != null) {
                throw new EmailAlreadyExistException("Already Existing Email. Please Try With Another One...");
            }
        } catch (NoResultException ignored) {
            // No existing user found, continue
        }

        // Step 3: Create a new user entity
        UsersEntity user = new UsersEntity();
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setGender(gender);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password); // Hash the password for security

        // Convert the imageBytes to a Blob
        try {
            user.setUserImage(imageBytes); // Set the user image
        } catch (Exception e) {
            throw new ImageFailedException("Failed to convert image bytes to Blob");
        }

        // Persist the user entity
        em.persist(user);

        // Step 4: Assign the newly created user as an admin
        EmployeeEntity admin = new EmployeeEntity();
        admin.setUserId(user); // Set the user ID
        RolesEntity roleEntity = em.find(RolesEntity.class, role);
        if (roleEntity == null) {
            throw new IllegalArgumentException("Invalid role ID: " + role);
        }
        admin.setRoleId(roleEntity);
        admin.setPermissions(permissions);

        // Persist the admin entity
        em.persist(admin);
    }

    @Override
    public EmployeeEntity getAdminById(int adminId) throws AdminNotFoundException {
        EmployeeEntity admin = em.find(EmployeeEntity.class, adminId);
        if (admin == null) {
            throw new AdminNotFoundException("Admin with ID " + adminId + " not found.");
        }
        return admin;
    }

    @Override
    public List<EmployeeEntity> getAllAdmins()
            throws AdminListEmptyException {
        Query query = em.createNamedQuery("AdminsEntity.findAll", EmployeeEntity.class);
        List<EmployeeEntity> adminlist = query.getResultList();
        if (adminlist.isEmpty()) {
            throw new AdminListEmptyException("List is Empty ...!");
        }
        return adminlist;
    }

    @Override
    public List<EmployeeEntity> getAdminsByRole(String role) throws AdminListEmptyException {
        TypedQuery<EmployeeEntity> query = em.createNamedQuery("AdminsEntity.findByRole", EmployeeEntity.class);
        query.setParameter("role", role);
        List<EmployeeEntity> adminlist = query.getResultList();
        if (adminlist.isEmpty()) {
            throw new AdminListEmptyException("List is Empty ...!");
        }
        return adminlist;
    }

    @Override
    public void updateAdmin(int adminId, String role, String permissions, String firstName, String secondName, String gender, Date dateOfBirth, String email, String phone, String password)
            throws AdminNotFoundException, FirstNameNullException, GenderNullException, DateofBirthNullException, EmailNullException, PhoneNumberNullException, PasswordNullException, PermissionNullException {

        // Step 1: Find the Admin
        EmployeeEntity admin = em.find(EmployeeEntity.class, adminId);
        if (admin == null) {
            throw new AdminNotFoundException("Employee with ID " + adminId + " not found.");
        }

        // Step 2: Update Admin Details
        if (role != null && !role.isEmpty()) {
            RolesEntity roleEntity = em.find(RolesEntity.class, role);
            if (roleEntity == null) {
                throw new IllegalArgumentException("Role Does Not Found!");
            } else {
                admin.setRoleId(roleEntity);
            }// Ensure the role is valid
        }
        if (permissions != null && !permissions.isEmpty()) {
            admin.setPermissions(permissions);
        }

        // Step 3: Update Related User Details
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
        UsersEntity user = admin.getUserId();
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setGender(gender);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        // Step 4: Persist Changes
        em.merge(admin); // Update Admin
        em.merge(user);  // Update User
    }

    @Override
    public void deleteAdmin(int adminId) throws AdminNotFoundException {
        EmployeeEntity admin = em.find(EmployeeEntity.class, adminId);
        if (admin == null) {
            throw new AdminNotFoundException("Admin with ID " + adminId + " not found.");
        }

        // Fetch the associated user
        UsersEntity user = admin.getUserId();

        // Delete the admin
        em.remove(admin);

        // Delete the user
        if (user != null) {
            em.remove(user);
        }
    }

    @Override
    public EmployeeEntity getAdminByEmail(String email, String Password) throws AdminNotFoundException, EmailNullException {
        if (email == null || email.isEmpty()) {
            throw new EmailNullException("Email cannot be null or empty.");
        }

        // Step 1: Fetch the user by email
        TypedQuery<UsersEntity> userQuery = em.createQuery(
                "SELECT u FROM UsersEntity u WHERE u.email = :email and u.password = :Password", UsersEntity.class
        );
        userQuery.setParameter("email", email); // Set the email parameter
        userQuery.setParameter("Password", Password);

        UsersEntity users = null;
        try {
            users = userQuery.getSingleResult(); // Fetch user
        } catch (NoResultException e) {
            throw new AdminNotFoundException("No user found with the given email and password.");
        }
//        if (users == null) {
//            throw new AdminNotFoundException("User with email " + email + " not found.");
//        }

        // Step 2: Fetch the admin by user ID
        TypedQuery<EmployeeEntity> adminQuery = em.createQuery(
                "SELECT a FROM EmployeeEntity a WHERE a.userId = :userId", EmployeeEntity.class
        );
        adminQuery.setParameter("userId", users);

        List<EmployeeEntity> admins = adminQuery.getResultList();
        if (admins.isEmpty()) {
            throw new AdminNotFoundException("Admin for email " + email + " not found.");
        }

        return admins.get(0);
    }

    @Override
    public long countTotalEmployees() throws EmployeeFetchException {
        try {
            return em.createQuery("SELECT COUNT(e) FROM EmployeeEntity e", Long.class).getSingleResult();
        } catch (Exception e) {
            throw new EmployeeFetchException("Failed to count employees");
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
