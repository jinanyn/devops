package cn.com.gwssi.patent.xml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import lombok.Data;

@XStreamAlias("heading")
@XStreamConverter(value= ToAttributedValueConverter.class, strings={"text"})
@Data
public class Heading {

    private String text;
}
