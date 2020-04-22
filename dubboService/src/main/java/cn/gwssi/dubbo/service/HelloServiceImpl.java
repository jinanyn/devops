package cn.gwssi.dubbo.service;

import cn.gwssi.api.dubbo.service.IHelloService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import java.util.Date;

@Service
@Component
public class HelloServiceImpl implements IHelloService {
    @Override
    public String hi(String name, int age) {
        System.err.println("Provider接收到的是：" + name + "," + age);
        return "your name is:" + name + ",age is:" + age + "," + new Date().toString();
    }
}
