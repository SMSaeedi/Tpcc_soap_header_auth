package com.example.demo.tpcc.webService;

import com.example.demo.tpcc.model.DeliveryInputParams;
import com.example.demo.tpcc.model.DeliveryResult;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://impl.webService.delivery.demo.example.com/")
public interface Delivery {

    @WebMethod(operationName = "deliveryTransaction")
    public DeliveryResult deliveryTransaction(@WebParam(name = "inpu") DeliveryInputParams inpu) throws Exception;

    @WebMethod(operationName = "deliveryTransactionHardCode")
    public DeliveryResult deliveryTransactionHardCode(@WebParam(name = "input") DeliveryInputParams input) throws Exception;
}