package cn.gwssi.util;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class ShellExecUtil {
    public static List<String> runShell(String shStr)
            throws Exception
    {
        List strList = new ArrayList();

        Process process = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", shStr }, null, null);

        InputStreamReader ir = new InputStreamReader(process
                .getInputStream());
        LineNumberReader input = new LineNumberReader(ir);

        process.waitFor();
        String line;
        while ((line = input.readLine()) != null) {
            strList.add(line);
        }
        return strList;
    }
}
