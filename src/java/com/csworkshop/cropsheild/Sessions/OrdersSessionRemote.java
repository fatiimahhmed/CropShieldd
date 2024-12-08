/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.CustomersExceptions.CustomerNotFoundException;
import com.csworkshop.cropsheild.Entity.OrdersEntity;
import com.csworkshop.cropsheild.OrdersExceptions.LandNotFoundException;
import com.csworkshop.cropsheild.OrdersExceptions.OrderDateNullException;
import com.csworkshop.cropsheild.OrdersExceptions.OrderListEmptyException;
import com.csworkshop.cropsheild.OrdersExceptions.OrderNotFoundException;
import com.csworkshop.cropsheild.OrdersExceptions.OrderStatusNotFoundException;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface OrdersSessionRemote {

    public void addOrder(int customerId, int landId, int statusId, Date requestedDate) throws OrderDateNullException, CustomerNotFoundException, LandNotFoundException, OrderStatusNotFoundException;

    public OrdersEntity getOrderById(int orderId) throws OrderNotFoundException;

    public List<OrdersEntity> getAllOrders() throws OrderListEmptyException;

    public void updateOrder(int orderId, int statusId, Date requestedDate, int landId, int CustomerId) throws OrderNotFoundException;

    public void deleteOrder(int orderId) throws OrderNotFoundException;

    public List<OrdersEntity> getOrdersByLandId(int landId) throws OrderListEmptyException;

    public List<OrdersEntity> getOrdersByCustomerId(int customerId) throws OrderListEmptyException;
    
}
