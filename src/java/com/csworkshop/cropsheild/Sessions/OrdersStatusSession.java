/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.Entity.OrderStatusEntity;
import com.csworkshop.cropsheild.OrdersExceptions.OrderStatusListEmptyException;
import com.csworkshop.cropsheild.OrdersExceptions.OrderStatusNotFoundException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author syeda
 */
@Stateless
public class OrdersStatusSession implements OrdersStatusSessionRemote {

    @PersistenceContext(unitName = "CropSheildPU")
    private EntityManager em;

    @Override
    public void addOrderStatus(String statusName, String description) {
        // Validate inputs
        if (statusName == null || statusName.isEmpty()) {
            throw new IllegalArgumentException("Status Name cannot be null or empty!");
        }

        // Create a new OrderStatus entity
        OrderStatusEntity status = new OrderStatusEntity();
        status.setStatusName(statusName);
        status.setDescription(description);

        // Persist the status
        em.persist(status);
    }

    @Override
    public OrderStatusEntity getOrderStatusById(int statusId) throws OrderStatusNotFoundException {
        OrderStatusEntity status = em.find(OrderStatusEntity.class, statusId);
        if (status == null) {
            throw new OrderStatusNotFoundException("Order Status with ID " + statusId + " not found.");
        }
        return status;
    }

    @Override
    public List<OrderStatusEntity> getAllOrderStatuses() throws OrderStatusListEmptyException {
        Query query = em.createNamedQuery("OrderStatusEntity.findAll", OrderStatusEntity.class);
        List<OrderStatusEntity> statusList = query.getResultList();
        if (statusList.isEmpty()) {
            throw new OrderStatusListEmptyException("Order Status list is empty.");
        }
        return statusList;
    }

    @Override
    public void updateOrderStatus(int statusId, String statusName, String description) throws OrderStatusNotFoundException {
        // Find the status by ID
        OrderStatusEntity status = em.find(OrderStatusEntity.class, statusId);
        if (status == null) {
            throw new OrderStatusNotFoundException("Order Status with ID " + statusId + " not found.");
        }

        // Update the status details
        if (statusName != null && !statusName.isEmpty()) {
            status.setStatusName(statusName);
        }
        if (description != null) {
            status.setDescription(description);
        }

        // Persist changes
        em.merge(status);
    }

    @Override
    public void deleteOrderStatus(int statusId) throws OrderStatusNotFoundException {
        // Find the status by ID
        OrderStatusEntity status = em.find(OrderStatusEntity.class, statusId);
        if (status == null) {
            throw new OrderStatusNotFoundException("Order Status with ID " + statusId + " not found.");
        }

        // Remove the status
        em.remove(status);
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
