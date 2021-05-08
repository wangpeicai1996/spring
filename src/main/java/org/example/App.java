package org.example;

import org.example.demo.Cust;
import org.example.demo.TestBeanPostProcessor;
import org.example.demo.User;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.*;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.TypedValue;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        //手动注册bean到容器中
        System.out.println("Hello World!");
        AbstractRefreshableApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.getBeanFactory();
        factory.addBeanPostProcessor(new TestBeanPostProcessor());
        factory.setAllowBeanDefinitionOverriding(false);
        AbstractBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(User.class);
        beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        PropertyValue pv1 = new PropertyValue("name","李四");
        beanDefinition.getPropertyValues().addPropertyValue(pv1);
        beanDefinition.getPropertyValues().add("age",20);
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition,"user2");
        BeanDefinitionReaderUtils.registerBeanDefinition(holder,factory);
        AbstractBeanDefinition beanDefinition2 = new GenericBeanDefinition();
        beanDefinition2.setBeanClass(User.class);
        beanDefinition2.setScope(BeanDefinition.SCOPE_SINGLETON);
        PropertyValue pv2 = new PropertyValue("name","王五");
        beanDefinition2.getPropertyValues().addPropertyValue(pv2);
        beanDefinition2.getPropertyValues().add("age",20);
        factory.registerBeanDefinition("user3",beanDefinition2);
        BeanDefinition bd = factory.getBeanDefinition("user1");

        //通过beanDefinitionBuilder构建
        BeanDefinitionBuilder userBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        userBuilder.addPropertyValue("name",new TypedStringValue("王宝强"));
        userBuilder.addPropertyValue("age",40);
        BeanDefinition userDefinition = userBuilder.getBeanDefinition();
        BeanDefinitionBuilder custBuilder = BeanDefinitionBuilder.genericBeanDefinition(Cust.class);
        custBuilder.addPropertyValue("custId",new TypedStringValue("001"));
        custBuilder.addPropertyValue("user",userDefinition);
        BeanDefinition custDefinition = custBuilder.getBeanDefinition();
        String custBeanName = BeanDefinitionReaderUtils.generateBeanName(custDefinition,factory);
        System.out.println("生成的beanName="+custBeanName);
        BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(custDefinition,custBeanName),factory);


        User user1 = (User)context.getBean("user1");
        User user2 = (User)context.getBean("user2");
        User user3 = (User)context.getBean("user3");
        Cust cust = (Cust)context.getBean(Cust.class);
        System.out.println("xml配置的bean对象user1="+user1.getName());
        System.out.println("通过BeanDefinitionReaderUtils对象registerBeanDefinition="+user2.getName());
        System.out.println("通过DefaultListableBeanFactory对象registerBeanDefinition="+user3.getName());
        System.out.println("cust=="+cust.getCustId());
        User re = cust.getUser();
        System.out.println("Cust="+re.getName());
        String []  difinitions = context.getBeanDefinitionNames();
        for (String str : difinitions) {
            System.out.println("bean定义："+str);
        }

    }
}
