/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csworkshop.cropsheild.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author syeda
 */
@Entity
@Table(name = "orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdersEntity.findAll", query = "SELECT o FROM OrdersEntity o"),
    @NamedQuery(name = "OrdersEntity.findByOrderId", query = "SELECT o FROM OrdersEntity o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "OrdersEntity.findByRequestedDate", query = "SELECT o FROM OrdersEntity o WHERE o.requestedDate = :requestedDate")})
public class OrdersEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "order_id", nullable = false)
    private Integer orderId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "requested_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date requestedDate;
    @OneToMany(mappedBy = "orderId")
    private List<JobsEntity> jobsEntityList;
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    @ManyToOne(optional = false)
    private CustomersEntity customerId;
    @JoinColumn(name = "land_id", referencedColumnName = "land_id", nullable = false)
    @ManyToOne(optional = false)
    private LandDetailsEntity landId;
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    @ManyToOne
    private OrderStatusEntity statusId;

    public OrdersEntity() {
    }

    public OrdersEntity(Integer orderId) {
        this.orderId = orderId;
    }

    public OrdersEntity(Integer orderId, Date requestedDate) {
        this.orderId = orderId;
        this.requestedDate = requestedDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    @XmlTransient
    public List<JobsEntity> getJobsEntityList() {
        return jobsEntityList;
    }

    public void setJobsEntityList(List<JobsEntity> jobsEntityList) {
        this.jobsEntityList = jobsEntityList;
    }

    public CustomersEntity getCustomerId() {
        return customerId;
    }

    public void setCustomerId(CustomersEntity customerId) {
        this.customerId = customerId;
    }

    public LandDetailsEntity getLandId() {
        return landId;
    }

    public void setLandId(LandDetailsEntity landId) {
        this.landId = landId;
    }

    public OrderStatusEntity getStatusId() {
        return statusId;
    }

    public void setStatusId(OrderStatusEntity statusId) {
        this.statusId = statusId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdersEntity)) {
            return false;
        }
        OrdersEntity other = (OrdersEntity) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.cropsheild.Entity.OrdersEntity[ orderId=" + orderId + " ]";
    }
    
}
