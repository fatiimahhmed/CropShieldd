/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.AdminsExceptions.EmailAlreadyExistException;
import com.csworkshop.cropsheild.AdminsExceptions.ImageFailedException;
import com.csworkshop.cropsheild.CustomersExceptions.CustomerListEmptyException;
import com.csworkshop.cropsheild.CustomersExceptions.CustomerNotFoundException;
import com.csworkshop.cropsheild.CustomersExceptions.CustomerTypeNullException;
import com.csworkshop.cropsheild.Entity.CustomersEntity;
import com.csworkshop.cropsheild.UsersExceptions.DateofBirthNullException;
import com.csworkshop.cropsheild.UsersExceptions.EmailNullException;
import com.csworkshop.cropsheild.UsersExceptions.FirstNameNullException;
import com.csworkshop.cropsheild.UsersExceptions.GenderNullException;
import com.csworkshop.cropsheild.UsersExceptions.PasswordNullException;
import com.csworkshop.cropsheild.UsersExceptions.PhoneNumberNullException;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface CustomersSessionRemote {

        public void addCustomer(String firstName, String secondName, String gender, Date dateOfBirth, String email, String phone, String password, String customerType, String address, byte[] imageBytes)
            throws FirstNameNullException, GenderNullException, DateofBirthNullException, EmailNullException, PhoneNumberNullException, PasswordNullException, CustomerTypeNullException,
            EmailAlreadyExistException,ImageFailedException;

    public CustomersEntity getCustomerById(int customerId) throws CustomerNotFoundException;

    public List<CustomersEntity> getAllCustomers() throws CustomerListEmptyException;

    public List<CustomersEntity> getCustomersByType(String customerType) throws CustomerListEmptyException;

    public void updateCustomer(int customerId, String customerType, String address, String firstName, String secondName, String gender, Date dateOfBirth, String email, String phone, String password) throws CustomerNotFoundException, FirstNameNullException, GenderNullException, DateofBirthNullException, EmailNullException, PhoneNumberNullException, PasswordNullException, CustomerTypeNullException;

    public void deleteCustomer(int customerId) throws CustomerNotFoundException;

    public CustomersEntity getCustomerByEmail(String email , String password) throws CustomerNotFoundException, EmailNullException;

    public long countAllCustomers() throws CustomerNotFoundException;
    
}
