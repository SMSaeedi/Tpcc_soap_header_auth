package com.example.demo.soap;

import com.example.demo.tpcc.model.DeliveryInputParams;
import com.example.demo.tpcc.model.DistrictDelivery;
import com.example.demo.tpcc.webService.Delivery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeliverySoapTest {

    @Test
    public void testSoap() {
        DistrictDelivery districtDelivery = new DistrictDelivery();
        DeliveryInputParams inputParams = new DeliveryInputParams();
        List<DistrictDelivery> list = new ArrayList<>();

        districtDelivery.setDistrict(10);
        districtDelivery.setOrderid(10L);
        list.add(districtDelivery);

        try {
            URL url = null;
            try {
                url = new URL("http://localhost:8889/ws/Delivery?wsdl");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            QName qname = new QName("http://impl.webService.delivery.demo.example.com/", "Delivery");
            Service service = Service.create(url, qname);
            Delivery factory = service.getPort(Delivery.class);

            // ******************Authentication*****************************
            Map<String, Object> req_ctx = ((BindingProvider) factory).getRequestContext();
            req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);

            Map<String, List<String>> headers = new HashMap<String, List<String>>();
            headers.put("Username", Collections.singletonList("msaeedi"));
            headers.put("Password", Collections.singletonList("30ms1370"));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

            factory.deliveryTransaction(inputParams);
        } catch (Exception e) {
            System.out.println("fail");
            e.printStackTrace();
        }
    }
}