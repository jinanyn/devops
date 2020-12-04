/**
 *
 */
package cn.com.gwssi.patent.xml.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ensing
 */
@Slf4j
public class PathentXmlUtil {
    private static final String regEx_dtd = "<!DOCTYPE[^>]*?>";

    private static final String regEx_sheet = "<\\?xml-stylesheet[^>]*?>";

    private static final String REP_B = "<b>|</b>|<b/>";
    private static final String REP_B_S = "&lt;b&gt;\\s*&lt;/b&gt;";

    private static final String REP_BR = "<br/>";

    private static final String REP_PB = "<pb pnum=\"[0-9a-zA-Z]+\"/>";

    // private static final String REP_P_B =
    // "(<p[^,]*>)?\\s*&lt;b&gt;([^,]+)&lt;/b&gt;\\s*(</p>)?";
    private static final String REP_P_B = "(<p[^>]*>)((.*?)(</p>))";
    private static final String REP_IMG = "(<img)((.*?)(/>))";
    // private static final String REP_P_B =
    // "\\s*&lt;b&gt;([^,]+)&lt;/b&gt;\\s*(<p[^,]*>)?(</p>)?";
    // private static final String REP_NUMBER_NOTE = "<!-- [0-9]+ -->";

    private static final Set<String> titles = CaseInfoUtil.PATENT_DESCRIPTION_HEADING_CONTENT.keySet();

