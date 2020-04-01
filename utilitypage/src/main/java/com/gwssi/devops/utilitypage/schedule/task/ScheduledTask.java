package com.gwssi.devops.utilitypage.schedule.task;

import java.util.concurrent.ScheduledFuture;

/**
 * @program:
 * @description: 定时任务控制类
 * @author:
 * @date:
 **/
public final class ScheduledTask {

    public volatile ScheduledFuture<?> future;
    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}
