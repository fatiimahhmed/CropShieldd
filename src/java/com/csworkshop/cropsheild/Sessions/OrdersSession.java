/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.CustomersExceptions.CustomerNotFoundException;
import com.csworkshop.cropsheild.Entity.CustomersEntity;
import com.csworkshop.cropsheild.Entity.LandDetailsEntity;
import com.csworkshop.cropsheild.Entity.OrderStatusEntity;
import com.csworkshop.cropsheild.Entity.OrdersEntity;
import com.csworkshop.cropsheild.OrdersExceptions.LandNotFoundException;
import com.csworkshop.cropsheild.OrdersExceptions.OrderDateNullException;
import com.csworkshop.cropsheild.OrdersExceptions.OrderListEmptyException;
import com.csworkshop.cropsheild.OrdersExceptions.OrderNotFoundException;
import com.csworkshop.cropsheild.OrdersExceptions.OrderStatusNotFoundException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author syeda
 */
@Stateless
public class OrdersSession implements OrdersSessionRemote {

    @PersistenceContext(unitName = "CropSheildPU")
    private EntityManager em;

    @Override
    public void addOrder(int customerId, int landId, int statusId, Date requestedDate)
            throws OrderDateNullException, CustomerNotFoundException, LandNotFoundException, OrderStatusNotFoundException {
        // Validate inputs
        if (requestedDate == null) {
            throw new OrderDateNullException("Requested Date cannot be null.");
        }

        // Find the related entities
        CustomersEntity customer = em.find(CustomersEntity.class, customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
        }

        LandDetailsEntity land = em.find(LandDetailsEntity.class, landId);
        if (land == null) {
            throw new LandNotFoundException("Land with ID " + landId + " not found.");
        }

        OrderStatusEntity status = em.find(OrderStatusEntity.class, statusId);
        if (status == null) {
            throw new OrderStatusNotFoundException("Order Status with ID " + statusId + " not found.");
        }

        // Create a new Orders entity
        OrdersEntity order = new OrdersEntity();
        order.setCustomerId(customer);
        order.setLandId(land);
        order.setStatusId(status);
        order.setRequestedDate(requestedDate);

        // Persist the order
        em.persist(order);
    }

    @Override
    public List<OrdersEntity> getOrdersByLandId(int landId) throws OrderListEmptyException {
        TypedQuery<OrdersEntity> query = em.createQuery(
                "SELECT o FROM OrdersEntity o WHERE o.landId.landId = :landId", OrdersEntity.class);
        query.setParameter("landId", landId);

        List<OrdersEntity> orderList = query.getResultList();
        if (orderList.isEmpty()) {
            throw new OrderListEmptyException("No orders found for land ID " + landId + ".");
        }
        return orderList;
    }

    @Override
    public List<OrdersEntity> getOrdersByCustomerId(int customerId) throws OrderListEmptyException {
        TypedQuery<OrdersEntity> query = em.createQuery(
                "SELECT o FROM OrdersEntity o WHERE o.customerId.customerId = :customerId", OrdersEntity.class);
        query.setParameter("customerId", customerId);

        List<OrdersEntity> orderList = query.getResultList();
        if (orderList.isEmpty()) {
            throw new OrderListEmptyException("No orders found for customer ID " + customerId + ".");
        }
        return orderList;
    }

    @Override
    public OrdersEntity getOrderById(int orderId) throws OrderNotFoundException {
        OrdersEntity order = em.find(OrdersEntity.class, orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found.");
        }
        return order;
    }

    @Override
    public List<OrdersEntity> getAllOrders() throws OrderListEmptyException {
        Query query = em.createNamedQuery("OrdersEntity.findAll", OrdersEntity.class);
        List<OrdersEntity> orderList = query.getResultList();
        if (orderList.isEmpty()) {
            throw new OrderListEmptyException("Order list is empty.");
        }
        return orderList;
    }

    @Override
    public void updateOrder(int orderId, int statusId, Date requestedDate, int landId, int CustomerId) throws OrderNotFoundException {
        // Find the order by ID
        OrdersEntity order = em.find(OrdersEntity.class, orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found.");
        }

        // Update order details
        if (requestedDate != null) {
            order.setRequestedDate(requestedDate);
        }

        LandDetailsEntity land = em.find(LandDetailsEntity.class, landId);
        if (land != null) {
            order.setLandId(land);
        }

        OrderStatusEntity status = em.find(OrderStatusEntity.class, statusId);
        if (status != null) {
            order.setStatusId(status);
        }

        CustomersEntity customer = em.find(CustomersEntity.class, CustomerId);
        if (customer != null) {
            order.setCustomerId(customer);
        }
        // Persist changes
        em.merge(order);
    }

    @Override
    public void deleteOrder(int orderId) throws OrderNotFoundException {
        // Find the order by ID
        OrdersEntity order = em.find(OrdersEntity.class, orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found.");
        }

        // Remove the order
        em.remove(order);
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