    /**
     * 修改公布公告文本Xml文件以满足出版要求
     *
     * @param FilePath
     * @throws Exception
     */
    public static void gbdXmlDisposeIng(String FilePath) throws Exception {

        // 用于测试的临时路径
        String FilePathTmp = FilePath;

        // 加载一个dom4j的解析器
        SAXReader reader = new SAXReader();
        // 先过滤掉dtd的校验，需要校验的时候，将以下语句注释掉
        try {
            reader
                    .setFeature(
                            "http://apache.org/xml/features/nonvalidating/load-external-dtd",
                            false);
        } catch (SAXException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Document doc = null;
        short NOTE_TYPE = 3;// 定义节点类型
        String docXMLEncode = null;

        // 规则一的处理，规则如下
        // <claim>
        // <tables></tables>
        // </claim>
        // 对于这种形式的文件转换成如下形式，增加<claim-text>
        // <claim>
        // <claim-text>
        // <tables></tables>
        // </claim-text>
        // </claim>

        try {
            // 读取xml文档
            doc = reader.read(new File(FilePathTmp));

            docXMLEncode = doc.getXMLEncoding();
            log.info("获取的xml文件的编码格式为" + docXMLEncode);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 直接查找xml文件中所有符合<claim><tables>条件的，返回的是tables的节点
        List<Element> tables = doc.selectNodes("//claim/tables");

        log.info("规则一 符合<claim><tables>条件的个数" + tables.size());

        //
        for (Element table : tables) {
            // 为claim增加子节点claim-text
            // 第一种实现方法
            // List list = table.getParent().content();
            // Element claimtextNode =
            // DocumentHelper.createElement("claim-text");
            // list.add(0, claimtextNode);
            // claimtextNode.setText("");
            // table.getParent().remove(table);
            // claimtextNode.add(table);

            // 第二种实现方法
            // 为table的父节点claim增加子节点claim-text
            Element claimtextNode = table.getParent().addElement("claim-text");
            // 删除claim与table的父子关系
            table.getParent().remove(table);
            // 将table节点的父节点指向为创建的claim-text节点
            claimtextNode.add(table);
        }

        // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 规则二 增加tables节点的num属性
        // 首先在<tables>节点中移除节点的所有属性Attributes ，再增加num属性（四位数字）num=’0001’ 排序递增

        // 读取xml文档
        // doc = reader.read(new File(FilePathTmp));

        // 直接查找xml文件中所有符合<tables>条件的，返回的是tables的节点
        List<Element> tabless = doc.selectNodes("//tables");//

        int num = 0;// 用于统计tables的个数
        for (Element element : tabless) {
            // 克隆，为了后面的删除属性，因为如果不克隆，在删除某个属性的时候总会影响到定义的数据结构
            Element element2 = (Element) element.clone();

            // 遍历节点的所有属性
            // 该方法虽然能删除， 因为在用remove方法删除的时候会将指针自动往下移，而循环中迭代器也需要将指针下移，导致错误。
            // 第一种实现方法

            // 将该节点所有属性放到list中，从list中删除
            List<Attribute> list = element2.attributes();
            // 循环list，将属性删除
            for (int i = 0; i < list.size(); i++) {
                String attName = list.get(i).getName();
                if (element.remove(list.get(i))) {
                    log.info("规则二  删除" + attName + "成功");
                } else {
                    log.error("规则二  删除" + attName + "失败");
                    throw new Exception(
                            "规则二 删除tables属性时错误");
                }
            }

            // 为tables增加num属性
            num++;
            DecimalFormat format = new DecimalFormat("0000");
            String numStr = format.format(num);
            element.addAttribute("num", numStr);
        }

        // /////////////////////////////////////////////////////////////////////////////////////////
        // 规则三 移除<table>节点的所有属性Attributes ，可参考规则二

        // 读取xml文档
        // doc = reader.read(new File(FilePathTmp));

        // 直接查找xml文件中所有符合<tables>条件的，返回的是table的节点
        List<Element> table = doc.selectNodes("//table");//

        for (Element element : table) {
            // 克隆，为了后面的删除属性，因为如果不克隆，在删除某个属性的时候总会影响到定义的数据结构
            Element element2 = (Element) element.clone();

            // 将该节点所有属性放到list中，从list中删除
            List<Attribute> list = element2.attributes();
            // 循环list，将属性删除
            for (int i = 0; i < list.size(); i++) {
                String attName = list.get(i).getName();
                if (element.remove(list.get(i))) {
                    log.info("规则三  删除" + attName + "成功");
                } else {
                    log.error("规则三  删除" + attName + "失败");
                    throw new Exception("规则二 删除table属性是错误");
                }
            }
        }
        // ///////////////////////////////////////////////////////////////////
        // 规则四
        // 如果<table>第一个子节点=<row>节点,查出<row>节点下的子节点的个数为列数cols的值
        // 如果<table>第一个子节点=<thead>节点, 查出<thead>节点下的子节点的子节点的个数为列数cols的值
        // 如果<table>第一个子节点=<tgroup>节点,如果<tgroup>的第一个子节点=<colspec>
        // 退出循环，否则<tgroup>的第一个子节点下的子节点个数为列数cols的值
        // 如果<table>节点第一个子节点=<tgroup>节点，在<tgroup>节点中增加列数cols属性，值为上面查出的列数
        // 如果列数cols值大于0,循环列数，则在<tgroup>节点下顺序增加<colspec>节点，<colspec>节点有两个属性colname=’c000’，
        // 000为第几列数（三位数字），属性二colwidth=’100/ cols %’ cols为列数

        // 直接查找xml文件中所有符合<table>条件的，返回的是table的节点
        List<Element> tableLst = doc.selectNodes("//table");

        int cols = 0;
        boolean exit = false;
        // 循环所有的table
        for (Element tableElement : tableLst) {
            // 如果table有子节点
            if (tableElement.elements().size() > 0) {
                Element tableFirstChildElement = (Element) tableElement
                        .elements().get(0);
                log.info("规则四  获取table的第一个子节点的类型名称"
                        + tableFirstChildElement.getName());
                if (0 == tableFirstChildElement.getName().compareTo("row")) {
                    cols = tableFirstChildElement.elements().size();
                } else if (0 == tableFirstChildElement.getName().compareTo(
                        "thead")) {
                    Element tableFirstChildChildElement = (Element) tableFirstChildElement
                            .elements().get(0);
                    log.info("规则四  获取table的第一个子节点的第一个子节点的类型名称"
                            + tableFirstChildChildElement.getName());
                    cols = tableFirstChildChildElement.elements().size();
                } else if (0 == tableFirstChildElement.getName().compareTo(
                        "tgroup")) {
                    Element tableTgroupFirstChildElement = (Element) tableFirstChildElement
                            .elements().get(0);
                    if (0 == tableTgroupFirstChildElement.getName().compareTo(
                            "colspec")) {
                        exit = true;
                        log
                                .debug("规则四  table的第一个节点为tgroup的第一个节点为clospec,直接退出循环");
                    } else {
                        Element tableTgroupFirstChildChildElement = (Element) tableTgroupFirstChildElement
                                .elements().get(0);
                        cols = tableTgroupFirstChildChildElement.elements()
                                .size();
                    }
                } else {
                    log.error("规则四  table不符合格式要求，抛出异常");
                    throw new Exception(
                            "规则四  table不符合格式要求，抛出异常");
                }

                log.info("规则四  统计出的cols个数=" + cols);
                log.info("规则四 是否退出循环标记exit=" + exit);

                if (!exit) {

                    // 用于记录tgroup节点中所有节点的列表，由于增加colspec节点
                    List<Element> tableTgroupList = null;
                    // 获取tgroup节点下的第一个子节点，新节点需要增加在这个节点之前。
                    Element tableTgroupListFirstChild = null;

                    // 如果table的第一个子节点是tgroup，则在group节点中增加cols的属性，值为上面求出的结果。
                    if (0 == tableFirstChildElement.getName().compareTo(
                            "tgroup")) {
                        tableFirstChildElement.addAttribute("cols",
                                new Integer(cols).toString());
                        tableTgroupList = tableFirstChildElement.elements();

                        tableTgroupListFirstChild = tableTgroupList.get(0);
                    } else {
                        log.error("规则四  table不符合要求，要抛出异常");
                        throw new Exception(
                                "规则四  table的下级节点不是tgroup，不符合格式要求，抛出异常");
                    }

                    // 如果列数cols值大于0,循环列数，则在<tgroup>节点下顺序增加<colspec>节点，<colspec>节点有两个属性colname=’c000’，
                    // 000为第几列数（三位数字），属性二colwidth=’100/ cols %’ cols为列数
                    if (cols > 0) {
                        for (int c = 1; c <= cols; c++) {
                            DecimalFormat format = new DecimalFormat(
                                    "000");
                            String colStr = format.format(c);
                            Element colspecNode = DocumentHelper
                                    .createElement("colspec");
                            colspecNode.addAttribute("colname", "c" + colStr);
                            colspecNode.addAttribute("colwidth", new Integer(
                                    100 / cols).toString()
                                    + "%");
                            tableTgroupList.add(tableTgroupList
                                            .indexOf(tableTgroupListFirstChild),
                                    colspecNode);
                        }
                    }
                }

            } else {
                log.info("规则四 table没有任何子节点，不执行任何操作");
            }
        }
        // ////////////////////////////////////////////////////////////////////////////////////
        // 规则五 查找出所有<entry>节点去掉节点的所有属性，然后<entry>节点增加属性morerows属性值为’1’

        // 直接查找xml文件中所有符合<entry>条件的，返回的是entry的节点
        List<Element> entrys = doc.selectNodes("//entry");//

        for (Element element : entrys) {
            // 克隆，为了后面的删除属性，因为如果不克隆，在删除某个属性的时候总会影响到定义的数据结构
            Element element2 = (Element) element.clone();

            // 将该节点所有属性放到list中，从list中删除
            List<Attribute> list = element2.attributes();
            // 循环list，将属性删除
            for (int i = 0; i < list.size(); i++) {
                String attName = list.get(i).getName();
                if (element.remove(list.get(i))) {
                    log.info("规则五  删除" + attName + "成功");
                } else {
                    log.error("规则五  删除" + attName + "失败");
                    throw new Exception(
                            "规则五  删除entry属性" + attName + "失败");
                }
            }

            // 为entry增加morerows属性
            element.addAttribute("morerows", "1");
        }

        // ////////////////////////////////////////////////////////////////////
        // 规则六 去掉< entry >节点中的<p>节点的标识（即<p>a</p>转变为a）
        // 查找出所有<entry>节点下，所有子节点的名称=<p>节点或子节点的名称=<claim-text>节点，如果<entry>子节点的子节点个数大于0,
        // 则去掉<p>节点中包含的所有子节点，（上下角标保留内容），否则移除<p>节点保留内容 <sub>

        // 直接查找xml文件中所有符合<entry>条件的，返回的是entry的节点
        List<Element> entryss = doc.selectNodes("//entry");

        for (Element entryElement : entryss) {
            List pParentList = entryElement.elements();

            List<Element> pElementLst = entryElement.elements();
            for (Element pElement : pElementLst) {
                if (0 == pElement.getName().compareTo("p")
                        || 0 == pElement.getName().compareTo("claim-text")) {
                    log.info("规则六  p节点的内容个数为" + pElement.elements().size());

                    // 将p或者claim-text节点转换成一个待删除的节点标记，之后在文件流中将该标记删除
                    Element pdeleteNode = pElement.createCopy("deleteTmp");

                    pParentList.add(pParentList.indexOf(pElement), pdeleteNode);

                    entryElement.remove(pElement);

                    // 删除临时节点中的所有属性
                    Element pdeleteNode2 = (Element) pdeleteNode.clone();
                    List<Attribute> deleArrList = pdeleteNode2.attributes();
                    // 循环deleArrList，将属性删除
                    for (int i = 0; i < deleArrList.size(); i++) {
                        String attName = deleArrList.get(i).getName();
                        if (pdeleteNode.remove(deleArrList.get(i))) {
                            log.info("规则六  删除" + attName + "成功");
                        } else {
                            log.info("规则六  删除" + attName + "失败");
                            throw new Exception("规则六  删除entry属性" + attName + "失败");
                        }
                    }

                } else {
                    log.info("规则六  非p节点或者claim-text节点，直接退出循环");
                }
            }
        }
        // ///////////////////////////////////////////////////////////////////////////////////////////
        // /规则七 去掉所有<tbody>节点中的<thead>节点的标识
        // 查找出所有<tbody>节点下子节点=<thead>节点 去掉里面的子节点保留内容（方法如同去掉<p>节点一样） 可参考规则六
        // 直接查找xml文件中所有符合<tbody><thead></thead> </tbody> 条件的，返回的是tbody的节点
        List<Element> tbodys = doc.selectNodes("//tbody");

        for (Element tbodyElement : tbodys) {
            List<Element> pParentList = tbodyElement.elements();

            List<Element> theadElementLst = tbodyElement.elements();
            for (Element theadElement : theadElementLst) {
                if (0 == theadElement.getName().compareTo("thead")) {

                    // 将thead节点转换成一个待删除的节点标记，之后在文件流中将该标记删除
                    Element theaddeleteNode = theadElement
                            .createCopy("deleteTmp");

                    pParentList.add(pParentList.indexOf(theadElement),
                            theaddeleteNode);

                    tbodyElement.remove(theadElement);

                    // 删除临时节点中的所有属性
                    Element theaddeleteNode2 = (Element) theaddeleteNode
                            .clone();
                    List<Attribute> deleArrList = theaddeleteNode2.attributes();
                    // 循环deleArrList，将属性删除
                    for (int i = 0; i < deleArrList.size(); i++) {
                        String attName = deleArrList.get(i).getName();
                        if (theaddeleteNode.remove(deleArrList.get(i))) {
                            log.info("规则七  删除" + attName + "成功");
                        } else {
                            log.info("规则七  删除" + attName + "失败");
                            throw new Exception(
                                    "规则七  删除entry属性" + attName + "失败");
                        }
                    }
                    // p节点下存在其他子节点
                    // if( theadElement.elements().size() > 0){
                    // List<Element> theadChildElementLts =
                    // theadElement.elements();
                    //
                    // for(Element theadChildElement : theadChildElementLts){
                    // Element theadChildElement2 =
                    // (Element)theadChildElement.clone();
                    // pParentList.add(pParentList.indexOf(theadElement),
                    // theadChildElement2);
                    //
                    // // pElement.addText(pChildElement.getText()+"\n");
                    // // log.info("规则六
                    // p的子节点的内容是"+pChildElement.getText());
                    //
                    //
                    // }
                    // tbodyElement.remove(theadElement);
                    // }else{//p节点下不存在其他子节点只存在内容
                    // //不确定，暂时注释掉
                    // // String pString = pElement.getText();
                    // // entryElement.addText(pString+"\n");
                    // // entryElement.remove(pElement);
                    // }

                } else {
                    log.info("规则七  非thead节点，直接退出循环");
                }
            }
        }
        // ////////////////////////////////////////////////////////////////////////////
        // 规则八 在<img>节点增加<figure>节点
        // 查找出所有<img>节点，如果其父节点=<cn-abst-figure>，在< cn-abst-figure
        // >节点和<img>节点之间增加<figure >节点，移除<figure>节点的所有属性
        // 然后<figure>节点增加num属性,num值为<img>的个数，num=’0000’

        // 直接查找xml文件中所有符合
        // <cn-abst-figure>
        // <img></img></cn-abst-figure>条件的，返回的是img的节点
        List<Element> imgss = doc.selectNodes("//img");
        int imgI = 0;// 统计img的个数
        for (Element imgElement : imgss) {
            List<Element> imgParentElement = imgElement.getParent().elements();

            // 判断父节点是否是cn-abst-figure
            if (0 == imgElement.getParent().getName().compareTo(
                    "cn-abst-figure")) {
                Element figureNode = DocumentHelper.createElement("figure");
                imgParentElement.add(imgParentElement.indexOf(imgElement),
                        figureNode);
                Element imgElement2 = (Element) imgElement.clone();
                figureNode.add(imgElement2);
                imgElement.getParent().remove(imgElement);

                // 为figure增加num属性
                imgI++;
                DecimalFormat format = new DecimalFormat(
                        "0000");
                String numStr = format.format(imgI);
                figureNode.addAttribute("num", numStr);

            } else {
                log.info("规则八 img的父节点不是cn-abst-figure，退出循环");
            }
        }

        // 新增，为了解决已经存在
        // <cn-abst-figure>
        // <figure num=""><img></img></figure>
        // </cn-abst-figure>这样的节点，而且
        // 也存在<cn-abst-figure>
        // <img></img>
        // </cn-abst-figure>这样的节点，再统一figure个数并增加num属性
        int figureI = 0;
        List<Element> figures = doc.selectNodes("//figure");
        if (figures.size() > 0) {
            for (Element figureElement : figures) {
                if (figureElement.getParent().getName().compareTo(
                        "cn-abst-figure") == 0) {
                    Element figureElement2 = (Element) figureElement.clone();
                    // 删除figure的所有属性
                    List<Attribute> figureAttrlist = figureElement2.attributes();
                    log.info("规则八 figure节点为" + figureElement.getName() + "的属性的个数为"
                            + figureAttrlist.size());
                    if (figureAttrlist.size() > 0) {
                        for (int aI = 0; aI < figureAttrlist.size(); aI++) {
                            log.info("规则八 figure节点的属性为"
                                    + figureAttrlist.get(aI).getName());
                            figureElement.remove(figureAttrlist.get(aI));
                            log.info("规则八 属性的循环次数为" + aI);
                        }
                    }
                    // 为figure增加num属性
                    figureI++;
                    DecimalFormat format = new DecimalFormat(
                            "0000");
                    String numStr = format.format(figureI);
                    figureElement.addAttribute("num", numStr);
                }
            }
        } else {
            log.info("规则八 不存在figure节点，退出循环");
        }

        // //////////////////////////////////////////////////////////////////////////////////////////////////////
        // 规则九 删除<cn-drawings>节点中包含的文本
        // 如果<cn-drawings>节点的子节点类型为NODE_TEXT，则删除这个子节点

        // 直接查找xml文件中所有符合<cn-drawings> </cn-drawings>条件的
        List<Element> cndrawings = doc.selectNodes("//cn-drawings");

        for (Element cndrawingElement : cndrawings) {
            log.info("规则九 得到cn-drawings节点的节点类型"
                    + cndrawingElement.getNodeType());
            List<Element> nodetextLst = cndrawingElement.elements();

            for (Element nodetextElement : nodetextLst) {
                log.info("规则九 得到cn-drawings节点的所有子节点的节点类型"
                        + nodetextElement.getNodeType());
                // NODE_TEXT的节点类型为3
                if (NOTE_TYPE == nodetextElement.getNodeType()) {
                    nodetextLst.remove(nodetextElement);
                }
            }
        }

        // ///////////////////////////////////////////////////////////////////////////////////////////////
        // /规则十 删除< cn-abst-figure >节点中包含的文本
        // 如果<cn-abst-figure>节点的子节点类型为NODE_TEXT，则删除这个子节点 同规则九

        // 直接查找xml文件中所有符合<cn-abst-figure> </cn-abst-figure>条件的
        List<Element> cnabstfiguress = doc.selectNodes("//cn-abst-figure");

        for (Element cnabstfiguresElement : cnabstfiguress) {
            List<Element> nodetextLst = cnabstfiguresElement.elements();

            for (Element nodetextElement : nodetextLst) {
                log.info("规则十 得到cn-abst-figure节点的所有子节点的节点类型"
                        + nodetextElement.getNodeType());
                // NODE_TEXT的节点类型为3
                if (NOTE_TYPE == nodetextElement.getNodeType()) {
                    nodetextLst.remove(nodetextElement);
                }
            }
        }

        // /////////////////////////////////////////////////////////////////////////////////////////////
        // /规则十一 更改<img>节点属性的大小写
        // 查找所有的<img>节点，如果<img>节点属性img-format =TIF改为tif,
        // 如果<img>节点属性img-format =JPG改为jpg

        // 直接查找xml文件中所有符合<img> </img>条件的
        List<Element> imgs = doc.selectNodes("//img");

        for (Element imgsElement : imgs) {
            List<Attribute> imgAttr = imgsElement.attributes();
            for (Attribute imgAttribute : imgAttr) {
                if (0 == imgAttribute.getName().compareTo("img-format")) {
                    if (0 == imgAttribute.getText().compareTo("TIF")) {
                        imgAttribute.setText("tif");
                        log.info("规则十一 修改img的属性img-format值TIF的大小写成功");
                    } else if (0 == imgAttribute.getText().compareTo("JPG")) {
                        imgAttribute.setText("jpg");
                        log.info("规则十一 修改img的属性img-format值JPG的大小写成功");
                    }
                }
            }
        }

        // ////////////////////////////////////////////////////////////////////////////////////////////////
        // /规则十二 删除xml所有的<del>节点。

        // 直接查找xml文件中所有符合<del> </del>条件的
        List<Element> dels = doc.selectNodes("//del");
        log.info("规则十二 删除del的节点");

        for (Element delElement : dels) {
            Element parentElement = delElement.getParent();
            parentElement.remove(delElement);
            log.info("规则十二 删除del的节点成功");
        }

        // ///////////////////////////////////////////////////////////////////////////////////////////////////////
        // 规则十三
        // 删除xml中的所有的<ins>节点，去掉<ins>节点的标识（即<ins>a</ins>转为a）（方法如同去掉<p>节点一样）

        // 直接查找xml文件中所有符合<ins> </ins>条件的
        List<Element> inss = doc.selectNodes("//ins");

        for (Element insElement : inss) {
            List parentIns = insElement.getParent().elements();// 只有一个父节点，求list是为了在某个位置增加子节点，list.size
            // = 1
            List<Element> childIns = insElement.elements();

            log.info("规则十三 ins的父节点的list的长度为" + parentIns.size());

            // 将ins节点转换成一个待删除的节点标记，之后在文件流中将该标记删除
            Element insdeleteNode = insElement.createCopy("deleteTmp");

            parentIns.add(parentIns.indexOf(insElement), insdeleteNode);

            insElement.getParent().remove(insElement);

            // 删除临时节点中的所有属性
            Element insdeleteNode2 = (Element) insdeleteNode.clone();
            List<Attribute> deleArrList = insdeleteNode2.attributes();
            // 循环deleArrList，将属性删除
            for (int i = 0; i < deleArrList.size(); i++) {
                String attName = deleArrList.get(i).getName();
                if (insdeleteNode.remove(deleArrList.get(i))) {
                    log.info("规则十三  删除" + attName + "成功");
                } else {
                    log.info("规则十三  删除" + attName + "失败");
                    throw new Exception(
                            "规则十三  删除entry属性" + attName + "失败");
                }
            }

            // 如果ins节点还有子节点
            // if(insElement.elements().size()>0){
            // for(Element insElementChild : childIns){
            // Element insElementChildTmp = (Element)insElementChild.clone();
            // parentIns.add(parentIns.indexOf(insElement), insElementChildTmp);
            // }
            // parentIns.remove(insElement);
            // }else{//没有子节点，则删除标记 保留内容
            // //insElement.detach();
            // //parentIns.add(parentIns.indexOf(insElement), insElement.);
            // // insElement.getParent().setText(insElement.getText());
            // // insElement.getParent().remove(insElement);
            // }

        }

        // /////////////////////////////////////////////////////////////////////////////////
        // 规则十四 删除xml中的所有的<a>节点，去掉<a>节点的标识（即<a>a</a>转为a）（方法如同去掉<p>节点一样）

        // 直接查找xml文件中所有符合<a> </a>条件的
        List<Element> asLst = doc.selectNodes("//a");

        for (Element aElement : asLst) {
            List parentIns = aElement.getParent().elements();// 只有一个父节点，求list是为了在某个位置增加子节点，list.size
            // = 1
            List<Element> childIns = aElement.elements();

            log.info("规则十四 a的父节点的list的长度为" + parentIns.size());

            // 将a节点转换成一个待删除的节点标记，之后在文件流中将该标记删除
            Element adeleteNode = aElement.createCopy("deleteTmp");

            parentIns.add(parentIns.indexOf(aElement), adeleteNode);

            aElement.getParent().remove(aElement);

            // 删除临时节点中的所有属性
            Element adeleteNode2 = (Element) adeleteNode.clone();
            List<Attribute> deleArrList = adeleteNode2.attributes();
            // 循环deleArrList，将属性删除
            for (int i = 0; i < deleArrList.size(); i++) {
                String attName = deleArrList.get(i).getName();
                if (adeleteNode.remove(deleArrList.get(i))) {
                    log.info("规则十四  删除" + attName + "成功");
                } else {
                    log.info("规则十四  删除" + attName + "失败");
                    throw new Exception(
                            "规则十四  删除entry属性" + attName + "失败");
                }
            }

            // //如果a节点还有子节点
            // if(aElement.elements().size()>0){
            // for(Element insElementChild : childIns){
            // Element insElementChildTmp = (Element)insElementChild.clone();
            // parentIns.add(parentIns.indexOf(aElement), insElementChildTmp);
            // }
            // parentIns.remove(aElement);
            // }else{//没有子节点，则删除标记 保留内容
            // // aElement.getParent().setText(aElement.getText());
            // // aElement.getParent().remove(aElement);
            // }
        }

        // ///////////////////////////////////////////////////////////////////////////////////////////
        // 规则十五 删除xml中的所有的<pb>节点。 同规则十二

        // 直接查找xml文件中所有符合<pb> </pb>条件的
        List<Element> pbs = doc.selectNodes("//pb");

        for (Element pbElement : pbs) {
            Element parentElement = pbElement.getParent();
            parentElement.remove(pbElement);
            log.info("规则十五 删除pb的节点成功");
        }

        // //////////////////////////////////////////////////////////////////////////////////////////////////
        // 规则十六 删除xml中的所有空的<b>节点 查找出所有<invention-title>节点，
        // 如果<invention-title>节点没有子节点删除此节点
        // 如果<invention-title>节点有一个子节点，并且节点内容为空，并且子节点的名称为<#text>,删除此节点
        // 如果<invention-title>节点有一个子节点，并且节点内容为”?”，并且子节点的名称为<#text>,删除此节点

        // 直接查找xml文件中所有符合<pb> </pb>条件的
        List<Element> inventiontitles = doc.selectNodes("//invention-title");

        if (inventiontitles.size() > 1) {
            for (Element inventiontitleElement : inventiontitles) {
                if (0 == inventiontitleElement.getText().trim().compareTo("")) {
                    inventiontitleElement.getParent().remove(
                            inventiontitleElement);
                }
            }
        } else if (inventiontitles.size() == 1) {
            List<Element> childe = inventiontitles.get(0).elements();
            for (Element element : childe) {
                if (0 == element.getText().trim().compareTo("")
                        || 0 == element.getText().trim().compareTo("?")) {
                    if (0 == element.getName().compareTo(
                            "#text")) {
                        element.getParent().remove(
                                element);
                    }
                }
            }
        }

        // for (Element inventiontitleElement : inventiontitles) {
        // if (0 == inventiontitleElement.elements().size()) {
        // //inventiontitleElement.getParent().remove(inventiontitleElement);
        // } else if (1 == inventiontitleElement.elements().size()) {
        // if (0 == inventiontitleElement.getText().trim().compareTo("")
        // || 0 == inventiontitleElement.getText().trim().compareTo("?")) {
        // Element inventiontitleChildElement = (Element) inventiontitleElement
        // .elements().get(0);
        // if (0 == inventiontitleChildElement.getName().compareTo(
        // "#text")) {
        // inventiontitleElement.getParent().remove(
        // inventiontitleElement);
        // }
        // }
        // }
        // }

        // //////////////////////////////////////////////////////////////////////////////////////////////////
        // 规则十七 在<maths>节点增加num属性
        // 查找出所有<maths>节点，先移除节点的所有属性，然后增加num属性 num=’0000’,为四位有效数字

        // 直接查找xml文件中所有符合<maths> </maths>条件的
        List<Element> mathss = doc.selectNodes("//maths");
        int attNum = 0;
        for (Element mathElement : mathss) {
            Element mathElement2 = (Element) mathElement.clone();
            List<Attribute> mathElementAttrList = mathElement2.attributes();

            for (int i = 0; i < mathElementAttrList.size(); i++) {
                mathElement.remove(mathElementAttrList.get(i));
            }

            // 为maths增加num属性
            attNum++;
            DecimalFormat format = new DecimalFormat("0000");
            String numStr = format.format(attNum);
            mathElement.addAttribute("num", numStr);
        }

        // 将处理完成的xml文件保存

        OutputFormat format = null;
        XMLWriter xmlwriter = null;
        // 将定义好的内容写入xml文件中
        try {
            // 进行格式化
            // format = OutputFormat.createPrettyPrint();
            // 设定编码
            // format.setEncoding("UTF-8");
            xmlwriter = new XMLWriter(new FileOutputStream(FilePathTmp));
            xmlwriter.write(doc);
            xmlwriter.flush();
            xmlwriter.close();
            log.info("-----------Xmlfile successfully save-------------");
        } catch (Exception e) {
            e.printStackTrace();
            log
                    .debug("-----------Exception occured during of save xmlfile -------");
            throw new Exception(
                    "处理完xml文件后，将修改后的xml内容回存失败");
        }

        File f = new File(FilePathTmp);
        // 将xml文件中临时的标记deleteTmp删除
        // 20180301gjt 原实现 使用的是烽火台类，注掉
        // FileUtils.readFileToString(FilePathTmp, "UTF-8");
        String s = FileUtils.readFileToString(f, "UTF-8");

        if (s.indexOf("<deleteTmp>") != -1 || s.indexOf("<deleteTmp/>") != -1) {
            log.info("---------需要删除相应deleteTmp节点-------------");
            s = s.replaceAll("<deleteTmp>", "");
            s = s.replaceAll("</deleteTmp>", "");
            s = s.replaceAll("<deleteTmp/>", "");
            try {
                // 20180301gjt 原实现 使用的是烽火台类，注掉
                // FileUtil.saveFile(FilePathTmp, s.getBytes("UTF-8"));
                FileUtils.writeStringToFile(f, s, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new Exception("文件格式转换失败~~~~~~~~~~");
            }
        }
    }

    public static String transferTemplet(String context, String shenqingh, Integer fileCode)  {
        if (FileInfoConstants.FILE_CODE_CLAIM.equals(fileCode) || FileInfoConstants.FILE_CODE_DESCRIPTION.equals(fileCode)
                || FileInfoConstants.FILE_CODE_DESCRIPTION_ABSTRACT.equals(fileCode)) {
            context = filterContext(context);// 通用特殊符处理
            context = tagImgFormat(context);// img格式化

            // math标签删除
            context = context.replaceAll("(<math>)((?!</math>).)*(</math>)", "").replaceAll("(<|</)maths[^>]*>",
                    "");
            //补全 xxxx段 将 <p  xxxx /> 改为 <p xxxx></p>
            String xx = "<p[^>n]*num=\"XXXX\"[^>/]*/>";
            context = context.replaceAll(xx, "<p num=\"XXXX\"></p>");
            // 说明书特殊格式处理
            if (FileInfoConstants.FILE_CODE_DESCRIPTION.equals(fileCode)) {
                //将<p>标签里的其它标签转义
                Matcher matcher = Pattern.compile("<p[^>]*>(((?!(</p>)).)*)</p>").matcher(context);
                while (matcher.find()) {
                    //获取p标签的内容(带p标签)
                    String pStr = matcher.group();
                    //获取p标签的内容(不带p标签)
                    String pContent = matcher.group(1);
                    //先去掉注释，再将p标签里其它标签转义
                    String zhuanyihou = pContent.replaceAll("<!\\-\\-((?!(\\-\\->)).)*\\-\\->", "")
                            .replaceAll("<", "&lt;").replaceAll(">", "&gt;");

                    String pStrAfter = pStr.replace(pContent, zhuanyihou);
                    context = context.replace(pStr, pStrAfter);
                }
                if (isStandardFieldXml(context)) {//五个域的标准结构处理多heading情况
                    context = formatStandardMoreHeading(context);
                } else if (isStandardHeadingXml(context)) {//五个heading时直接转为标准结构
                    context = toStandard100002Xml(context);
                } else {//其他情况视未非标准结构，按特殊结构以解析为目的特殊处理，可能导致规则跑错
                    try {
                        context = format100002(context);
                    } catch (Exception e) {
                        log.error(ExceptionUtil.getMessage(e));
                        throw new BusinessException(ExceptionUtil.getMessage(e,true));
                    }
                }

            }
        }
        return context;
    }

    private static String toHeadingContentFormat(String context) {
        context = context.replaceAll("<technical-field>", "").replaceAll("</technical-field>", "")
                .replaceAll("<background-art>", "").replaceAll("</background-art>", "").replaceAll("<disclosure>", "")
                .replaceAll("</disclosure>", "").replaceAll("<description-of-drawings>", "")
                .replaceAll("</description-of-drawings>", "").replaceAll("<mode-for-invention>", "")
                .replaceAll("</mode-for-invention>", "");

        return context;
    }

    private static String format100002ToHadXiaobiaoti(String text) throws Exception {
        // 对特殊符号进行处理，转成utf8格式的符号
        byte bytes[] = {(byte) 0xC2, (byte) 0xA0};
        String UTFSpace = new String(bytes, "utf-8");
        text = text.replaceAll(UTFSpace, " ");
        //去掉没用的heading
        Matcher heading = Pattern.compile("<heading>([\u4e00-\u9fa5]{0,3})</heading>").matcher(text);
        while (heading.find()) {
            String content = heading.group(1);
            text = text.replace(content, "");
        }
        // 写在p标签中的标题，转成heading
        Matcher m = Pattern.compile(REP_P_B).matcher(text);
        int titleIndex = 0; //第i个小标题
        while (m.find()) {
            String group = m.group(3);
            String title = getCompareTitle(group);
            String numStr = m.group(1);
            numStr = numStr.split("num=\"")[1];
            numStr = numStr.substring(0, numStr.indexOf("\""));
            if ("0002".equals(numStr)) {
                log.debug("for debug");
            }
            if (titles.contains(title) && titleIndex < 5) {//不满足五个小标题情况下继续分析
                titleIndex++;
                String headingXml = "<heading>" + group + "</heading>";
                if (!"XXXX".equals(numStr)) {
                    //headingXml = "<heading>【" + numStr + "】" + headingXml + "</heading>";
                    headingXml = "<heading>【" + numStr + "】" + group + "</heading>";
                }

                text = text.replace(m.group(1) + group + "</p>", headingXml);
            } else {
                // 标题和内容成一行时。。。，自动处理为标题和段落部分，段号放到段落部分
                String[] sentences = group.split("[,.:;，。：；]");
                if (sentences.length == 1) {
                    continue;
                }
                String titleSentence = group.substring(0, sentences[0].length() + 1);
                title = getCompareTitle(titleSentence);
                if (titles.contains(title) && titleIndex < 5) {//不满足五个小标题情况下继续分析
                    titleIndex++;
                    log.error("标题与内容成一行，特殊处理后与原文已经不一致！！！！");
                    String newXmlPart = "<heading>" + titleSentence + "</heading>";
                    newXmlPart += m.group(1) + group.replace(titleSentence, "") + "</p>";
                    // 构造新结构
                    text = text.replace(m.group(1) + group + "</p>", newXmlPart);
                }
            }
        }

        String context = toStandard100002Xml(text);

        return context.toString();
    }

    /**
     * 去除文档中的全角空格
     *
     * @return
     */
    private static String trimBlank(String str) {
        if (str == null) {
            return null;
        }
        return str.trim().replaceAll("　", "").replaceAll(" ", "").replaceAll("\\s", "");
    }

    private static String getCompareTitle(String title) {
        title = trimBlank(title);
        String ignoreWord = "([\\(（])?(一|二|三|四|五|六|七|八|九|十)+([\\)）])?[\\.、]?";
        Pattern pattern = Pattern.compile(ignoreWord);
        Matcher matcher = pattern.matcher(title);
        if (matcher.find() && matcher.start() == 0) {// 存在特殊结构，并且特殊结构在开始部分
            title = title.replaceFirst(ignoreWord, "");
        }
        return PatternUtil.getChineseInStr(title);
    }

    private static String pNumXXXXFormat(String context) {
        // TODO Auto-generated method stub
        String replaceP = "</p>\\s*<p[^>n]*num=\"XXXX\"[^>]*>";
        String replaceP1 = "</p>\\s*<p Italic=\"0\" num=\"XXXX\">";
        context = context.replaceAll(replaceP, "&lt;br/&gt;");
        context = context.replaceAll(replaceP1, "&lt;br/&gt;");

        return context;
    }

    private static String format100002(String context) throws Exception {
        // 删掉空heading
        context = context.replaceAll("<heading [^>]*>\\s*</heading>", "");
        // 删掉空<b>    </b>
        context = context.replaceAll("<b>\\s*</b>", "");
        // 转为标题，内容结构
        context = toHeadingContentFormat(context);
        // 对标题内容结构做格式处理
        context = format100002ToHadXiaobiaoti(context);
        return context;
        // 有小标题结构
        // return format100002Had_Xiaobiaoti(context);
    }

    /**
     * 处理heading里面的内容
     *
     * @param flegment
     * @return
     */
    public static String filterSpace(String flegment) {
        int index = flegment.lastIndexOf("</heading>");
        int start = flegment.indexOf(">");
        String suffix = "";
        String prefix = "";
        if (index >= start + 1) {
            suffix = flegment.substring(start + 1, index);
            prefix = flegment.substring(0, start + 1);
        }
        return prefix + suffix.replaceAll(" ", "") + flegment.substring(index);
    }

    private static String toStandard100002Xml(String text) {
        // 转为结构化用标准格式
        text = text.replaceAll("</description>", "").replaceAll("</cn-application-body>", "");
        String[] flgments = text.split("<heading");

        if (flgments.length != 6) {// 判断是否正好五个小标题
            log.error("小标题数量不是五个，解析或相应规则可能出现问题");
        }

        StringBuffer context = new StringBuffer();
        for (int i = 0; i < flgments.length; i++) {
            if (i == 0) {
                if (flgments[0].trim().charAt(0) != '<') {
                    context.append(flgments[0].trim().substring(1));
                } else {
                    context.append(flgments[0]);
                }
            }
            if (i == 1) {
                context.append("<technical-field><heading" + filterSpace(flgments[1]) + "</technical-field>");
            }
            if (i == 2) {
                context.append("<background-art><heading" + filterSpace(flgments[2]) + "</background-art>");
            }
            if (i == 3) {
                context.append("<disclosure><heading" + filterSpace(flgments[3]) + "</disclosure>");
            }
            if (i == 4) {
                context.append(
                        "<description-of-drawings><heading" + filterSpace(flgments[4]) + "</description-of-drawings>");
            }
            if (i == 5) {
                context.append("<mode-for-invention><heading" + filterSpace(flgments[5]));
            }
            if (i > 5) {
                context.append("<p Italic=\"0\" num=\"XXXX\" " + flgments[i].replace("</heading>", "</p>"));
            }
            if (i >= 5 && i == flgments.length - 1) {
                context.append("</mode-for-invention>");
            }
        }

        context.append("</description>");
        context.append("</cn-application-body>");
        String content = context.toString();
        //2018/09/12   在此处添加对 XXXX 段的处理
        //此处中括号中有两个空格，一个全角的一个半角的，请留意，不要误删了！！！！！（目的：将</p>前的空格删掉）
        content = content.replaceAll("[  ]*</p>", "</p>");
        // 合并xxxx段落如果有上一段，合并到上一段，如果没有，则不作处理
        content = pNumXXXXFormat(content);
        return content;
    }

    public static boolean isStandardHeadingXml(String context) {
        if (context.split("<heading").length != 6) {
            return false;
        }
        return true;
    }

    private static String getBiaotiYuNr(String context, String tag) {
        int start = context.indexOf("<" + tag + ">");
        int end = context.indexOf("</" + tag + ">");
        if (end <= start) {// 无给定域内容
            return null;
        }
        return context.substring(start, end + 3 + tag.length());
    }

    /**
     * 如果是五个域的结构，处理下各个域中多个heading情况和xxxx段情况并且返回true，否则返回false
     * @param context
     * @return
     */
    private static String formatStandardMoreHeading(String context) {

        List<String> xiaobiaotiContextList = new ArrayList<String>();

        // 技术领域域进行特殊处理
        String technicalFieldContent = getBiaotiYuNr(context, "technical-field");
        if (StringUtils.isNotBlank(technicalFieldContent)) {
            xiaobiaotiContextList.add(technicalFieldContent);
        }

        // 背景技术域进行特殊处理
        String backgroundArtContent = getBiaotiYuNr(context, "background-art");
        if (StringUtils.isNotBlank(backgroundArtContent)) {
            xiaobiaotiContextList.add(backgroundArtContent);
        }

        // 实用新型内容域特殊处理
        String disclosureContent = getBiaotiYuNr(context, "disclosure");
        if (StringUtils.isNotBlank(disclosureContent)) {
            xiaobiaotiContextList.add(disclosureContent);
        }

        // 对附图说明域进行特殊处理
        String descriptionOfDrawingContent = getBiaotiYuNr(context, "description-of-drawing");
        if (StringUtils.isNotBlank(descriptionOfDrawingContent)) {
            xiaobiaotiContextList.add(descriptionOfDrawingContent);
        }

        // 对具体实施方式域进行特殊处理
        String modeForInventionContent = getBiaotiYuNr(context, "mode-for-invention");
        if (StringUtils.isNotBlank(modeForInventionContent)) {
            xiaobiaotiContextList.add(modeForInventionContent);
        }

        for (String filedText : xiaobiaotiContextList) {
            String oldFiledText = filedText;
            String[] headings = filedText.split("<heading");
            if (headings != null && headings.length > 1) {
                // 一个域下多个heading，多余的转为p
                // TODO 上述方式会导致 这个p就没有段号，段号规则发通知书出现xxxx段问题
                filedText = filedText.replaceAll("<heading[^>]*>", "<p Italic=\"0\" num=\"XXXX\">&lt;b&gt;").replaceAll("</heading>", "&lt;/b&gt;</p>")
                        .replaceFirst("<p Italic=\"0\" num=\"XXXX\">&lt;b&gt;", "<heading>").replaceFirst("&lt;/b&gt;</p>", "</heading>");
            }
            //此处中括号中有两个空格，一个全角的一个半角的，请留意，不要误删了！！！！！（目的：将</p>前的空格删掉）
            filedText = filedText.replaceAll("[  ]*</p>", "</p>");
            // 合并xxxx段落如果有上一段，合并到上一段，如果没有，则不作处理
            filedText = pNumXXXXFormat(filedText);

            if (!oldFiledText.equals(filedText)) {
                context = context.replace(oldFiledText, filedText);
            }

        }

        return context;
    }

    /**
     * 过滤特定的内容
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String filterContext(String str) {
        str = PatternUtil.deleletMatchStr(str, regEx_dtd);// 干掉"<!DOCTYPE"标签
        str = PatternUtil.deleletMatchStr(str, regEx_sheet);// 干掉"<?xml-stylesheet"
        str = str.replaceAll(REP_B, "");// 干掉<b
        str = str.replaceAll(REP_BR, "");// 干掉<br
        str = str.replaceAll(REP_PB, "");// 干掉<pb

        // str = str.replaceAll(REP_NUMBER_NOTE,"");
        // 上角标处理
        str = str.replaceAll("<sup>", "&lt;sup&gt;");
        str = str.replaceAll("</sup>", "&lt;/sup&gt;");
        // 下角标处理
        str = str.replaceAll("<sub>", "&lt;sub&gt;");
        str = str.replaceAll("</sub>", "&lt;/sub&gt;");
        // i标签处理处理
        str = str.replaceAll("<i>", "&lt;i&gt;");
        str = str.replaceAll("</i>", "&lt;/i&gt;");

        // u标签处理处理
        str = str.replaceAll("<u>", "&lt;u&gt;");
        str = str.replaceAll("</u>", "&lt;/u&gt;");
        return str;
    }

    /**
     * img标签格式化
     *
     * @param context
     * @return
     */
    private static String tagImgFormat(String context) {
        Matcher matcher = Pattern.compile(REP_IMG).matcher(context);
        while (matcher.find()) {
            String imgStr = matcher.group(1);
            String imgStr1 = matcher.group(3);
            String imgStr2 = matcher.group(4);
            context = context.replace(imgStr + imgStr1 + imgStr2, "&lt;img" + imgStr1 + "/&gt;");
        }
        return context;
    }

    /**
     * 如果五个标准域元素都有则返回true 否则返回false
     * @param context
     * @return
     */
    public static boolean isStandardFieldXml(String context) {
        // 技术领域域进行特殊处理
        if (context.indexOf("<technical-field>") == -1 || context.indexOf("</technical-field>") == -1) {
            return false;
        }

        // 背景技术域进行特殊处理
        if (context.indexOf("<background-art>") == -1 || context.indexOf("</background-art>") == -1) {
            return false;
        }

        // 实用新型内容域特殊处理
        if (context.indexOf("<disclosure>") == -1 || context.indexOf("</disclosure>") == -1) {
            return false;
        }

        // 对附图说明域进行特殊处理
        if (context.indexOf("<description-of-drawings>") == -1 || context.indexOf("</description-of-drawings>") == -1) {
            return false;
        }

        // 对具体实施方式域进行特殊处理
        if (context.indexOf("<mode-for-invention>") == -1 || context.indexOf("</mode-for-invention>") == -1) {
            return false;
        }
        return true;
    }
}
