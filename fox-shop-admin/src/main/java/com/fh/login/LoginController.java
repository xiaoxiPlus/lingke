package com.fh.login;

import com.fh.authorization.LoginAnnotation;
import com.fh.codeGather.CommonReturn;
import com.fh.codeGather.ReturnCode;
import com.fh.umsAdmin.service.IUmsAdminService;
import com.fh.websocket.WebSocketServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
//
import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/loginController")
@Api(value = "登陆控制层")
public class LoginController {

    @Resource
    private IUmsAdminService adminService;
    @Resource
    private WebSocketServer webSocketServer;

    @PostMapping("/login")
    @ApiOperation("用户登陆")
    @LoginAnnotation("用户登陆")
    public CommonReturn loginUser(@RequestParam("username") @ApiParam(value = "用户名", required = true, type = "String") String username,
                                  @RequestParam("password") @ApiParam(value = "用户密码", required = true, type = "String") String password) throws IOException {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return CommonReturn.error(ReturnCode.USERNAME_PASSWORD_NULL);
        }
        webSocketServer.checkCurrUserLogin(username);
        String token = adminService.login(username, password);
        if (token == null) {
            return CommonReturn.error(ReturnCode.LOGIN_DISABLED);
        }

        return CommonReturn.success(token);
    }

    /*退出登陆*/
    @GetMapping("/adminLoginOut/{username}")
    public CommonReturn adminLoginOut(@PathVariable("username") String username) throws IOException {
        adminService.adminLoginOut(username);

        return CommonReturn.success();
    }
}