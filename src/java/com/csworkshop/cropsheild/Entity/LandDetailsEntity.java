/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csworkshop.cropsheild.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author syeda
 */
@Entity
@Table(name = "land_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LandDetailsEntity.findAll", query = "SELECT l FROM LandDetailsEntity l"),
    @NamedQuery(name = "LandDetailsEntity.findByLandId", query = "SELECT l FROM LandDetailsEntity l WHERE l.landId = :landId"),
    @NamedQuery(name = "LandDetailsEntity.findByTopLeftLatitude", query = "SELECT l FROM LandDetailsEntity l WHERE l.topLeftLatitude = :topLeftLatitude"),
    @NamedQuery(name = "LandDetailsEntity.findByTopLeftLongitude", query = "SELECT l FROM LandDetailsEntity l WHERE l.topLeftLongitude = :topLeftLongitude"),
    @NamedQuery(name = "LandDetailsEntity.findByBottomRightLatitude", query = "SELECT l FROM LandDetailsEntity l WHERE l.bottomRightLatitude = :bottomRightLatitude"),
    @NamedQuery(name = "LandDetailsEntity.findByBottomRightLongitude", query = "SELECT l FROM LandDetailsEntity l WHERE l.bottomRightLongitude = :bottomRightLongitude"),
    @NamedQuery(name = "LandDetailsEntity.findByPlaceName", query = "SELECT l FROM LandDetailsEntity l WHERE l.placeName = :placeName"),
    @NamedQuery(name = "LandDetailsEntity.findByLandSize", query = "SELECT l FROM LandDetailsEntity l WHERE l.landSize = :landSize")})
public class LandDetailsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "land_id", nullable = false)
    private Integer landId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "top_left_latitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal topLeftLatitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "top_left_longitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal topLeftLongitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bottom_right_latitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal bottomRightLatitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bottom_right_longitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal bottomRightLongitude;
    @Size(max = 100)
    @Column(name = "place_name", length = 100)
    private String placeName;
    @Column(name = "land_size", precision = 10, scale = 2)
    private BigDecimal landSize;
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    @ManyToOne(optional = false)
    private CustomersEntity customerId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "landId")
    private List<OrdersEntity> ordersEntityList;

    public LandDetailsEntity() {
    }

    public LandDetailsEntity(Integer landId) {
        this.landId = landId;
    }

    public LandDetailsEntity(Integer landId, BigDecimal topLeftLatitude, BigDecimal topLeftLongitude, BigDecimal bottomRightLatitude, BigDecimal bottomRightLongitude) {
        this.landId = landId;
        this.topLeftLatitude = topLeftLatitude;
        this.topLeftLongitude = topLeftLongitude;
        this.bottomRightLatitude = bottomRightLatitude;
        this.bottomRightLongitude = bottomRightLongitude;
    }

    public Integer getLandId() {
        return landId;
    }

    public void setLandId(Integer landId) {
        this.landId = landId;
    }

    public BigDecimal getTopLeftLatitude() {
        return topLeftLatitude;
    }

    public void setTopLeftLatitude(BigDecimal topLeftLatitude) {
        this.topLeftLatitude = topLeftLatitude;
    }

    public BigDecimal getTopLeftLongitude() {
        return topLeftLongitude;
    }

    public void setTopLeftLongitude(BigDecimal topLeftLongitude) {
        this.topLeftLongitude = topLeftLongitude;
    }

    public BigDecimal getBottomRightLatitude() {
        return bottomRightLatitude;
    }

    public void setBottomRightLatitude(BigDecimal bottomRightLatitude) {
        this.bottomRightLatitude = bottomRightLatitude;
    }

    public BigDecimal getBottomRightLongitude() {
        return bottomRightLongitude;
    }

    public void setBottomRightLongitude(BigDecimal bottomRightLongitude) {
        this.bottomRightLongitude = bottomRightLongitude;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public BigDecimal getLandSize() {
        return landSize;
    }

    public void setLandSize(BigDecimal landSize) {
        this.landSize = landSize;
    }

    public CustomersEntity getCustomerId() {
        return customerId;
    }

    public void setCustomerId(CustomersEntity customerId) {
        this.customerId = customerId;
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
        hash += (landId != null ? landId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LandDetailsEntity)) {
            return false;
        }
        LandDetailsEntity other = (LandDetailsEntity) object;
        if ((this.landId == null && other.landId != null) || (this.landId != null && !this.landId.equals(other.landId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.cropsheild.Entity.LandDetailsEntity[ landId=" + landId + " ]";
    }
    
}
