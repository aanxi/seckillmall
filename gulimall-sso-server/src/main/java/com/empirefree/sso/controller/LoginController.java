package com.empirefree.sso.controller;

import com.empirefree.sso.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @program: renren-fast
 * @description:
 * @author: huyuqiao
 * @create: 2022/02/05 12:01
 */
@Slf4j
@Controller
public class LoginController {

    private Logger logger = Logger.getLogger(String.valueOf(LoginController.class));

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ResponseBody
    @GetMapping("/userinfo")
    public String getUserInfo(@RequestParam("token") String token){
        return stringRedisTemplate.opsForValue().get(token);
    }

    @GetMapping("/login.html")
    public String loginPage(@RequestParam("redirect_url") String url, User user, Model model, @CookieValue(value = "sso_token",required = false) String sso_token){
        model.addAttribute("url", url);

/*        if(!StringUtils.isEmpty(sso_token)){
            // 有人登录过
            return "redirect:" + user.getUrl() + "?username=" + user.getUsername() + "&token=" + sso_token;
        }
        model.addAttribute("url", user.getUrl());
        model.addAttribute("username", user.getUsername());*/

        if (!StringUtils.isEmpty(sso_token)){
            return "redirect:" + url + "?token=" + sso_token;

        }
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(String username, String password, String url, User user, HttpServletResponse response){
        logger.info("LoginController.doLogin--进入登录界面:" + url);
/*        if(!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword()) && "fire".equals(user.getUsername()) && "fire".equals(user.getPassword())){
            // 登录成功跳转 跳回之前的页面
            String uuid = UUID.randomUUID().toString().replace("-", "");
            Cookie cookie = new Cookie("sso_token", uuid);
            response.addCookie(cookie);
            stringRedisTemplate.opsForValue().set(user.getUser(), uuid, 30, TimeUnit.MINUTES);
            return "redirect:" + user.getUrl() + "?username=" + user.getUser() + "&token=" + uuid;
        }*/
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
            String uuid = UUID.randomUUID().toString().replace("-", "");
            stringRedisTemplate.opsForValue().set(uuid, username);
            // 可以看出，默认是没有Cookie的，除非session设置或者response添加
            Cookie cookie = new Cookie("sso_token", uuid);
            response.addCookie(cookie);
            return "redirect:" + url + "?token=" + uuid;
        }
        // 登录失败
        return "login";
    }
}
