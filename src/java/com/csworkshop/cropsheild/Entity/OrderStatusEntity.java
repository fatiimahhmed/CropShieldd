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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author syeda
 */
@Entity
@Table(name = "order_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderStatusEntity.findAll", query = "SELECT o FROM OrderStatusEntity o"),
    @NamedQuery(name = "OrderStatusEntity.findByStatusId", query = "SELECT o FROM OrderStatusEntity o WHERE o.statusId = :statusId"),
    @NamedQuery(name = "OrderStatusEntity.findByStatusName", query = "SELECT o FROM OrderStatusEntity o WHERE o.statusName = :statusName"),
    @NamedQuery(name = "OrderStatusEntity.findByDescription", query = "SELECT o FROM OrderStatusEntity o WHERE o.description = :description")})
public class OrderStatusEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "status_id", nullable = false)
    private Integer statusId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "status_name", nullable = false, length = 50)
    private String statusName;
    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;
    @OneToMany(mappedBy = "statusId")
    private List<OrdersEntity> ordersEntityList;

    public OrderStatusEntity() {
    }

    public OrderStatusEntity(Integer statusId) {
        this.statusId = statusId;
    }

    public OrderStatusEntity(Integer statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<OrdersEntity> getOrdersEntityList() {
        return ordersEntityList;
    }

    public void setOrdersEntityList(List<OrdersEntity> ordersEntityList) {
        this.ordersEntityList = ordersEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statusId != null ? statusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderStatusEntity)) {
            return false;
        }
        OrderStatusEntity other = (OrderStatusEntity) object;
        if ((this.statusId == null && other.statusId != null) || (this.statusId != null && !this.statusId.equals(other.statusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.cropsheild.Entity.OrderStatusEntity[ statusId=" + statusId + " ]";
    }
    
}
