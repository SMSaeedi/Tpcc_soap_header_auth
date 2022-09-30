package com.example.demo.tpcc.webService.impl;


import com.example.demo.tpcc.model.PaymentModel;
import com.example.demo.tpcc.model.PaymentResult;
import com.example.demo.tpcc.model.PaymentStatemnt;
import com.example.demo.tpcc.webService.Payment;


import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

@WebService(serviceName = "paymentWebService")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class PaymentImpl implements Payment {

    @Override
    public PaymentResult paymentTransaction(PaymentModel input) {
        return new PaymentStatemnt().paymentTransaction(input);
    }

    @Override
    public PaymentResult paymentTransactionTest() {
        return new PaymentStatemnt().paymentTransactionTest();
    }
}
