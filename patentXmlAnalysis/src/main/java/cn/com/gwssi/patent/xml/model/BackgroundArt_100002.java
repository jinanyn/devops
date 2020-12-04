package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

@XStreamAlias("background-art")
@Data
public class BackgroundArt_100002 {
    @XStreamAsAttribute
    private String heading;

    @XStreamImplicit(itemFieldName="p")
    private List<P> pList;

    public String getBackgroundArtText(){
        StringBuffer bgatext = new StringBuffer();
        if(pList != null && pList.size() > 0){
            for(P text : pList){
                bgatext.append(text.getText());
            }
        }
        return bgatext.toString();
    }
}
