package com.clx.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.clx.common.R;
import com.clx.entity.User;
import com.clx.service.UserService;
import com.clx.utils.ValidateCodeUtils;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Send a text message with a verification code on your phone
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //Get a mobile phone number
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)){
            //Generate a random 4-digit verification code
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code:{}", code);


            //Call the SMS API provided by Alibaba Cloud to send SMS
            // SMSUtils.sendMessage("panda delivery","",phone, code);

            //You need to save the generated verification code and save it to the session
            // session.setAttribute(phone,code);

            //The generated verification code is cached in Redis and set to be valid for 5 minutes
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

            return R.success("The SMS message of the mobile phone verification code is successfully sent");
        }

        return R.error("The SMS failed to be sent");
    }


    /**
     * Mobile user login
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());

        //Get a mobile phone number
        String phone = map.get("phone").toString();
        //Get a verification code
        String code = map.get("code").toString();
        //Obtain the saved verification code from the session
        // Object codeInSession = session.getAttribute(phone);

        //Get the cached verification code from Redis
        Object codeInSession = redisTemplate.opsForValue().get(phone);

        //Compare the verification code (the verification code submitted on the page is compared with the verification code saved in the session)
        if (codeInSession != null && codeInSession.equals(code)){
            //If the comparison is successful, the login is successful
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);

            User user = userService.getOne(queryWrapper);
            if (user == null){
                //Determine whether the user corresponding to the current mobile phone number is a new user,
                // and if it is a new user, the registration will be automatically completed
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());

            //If the user logs in successfully, delete the verification code cached in Redis
            redisTemplate.delete(phone);
            return R.success(user);

        }

        return R.error("Login failed");
    }


}
