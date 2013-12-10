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
    Map<String, String> properties = [:];
    NodeConfig config;
    String typeName;

    void putProperties(Map<String, String> dataItem) {
        def configProperties = config.getProperties();
        for (String prop : configProperties) {
            properties[prop] = dataItem[prop]
        }
        if (getIdentityProperty() == null) {
            properties['identityProperty'] = dataItem['identityProperty'];
        }
    }

    abstract Element buildElement(Element parentNode);

    protected void putProperties(Element parent) {
        Element propertiesNode = parent.addElement("properties");
        properties.each { key, val ->
            if (val == null) {
                return
            }
            if (!config.getProperties().contains(key)) {
                return
            }
            Element propertyNode = propertiesNode.addElement(key);
            propertyNode.addText(val);
        }
    }

    protected void addAttributes(Element node, Map<String, String> attributes) {
        attributes.each { key, val ->
            node.addAttribute(key, val);
        }
    }

    abstract String getNodeId();

    String getIdentityProperty() {
        return properties["identityProperty"];
    }
}
