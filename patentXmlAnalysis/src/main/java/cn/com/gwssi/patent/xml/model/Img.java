package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

@XStreamAlias("img")
@Data
public class Img {
    @XStreamAsAttribute
    private String id;

    @XStreamAlias("file")
    @XStreamAsAttribute
    private String fileName;

    @XStreamAlias("wi")
    @XStreamAsAttribute
    private double width;

    @XStreamAlias("he")
    @XStreamAsAttribute
    private double heigth;

    @XStreamAlias("img-content")
    @XStreamAsAttribute
    private String imgContent;

    @XStreamAlias("img-format")
    @XStreamAsAttribute
    private String imgFormat;

    @XStreamAsAttribute
    private String orientation;

    @XStreamAsAttribute
    private String inline;

    private String imgUrl;
}
