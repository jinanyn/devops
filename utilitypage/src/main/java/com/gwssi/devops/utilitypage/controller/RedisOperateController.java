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

    @RequestMapping(value = {"getRedisKeyValue"}, method = {RequestMethod.POST})
    public Map<String, String> getKeyValue(@RequestParam("redisKey") String redisKey) throws IOException {
        if (StringUtils.isEmpty(redisKey)) {
            log.info("redisKey为空");
            throw new RuntimeException("redisKey为空");
        }
        Object value = this.redisUtil.get(redisKey);

        Map map = new HashMap();
        map.put("result", "success");
        map.put("data", value.toString());
        return map;
    }
}
