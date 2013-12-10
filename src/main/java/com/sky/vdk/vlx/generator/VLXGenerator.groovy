package com.sky.vdk.vlx.generator

import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.annotations.VlxProperty
import com.sky.vdk.vlx.generator.exceptions.NotAnnotatedClassException
import com.sky.vdk.vlx.generator.exceptions.VlxTypeNameConflictException
import com.sky.vdk.vlx.generator.nodecfg.EndNodeConfig
import com.sky.vdk.vlx.generator.nodecfg.LinkNodeConfig
import com.sky.vdk.vlx.generator.nodecfg.NodeConfig
import com.sky.vdk.vlx.generator.nodecfg.PropertyInfo
import com.sky.vdk.vlx.generator.nodedata.EndNodeBean
import com.sky.vdk.vlx.generator.nodedata.LinkNodeBean
import com.sky.vdk.vlx.generator.nodedata.NodeBean
import com.sky.vdk.vlx.generator.utils.SkyXMLUtils
import org.apache.commons.io.FileUtils
import org.apache.log4j.Logger
import org.dom4j.Document
import org.dom4j.DocumentHelper
import org.dom4j.Element

import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Created with IntelliJ IDEA.
 * User: yixian
 * Date: 13-11-6
 * Time: 下午4:20
 */
class VLXGenerator {

    private Logger logger = Logger.getLogger(getClass());
    Map<String, EndNodeConfig> endConfigs = [:];
    Map<String, LinkNodeConfig> linkConfigs = [:];
    Map<String, EndNodeBean> endNodes = [:];
    Map<String, LinkNodeBean> linkNodes = [:];
    private Document document;
    private VLXModelInitor initor;

    /**
     * 初始化
     */
    {
        initor = VLXModelInitor.getInstance().clone() as VLXModelInitor;
        document = DocumentHelper.parseText(initor.baseVLX);
        loadNodeConfig();
    }

    void reset() {
        endNodes = [:];
        linkNodes = [:];
    }

    private void loadNodeConfig() {
        logger.debug("loading node configs,document is null:" + (document == null));
        if (document != null) {
            def endTypesNode = document.selectSingleNode("vlx:vlx/typeCatalogue/endTypes") as Element;
            def linkTypesNode = document.selectSingleNode("vlx:vlx/typeCatalogue/linkTypes") as Element;
            loadTypes(endTypesNode, "end");
            loadTypes(linkTypesNode, "link");
        }
        displayConfigs();
    }

    private void displayConfigs() {
        logger.info("loaded config:");
        endConfigs.each { Map.Entry<String, String> entry ->
            logger.info(entry.getValue().toString());
        }
        linkConfigs.each { Map.Entry<String, String> entry ->
            logger.info(entry.getValue().toString());
        }
    }

    private void loadTypes(Element nodeList, String nodeType) {
        def nodeConfigs = nodeType == "end" ? endConfigs : linkConfigs;
        for (Iterator ite = nodeList.elementIterator(); ite.hasNext();) {
            Element element = ite.next() as Element;
            logger.debug("load element:" + element.getName());
            String localName = element.attributeValue("localName");
            List<String> properties = loadElementProperties(element);
            nodeConfigs[localName] = NodeConfig.createInstance(nodeType, localName, properties);
        }
    }

    private List<String> loadElementProperties(Element element) {
        List<String> eleProperties = [];
        for (Iterator propertyIte = element.elementIterator("property"); propertyIte.hasNext();) {
            Element propertyElement = propertyIte.next() as Element;
            eleProperties.add(propertyElement.attributeValue("localName"));
        }
        return eleProperties;
    }

    /**
     * 根据catType取得End节点的配置
     * @param name
     * @return
     */
    NodeConfig getEndConfig(String name) {
        return endConfigs[name] as NodeConfig;
    }

    /**
     * 根据catType取得Link节点的配置
     * @param name
     * @return
     */
    NodeConfig getLinkConfig(String name) {
        return linkConfigs[name] as NodeConfig;
    }

    /**
     * 添加End节点
     * @param catType
     * @param rowData
     * @return
     */
    EndNodeBean addEnd(String catType, Map<String, String> rowData) {
        def endNode = getEndConfig(catType)?.newNode(rowData) as EndNodeBean;
        endNodes[endNode.getNodeId()] = endNode;
        return endNode;
    }

