package cn.gwssi.util;

import org.apache.commons.lang.StringUtils;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShellExecUtil {
    public static List<String> runShell(String shStr, Long... timeOut)
            throws Exception {
        List<String> strList = new ArrayList();

        Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", shStr}, null, null);

        InputStreamReader ir = new InputStreamReader(process
                .getInputStream());
        LineNumberReader input = new LineNumberReader(ir);
        if (timeOut != null && timeOut.length > 0) {
            process.waitFor(timeOut[0], TimeUnit.SECONDS);
        } else {
            process.waitFor();
        }
        String line;
        while ((line = input.readLine()) != null) {
            strList.add(line);
        }
        return strList;
    }
}
