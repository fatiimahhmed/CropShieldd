/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.AdminsExceptions.AdminListEmptyException;
import com.csworkshop.cropsheild.AdminsExceptions.AdminNotFoundException;
import com.csworkshop.cropsheild.AdminsExceptions.EmailAlreadyExistException;
import com.csworkshop.cropsheild.AdminsExceptions.EmployeeFetchException;
import com.csworkshop.cropsheild.AdminsExceptions.ImageFailedException;
import com.csworkshop.cropsheild.AdminsExceptions.PermissionNullException;

import com.csworkshop.cropsheild.Entity.EmployeeEntity;
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
public interface EmployeesSessionRemote {

  

    public EmployeeEntity getAdminById(int adminId) throws AdminNotFoundException;

    public List<EmployeeEntity> getAllAdmins()
    throws AdminListEmptyException;

    public List<EmployeeEntity> getAdminsByRole(String role) throws AdminListEmptyException;

    public void updateAdmin(int adminId, String role, String permissions, String firstName, String secondName, String gender, Date dateOfBirth, String email, String phone, String password) throws AdminNotFoundException, FirstNameNullException, GenderNullException, DateofBirthNullException, EmailNullException, PhoneNumberNullException, PasswordNullException, PermissionNullException;

    public void deleteAdmin(int adminId) throws AdminNotFoundException;

    public EmployeeEntity getAdminByEmail(String email , String Password) throws AdminNotFoundException, EmailNullException;

   public void addAdmin(String firstName, String secondName, String gender, Date dateOfBirth, String email, String phone, String password, int role, byte[] imageBytes, String permissions)
            throws FirstNameNullException, GenderNullException, DateofBirthNullException, EmailNullException, PhoneNumberNullException, PasswordNullException, PermissionNullException,
            EmailAlreadyExistException,ImageFailedException;

    public long countTotalEmployees() throws EmployeeFetchException;

       
}
