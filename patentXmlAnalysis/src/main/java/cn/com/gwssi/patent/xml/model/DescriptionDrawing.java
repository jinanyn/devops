package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("cn-drawings")
@Data
public class DescriptionDrawing {
    @XStreamImplicit(itemFieldName="figure")
    private List<Figure> figureList;

    @XStreamImplicit(itemFieldName="pb")
    private List<Pb> pbList;

    @XStreamImplicit(itemFieldName="cn-drawing-p")
    private List<CnDrawingP> cnDrawingPList;

    public List<Pb> getPbList() {
        return pbList == null ? pbList = new ArrayList<Pb>() : pbList;
    }
    public List<Figure> getFigureList() {
        return figureList == null ? figureList = new ArrayList<Figure>() : figureList;
    }
    public List<CnDrawingP> getCnDrawingPList() {
        return cnDrawingPList == null ? cnDrawingPList = new ArrayList<CnDrawingP>() : cnDrawingPList;
    }
}
