package com.example.demo.tpcc.webService;
import com.example.demo.tpcc.model.ShortOrder;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://impl.webService.newOrder.demo.example.com/")
public interface NewOrder {

    @WebMethod(operationName = "newOrderTransaction")
    public ShortOrder newOrderTransaction(@WebParam(name = "w_id") String w_id, @WebParam(name = "d_id") String d_id, @WebParam(name = "c_id") String c_id);
}