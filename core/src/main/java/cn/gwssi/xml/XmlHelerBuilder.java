package cn.gwssi.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlHelerBuilder {
    /**
     * 将XML转为指定的POJO对象
     *
     * @param clazz  需要转换的类
     * @param xmlStr xml数据
     * @return
     */
    public static Object convertXmlToObject(Class<?> clazz, String xmlStr) {
        Object xmlObject = null;
        Reader reader = null;
        //利用JAXBContext将类转为一个实例
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(clazz);
            //XMl 转为对象的接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            reader = new StringReader(xmlStr);
            xmlObject = unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return xmlObject;
    }

    /**
     * 将对象直接转换成String类型的 XML输出
     *
     * @param obj
     * @return
     */
    public static String convertObjectToXml(Object obj) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }


}
