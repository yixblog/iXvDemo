package com.sky.vdk.vlx.generator

import com.sky.vdk.vlx.generator.annotations.VlxEndType
import com.sky.vdk.vlx.generator.nodecfg.PropertyInfo
import com.sky.vdk.vlx.generator.utils.MD5
import com.sky.vdk.vlx.generator.utils.SkyXMLUtils
import org.apache.log4j.Logger
import org.dom4j.Document
import org.dom4j.DocumentException
import org.dom4j.DocumentHelper
import org.dom4j.Element
import org.dom4j.io.SAXReader

/**
 * Created with IntelliJ IDEA.
 * User: Shine
 * Date: 13-11-28
 * Time: 下午4:46
 */
class VLXModelInitor implements Cloneable {
    private Logger log = Logger.getLogger(getClass());
    private static final String configPath = "vlx_config.xml";
    private String baseVLX;
    private static String sourceConfig;

    private final static VLXModelInitor initor = new VLXModelInitor();

    public static VLXModelInitor getInstance() {
        return initor;
    }

    public String getBaseVLX() {
        return baseVLX;
    }

    private VLXModelInitor() {
        try {
            loadSourceConfig();
            initVLX(sourceConfig);
        } catch (DocumentException ex) {
            log.warn("打开文件失败");
            log.error(ex.getMessage());
        } catch (FileNotFoundException ex) {
            log.warn("打开文件失败");
            log.error(ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            log.warn("打开文件失败");
            log.error(ex.getMessage());
        }
    }

    private void loadSourceConfig() {
        /*
             * 保存document对象为字符串格式
             */
        SAXReader reader = new SAXReader();
        /*
        * 处理文件输入部分，进行中文编码转换
        */

        String fileName = getClass().getClassLoader().getResource("$configPath")?.getPath();
        log.debug("filename:$fileName")
        File f = new File(fileName);
        BufferedReader BRreader = new BufferedReader(new InputStreamReader(
                new FileInputStream(f), "UTF-8"));

        /*
         * 创建Document对象
         */
        Document document = reader.read(BRreader);
        sourceConfig = SkyXMLUtils.formatXMLOutput(document);

    }

    public void initVLX(String sourceConfig) {
        Document document = DocumentHelper.parseText(sourceConfig);
        /*
         * 转换config文件为vlx格式配置文件 翻译处理实体定义部分
         */
        Document document_new = conversion(document);
        /*
         * 转换config文件为vlx格式配置文件 翻译处理链接定义部分
         */
        document_new = this.converLinkTypes(document_new, document);

        baseVLX = SkyXMLUtils.formatXMLOutput(document_new);

    }

    protected Document conversion(Document configDocument) throws DocumentException {
        // 声明实体类型需要变量

        Document document = DocumentHelper.createDocument();
        // 创建根节点
        Element vlx = document.addElement("vlx:vlx")
        vlx.addAttribute("service", "PersonDatabase")
        vlx.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance")
        vlx.addAttribute("xmlns:vlx", "http://www.i2group.com/Schemas/2001-12-07/VLXSchema");
        // 创建3个主要节点
        vlx.addElement("appletReturnedData");
        Element typeCatalogue = vlx.addElement("typeCatalogue");
        Element content = vlx.addElement("content");
        // 添加第一个主要节点
        typeCatalogue.addAttribute("identityProperty", "guidFAFA");

        // 添加endTypes
        Element endTypes = typeCatalogue.addElement("endTypes");

        // 添加linkTypes
        typeCatalogue.addElement("linkTypes");

        // 添加实体
        content.addElement("ends");

        // 添加链接
        content.addElement("links");

        // 枚举名称为endType的节点
        List endTypeList = configDocument.selectNodes("/root/endTypes/endType");
        endTypeList.each { Element _endType ->
            // 开始转换实体==========================
            Element endType = endTypes.addElement("endType"); // 创建endType节点
            if (_endType.attributeValue("localName") != null) {
                copyPropertyAttributes(endType, _endType, 'LNIconFile', 'localName', 'displayName');
                endType.addAttribute("tGUID", computeMD5(_endType.attributeValue("localName").trim()))
                endType.addAttribute("representation", "theme");
            }
            List endTypeChildrenNodes = _endType.elements();
            endTypeChildrenNodes.each { Element _property ->
                if (isElementOfTagName(_property, "imageURI")) {
                    fillElementText(endType, _property);
                } else if (isElementOfTagName(_property, "property")) {
                    generatePropertyElement(endType, _property);
                }
            }
            endTypeChildrenNodes.clear();
            // 实体转换结束==========================

        }

        // Add datetime property
        Element forms = typeCatalogue.addElement("forms");
        Element form = forms.addElement("form").addAttribute("fGUID", "guidD1219C6C-067B-45EB-A0F9-B4BB7970E4C0");
        form.addElement("FormName").addText("DateTime");
        form.addElement("baseForm").addText("dateTime");
        Element formatters = form.addElement("formatters");

//		formatters.addElement("formatter").addAttribute("syntax", ".net")
//		.addText("yyyy-MM-dd HH:mm:ss");
        formatters.addElement("formatter").addAttribute("syntax", ".net")
                .addText("yyyy-MM-dd HH:mm:ss zzz");
        formatters.addElement("formatter").addAttribute("syntax", ".net")
                .addText("yyyyMMddHHmm");
        formatters.addElement("formatter").addAttribute("syntax", ".net")
                .addText("yyyyMMdd");
        formatters.addElement("formatter").addAttribute("syntax", ".net")
                .addText("yyyy-MM-dd");
        // Element formatter =
        // formatters.addElement("formatter").addAttribute("syntax",".net").addText("yyyyMMddHHmm");
        // formatter =
        // formatters.addElement("formatter").addAttribute("syntax",".net").addText("yyyy-MM-dd
        // hh:mm");

        return document;
    }

    private void fillElementText(Element target, Element source) {
        Element element = target.addElement(source.getName()).addText(source.getText().replaceAll(/[\r\n]/, '').trim());
        element.appendAttributes(source);
    }

    private void copyPropertyAttributes(Element targetElement, Element sourceElement, String... attributeNames) {
        attributeNames?.each { attrName ->
            targetElement.addAttribute(attrName, sourceElement.attributeValue(attrName)?.trim());
        }
    }

    private String computeMD5(String localName) {
        new MD5(localName).compute()
    }

    private boolean isElementOfTagName(Element checkElement, String... tagNames) {
        for (String name : tagNames) {
            if (checkElement.getName() == name) {
                return true;
            }
        }
        return false;
    }


    protected Document converLinkTypes(Document vlxDocument, Document configDocument) throws DocumentException {
        //CAUTION!! 勿删！！！重新加载xml以注册vlx:vlx namespace，否则下方vlx:vlx XPath表达式报错
        vlxDocument = DocumentHelper.parseText(vlxDocument.asXML());
        List configLinkTypeList = configDocument.selectNodes("/root/LinkTypes/LinkType");
        Element linkTypes = vlxDocument.selectSingleNode("/vlx:vlx/typeCatalogue/linkTypes") as Element;
        configLinkTypeList.each { Element _linkType ->
            Element linkType = linkTypes.addElement("linkType");
            copyPropertyAttributes(linkType, _linkType, 'localName', 'tGUID', 'color', 'displayName', 'isOrdered', 'isSymmertricLink', 'kindOf', 'LNDateTime', 'service', 'showArrows')

            Iterator _iter1 = _linkType.elementIterator("property");
            while (_iter1.hasNext()) {
                Element _property = (Element) _iter1.next();
                generatePropertyElement(linkType, _property)
            }
        }
        return vlxDocument;
    }

    private void generatePropertyElement(Element parentElement, Element _property) {
        Element property = parentElement.addElement("property");

        if (_property.attributeValue("localName") != null) {
            property.addAttribute("localName", _property.attributeValue("localName").trim());
            property.addAttribute("pGUID", computeMD5(_property.attributeValue("localName")));
        }
        copyPropertyAttributes(property, _property, 'displayName', 'baseProperty', 'isHidden', 'idType', 'fGUID', 'isLabel', 'isVLVFToolTip', 'isPicklistColumn', 'mergeBehaviour', 'service', 'update')

        List propertyChildren = _property.elements();
        propertyChildren.each { Element _child_property ->
            if (isElementOfTagName(_child_property, "attribute")) {
                Element attribute = property.addElement("attribute");
                copyPropertyAttributes(attribute, _child_property, 'isUser', 'userCanAdd', 'userCanRemove', 'vlvfDisplayAsGlyph');

                List attrChildren = _child_property.elements();
                attrChildren.each { Element _child_attribute ->
                    if (isElementOfTagName(_child_attribute, "Symbol")) {
                        Element symbol = attribute.addElement("Symbol");
                        copyPropertyAttributes(symbol, _child_attribute, 'height', 'LNIconFile', 'position', 'shown', 'URI', 'width')

                        List symbolChildren = _child_attribute.elements();
                        symbolChildren.each { Element _child_Symbol ->
                            if (isElementOfTagName(_child_Symbol, "useValueAsURI")) {
                                Element useValueAsURI = symbol.addElement("useValueAsURI");
                                List uriChildren = _child_Symbol.elements();
                                uriChildren.each { Element _child_useValueAsURI ->
                                    if (isElementOfTagName(_child_useValueAsURI, "prefix", "suffix")) {
                                        fillElementText(useValueAsURI, _child_useValueAsURI);
                                    }
                                }
                                // iter5.remove();
                                uriChildren.clear();
                            }
                        }
                        // iter4.remove();
                        symbolChildren.clear();
                    } else if (isElementOfTagName(_child_attribute, "OnlyShownIfVal")) {
                        attribute.addElement("OnlyShownIfVal");
                    } else if (isElementOfTagName(_child_attribute, "Prefix", "ShowOnChart", "ShowValue", "Suffix")) {
                        fillElementText(attribute, _child_attribute);
                    }
                }
                // iter3.remove();
                attrChildren.clear();
            } else if (isElementOfTagName(_child_property, "card")) {
                Element card = property.addElement("card");
                List cardChildren = _child_property.elements();
                cardChildren.each { Element _child_card ->
                    if (isElementOfTagName(_child_card, "ConvertTabs")) {
                        Element convertTabs = card.addElement("ConvertTabs");
                        copyPropertyAttributes(convertTabs, _child_card, 'numberOfSpaces');
                    } else if (isElementOfTagName(_child_card, "InsertLineBreaks")) {
                        Element insertLineBreaks = card.addElement("InsertLineBreaks");
                        copyPropertyAttributes(insertLineBreaks, _child_card, 'numberOfCharacters');
                    } else if (isElementOfTagName(_child_card, "RemoveLineBreaks")) {
                        card.addElement("RemoveLineBreaks");
                    }
                }
                // iter6.remove();
                cardChildren.clear();
            } else if (isElementOfTagName(_child_property, "databaseProxySettings")) {
                Element databaseProxySettings = property.addElement("databaseProxySettings");
                copyPropertyAttributes(databaseProxySettings, _child_property, 'className', 'classID', 'instanceName', 'connectString');
            } else if (isElementOfTagName(_child_property, "identityProperty")) {
                property.addElement("identityProperty");
            }
        }
        // iter2.remove();
        propertyChildren.clear();
    }

    Document addVlxEndType(VlxEndType vlxEndType, List<PropertyInfo> propertyInfos) {
        Document configDoc = DocumentHelper.parseText(sourceConfig);
        Element endTypesElement = configDoc.selectSingleNode("root/endTypes") as Element;

        Element endType = endTypesElement.addElement("endType");
        SkyXMLUtils.addElementProperties(endType, [localName: vlxEndType.localName(), displayName: vlxEndType.displayName()]);

        Element imageEle = endType.addElement("imageURI");
        SkyXMLUtils.addElementProperties(imageEle, [width: vlxEndType.imageSize().toString(), height: vlxEndType.imageSize().toString()]);
        imageEle.setText(vlxEndType.imageURI());

        propertyInfos.each { PropertyInfo info ->
            Element property = endType.addElement("property");
            Map<String,String> attributesToAddition = [localName: info.getLocalName(), displayName: info.getDisplayName(), canSearch: 'true', canShow: 'true'];
            if(info.isIsLabel()){
                attributesToAddition['isLabel']='true';
            }
            SkyXMLUtils.addElementProperties(property, attributesToAddition);
        }
        initVLX(SkyXMLUtils.formatXMLOutput(configDoc));
        return DocumentHelper.parseText(baseVLX);
    }
}
