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
@XmlRootElement(name = "server")
@XmlAccessorType(XmlAccessType.NONE)
public class ServerInfo {
    @XmlElement(name = "ip")
    private String ip;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "state")
    private String state;
    @XmlElement(name = "visitTime")
    private String visitTime;//对应时刻
}
