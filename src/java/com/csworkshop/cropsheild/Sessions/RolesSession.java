/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.CommonExceptions.DuplicateRoleException;
import com.csworkshop.cropsheild.CommonExceptions.RoleFetchException;
import com.csworkshop.cropsheild.CommonExceptions.RoleNotFoundException;
import com.csworkshop.cropsheild.Entity.RolesEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author syeda
 */
@Stateless
public class RolesSession implements RolesSessionRemote {

    @PersistenceContext(unitName = "CropSheildPU")
    private EntityManager em;

    @Override
    public void addRole(String roleName) throws DuplicateRoleException {
        // Check for duplicate roles
        RolesEntity existingRole = null;
        try {
            existingRole = em.createQuery(
                    "SELECT r FROM RolesEntity r WHERE r.roleName = :roleName", RolesEntity.class)
                    .setParameter("roleName", roleName)
                    .getSingleResult();
        } catch (NoResultException e) {
            // No duplicate found
        }

        if (existingRole != null) {
            throw new DuplicateRoleException("Role with name '" + roleName + "' already exists.");
        }

        // If no duplicate, create the new role
        RolesEntity role = new RolesEntity();
        role.setRoleName(roleName);
        em.persist(role);
    }

    @Override
    public void updateRole(int roleId, String newRoleName) throws RoleNotFoundException {
        RolesEntity role = em.find(RolesEntity.class, roleId);
        if (role == null) {
            throw new RoleNotFoundException("Role with ID " + roleId + " not found.");
        }

        role.setRoleName(newRoleName);
        em.merge(role);
    }

    @Override
    public void deleteRole(int roleId) throws RoleNotFoundException {
        RolesEntity role = em.find(RolesEntity.class, roleId);
        if (role == null) {
            throw new RoleNotFoundException("Role with ID " + roleId + " not found.");
        }

        em.remove(role);
    }

    @Override
    public List<RolesEntity> getAllRoles() throws RoleFetchException {
        try {
            return em.createQuery("SELECT r FROM RolesEntity r", RolesEntity.class).getResultList();
        } catch (Exception e) {
            throw new RoleFetchException("Failed to fetch roles from the database", e);
        }
    }

    @Override
    public RolesEntity getRoleById(int roleId) throws RoleNotFoundException {
        RolesEntity role = em.find(RolesEntity.class, roleId);
        if (role == null) {
            throw new RoleNotFoundException("Role with ID " + roleId + " not found.");
        }

        return role;
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
