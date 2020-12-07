package cn.com.gwssi.patent.xml.model;

import lombok.Data;

@Data
public class RegexMatch {
    private String matchStr;
    private Integer start;
    private Integer end;

    public RegexMatch(String matchStr, Integer start, Integer end) {
        this.matchStr = matchStr;
        this.start = start;
        this.end = end;
    }
}
