package com.sky.vdk.vlx.generator.nodecfg

import com.sky.vdk.vlx.generator.nodedata.EndNodeBean
import com.sky.vdk.vlx.generator.nodedata.NodeBean

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-11-6
 * Time: 下午6:14
 * To change this template use File | Settings | File Templates.
 */
class EndNodeConfig extends NodeConfig {
    EndNodeConfig(String nodeName, List<String> properties) {
        this.nodeName = nodeName;
        this.properties = properties;
    }

    @Override
    NodeBean newNode(Map<String,String> dataItem) {
        NodeBean endNode = new EndNodeBean();
        initNode(endNode);
        endNode.putProperties(dataItem);
        return endNode;
    }

    public boolean equals(Object obj) {
        if (!obj instanceof EndNodeConfig) {
            return false;
        }
        EndNodeConfig cfg = (EndNodeConfig) obj;
        if (this.nodeName == cfg.nodeName) {
            if (this.properties?.size() == cfg.properties?.size()) {
                return this.properties?.containsAll(cfg.properties)
            }
        }
        return false;
    }
}
