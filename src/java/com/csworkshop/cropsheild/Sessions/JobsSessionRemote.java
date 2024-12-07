/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.AdminsExceptions.AdminNotFoundException;
import com.csworkshop.cropsheild.CommonExceptions.InvalidJobStatusException;
import com.csworkshop.cropsheild.CommonExceptions.JobNotFoundException;
import com.csworkshop.cropsheild.CommonExceptions.JobStatusEmptyException;
import com.csworkshop.cropsheild.CommonExceptions.JobsFetchException;
import com.csworkshop.cropsheild.DroneandLandExceptions.DroneNotFoundException;
import com.csworkshop.cropsheild.Entity.JobsEntity;
import com.csworkshop.cropsheild.OrdersExceptions.OrderNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface JobsSessionRemote {

    public void addJob(int orderId, int employeeId, int droneId, String jobStatus) throws JobStatusEmptyException, DroneNotFoundException, AdminNotFoundException, OrderNotFoundException;

    public JobsEntity getJobById(int jobId) throws JobNotFoundException;

    public List<JobsEntity> getAllJobs() throws JobsFetchException;

    public void updateJobStatus(int jobId, String jobStatus) throws JobNotFoundException;

    public void assignDroneToJob(int jobId, int droneId) throws DroneNotFoundException;

    public List<JobsEntity> getJobsByStatus(String jobStatus) throws InvalidJobStatusException, JobsFetchException;

    public void deleteJob(int jobId) throws JobNotFoundException;
    
}
