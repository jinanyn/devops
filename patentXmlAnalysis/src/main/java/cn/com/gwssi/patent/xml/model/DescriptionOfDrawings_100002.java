package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

@XStreamAlias("description-of-drawings")
@Data
public class DescriptionOfDrawings_100002 {
    @XStreamAsAttribute
    private String heading;

    @XStreamImplicit(itemFieldName="p")
    private List<P> pList;

    public String getHeading() {
        return heading;
    }
    public String getDescriptionOfDrawingsText(){
        StringBuffer dodtext = new StringBuffer();
        if(pList != null && pList.size() > 0){
            for(P text : pList){
                dodtext.append(text.getText());
            }
        }
        return dodtext.toString();
    }
    public String getDescriptionOfDrawingsTextForSc0202008(){
        StringBuffer dodtext = new StringBuffer();
        if(pList != null && pList.size() > 0){
            for(P text : pList){
                dodtext.append(text.getText()).append("。");
            }
        }
        return dodtext.toString();
    }
}
