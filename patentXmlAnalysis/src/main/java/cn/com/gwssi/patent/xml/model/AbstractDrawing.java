package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("cn-abstract")
@Data
public class AbstractDrawing {
    @XStreamImplicit(itemFieldName="p")
    private List<P> pList;

    @XStreamAlias("cn-abst-figure")
    private CnAbstFigure cnAbstFigure;

    public List<P> getpList() {
        return pList == null ? pList = new ArrayList<P>() : pList;
    }
}
