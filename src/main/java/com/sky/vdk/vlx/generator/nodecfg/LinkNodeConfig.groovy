package com.sky.vdk.vlx.generator.nodecfg

import com.sky.vdk.vlx.generator.nodedata.LinkNodeBean
import com.sky.vdk.vlx.generator.nodedata.NodeBean

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-11-6
 * Time: 下午6:16
 */
class LinkNodeConfig extends NodeConfig{
    LinkNodeConfig(String linkName,def properties){
        this.nodeName = linkName;
        this.properties = properties;
    }

    @Override
    NodeBean newNode(dataItem) {
        NodeBean node = new LinkNodeBean();
        initNode(node);
        node.putProperties(dataItem);
        return node;
    }
}
