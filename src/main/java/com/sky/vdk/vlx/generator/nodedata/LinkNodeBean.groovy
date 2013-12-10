package com.sky.vdk.vlx.generator.nodedata

import org.dom4j.Element

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-11-7
 * Time: 上午10:04
 * To change this template use File | Settings | File Templates.
 */
class LinkNodeBean extends NodeBean {
    EndNodeBean end1;
    EndNodeBean end2;
    LinkDirection direction;

    void linkNodes(EndNodeBean end1, EndNodeBean end2) {
        this.end1 = end1;
        this.end2 = end2;
        if (getIdentityProperty() == null) {
            properties["identityProperty"] = "${end1.getIdentityProperty()}-${end2.getIdentityProperty()}"
        }
    }

    @Override
    Element buildElement(Element parentNode) {
        Element linkNode = parentNode.addElement("link");


        Map<String, String> attributes = [catType: typeName, end1identity: end1.getIdentityProperty(), end2identity: end2.getIdentityProperty(), id: getNodeId(), end1id: end1.getNodeId(), end2id: end2.getNodeId()]
        if (direction != null) {
            attributes['direction'] = direction.toString();
        }
        addAttributes(linkNode, attributes);
        putProperties(linkNode);
        return linkNode;
    }

    @Override
    String getNodeId() {
        return "id-${end1.getNodeId()}-${end2.getNodeId()}-${getIdentityProperty()}"
    }
}
