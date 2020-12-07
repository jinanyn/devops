package cn.com.gwssi.patent.xml.service.impl;

import cn.com.gwssi.patent.xml.model.Claim;
import cn.com.gwssi.patent.xml.model.CnClaims;
import cn.com.gwssi.patent.xml.model.PatentFile_100001;
import cn.com.gwssi.patent.xml.service.ICKEditorContentAnalysis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Slf4j
@Service
public class CKEditorContentAnalysisImpl implements ICKEditorContentAnalysis {
    @Override
    public String claim(@NotEmpty PatentFile_100001 patentFile100001) {
        CnClaims cnClaims = patentFile100001.getCnClaims();
        if(cnClaims == null){
            return "";
        }
        List<Claim> claimList = cnClaims.getClaimList();
        if(claimList == null || claimList.size() == 0){
            return "";
        }
        StringBuilder strBui = new StringBuilder();
        claimList.stream().forEach(claim -> {
            strBui.append("<p><span id="+claim.getId()+" num ="+claim.getNum()+"></span>");
            strBui.append("<strong>"+claim.getNum()+".</strong>");
            strBui.append(claim.getClaimText());
            strBui.append("</p>");
        });
        return strBui.toString();
    }
}
