package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.io.xml.XppDriver;
import lombok.Data;

import java.util.List;

@XStreamAlias("cn-application-body")
@Data
public class PatentFile_100001 {
    @XStreamAsAttribute
    private String lang;

    @XStreamAsAttribute
    private String country;

    @XStreamAlias("cn-claims")
    @XStreamAsAttribute
    private CnClaims cnClaims;

    private String splitClaimsText;

    private String shenqingh;

    private String version;

    private String baseDir;//解析实体的存储路径

    public PatentFile_100001 parseXmlText(String xmlText, String shenqingh, String version, String baseDir) {
        XStream xstream = new XStream(new XppDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(new Class[]{PatentFile_100001.class, CnClaims.class, Claim.class, ClaimText.class, Pb.class});
        xstream.processAnnotations(PatentFile_100001.class);
        xstream.processAnnotations(CnClaims.class);
        xstream.processAnnotations(Claim.class);
        xstream.processAnnotations(ClaimText.class);
        xstream.processAnnotations(Pb.class);
        PatentFile_100001 patentFile100001 = (PatentFile_100001) xstream.fromXML(xmlText);
        patentFile100001.setVersion(version);
        patentFile100001.setShenqingh(shenqingh);
        patentFile100001.setBaseDir(baseDir.replaceAll("\\\\","/"));
        return patentFile100001;
    }

    public String getClaimsText() {
        if (cnClaims == null) {
            return null;
        }
        List<Claim> claimList = cnClaims.getClaimList();
        if (claimList == null || claimList.size() == 0) {
            return null;
        }
        StringBuffer claimsText = new StringBuffer();
        for (Claim c : claimList) {
            claimsText.append(c.getNum()).append(".").append(c.getClaimText()).append("\n");
        }
        return claimsText.toString();
    }
}
