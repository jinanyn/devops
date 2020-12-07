package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("claim")
@Data
public class Claim {

    @XStreamAsAttribute
    private String id;

    @XStreamAsAttribute
    private String num;

    @XStreamImplicit(itemFieldName="claim-text")
    private List<ClaimText> claimTextList;

    public List<ClaimText> getClaimTextList() {
        return claimTextList == null ? claimTextList = new ArrayList<ClaimText>() : claimTextList;
    }

    public String getClaimText(){
        StringBuffer claimtext = new StringBuffer();
        if(claimTextList != null && claimTextList.size() > 0){
            for(ClaimText text : claimTextList){
                claimtext.append(text.getText());
            }
        }
        return claimtext.toString();
    }
}
