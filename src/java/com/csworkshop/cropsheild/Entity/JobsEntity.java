/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csworkshop.cropsheild.Entity;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author syeda
 */
@Entity
@Table(name = "jobs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JobsEntity.findAll", query = "SELECT j FROM JobsEntity j"),
    @NamedQuery(name = "JobsEntity.findByJobId", query = "SELECT j FROM JobsEntity j WHERE j.jobId = :jobId"),
    @NamedQuery(name = "JobsEntity.findByJobStatus", query = "SELECT j FROM JobsEntity j WHERE j.jobStatus = :jobStatus")})
public class JobsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "job_id", nullable = false)
    private Integer jobId;
    @Size(max = 11)
    @Column(name = "job_status", length = 11)
    private String jobStatus;
    @JoinColumn(name = "drone_id", referencedColumnName = "drone_id")
    @ManyToOne
    private DronesEntity droneId;
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @ManyToOne
    private EmployeeEntity employeeId;
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @ManyToOne
    private OrdersEntity orderId;

    public JobsEntity() {
    }

    public JobsEntity(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public DronesEntity getDroneId() {
        return droneId;
    }

    public void setDroneId(DronesEntity droneId) {
        this.droneId = droneId;
    }

    public EmployeeEntity getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(EmployeeEntity employeeId) {
        this.employeeId = employeeId;
    }

    public OrdersEntity getOrderId() {
        return orderId;
    }

    public void setOrderId(OrdersEntity orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobId != null ? jobId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JobsEntity)) {
            return false;
        }
        JobsEntity other = (JobsEntity) object;
        if ((this.jobId == null && other.jobId != null) || (this.jobId != null && !this.jobId.equals(other.jobId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.cropsheild.Entity.JobsEntity[ jobId=" + jobId + " ]";
    }
    
}
