package com.sky.vdk.vlx.generator.nodedata

import com.sky.vdk.vlx.generator.nodecfg.NodeConfig
import org.dom4j.Element

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-11-6
 * Time: 下午6:13
 */
abstract class NodeBean {
    def properties = [:];
    NodeConfig config;
    def typeName;

    void putProperties(Map<String, String> dataItem) {
        def configProperties = config.getProperties();
        for (String prop : configProperties) {
//            if (dataItem[prop] != null) {
                properties[prop] = dataItem[prop]
//            }
        }
    }

    abstract Element buildElement(Element parentNode);

    protected void putProperties(Element parent) {
        Element propertiesNode = parent.addElement("properties");
        properties.each { def property ->
            if(property.getValue()==null){
                return
            }
            Element propertyNode = propertiesNode.addElement(property.getKey() as String);
            propertyNode.addText(property.getValue() as String);
        }
    }

    protected void addAttributes(Element node, def attributes) {
        attributes.each { def entry ->
            node.addAttribute(entry.getKey() as String, entry.getValue() as String);
        }
    }

    abstract String getNodeId();

    String getIdentityProperty() {
        return properties["identityProperty"];
    }
}
