/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csworkshop.cropsheild.Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "customers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomersEntity.findAll", query = "SELECT c FROM CustomersEntity c"),
    @NamedQuery(name = "CustomersEntity.findByCustomerId", query = "SELECT c FROM CustomersEntity c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CustomersEntity.findByCustomerType", query = "SELECT c FROM CustomersEntity c WHERE c.customerType = :customerType")})
public class CustomersEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;
    @Size(max = 10)
    @Column(name = "customer_type", length = 10)
    private String customerType;
    @Lob
    @Size(max = 65535)
    @Column(name = "address", length = 65535)
    private String address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private List<LandDetailsEntity> landDetailsEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private List<OrdersEntity> ordersEntityList;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private UsersEntity userId;

    public CustomersEntity() {
    }

    public CustomersEntity(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlTransient
    public List<LandDetailsEntity> getLandDetailsEntityList() {
        return landDetailsEntityList;
    }

    public void setLandDetailsEntityList(List<LandDetailsEntity> landDetailsEntityList) {
        this.landDetailsEntityList = landDetailsEntityList;
    }

    @XmlTransient
    public List<OrdersEntity> getOrdersEntityList() {
        return ordersEntityList;
    }

    public void setOrdersEntityList(List<OrdersEntity> ordersEntityList) {
        this.ordersEntityList = ordersEntityList;
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
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomersEntity)) {
            return false;
        }
        CustomersEntity other = (CustomersEntity) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.cropsheild.Entity.CustomersEntity[ customerId=" + customerId + " ]";
    }
    
}
