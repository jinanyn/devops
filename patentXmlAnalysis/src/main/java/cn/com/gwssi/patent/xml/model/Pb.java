package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@XStreamAlias("pb")
@Data
public class Pb {
    @XStreamAlias("pnum")
    private int num;
}
