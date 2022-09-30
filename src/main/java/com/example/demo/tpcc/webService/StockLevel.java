package com.example.demo.tpcc.webService;

import com.example.demo.tpcc.model.PaymentResult;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://impl.webService.stockLevel.demo.example.com/")
public interface StockLevel {

    @WebMethod(operationName = "stockLevelTransaction")
    public long stockLevelTransaction(@WebParam(name = "input") PaymentResult input) throws Exception;

    @WebMethod(operationName = "stockLevelTransactionTest")
    public long stockLevelTransactionTest(@WebParam(name = "input") PaymentResult input, @WebParam(name = "threshold") long threshold) throws Exception;
}