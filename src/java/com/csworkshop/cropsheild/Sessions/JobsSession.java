/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.AdminsExceptions.AdminNotFoundException;
import com.csworkshop.cropsheild.CommonExceptions.InvalidJobStatusException;
import com.csworkshop.cropsheild.CommonExceptions.JobNotFoundException;
import com.csworkshop.cropsheild.CommonExceptions.JobStatusEmptyException;
import com.csworkshop.cropsheild.CommonExceptions.JobsFetchException;
import com.csworkshop.cropsheild.DroneandLandExceptions.DroneNotFoundException;
import com.csworkshop.cropsheild.Entity.DronesEntity;
import com.csworkshop.cropsheild.Entity.EmployeeEntity;
import com.csworkshop.cropsheild.Entity.JobsEntity;

import com.csworkshop.cropsheild.Entity.OrdersEntity;
import com.csworkshop.cropsheild.OrdersExceptions.OrderNotFoundException;
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
public class JobsSession implements JobsSessionRemote {

    @PersistenceContext(unitName = "CropSheildPU")
    private EntityManager em;
    private OrdersSessionRemote orderremote;
    private EmployeesSessionRemote employeeremote;
    private DroneSessionRemote dronesession;

    // 1. Add a new job
    @Override
    public void addJob(int orderId, int employeeId, int droneId, String jobStatus) throws JobStatusEmptyException, DroneNotFoundException, AdminNotFoundException, OrderNotFoundException {
        JobsEntity job = new JobsEntity();
        if (orderId == 0) {
            throw new OrderNotFoundException("Order Id Invalid try again...!");
        }
        if (employeeId == 0) {
            throw new AdminNotFoundException("Invlaid Employee Id");
        }
        if (droneId == 0) {
            throw new DroneNotFoundException("Invalid Drone Id");
        }
        if (jobStatus.isEmpty()) {
            throw new JobStatusEmptyException("Job Status Empty Please Select");
        }

        OrdersEntity order = orderremote.getOrderById(orderId);
        EmployeeEntity employee = employeeremote.getAdminById(employeeId);
        DronesEntity drone = dronesession.getDroneById(droneId);

        job.setDroneId(drone);
        job.setEmployeeId(employee);
        job.setOrderId(order);
        job.setJobStatus(jobStatus);
        em.persist(job);
    }

    // 2. Get a job by ID
    @Override
    public JobsEntity getJobById(int jobId) throws JobNotFoundException {
        JobsEntity job = em.find(JobsEntity.class, jobId);
        if (job == null) {
            throw new JobNotFoundException("Job with ID " + jobId + " not found.");
        }
        return job;
    }

    // 3. Get all jobs
    @Override
    public List<JobsEntity> getAllJobs() throws JobsFetchException {
        try {
            return em.createQuery("SELECT j FROM JobsEntity j", JobsEntity.class).getResultList();
        } catch (Exception e) {

            throw new JobsFetchException("Failed to fetch jobs from the database.");
        }
    }

    // 4. Update job status
    @Override
    public void updateJobStatus(int jobId, String jobStatus) throws JobNotFoundException {
        JobsEntity job = em.find(JobsEntity.class, jobId);
        if (job != null) {
            job.setJobStatus(jobStatus);
            em.merge(job);
        } else {
            throw new JobNotFoundException("Job with ID " + jobId + " not found.");
        }
    }

    // 5. Assign a drone to a job
    @Override
    public void assignDroneToJob(int jobId, int droneId) throws DroneNotFoundException {
        JobsEntity job = em.find(JobsEntity.class, jobId);
        if (job != null) {
            DronesEntity Drone = dronesession.getDroneById(droneId);
            job.setDroneId(Drone);
            em.merge(job);
        } else {
            throw new IllegalArgumentException("Job with ID " + jobId + " not found.");
        }
    }

    // 6. Get all jobs by status
    @Override
    public List<JobsEntity> getJobsByStatus(String jobStatus) throws InvalidJobStatusException, JobsFetchException {
        try {
            // Validate jobStatus
            if (jobStatus == null || jobStatus.isEmpty()) {
                throw new InvalidJobStatusException("Job status cannot be null or empty.");
            }

            // Execute the query
            TypedQuery<JobsEntity> query = em.createQuery(
                    "SELECT j FROM JobsEntity j WHERE j.jobStatus = :status", JobsEntity.class);
            query.setParameter("status", jobStatus);
            return query.getResultList();
        } catch (IllegalArgumentException e) {
            // Handle query or parameter errors
            throw new InvalidJobStatusException("Invalid job status: ");
        } catch (InvalidJobStatusException e) {
            // Handle general errors during query execution
            throw new JobsFetchException("Failed to fetch jobs with status: ");
        }
    }

    // 7. Delete a job
    @Override
    public void deleteJob(int jobId) throws JobNotFoundException {
        JobsEntity job = em.find(JobsEntity.class, jobId);
        if (job != null) {
            em.remove(job);
        } else {
            throw new JobNotFoundException("Job with ID " + jobId + " not found.");
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
