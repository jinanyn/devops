package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

@XStreamAlias("figure")
@Data
public class Figure {
    @XStreamAsAttribute
    private String id;

    @XStreamAsAttribute
    private String num;

    @XStreamAlias("figure-labels")
    @XStreamAsAttribute
    private String figureLabels;

    @XStreamAsAttribute
    private Img img;
}
