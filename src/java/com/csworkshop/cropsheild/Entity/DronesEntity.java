/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csworkshop.cropsheild.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "drones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DronesEntity.findAll", query = "SELECT d FROM DronesEntity d"),
    @NamedQuery(name = "DronesEntity.findByDroneId", query = "SELECT d FROM DronesEntity d WHERE d.droneId = :droneId"),
    @NamedQuery(name = "DronesEntity.findByDroneName", query = "SELECT d FROM DronesEntity d WHERE d.droneName = :droneName"),
    @NamedQuery(name = "DronesEntity.findByStatus", query = "SELECT d FROM DronesEntity d WHERE d.status = :status"),
    @NamedQuery(name = "DronesEntity.findByPayloadCapacity", query = "SELECT d FROM DronesEntity d WHERE d.payloadCapacity = :payloadCapacity"),
    @NamedQuery(name = "DronesEntity.findByCurrentLatitude", query = "SELECT d FROM DronesEntity d WHERE d.currentLatitude = :currentLatitude"),
    @NamedQuery(name = "DronesEntity.findByCurrentLongitude", query = "SELECT d FROM DronesEntity d WHERE d.currentLongitude = :currentLongitude"),
    @NamedQuery(name = "DronesEntity.findByHomeLatitude", query = "SELECT d FROM DronesEntity d WHERE d.homeLatitude = :homeLatitude"),
    @NamedQuery(name = "DronesEntity.findByHomeLongitude", query = "SELECT d FROM DronesEntity d WHERE d.homeLongitude = :homeLongitude")})
public class DronesEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "drone_id", nullable = false)
    private Integer droneId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "drone_name", nullable = false, length = 50)
    private String droneName;
    @Size(max = 11)
    @Column(name = "status", length = 11)
    private String status;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "payload_capacity", nullable = false, precision = 10, scale = 2)
    private BigDecimal payloadCapacity;
    @Column(name = "current_latitude", precision = 10, scale = 7)
    private BigDecimal currentLatitude;
    @Column(name = "current_longitude", precision = 10, scale = 7)
    private BigDecimal currentLongitude;
    @Column(name = "home_latitude", precision = 10, scale = 7)
    private BigDecimal homeLatitude;
    @Column(name = "home_longitude", precision = 10, scale = 7)
    private BigDecimal homeLongitude;
    @OneToMany(mappedBy = "droneId")
    private List<JobsEntity> jobsEntityList;

    public DronesEntity() {
    }

    public DronesEntity(Integer droneId) {
        this.droneId = droneId;
    }

    public DronesEntity(Integer droneId, String droneName, BigDecimal payloadCapacity) {
        this.droneId = droneId;
        this.droneName = droneName;
        this.payloadCapacity = payloadCapacity;
    }

    public Integer getDroneId() {
        return droneId;
    }

    public void setDroneId(Integer droneId) {
        this.droneId = droneId;
    }

    public String getDroneName() {
        return droneName;
    }

    public void setDroneName(String droneName) {
        this.droneName = droneName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPayloadCapacity() {
        return payloadCapacity;
    }

    public void setPayloadCapacity(BigDecimal payloadCapacity) {
        this.payloadCapacity = payloadCapacity;
    }

    public BigDecimal getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(BigDecimal currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public BigDecimal getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(BigDecimal currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public BigDecimal getHomeLatitude() {
        return homeLatitude;
    }

    public void setHomeLatitude(BigDecimal homeLatitude) {
        this.homeLatitude = homeLatitude;
    }

    public BigDecimal getHomeLongitude() {
        return homeLongitude;
    }

    public void setHomeLongitude(BigDecimal homeLongitude) {
        this.homeLongitude = homeLongitude;
    }

    @XmlTransient
    public List<JobsEntity> getJobsEntityList() {
        return jobsEntityList;
    }

    public void setJobsEntityList(List<JobsEntity> jobsEntityList) {
        this.jobsEntityList = jobsEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (droneId != null ? droneId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DronesEntity)) {
            return false;
        }
        DronesEntity other = (DronesEntity) object;
        if ((this.droneId == null && other.droneId != null) || (this.droneId != null && !this.droneId.equals(other.droneId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.cropsheild.Entity.DronesEntity[ droneId=" + droneId + " ]";
    }
    
}
