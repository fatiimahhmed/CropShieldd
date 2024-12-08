/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
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
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface UsersSessionRemote {

    

    public void addUser(String firstName, String secondName, String gender, Date dateOfBirth, String email, String phone, String password , byte[] imageBytes)
            throws FirstNameNullException, GenderNullException, DateofBirthNullException, EmailNullException, PhoneNumberNullException, PasswordNullException,
            ImageFailedException;

    public UsersEntity getUserById(int id) throws UserNotFoundException;

    public List<UsersEntity> getUserByFirstName(String firstName) throws FirstNameNullException, UserNotFoundException;

    public UsersEntity getUserByEmail(String email) throws EmailNullException, UserNotFoundException;

    public UsersEntity getUserByPhone(String phone) throws PhoneNumberNullException, UserNotFoundException;

    public List<UsersEntity> getAllUsers();

    public void updateUser(int userId, String firstName, String secondName, String gender, Date dateOfBirth, String email, String phone, String password) throws UserNotFoundException, FirstNameNullException, GenderNullException, DateofBirthNullException, EmailNullException, PhoneNumberNullException, PasswordNullException;

    public void deleteUser(int userId) throws UserNotFoundException;

    
    
}
