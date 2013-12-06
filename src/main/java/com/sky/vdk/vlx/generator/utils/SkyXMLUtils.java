package com.sky.vdk.vlx.generator.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-11-28
 * Time: 下午9:51
 * XML操作类
 */
public class SkyXMLUtils {

    public static String formatXMLOutput(Document doc) {
        OutputFormat format = new OutputFormat("  ", true);
        StringWriter writer = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(writer, format);
        try {
            xmlWriter.write(doc);
            xmlWriter.flush();
        } catch (IOException ignored) {
        }

        return writer.toString();
    }

    public static void addElementProperties(Element element, Map<String, String> properties) {
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            element.addAttribute(entry.getKey(), entry.getValue());
        }
    }
}
