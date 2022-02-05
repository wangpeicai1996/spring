package org.example.demo;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;


@Component
public class MyFactoryBean implements FactoryBean<Order> {

    @Override
    public Order getObject() throws Exception {
        Order order = new Order();
        order.setOrderId(111);
        order.setUserName("测试MyFactoryBean返回对象");
        return order;
    }

    @Override
    public Class<?> getObjectType() {
        return Order.class;
    }
}
