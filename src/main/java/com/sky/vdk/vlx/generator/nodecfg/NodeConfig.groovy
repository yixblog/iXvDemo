package com.sky.vdk.vlx.generator.nodecfg

import com.sky.vdk.vlx.generator.nodedata.NodeBean

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-11-6
 * Time: 下午5:58
 */
abstract class NodeConfig {
    String nodeName;
    def properties;

    public static NodeConfig createInstance(String type, String nodeName, def properties) {
        if (type == 'end') {
            return new EndNodeConfig(nodeName, properties);
        } else if (type == 'link') {
            return new LinkNodeConfig(nodeName, properties);
        }
        return null;
    }

    public abstract NodeBean newNode(def dataItem);

    public String toString() {
        def builder = getClass().toString() + ":" + nodeName + ":[";
        if (properties.size() > 0) {
            for (String property : properties) {
                builder += property + ","
            }
            builder = builder.substring(0, builder.lastIndexOf(","));
        }
        builder += "]";
        return builder;
    }

    protected void initNode(NodeBean node) {
        node.setConfig(this);
        node.setTypeName(nodeName);
    }

}
