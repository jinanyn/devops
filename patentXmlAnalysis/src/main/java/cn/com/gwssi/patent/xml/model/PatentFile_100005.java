package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.io.xml.XppDriver;
import lombok.Data;

import java.util.List;

@XStreamAlias("cn-application-body")
@Data
public class PatentFile_100005 {
    @XStreamAsAttribute
    private String lang;

    @XStreamAsAttribute
    private String country;

    @XStreamAlias("cn-abstract")
    @XStreamAsAttribute
    private AbstractDrawing abstractDrawing;

    private String shenqingh;

    private String version;

    private String baseDir;//解析实体的存储路径

    /**
     * 1：解析成功 2：未找到版本信息 3：文件不存在 4：解析异常
     */
    private String status;

    public PatentFile_100005 parseXmlText(String xmlText, String shenqingh, String version, String baseDir) {
        XStream xstream = new XStream(new XppDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(new Class[]{PatentFile_100005.class, AbstractDrawing.class, CnAbstFigure.class, Img.class, Pb.class, P.class});
        xstream.processAnnotations(PatentFile_100005.class);
        xstream.processAnnotations(AbstractDrawing.class);
        xstream.processAnnotations(CnAbstFigure.class);
        xstream.processAnnotations(Img.class);
        xstream.processAnnotations(Pb.class);
        xstream.processAnnotations(P.class);
        PatentFile_100005 patentFile100005 = (PatentFile_100005) xstream.fromXML(xmlText);
        patentFile100005.setVersion(version);
        patentFile100005.setShenqingh(shenqingh);
        patentFile100005.setBaseDir(baseDir.replaceAll("\\\\","/"));
        return patentFile100005;
    }

    public List<Figure> getDigestDrawing(){
        if(abstractDrawing==null||abstractDrawing.getCnAbstFigure()==null){
            return null;
        }
        return abstractDrawing.getCnAbstFigure().getFigures();
    }
}
