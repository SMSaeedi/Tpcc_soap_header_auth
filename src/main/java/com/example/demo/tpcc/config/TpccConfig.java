package com.example.demo.tpcc.config;

import com.example.demo.tpcc.webService.*;
import com.example.demo.tpcc.webService.impl.*;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Configuration
public class TpccConfig implements BeanFactoryPostProcessor {

    @Value("${cxf.path}")
    protected String ws;

    @GetMapping("/")
    public RedirectView hello(HttpServletRequest request) {
        return new RedirectView(request.getContextPath() + ws);
    }

    @Autowired
    private Bus bus;

    @Bean
    public Delivery changePayment() {
        return new DeliveryImpl();
    }

    @Bean
    public NewOrder newOrder() {
        return new NewOrderImpl();
    }

    @Bean
    public OrderStatus orderStatus() {
        return new OrderStatusImpl();
    }

    @Bean
    public Payment payment() {
        return new PaymentImpl() {
        };
    }

    @Bean
    public StockLevel stockLevel() {
        return new StockLevelImpl() {
        };
    }

  /*  @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, stockLevelService());
        endpoint.publish("/TpcC");
        return endpoint;
    }*/

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        Arrays.stream(new String[]{"stockLevel", "payment", "newOrder", "changePayment", "orderStatus"}).forEach(str -> {
            bus = factory.getBean(Bus.class);
            JaxWsServerFactoryBean bean = new JaxWsServerFactoryBean();
            bean.setAddress("/" + str + "/TpcC");
            bean.setBus(bus);
            bean.setServiceClass(TpccConfig.class);
            factory.registerSingleton(str, bean.create());
        });
    }

}