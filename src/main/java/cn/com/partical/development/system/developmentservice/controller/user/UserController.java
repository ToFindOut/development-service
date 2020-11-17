package cn.com.partical.development.system.developmentservice.controller.user;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wang
 * @date 2020/11/17 14:55
 */
@RestController
@RequestMapping("/user/manage")
@Api(tags = "用户模块管理")
public class UserController {

    @RequestMapping(value = "/test/single", method = RequestMethod.GET)
    public void testSingle() {
            System.out.println("测试");
    }
}
