/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csworkshop.cropsheild.Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author syeda
 */
@Entity
@Table(name = "employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmployeeEntity.findAll", query = "SELECT e FROM EmployeeEntity e"),
    @NamedQuery(name = "EmployeeEntity.findByEmployeeId", query = "SELECT e FROM EmployeeEntity e WHERE e.employeeId = :employeeId")})
public class EmployeeEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;
    @Lob
    @Size(max = 65535)
    @Column(name = "permissions", length = 65535)
    private String permissions;
    @OneToMany(mappedBy = "employeeId")
    private List<JobsEntity> jobsEntityList;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne
    private RolesEntity roleId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private UsersEntity userId;

    public EmployeeEntity() {
    }

    public EmployeeEntity(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @XmlTransient
    public List<JobsEntity> getJobsEntityList() {
        return jobsEntityList;
    }

    public void setJobsEntityList(List<JobsEntity> jobsEntityList) {
        this.jobsEntityList = jobsEntityList;
    }

    public RolesEntity getRoleId() {
        return roleId;
    }

    public void setRoleId(RolesEntity roleId) {
        this.roleId = roleId;
    }

    public UsersEntity getUserId() {
        return userId;
    }

    public void setUserId(UsersEntity userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeId != null ? employeeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeEntity)) {
            return false;
        }
        EmployeeEntity other = (EmployeeEntity) object;
        if ((this.employeeId == null && other.employeeId != null) || (this.employeeId != null && !this.employeeId.equals(other.employeeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.cropsheild.Entity.EmployeeEntity[ employeeId=" + employeeId + " ]";
    }
    
}
