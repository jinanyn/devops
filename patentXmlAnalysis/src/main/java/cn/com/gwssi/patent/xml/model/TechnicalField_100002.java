package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("technical-field")
@Data
public class TechnicalField_100002 {
    private String heading;

    @XStreamImplicit(itemFieldName="p")
    private List<P> pList;

    public List<P> getpList() {
        return pList == null ? pList = new ArrayList<P>() : pList;
    }

    public String getTechnicalFieldText(){
        StringBuffer tftext = new StringBuffer();
        if(pList != null && pList.size() > 0){
            for(P text : pList){
                tftext.append(text.getText());
            }
        }
        return tftext.toString();
    }
}
