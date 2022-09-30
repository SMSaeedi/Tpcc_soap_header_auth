package com.example.demo.tpcc.webService;

import com.example.demo.tpcc.model.Order;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://impl.webService.demo.example.com/")
public interface OrderStatus {

    @WebMethod(operationName = "orderStatusTransaction")
    public Order orderStatusTransaction(@WebParam(name = "input") String w_id, String d_id, String c_id, String c_last, boolean c_by_name) throws Exception;
}