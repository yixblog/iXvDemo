package com.sky.vdk.vlx.generator.nodedata

import com.sky.vdk.vlx.generator.utils.SkyXMLUtils
import org.dom4j.Element

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-11-6
 * Time: 下午6:21
 */
class EndNodeBean extends NodeBean {

    ImageConfig imageConfig;

    @Override
    Element buildElement(Element parentNode) {
        Element endNode = parentNode.addElement("end");
        imageConfig?.generateImageElement(endNode);
        SkyXMLUtils.addElementProperties(endNode, ["catType": typeName, "xPos": "0", "yPos": "0", "shown": "true", "id": getNodeId()]);
        putProperties(endNode);
        return endNode;
    }

    @Override
    String getNodeId() {
        return "id-$typeName-${getIdentityProperty()}";
    }

    void configImage(String url, int width, int height) {
        imageConfig = new ImageConfig(url: url, width: width, height: height);
    }

}
