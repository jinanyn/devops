package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("cn-drawing-p")
@Data
public class CnDrawingP {

    @XStreamImplicit(itemFieldName="p")
    private List<P> pList;

    public List<P> getpList() {
        return pList == null ? pList = new ArrayList<P>() : pList;
    }
}
