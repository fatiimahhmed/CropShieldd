/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.CustomersExceptions.CustomerNotFoundException;
import com.csworkshop.cropsheild.DroneandLandExceptions.CustomerIdNullException;
import com.csworkshop.cropsheild.DroneandLandExceptions.LandDetailsListEmptyException;
import com.csworkshop.cropsheild.DroneandLandExceptions.LandDetailsNotFoundException;
import com.csworkshop.cropsheild.DroneandLandExceptions.LandsizeNullException;
import com.csworkshop.cropsheild.DroneandLandExceptions.PlaceNameNullException;
import com.csworkshop.cropsheild.Entity.LandDetailsEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface LandDetailsSessionRemote {

    public LandDetailsEntity getLandById(int landId) throws LandDetailsNotFoundException;

    public List<LandDetailsEntity> getAllLandDetails() throws LandDetailsListEmptyException;

    public List<LandDetailsEntity> getLandByCustomerId(int CustomerId) throws LandDetailsListEmptyException;

    public void deleteLandDetails(int landId) throws LandDetailsNotFoundException;

    public void updateLandDetails(int landId, BigDecimal topLeftLatitude, BigDecimal topLeftLongitude, BigDecimal bottomRightLatitude, BigDecimal bottomRightLongitude, int CustomerId, String placeName, BigDecimal landSize) throws LandDetailsNotFoundException, PlaceNameNullException, LandsizeNullException, CustomerIdNullException, CustomerNotFoundException;

    public void addLandDetails(int CustomerId, BigDecimal topLeftLatitude, BigDecimal topLeftLongitude, BigDecimal bottomRightLatitude, BigDecimal bottomRightLongitude, String placeName, BigDecimal landSize) throws IllegalArgumentException, CustomerIdNullException, PlaceNameNullException, LandsizeNullException;
    
}
