package com.sky.vdk.vlx.generator.nodedata

import com.sky.vdk.vlx.generator.utils.SkyXMLUtils
import org.dom4j.Element


/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-12-4
 * Time: 下午4:57
 * 节点的自定义图片配置
 */
class ImageConfig {
    String url;
    int width = 32;
    int height = 32;

    void generateImageElement(Element parentNode) {
        Element formatting = parentNode.addElement("formatting");
        Element image = formatting.addElement("image");
        SkyXMLUtils.addElementProperties(image, [URI: url, width: width.toString(), height: height.toString()]);
    }
}
