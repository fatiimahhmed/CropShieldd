/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csworkshop.cropsheild.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author syeda
 */
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersEntity.findAll", query = "SELECT u FROM UsersEntity u"),
    @NamedQuery(name = "UsersEntity.findByUserId", query = "SELECT u FROM UsersEntity u WHERE u.userId = :userId"),
    @NamedQuery(name = "UsersEntity.findByFirstName", query = "SELECT u FROM UsersEntity u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "UsersEntity.findBySecondName", query = "SELECT u FROM UsersEntity u WHERE u.secondName = :secondName"),
    @NamedQuery(name = "UsersEntity.findByGender", query = "SELECT u FROM UsersEntity u WHERE u.gender = :gender"),
    @NamedQuery(name = "UsersEntity.findByDateOfBirth", query = "SELECT u FROM UsersEntity u WHERE u.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "UsersEntity.findByEmail", query = "SELECT u FROM UsersEntity u WHERE u.email = :email"),
    @NamedQuery(name = "UsersEntity.findByPhone", query = "SELECT u FROM UsersEntity u WHERE u.phone = :phone"),
    @NamedQuery(name = "UsersEntity.findByPassword", query = "SELECT u FROM UsersEntity u WHERE u.password = :password")})
public class UsersEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Size(max = 50)
    @Column(name = "second_name", length = 50)
    private String secondName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "gender", nullable = false, length = 6)
    private String gender;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @Lob
    @Column(name = "user_image")
    private byte[] userImage;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<CustomersEntity> customersEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<EmployeeEntity> employeeEntityList;

    public UsersEntity() {
    }

    public UsersEntity(Integer userId) {
        this.userId = userId;
    }

    public UsersEntity(Integer userId, String firstName, String gender, String email, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    @XmlTransient
    public List<CustomersEntity> getCustomersEntityList() {
        return customersEntityList;
    }

    public void setCustomersEntityList(List<CustomersEntity> customersEntityList) {
        this.customersEntityList = customersEntityList;
    }

    @XmlTransient
    public List<EmployeeEntity> getEmployeeEntityList() {
        return employeeEntityList;
    }

    public void setEmployeeEntityList(List<EmployeeEntity> employeeEntityList) {
        this.employeeEntityList = employeeEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersEntity)) {
            return false;
        }
        UsersEntity other = (UsersEntity) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.cropsheild.Entity.UsersEntity[ userId=" + userId + " ]";
    }
    
}
