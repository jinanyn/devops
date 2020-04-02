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
}
