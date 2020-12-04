package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("mode-for-invention")
@Data
public class ModeForInvention_100002 {
    @XStreamAsAttribute
    private String heading;

    @XStreamImplicit(itemFieldName="p")
    private List<P> pList;

    public List<P> getpList() {
        return pList == null ? pList = new ArrayList<P>() : pList;
    }

    public String getModeForInventionText(){
        StringBuffer mfitext = new StringBuffer();
        if(pList != null && pList.size() > 0){
            for(P text : pList){
                mfitext.append(text.getText());
            }
        }
        return mfitext.toString();
    }
}
