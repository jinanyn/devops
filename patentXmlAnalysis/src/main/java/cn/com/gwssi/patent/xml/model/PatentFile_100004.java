package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.io.xml.XppDriver;
import lombok.Data;

import java.util.List;

@XStreamAlias("cn-application-body")
@Data
public class PatentFile_100004 {

    @XStreamAsAttribute
    private String lang;

    @XStreamAsAttribute
    private String country;

    @XStreamAlias("cn-abstract")
    @XStreamAsAttribute
    private DescriptionAbstract descriptionAbstract;

    private String splitCnAbstractText;

    private String shenqingh;

    private String version;

    private String baseDir;//解析实体的存储路径

    public PatentFile_100004 parseXmlText(String xmlText, String shenqingh, String version, String baseDir) {
        XStream xstream = new XStream(new XppDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(new Class[]{PatentFile_100004.class, DescriptionAbstract.class, Heading.class, P.class});
        xstream.processAnnotations(PatentFile_100004.class);
        xstream.processAnnotations(DescriptionAbstract.class);
        xstream.processAnnotations(Heading.class);
        xstream.processAnnotations(P.class);
        PatentFile_100004 patentFile100004 = (PatentFile_100004) xstream.fromXML(xmlText);
        patentFile100004.setVersion(version);
        patentFile100004.setShenqingh(shenqingh);
        patentFile100004.setBaseDir(baseDir.replaceAll("\\\\","/"));
        return patentFile100004;
    }

    /**
     * 1：解析成功 2：未找到版本信息 3：文件不存在 4：解析异常
     */
    private String status;
    /**
     * 不是生成的get，包含了逻辑在里边
     *
     * @return
     */
    public String getContent() {
        if (descriptionAbstract == null) {
            return null;
        }
        String content = null;
        StringBuilder sb = new StringBuilder();
        List<P> pList = descriptionAbstract.getpList();
        if (pList != null) {
            for (P p : pList) {
                if (p.getText() == null) {
                    continue;
                }
                sb.append(p.getText());
            }
        }
        content = sb.length() > 0 ? sb.toString() : "";
        return content;
    }
}
