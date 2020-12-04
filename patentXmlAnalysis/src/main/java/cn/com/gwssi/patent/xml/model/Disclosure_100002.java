package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

@XStreamAlias("disclosure")
@Data
public class Disclosure_100002 {
    @XStreamAsAttribute
    private String heading;

    @XStreamImplicit(itemFieldName="p")
    private List<P> pList;

    public String getDisclosureText(){
        StringBuffer disclosuretext = new StringBuffer();
        if(pList != null && pList.size() > 0){
            for(P text : pList){
                disclosuretext.append(text.getText());
            }
        }
        return disclosuretext.toString();
    }
}
