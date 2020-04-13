package com.gwssi.devops.utilitypage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "serverInfoList")
public class ServerInfoList {
    @XmlElement(name = "server")
    private List<ServerInfo> ServerInfoList;
}
