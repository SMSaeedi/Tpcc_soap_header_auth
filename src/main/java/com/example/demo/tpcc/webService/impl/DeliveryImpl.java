package com.example.demo.tpcc.webService.impl;

import com.example.demo.tpcc.model.DeliveryInputParams;
import com.example.demo.tpcc.model.DeliveryResult;
import com.example.demo.tpcc.model.DeliveryStatement;
import com.example.demo.tpcc.webService.Delivery;

import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

@WebService(serviceName = "Delivery")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class DeliveryImpl implements Delivery {

    @Override
    public DeliveryResult deliveryTransaction(DeliveryInputParams input) {
        return new DeliveryStatement().deliveryTransaction(input);
    }

    @Override
    public DeliveryResult deliveryTransactionHardCode(DeliveryInputParams input) throws Exception {
        return new DeliveryStatement().deliveryTransactionHardCode(input);
    }
}