package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CnAbstFigure {
    @XStreamImplicit(itemFieldName="figure")
    private List<Figure> figures;

    @XStreamImplicit(itemFieldName="pb")
    private List<Pb> pbList;

    public List<Pb> getPbList() {
        return pbList == null ? pbList = new ArrayList<Pb>() : pbList;
    }
    public List<Figure> getFigures() {
        return figures == null ? figures = new ArrayList<Figure>() : figures;
    }
}
