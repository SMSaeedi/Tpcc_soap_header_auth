package com.example.demo.tpcc.webService.impl;

import com.example.demo.tpcc.model.Order;
import com.example.demo.tpcc.model.OrderStatusStatement;
import com.example.demo.tpcc.webService.OrderStatus;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.sql.SQLException;

@WebService(serviceName = "orderStatusWebService")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class OrderStatusImpl implements OrderStatus {

    @Override
    public Order orderStatusTransaction(String w_id, String d_id, String c_id, String c_last, boolean c_by_name) throws SQLException {
        return new OrderStatusStatement().orderStatusTransaction(w_id, d_id, c_id, c_last, c_by_name);
    }
}