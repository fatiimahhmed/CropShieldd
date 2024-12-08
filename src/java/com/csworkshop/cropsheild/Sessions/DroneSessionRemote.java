/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.DroneandLandExceptions.DroneListEmptyException;
import com.csworkshop.cropsheild.DroneandLandExceptions.DroneNameNullException;
import com.csworkshop.cropsheild.DroneandLandExceptions.DroneNotFoundException;
import com.csworkshop.cropsheild.DroneandLandExceptions.PayloadNullException;
import com.csworkshop.cropsheild.Entity.DronesEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface DroneSessionRemote {

    public void addDrone(String droneName, String status, BigDecimal payloadCapacity, BigDecimal currentLatitude, BigDecimal currentLongitude, BigDecimal homeLatitude, BigDecimal homeLongitude) throws DroneNameNullException, PayloadNullException;

    public DronesEntity getDroneById(int droneId) throws DroneNotFoundException;

    public List<DronesEntity> getAllDrones() throws DroneListEmptyException;

    public void deleteDrone(int droneId) throws DroneNotFoundException;

    public void updateDrone(int droneId, String droneName, String status, BigDecimal payloadCapacity, BigDecimal currentLatitude, BigDecimal currentLongitude, BigDecimal homeLatitude, BigDecimal homeLongitude) throws DroneNotFoundException;

    public List<DronesEntity> getDronesByName(String droneName) throws DroneNotFoundException, DroneNameNullException;
    
}
