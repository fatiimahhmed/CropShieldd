/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.CustomersExceptions.CustomerNotFoundException;
import com.csworkshop.cropsheild.DroneandLandExceptions.CustomerIdNullException;
import com.csworkshop.cropsheild.DroneandLandExceptions.LandDetailsListEmptyException;
import com.csworkshop.cropsheild.DroneandLandExceptions.LandDetailsNotFoundException;
import com.csworkshop.cropsheild.DroneandLandExceptions.LandsizeNullException;
import com.csworkshop.cropsheild.DroneandLandExceptions.PlaceNameNullException;
import com.csworkshop.cropsheild.Entity.CustomersEntity;
import com.csworkshop.cropsheild.Entity.LandDetailsEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author syeda
 */
@Stateless
public class LandDetailsSession implements LandDetailsSessionRemote {

    @PersistenceContext(unitName = "CropSheildPU")
    private EntityManager em;

    @Override
    public void addLandDetails(int CustomerId, BigDecimal topLeftLatitude, BigDecimal topLeftLongitude,
            BigDecimal bottomRightLatitude, BigDecimal bottomRightLongitude,
            String placeName, BigDecimal landSize) throws IllegalArgumentException, CustomerIdNullException, PlaceNameNullException, LandsizeNullException {
        // Validate input
        if (topLeftLatitude == null || topLeftLongitude == null || bottomRightLatitude == null || bottomRightLongitude == null) {
            throw new IllegalArgumentException("Coordinates cannot be null.");
        }
        if (landSize != null && landSize.compareTo(BigDecimal.ZERO) <= 0) {
            throw new LandsizeNullException("Land size must be greater than zero.");
        }
        if (placeName.isEmpty() || placeName == null) {
            throw new PlaceNameNullException("Please Enter Place Name First...!");
        }
        if (CustomerId == 0) {
            throw new CustomerIdNullException("Customer Id Cannot be zero.");
        }
        CustomersEntity customer;
        customer = em.find(CustomersEntity.class, CustomerId);
        if (customer == null) {
            throw new CustomerIdNullException("No Customer Found With this Id : " + CustomerId);
        }
        // Create a new LandDetails entity
        LandDetailsEntity land = new LandDetailsEntity();
        land.setCustomerId(customer);
        land.setTopLeftLatitude(topLeftLatitude);
        land.setTopLeftLongitude(topLeftLongitude);
        land.setBottomRightLatitude(bottomRightLatitude);
        land.setBottomRightLongitude(bottomRightLongitude);
        land.setPlaceName(placeName);
        land.setLandSize(landSize);

        // Persist the land details
        em.persist(land);
    }

    @Override
    public LandDetailsEntity getLandById(int landId) throws LandDetailsNotFoundException {
        LandDetailsEntity land = em.find(LandDetailsEntity.class, landId);
        if (land == null) {
            throw new LandDetailsNotFoundException("Land with ID " + landId + " not found.");
        }
        return land;
    }

    @Override
    public List<LandDetailsEntity> getAllLandDetails() throws LandDetailsListEmptyException {
        TypedQuery<LandDetailsEntity> query = em.createNamedQuery("LandDetailsEntity.findAll", LandDetailsEntity.class);
        List<LandDetailsEntity> landList = query.getResultList();
        if (landList.isEmpty()) {
            throw new LandDetailsListEmptyException("No land details found.");
        }
        return landList;
    }

    @Override
    public List<LandDetailsEntity> getLandByCustomerId(int CustomerId) throws LandDetailsListEmptyException {
        TypedQuery<LandDetailsEntity> query = em.createQuery(
                "SELECT l FROM LandDetailsEntity l WHERE l.customerId = :customerId", LandDetailsEntity.class);
        query.setParameter("customerId", CustomerId);

        List<LandDetailsEntity> landList = query.getResultList();
        if (landList.isEmpty()) {
            throw new LandDetailsListEmptyException("No land details found for Customer ID " + CustomerId + ".");
        }
        return landList;
    }

    @Override
    public void deleteLandDetails(int landId) throws LandDetailsNotFoundException {
        LandDetailsEntity land = em.find(LandDetailsEntity.class, landId);
        if (land == null) {
            throw new LandDetailsNotFoundException("Land with ID " + landId + " not found.");
        }
        em.remove(land);
    }

    @Override
    public void updateLandDetails(int landId, BigDecimal topLeftLatitude, BigDecimal topLeftLongitude,
            BigDecimal bottomRightLatitude, BigDecimal bottomRightLongitude, int CustomerId,
            String placeName, BigDecimal landSize) throws LandDetailsNotFoundException, PlaceNameNullException, LandsizeNullException, CustomerIdNullException, CustomerNotFoundException {
        // Find the land details
        LandDetailsEntity land = em.find(LandDetailsEntity.class, landId);
        if (land == null) {
            throw new LandDetailsNotFoundException("Land with ID " + landId + " not found.");
        }

        // Update land details
        if (topLeftLatitude != null) {
            land.setTopLeftLatitude(topLeftLatitude);
        }
        if (topLeftLongitude != null) {
            land.setTopLeftLongitude(topLeftLongitude);
        }
        if (bottomRightLatitude != null) {
            land.setBottomRightLatitude(bottomRightLatitude);
        }
        if (bottomRightLongitude != null) {
            land.setBottomRightLongitude(bottomRightLongitude);
        }

        if (CustomerId <= 0) {
            throw new CustomerIdNullException("Customer Id cannot be Null...");
        }
        CustomersEntity customer;
        
        customer = em.find(CustomersEntity.class, CustomerId);
        if (customer == null) {
            throw new CustomerNotFoundException("No Customer Found With this Id : " + CustomerId);
        }
        else
        {
            land.setCustomerId(customer);
        }
   
        if (placeName != null && !placeName.isEmpty()) {
            land.setPlaceName(placeName);
        } else {
            throw new PlaceNameNullException("Please Enter Land Name first ... !");
        }
        if (landSize != null && landSize.compareTo(BigDecimal.ZERO) > 0) {
            land.setLandSize(landSize);
        } else {
            throw new LandsizeNullException("Land Size Cannot Be zero ...!");
        }

        // Persist changes
        em.merge(land);
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
