package cn.gwssi.xml;

import cn.gwssi.model.NoticeInfoList;

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
     * 将XML转为指定的POJO对象
     *
     * @param clazz  需要转换的类
     * @param xmlStr xml数据
     * @return
     */
    public static <T> NoticeInfoList<T> convertXmlToObject_1(NoticeInfoList<T> clazz, String xmlStr) {
        NoticeInfoList<T> xmlObject = null;
        Reader reader = null;
        //利用JAXBContext将类转为一个实例
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(clazz.getClass());
            //XMl 转为对象的接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            reader = new StringReader(xmlStr);
            xmlObject = (NoticeInfoList<T>)unmarshaller.unmarshal(reader);
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

    /**
     * @Description xml 转成 bean(泛型使用)
     * @Param [xml, t]
     * @Return T
     */
    public static <T> T xmlToBean(String xml, T t, Class c) {
        Reader reader = null;
        try {
            ////获得 JAXBContext 类的新实例。参数为类的地址
            JAXBContext context = JAXBContext.newInstance(t.getClass(),c);
            //创建一个可以用来将 XML 数据转换为 java 内容树的 Unmarshaller 对象。
            Unmarshaller um = context.createUnmarshaller();
            //创建一个StringReader将xml报文转成流
            reader = new StringReader(xml);
            //调用unmarshal进行转换，并把Object类型强转为调用者的类型
            t = (T) um.unmarshal(reader);
            //将对象返回给调用者
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
        return t;
    }

}
