package com.example.demo.tpcc.webService.impl;


import com.example.demo.tpcc.model.NewOrderStatement;
import com.example.demo.tpcc.model.ShortOrder;
import com.example.demo.tpcc.webService.NewOrder;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

@WebService(serviceName = "newOrderWebService")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class NewOrderImpl implements NewOrder {

    @Override
    public ShortOrder newOrderTransaction(String w_id, String d_id, String c_id) {
        return new NewOrderStatement().newOrderTransaction(w_id, d_id, c_id);
    }
}