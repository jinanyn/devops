package com.gwssi.devops.utilitypage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "context")
@XmlAccessorType(XmlAccessType.NONE)
public class RtnDataList {

    @XmlElement(name = "error-code")
    private String errorCode;

    @XmlElement(name = "flowno")
    private String flowno;

    @XmlElement(name = "rtnData")
    private List<RtnData> bizDataList;
}

