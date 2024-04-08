package com.jpa.user1984.controller;



import com.jpa.user1984.security.domain.CustomMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String homeController(@AuthenticationPrincipal CustomMember customMember){
        log.info("************* / - customMember : {}", customMember);
        if(customMember == null){
            return "home";
        }
        return "loginHome";
    }

    @GetMapping("/order/book")
    public String bookOrder() { // 임시
        return "frontend/order/book/buy";
    }

    @GetMapping("/cms/home")
    public String cmsHome(){
        return "backend/home/home";
    }

    @GetMapping("/main/main")
    public String memberHome(){
        return "frontend/home/main";
    }
}
