package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.io.xml.XppDriver;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@XStreamAlias("cn-application-body")
@Data
public class PatentFile_100002 {
    @XStreamAsAttribute
    private String lang;

    @XStreamAsAttribute
    private String country;

    @XStreamAlias("description")
    @XStreamAsAttribute
    private Description description;

    private String splitDescriptionText;
    private String shenqingh;
    private String version;
    private String baseDir;//解析实体的存储路径

    /**
     *
     */
    public PatentFile_100002 parseXmlText(String xmlText, String shenqingh, String version, String baseDir) {
        XStream xstream = new XStream(new XppDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(new Class[]{PatentFile_100002.class, Description.class, TechnicalField_100002.class, BackgroundArt_100002.class,
                Disclosure_100002.class, DescriptionOfDrawings_100002.class, ModeForInvention_100002.class, P.class, Pb.class});
        xstream.processAnnotations(PatentFile_100002.class);
        xstream.processAnnotations(Description.class);
        // xstream.processAnnotations(InventionTitle_100002.class);
        xstream.processAnnotations(TechnicalField_100002.class);
        xstream.processAnnotations(BackgroundArt_100002.class);
        xstream.processAnnotations(Disclosure_100002.class);
        xstream.processAnnotations(DescriptionOfDrawings_100002.class);
        xstream.processAnnotations(ModeForInvention_100002.class);
        xstream.processAnnotations(P.class);
        xstream.processAnnotations(Pb.class);

        PatentFile_100002 patentFile100002 = (PatentFile_100002) xstream.fromXML(xmlText);
        patentFile100002.setVersion(version);
        patentFile100002.setShenqingh(shenqingh);
        patentFile100002.setBaseDir(baseDir.replaceAll("\\\\","/"));
        return patentFile100002;
    }

    public String getDescriptionText() {
        if (description == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(description.getInventionTitle()).append("\r\n");
        sb.append(description.getTechnicalField() == null ? "" : description.getTechnicalField().getHeading())
                .append("\r\n").append(description.getTechnicalFieldText()).append("\r\n");
        sb.append(description.getBackgroundArt() == null ? "" : description.getBackgroundArt().getHeading())
                .append("\r\n").append(description.getBackgroundArtText()).append("\r\n");
        sb.append(description.getDescriptionOfDrawings() == null ? ""
                : description.getDescriptionOfDrawings().getHeading()).append("\r\n")
                .append(description.getDescriptionOfDrawingsText()).append("\r\n");
        sb.append(description.getModeForInvention() == null ? "" : description.getModeForInvention().getHeading())
                .append("\r\n").append(description.getModeForInventionText()).append("\r\n");
        sb.append(description.getDisclosure() == null ? "" : description.getDisclosure().getHeading()).append("\r\n")
                .append(description.getDisclosureText()).append("\r\n");

        return sb.toString();
    }

    public String getDescriptionTextByXB() {
        if (description == null) {
            return null;
        }
        // 说明书

        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(description.getInventionTitle())) {
            sb.append("<h2 style=\" text-align:center; font-size: 20px; letter-spacing: 2; line-height: 2;\">"
                    + description.getInventionTitle() + "</h2>");
        }

        if ((description != null) && (getDescription().getPList() != null)) {
            List<P> pList = getDescription().getPList();
            if (pList != null && pList.size() > 0) {
                for (P p : pList) {
                    if (null != p.getNum() && p.getNum().indexOf("XXXX") == -1)
                        sb.append("<p class='text_indent_28'>" + "<b class='sms_b'>[" + p.getNum() + "]</b>"
                                + p.getText() + "</p>");
                    else
                        sb.append("<p class='text_indent_28'>" + p.getText() + "</p>");
                }
            }
        }

        // 取技术领域
        if ((description != null) && (description.getTechnicalField() != null)
                && (description.getTechnicalField().getpList() != null)) {
            List<P> pList = description.getTechnicalField().getpList();
            sb.append("<p class='text_indent_28'><b>" + description.getTechnicalField().getHeading() + "</b></p>");
            if (pList != null && pList.size() > 0) {
                for (P p : pList) {
                    if (null != p.getNum() && p.getNum().indexOf("XXXX") == -1)
                        sb.append("<p class='text_indent_28'>" + "<b class='sms_b'>[" + p.getNum() + "]</b>"
                                + p.getText() + "</p>");
                    else
                        sb.append("<p class='text_indent_28'>" + p.getText() + "</p>");
                }
            }
        }

        // 取背景技术
        if ((description != null) && (description.getBackgroundArt() != null)
                && (description.getBackgroundArt().getPList() != null)) {
            List<P> ppList = description.getBackgroundArt().getPList();
            sb.append("<p class='text_indent_28'><b>" + description.getBackgroundArt().getHeading() + "</b></p>");
            if (ppList != null && ppList.size() > 0) {
                for (P p : ppList) {
                    if (null != p.getNum() && p.getNum().indexOf("XXXX") == -1)
                        sb.append("<p class='text_indent_28'>" + "<b class='sms_b'>[" + p.getNum() + "]</b>"
                                + p.getText() + "</p>");
                    else
                        sb.append("<p class='text_indent_28'>" + p.getText() + "</p>");
                }
            }
        }

        // 取发明内容
        if ((description != null) && (description.getDisclosure() != null)
                && (description.getDisclosure().getPList() != null)) {
            List<P> pppList = description.getDisclosure().getPList();
            sb.append("<p class='text_indent_28'><b>" + description.getDisclosure().getHeading() + "</b></p>");
            if (pppList != null && pppList.size() > 0) {
                for (P p : pppList) {
                    if (null != p.getNum() && p.getNum().indexOf("XXXX") == -1)
                        sb.append("<p class='text_indent_28'>" + "<b class='sms_b'>[" + p.getNum() + "]</b>"
                                + p.getText() + "</p>");
                    else
                        sb.append("<p class='text_indent_28'>" + p.getText() + "</p>");
                }
            }
        }

        // 取附图说明
        if ((description != null) && (description.getDescriptionOfDrawings() != null)
                && (description.getDescriptionOfDrawings().getPList() != null)) {
            List<P> ppppList = description.getDescriptionOfDrawings().getPList();
            sb.append(
                    "<p class='text_indent_28'><b>" + description.getDescriptionOfDrawings().getHeading() + "</b></p>");
            if (ppppList != null && ppppList.size() > 0) {
                for (P p : ppppList) {
                    if (null != p.getNum() && p.getNum().indexOf("XXXX") == -1)
                        sb.append("<p class='text_indent_28'>" + "<b class='sms_b'>[" + p.getNum() + "]</b>"
                                + p.getText() + "</p>");
                    else
                        sb.append("<p class='text_indent_28'>" + p.getText() + "</p>");
                }
            }
        }

        // 取具体实施方式
        if ((description != null) && (description.getModeForInvention() != null)
                && (description.getModeForInvention().getpList() != null)) {
            List<P> pppppList = description.getModeForInvention().getpList();
            sb.append("<p class='text_indent_28'><b>" + description.getModeForInvention().getHeading() + "</b></p>");
            if (pppppList != null && pppppList.size() > 0) {
                for (P p : pppppList) {
                    if (null != p.getNum() && p.getNum().indexOf("XXXX") == -1)
                        sb.append("<p class='text_indent_28'>" + "<b class='sms_b'>[" + p.getNum() + "]</b>"
                                + p.getText() + "</p>");
                    else
                        sb.append("<p class='text_indent_28'>" + p.getText() + "</p>");
                }
            }
        }
        return sb.toString();
    }
}
