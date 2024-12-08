/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;


import com.csworkshop.cropsheild.CommonExceptions.DuplicateRoleException;
import com.csworkshop.cropsheild.CommonExceptions.RoleFetchException;
import com.csworkshop.cropsheild.CommonExceptions.RoleNotFoundException;
import com.csworkshop.cropsheild.Entity.RolesEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface RolesSessionRemote {

    public void addRole(String roleName) throws DuplicateRoleException;

    public void updateRole(int roleId, String newRoleName) throws RoleNotFoundException;

    public void deleteRole(int roleId) throws RoleNotFoundException;

    public List<RolesEntity> getAllRoles() throws RoleFetchException;

    public RolesEntity getRoleById(int roleId) throws RoleNotFoundException;
    
}
