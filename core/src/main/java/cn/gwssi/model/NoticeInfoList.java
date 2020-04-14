package cn.gwssi.model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "context")
@XmlAccessorType(XmlAccessType.FIELD)
public class NoticeInfoList<T> {
    @XmlElement(name = "error-code")
    private String errorCode;
    @XmlElement(name = "flowno")
    private String flowno;
    @XmlElement(name = "select-key")
    private String selectKey;
    @XmlAnyElement(lax = true)
    //@XmlElement(name = "rtnData")
    private List<T> bizDataList;
}
