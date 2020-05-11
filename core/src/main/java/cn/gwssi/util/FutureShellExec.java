package cn.gwssi.util;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class FutureShellExec implements Callable<List<String>> {

    private String shStr;

    public FutureShellExec(String shStr) {
        this.shStr = shStr;
    }

    @Override
    public List<String> call() throws Exception {
        List<String> strList = new ArrayList();

        log.info("shell command = " + this.shStr);
        Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", this.shStr}, null, null);
        process.waitFor();
        InputStreamReader ir = new InputStreamReader(process
                .getInputStream());
        LineNumberReader input = new LineNumberReader(ir);
        String line;
        while ((line = input.readLine()) != null) {
            strList.add(line);
        }
        return strList;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        FutureShellExec shellExec = new FutureShellExec("cmd cmd cmd");
        FutureTask<List<String>> futureTask = new FutureTask<>(shellExec);
        //futureTask.get(15L, TimeUnit.SECONDS);
        Thread thread = new Thread(futureTask);
        thread.setName("Task thread");
        thread.start();
        List<String> strList = futureTask.get(15L, TimeUnit.SECONDS);
        log.info(strList.toString());
        /*// 5. 调用get()方法获取任务结果,如果任务没有执行完成则阻塞等待
        new Thread(() -> {
            try {
                List<String> strList = futureTask.get(15L, TimeUnit.SECONDS);
                log.info(strList.toString());
            } catch (InterruptedException | TimeoutException | ExecutionException e) {
                e.printStackTrace();
            }
        }, "thread one").start();*/
    }
}
