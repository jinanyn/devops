package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("p")
@XStreamConverter(value= ToAttributedValueConverter.class, strings={"text"})
@Data
public class P {
    @XStreamAsAttribute
    private String Italic;

    @XStreamAsAttribute
    private String num;

    private String text;

    @XStreamImplicit
    private List<Pb> pbList;

    public List<Pb> getPbList() {
        return pbList == null ? pbList = new ArrayList<Pb>() : pbList;
    }
}
