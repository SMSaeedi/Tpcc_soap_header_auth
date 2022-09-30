package com.example.demo.tpcc.webService;

import com.example.demo.tpcc.model.PaymentModel;
import com.example.demo.tpcc.model.PaymentResult;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://impl.webService.demo.example.com/")
public interface Payment {

    @WebMethod(operationName = "paymentTransaction")
    public PaymentResult paymentTransaction(PaymentModel input);

    @WebMethod(operationName = "paymentTransactionTest")
    public PaymentResult paymentTransactionTest();

}