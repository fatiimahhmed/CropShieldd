/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.cropsheild.Sessions;

import com.csworkshop.cropsheild.Entity.OrderStatusEntity;
import com.csworkshop.cropsheild.OrdersExceptions.OrderStatusListEmptyException;
import com.csworkshop.cropsheild.OrdersExceptions.OrderStatusNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface OrdersStatusSessionRemote {

    public void addOrderStatus(String statusName, String description);

    public OrderStatusEntity getOrderStatusById(int statusId) throws OrderStatusNotFoundException;

    public List<OrderStatusEntity> getAllOrderStatuses() throws OrderStatusListEmptyException;

    public void updateOrderStatus(int statusId, String statusName, String description) throws OrderStatusNotFoundException;

    public void deleteOrderStatus(int statusId) throws OrderStatusNotFoundException;
    
}