    EndNodeBean addEndByPojo(Object pojo) {
        Class clazz = pojo.getClass();
        if (clazz.isAnnotationPresent(VlxEndType.class)) {
            return solveAnnotation(pojo);
        } else {
            throw new NotAnnotatedClassException(clazz);
        }
    }

    private EndNodeBean solveAnnotation(Object pojo) {
        Class clazz = pojo.getClass();
        VlxEndType endTypeInfo = clazz.getAnnotation(VlxEndType.class);
        List<PropertyInfo> propertiesInfo = solveProperties(clazz);
        EndNodeConfig config = checkInsertEndType(propertiesInfo, endTypeInfo, clazz)
        Map<String, String> endProperties = [:]
        propertiesInfo.each { PropertyInfo info ->
            String propertyValue = info.solveProperty(pojo)
            if (propertyValue != null) {
                endProperties.put(info.getLocalName(), propertyValue)
            } else {
                logger.debug("property is null:" + info.getLocalName())
            }
        }
        addEnd(config.getNodeName(), endProperties);
    }

    private EndNodeConfig checkInsertEndType(List<PropertyInfo> propertiesInfo, VlxEndType endTypeInfo, Class<?> clazz) {
        List<String> propertyNames = loadPropertyNames(propertiesInfo);
        EndNodeConfig config = NodeConfig.createInstance("end", endTypeInfo.localName(), propertyNames) as EndNodeConfig;
        if (endConfigs.get(config.getNodeName()) != null) {
            if (!endConfigs.get(config.getNodeName()).equals(config)) {
                throw new VlxTypeNameConflictException(config.getNodeName(), clazz);
            }
        } else {
            endConfigs.put(config.getNodeName(), config);
            appendEndTypeToDocument(endTypeInfo, propertiesInfo);
        }
        config
    }

    private void appendEndTypeToDocument(VlxEndType vlxEndType, List<PropertyInfo> propertyInfos) {
        document = initor.addVlxEndType(vlxEndType, propertyInfos);
    }

    private List<String> loadPropertyNames(List<PropertyInfo> propertyInfos) {
        List<String> propertyNames = [];
        propertyInfos.each { PropertyInfo info ->
            propertyNames.add(info.getLocalName());
        }
        return propertyNames;
    }

    private List<PropertyInfo> solveProperties(Class clazz) {
        List<PropertyInfo> properties = [];
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(VlxProperty.class)) {
                PropertyInfo property = PropertyInfo.newInstance(field);
                properties.add(property);
            }
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(VlxProperty.class)) {
                PropertyInfo property = PropertyInfo.newInstance(method);
                properties.add(property);
            }
        }
        return properties;
    }
/**
 * 关联两个节点
 * @param catType 节点类型
 * @param end1 起始节点
 * @param end2 结束节点
 * @param params 连接参数
 * @return
 */
    LinkNodeBean connectNodes(String catType, EndNodeBean end1, EndNodeBean end2, Map<String, String> params) {
        def linkNode = getLinkConfig(catType)?.newNode(params) as LinkNodeBean;
        linkNode.linkNodes(end1, end2);
        linkNodes[linkNode.getNodeId()] = linkNode;
        return linkNode;
    }

    /**
     * 生成最终XML字符串
     * @return
     */
    String generateVLX() {
        def copyDoc = document.clone() as Document;
        buildEndNodes(copyDoc);
        buildLinkNodes(copyDoc);
        return SkyXMLUtils.formatXMLOutput(copyDoc);
    }

    /**
     * 将最终生成的XML字符串写入文件
     * @param filePath
     */
    void generateToFile(String filePath) {
        FileUtils.write(new File(filePath), generateVLX(), "utf-8");
    }

    private void buildEndNodes(Document doc) {
        def ends = doc.selectSingleNode("vlx:vlx/content/ends") as Element;
        endNodes.each { Map.Entry<String, String> entry ->
            NodeBean nodeInfo = entry.getValue() as NodeBean;
            nodeInfo.buildElement(ends);
        }
    }

    private void buildLinkNodes(Document doc) {
        def links = doc.selectSingleNode("vlx:vlx/content/links") as Element;
        linkNodes.each { Map.Entry<String, String> entry ->
            NodeBean nodeInfo = entry.getValue() as NodeBean;
            nodeInfo.buildElement(links);
        }
    }

}
