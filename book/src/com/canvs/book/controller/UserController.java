package com.canvs.book.controller;

import com.canvs.book.pojo.Cart;
import com.canvs.book.pojo.User;
import com.canvs.book.service.CartItemService;
import com.canvs.book.service.UserService;
import com.canvs.ssm.utils.Utils;

import javax.servlet.http.HttpSession;

public class UserController {
    private UserService userService;
    private CartItemService cartItemService;
    public String login(String uname, String pwd, HttpSession session){
        User user = userService.login(uname, pwd);
        if (user != null){
            Cart cart = cartItemService.getCart(user);
            user.setCart(cart);
            session.setAttribute("currUser",user);
            return "redirect:book";
        }
        return "index";
    }
    public String regist(String uname,String pwd,String email,String verify,HttpSession session){
        Object kaptchaSessionKey = session.getAttribute("KAPTCHA_SESSION_KEY");
        if (Utils.isNotEmpty(verify) && verify.equals(kaptchaSessionKey)){
            User user = userService.regist(new User(uname, pwd, email,0));
            return "user/login";
        }
        return "user/regist";
    }
}
