package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.ay.AyRedisUtil;
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
@RequestMapping({"utility/ayredisOperate/"})
public class AyRedisOperateController {
    @Autowired
    private AyRedisUtil ayRedisUtil;

    @RequestMapping(value = {"selectajsx"}, method = {RequestMethod.POST})
    public Map<String, String> getKeyValue(@RequestParam("shenqingh") String shenqingh,@RequestParam("shenchadydm") String shenchadydm) throws IOException {
        if (StringUtils.isEmpty(shenqingh)) {
            log.info("shenqingh为空");
            throw new RuntimeException("shenqingh为空");
        }
        if (StringUtils.isEmpty(shenchadydm)) {
            log.info("shenchadydm为空");
            throw new RuntimeException("shenchadydm为空");
        }
        Object value = this.ayRedisUtil.zrankaj(shenqingh,shenchadydm);

        Map map = new HashMap();
        log.debug("xxxx"+value.toString());
        map.put("result", "success");
        map.put("data", value.toString());
        return map;
    }


}
