package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping({"utility/redisOperate/"})
public class RedisOperateController {
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = {"operRedisKeyValue"}, method = {RequestMethod.POST})
    public Map<String, String> operRedisKeyValue(@RequestParam("operWay") String operWay,@RequestParam("redisKey") String redisKey,@RequestParam("redisVal") String redisVal) throws IOException {
        if (StringUtils.isEmpty(operWay)) {
            log.info("operWay");
            throw new RuntimeException("operWay为空");
        }
        if (StringUtils.isEmpty(redisKey)) {
            log.info("redisKey为空");
            throw new RuntimeException("redisKey为空");
        }
        if (StringUtils.isEmpty(redisVal)) {
            log.info("redisKey为空");
            //throw new RuntimeException("redisKey为空");
        }
        Map map = new HashMap();
        if("get".equals(operWay)){
            Object value = this.redisUtil.get(redisKey);
            map.put("data", value == null ? "无此key["+redisKey+"]数据" : value.toString());
        }else if("set".equals(operWay)){
            Object value = this.redisUtil.set(redisKey,redisVal);
            map.put("data", "设置成功");
        }else{
            map.put("data", "不支持此操作");
        }

        map.put("result", "success");
        return map;
    }


}
