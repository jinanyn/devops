package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("description")
@Data
public class Description {
    @XStreamAlias("invention-title")
    @XStreamAsAttribute
    private String inventionTitle;
    /**
     * 技术领域
     */
    @XStreamAlias("technical-field")
    @XStreamAsAttribute
    private TechnicalField_100002 technicalField;

    /**
     * 背景技术
     */
    @XStreamAlias("background-art")
    @XStreamAsAttribute
    private BackgroundArt_100002 backgroundArt;

    /**
     * 实用新型内容
     */
    @XStreamAlias("disclosure")
    @XStreamAsAttribute
    private Disclosure_100002 disclosure;

    /**
     * 附图说明
     */
    @XStreamAlias("description-of-drawings")
    @XStreamAsAttribute
    private DescriptionOfDrawings_100002 descriptionOfDrawings;

    /**
     * 具体实施方式
     */
    @XStreamAlias("mode-for-invention")
    @XStreamAsAttribute
    private ModeForInvention_100002 modeForInvention;

    @XStreamImplicit(itemFieldName="p")
    private List<P> pList;

    public String getInventionTitle() {
        return inventionTitle==null?inventionTitle:inventionTitle.trim();
    }

    //获取技术领域
    public String getTechnicalFieldText(){
        if(null!=technicalField){
            return technicalField.getTechnicalFieldText();
        }
        return "";
    }

    //获取背景技术
    public String getBackgroundArtText(){
        if(null!=backgroundArt){
            return backgroundArt.getBackgroundArtText();
        }
        return "";
    }

    //获取发明内容
    public String getDisclosureText(){
        if(null!=disclosure){
            return disclosure.getDisclosureText();
        }
        return "";
    }

    //获取附图说明
    public String getDescriptionOfDrawingsText(){
        if(null!=descriptionOfDrawings){
            return descriptionOfDrawings.getDescriptionOfDrawingsText();
        }
        return "";
    }

    //获取附图说明for SC0202008
    public String getDescriptionOfDrawingsTextForSc0202008(){
        if(null!=descriptionOfDrawings){
            return descriptionOfDrawings.getDescriptionOfDrawingsTextForSc0202008();
        }
        return "";
    }

    //获取具体实施方式
    public String getModeForInventionText(){
        if(null!=modeForInvention){
            return modeForInvention.getModeForInventionText();
        }
        return "";
    }

    //获取所有段落
    public List<P> getDescriptionPList(){
        List<P> pList = new ArrayList<P>();
        if(null!=technicalField){
            pList.addAll(technicalField.getpList());
        }
        if(null!=backgroundArt){
            pList.addAll(backgroundArt.getPList());
        }
        if(null!=disclosure){
            pList.addAll(disclosure.getPList());
        }
        if(null!=descriptionOfDrawings){
            pList.addAll(descriptionOfDrawings.getPList());
        }
        if(null!=modeForInvention){
            pList.addAll(modeForInvention.getpList());
        }
        pList.addAll(this.pList);
        return pList;
    }
}
