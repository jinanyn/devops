package cn.gwssi.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "rtnData")
@XmlAccessorType(XmlAccessType.FIELD)
public class NoticeInfo {
    @XmlElement(name = "RID")
    private String rid;
    @XmlElement(name = "SHENQINGH")
    private String shenqingh;
    @XmlElement(name = "TONGZHISLX")
    private String tongzhislx;
    @XmlElement(name = "FID")
    private String fid;
    @XmlElement(name = "WENJIANCFLJ")
    private String wenjiancflj;
    @XmlElement(name = "TUXINGWJCFLJ")
    private String tuxingwjcflj;
    @XmlElement(name = "TONGZHISZT")
    private String tongzhiszt;
}
