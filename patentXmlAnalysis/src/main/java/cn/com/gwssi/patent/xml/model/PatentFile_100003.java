package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.io.xml.XppDriver;
import lombok.Data;

@XStreamAlias("cn-application-body")
@Data
public class PatentFile_100003 {
    @XStreamAsAttribute
    private String lang;

    @XStreamAsAttribute
    private String country;

    @XStreamAlias("cn-drawings")
    @XStreamAsAttribute
    private DescriptionDrawing descriptionDrawing;

    private String shenqingh;

    private String version;

    private String baseDir;//解析实体的存储路径

    public PatentFile_100003 parseXmlText(String xmlText, String shenqingh, String version, String baseDir) {
        XStream xstream = new XStream(new XppDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(new Class[]{PatentFile_100003.class, DescriptionDrawing.class, CnDrawingP.class, Figure.class, Img.class, P.class, Pb.class});
        xstream.processAnnotations(PatentFile_100003.class);
        xstream.processAnnotations(DescriptionDrawing.class);
        xstream.processAnnotations(CnDrawingP.class);
        xstream.processAnnotations(Figure.class);
        xstream.processAnnotations(Img.class);
        xstream.processAnnotations(Pb.class);
        xstream.processAnnotations(P.class);
        PatentFile_100003 patentFile100003 = (PatentFile_100003) xstream.fromXML(xmlText);
        patentFile100003.setVersion(version);
        patentFile100003.setShenqingh(shenqingh);
        patentFile100003.setBaseDir(baseDir.replaceAll("\\\\","/"));
        return patentFile100003;
    }
}
