package org.example.demo;

import org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

@Component
public class Test extends DefaultBeanDefinitionDocumentReader {
    @Override
    protected void preProcessXml(Element root) {
        super.preProcessXml(root);
        System.out.println("前置");
    }

    @Override
    protected void postProcessXml(Element root) {
        super.postProcessXml(root);
        System.out.println("后置");
    }
}
