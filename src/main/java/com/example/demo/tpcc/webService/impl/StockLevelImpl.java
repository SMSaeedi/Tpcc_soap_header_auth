package com.example.demo.tpcc.webService.impl;


import com.example.demo.tpcc.model.PaymentResult;
import com.example.demo.tpcc.model.StockLevelStatement;
import com.example.demo.tpcc.webService.StockLevel;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

@WebService(serviceName = "stockLevelWebService")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class StockLevelImpl implements StockLevel {

    @Override
    public long stockLevelTransaction(PaymentResult input) {
        return new StockLevelStatement().stockLevelTransaction(input);
    }

    @Override
    public long stockLevelTransactionTest(PaymentResult input, long threshold) {
        return new StockLevelStatement().stockLevelTransactionTest(input.getW_id(), input.getD_id(), threshold);
    }
}
