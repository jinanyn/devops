package com.gwssi.devops.utilitypage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "rtnData")
@XmlAccessorType(XmlAccessType.NONE)
public class RtnData {

    @XmlElement(name = "SHENQINGH")
    private String shenqingh;
    @XmlElement(name = "ZAIXIANSQH")
    private String zaixiansqh;
    @XmlElement(name = "CNT")
    private String cnt;
    @XmlElement(name = "SHENCHAYDM")
    private String shenchaydm;//审查员代码
    @XmlElement(name = "ANJIANZT")
    private String anjianzt;//
    @XmlElement(name = "ENTRYID")
    private String entryId;//
    @XmlElement(name = "LIUCHENGZT")
    private String liuchengzt;//
    @XmlElement(name = "ANJIANYWZT")
    private String anjianywzt;//
    @XmlElement(name = "ZHUANGTAIMC")
    private String zhuangtaimc;
    @XmlElement(name = "ZAIXIANAJZTMC")
    private String zaixianajztmc;
    @XmlElement(name = "ZAIXIANAJYWZT")
    private String zaixianajywzt;//
    @XmlElement(name = "YOUXIANQZT")
    private String youxianqzt;//
    @XmlElement(name = "DANGQIANZTBH")
    private String dangqianztbh;
    @XmlElement(name = "CODENAME")
    private String codename;//
    @XmlElement(name = "ZANTINGBJ")
    private String zantingbj;//
    @XmlElement(name = "GUAQIBJ")
    private String guaqibj;//
    @XmlElement(name = "ZHONGZHIBJ")
    private String zhongzhibj;//
    @XmlElement(name = "SUODINGBJ")
    private String suodingbj;//
    @XmlElement(name = "XH")
    private String xh;//
    @XmlElement(name = "TONGZHISLX")
    private String tongzhislx;//
    @XmlElement(name = "TONGZHISZT")
    private String tongzhiszt;//
    @XmlElement(name = "FAWENR")
    private String fawenr;//
    @XmlElement(name = "FASONGR")
    private String fasongr;//
    @XmlElement(name = "FAWENXLH")
    private String fawenxlh;//
    @XmlElement(name = "RUSHENSJ")
    private String rushensj;//
    @XmlElement(name = "TNAME")
    private String tname;
    @XmlElement(name = "NAME")
    private String name;
    @XmlElement(name = "STARTTIME")
    private String startTime;
    @XmlElement(name = "ENDTIME")
    private String endTime;
    @XmlElement(name = "RID")
    private String rid;
    @XmlElement(name = "PPHID")
    private String pphid;
    @XmlElement(name = "QIXIANSLH")
    private String qixianslh;
    @XmlElement(name = "NOTE")
    private String note;
    @XmlElement(name = "TEXT")
    private String text;
    @XmlElement(name = "RUANSAOPC")
    private String ruansaopc;
    @XmlElement(name = "STATUS")
    private String status;
    @XmlElement(name = "ANJIANSCCXD")
    private String anjiansccxd;
    @XmlElement(name = "STATE")
    private String state;
    @XmlElement(name = "DAIMAHID")
    private String daimahid;
    @XmlElement(name = "DAIMAHWJBBH")
    private String daimahwjbbh;
}
