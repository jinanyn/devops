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
    @XmlElement(name = "CNT")
    private String cnt;
    @XmlElement(name = "SHENCHAYDM")
    private String shenchaydm;//审查员代码
    @XmlElement(name = "ANJIANZT")
    private String anjianzt;//
    @XmlElement(name = "LIUCHENGZT")
    private String liuchengzt;//
    @XmlElement(name = "ANJIANYWZT")
    private String anjianywzt;//
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
    @XmlElement(name = "RUSHENSJ")
    private String rushensj;//
}
