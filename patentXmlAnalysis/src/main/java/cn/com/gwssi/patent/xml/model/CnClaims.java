package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("cn-claims")
@Data
public class CnClaims {
    @XStreamImplicit(itemFieldName = "p")
    private List<P> p;

    @XStreamImplicit(itemFieldName = "claim")
    private List<Claim> claimList;

    public List<Claim> getClaimList() {
        return claimList == null ? claimList = new ArrayList<Claim>() : claimList;
    }
}
