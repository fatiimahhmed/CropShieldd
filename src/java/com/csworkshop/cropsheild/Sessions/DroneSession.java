/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.DroneandLandExceptions.DroneListEmptyException;
import com.csworkshop.cropsheild.DroneandLandExceptions.DroneNameNullException;
import com.csworkshop.cropsheild.DroneandLandExceptions.DroneNotFoundException;
import com.csworkshop.cropsheild.DroneandLandExceptions.PayloadNullException;
import com.csworkshop.cropsheild.Entity.DronesEntity;
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
public class DroneSession implements DroneSessionRemote {

    @PersistenceContext(unitName = "CropSheildPU")
    private EntityManager em;

    @Override
    public void addDrone(String droneName, String status, BigDecimal payloadCapacity, BigDecimal currentLatitude,
            BigDecimal currentLongitude, BigDecimal homeLatitude, BigDecimal homeLongitude)
            throws DroneNameNullException, PayloadNullException {

        // Validate inputs
        if (droneName == null || droneName.isEmpty()) {
            throw new DroneNameNullException("Drone name cannot be null or empty.");
        }
        if (0 > payloadCapacity.compareTo(BigDecimal.ZERO)) {
            throw new PayloadNullException("Payload capacity must be greater than zero.");
        }

        // Create a new Drones entity
        DronesEntity drone = new DronesEntity();
        drone.setDroneName(droneName);
        drone.setStatus(status != null ? status : "Idle");
        drone.setPayloadCapacity(payloadCapacity);
        drone.setCurrentLatitude(currentLatitude);
        drone.setCurrentLongitude(currentLongitude);
        drone.setHomeLatitude(homeLatitude);
        drone.setHomeLongitude(homeLongitude);

        // Persist the drone
        em.persist(drone);
    }

    @Override
    public List<DronesEntity> getDronesByName(String droneName) throws DroneNotFoundException, DroneNameNullException {
        if (droneName == null || droneName.isEmpty()) {
            throw new DroneNameNullException("Drone name cannot be null or empty.");
        }

        TypedQuery<DronesEntity> query = em.createNamedQuery(
                "DronesEntity.findByDroneName", DronesEntity.class);
        query.setParameter("droneName", droneName);

        List<DronesEntity> drones = query.getResultList();

        if (drones.isEmpty()) {
            throw new DroneNotFoundException("No drones found with name " + droneName);
        }

        return drones;
    }

    @Override
    public DronesEntity getDroneById(int droneId) throws DroneNotFoundException {
        DronesEntity drone = em.find(DronesEntity.class, droneId);
        if (drone == null) {
            throw new DroneNotFoundException("Drone with ID " + droneId + " not found.");
        }
        return drone;
    }

    @Override
    public List<DronesEntity> getAllDrones() throws DroneListEmptyException {
        TypedQuery<DronesEntity> query = em.createNamedQuery("DronesEntity.findAll", DronesEntity.class);
        List<DronesEntity> dronesList = query.getResultList();
        System.out.println("Fetched drones: " + dronesList.size());
        if (dronesList.isEmpty()) {
            throw new DroneListEmptyException("Drone list is empty.");
        }
        return dronesList;
    }

    @Override
    public void deleteDrone(int droneId) throws DroneNotFoundException {
        // Find the drone
        DronesEntity drone = em.find(DronesEntity.class, droneId);
        if (drone == null) {
            throw new DroneNotFoundException("Drone with ID " + droneId + " not found.");
        }

        // Remove the drone
        em.remove(drone);
    }

    @Override
    public void updateDrone(int droneId, String droneName, String status, BigDecimal payloadCapacity,
            BigDecimal currentLatitude, BigDecimal currentLongitude,
            BigDecimal homeLatitude, BigDecimal homeLongitude)
            throws DroneNotFoundException {
        // Find the drone
        DronesEntity drone = em.find(DronesEntity.class, droneId);
        if (drone == null) {
            throw new DroneNotFoundException("Drone with ID " + droneId + " not found.");
        }

        // Update drone details
        if (droneName != null && !droneName.isEmpty()) {
            drone.setDroneName(droneName);
        }
        if (status != null && !status.isEmpty()) {
            drone.setStatus(status);
        }
        if (payloadCapacity != null && payloadCapacity.compareTo(BigDecimal.ZERO) > 0) {
            drone.setPayloadCapacity(payloadCapacity);
        }
        if (currentLatitude != null) {
            drone.setCurrentLatitude(currentLatitude);
        }
        if (currentLongitude != null) {
            drone.setCurrentLongitude(currentLongitude);
        }
        if (homeLatitude != null) {
            drone.setHomeLatitude(homeLatitude);
        }
        if (homeLongitude != null) {
            drone.setHomeLongitude(homeLongitude);
        }

        // Persist changes
        em.merge(drone);
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
