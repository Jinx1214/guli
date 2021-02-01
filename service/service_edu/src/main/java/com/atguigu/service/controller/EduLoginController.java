package com.atguigu.service.controller;

import com.atguigu.Result.R;
import org.springframework.web.bind.annotation.*;

import javax.crypto.interfaces.PBEKey;

@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {


    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R getInfo(){

        return R.ok().data("roles","[admin]").data("name","Super name").data("avatar","https://www.baidu.com/link?url=6MXMfScjkj1TLYU51WDpakVVDfFIhGD4aHmnKpFHTwwddsdmR7e1V3NkoTOwuiO9chFbmeIlB3Cgm_Nie42bCzDJ7L3h6KPq0sTmXZcNXidoD3UUPG_WeLzHq0AuLwDHVdpDSum7JbDbL2qnUOfr1KVwKO18Dhbo5PdHeOy9zQQd6ocbdUdAzBUkiTfY2qwQyX4WBIy1I1Ed0mlUAhByVKhFd5Uk1-JeowXUekyx_zWbniHokijzH2OCWrCam31UBRehX31yxr2YcE5c3e2Jlr8CpGovVumqRM_J5d_Av3zf9T7eL8DSlfZ9Tsde2XStSfuWGpeWtQRjGO3VGzRfPUyfg7AFIcXnE5e1g57M4r96hOJicnRJdzls0-8yWyNb4LAN0JoIqlz2cqdtsQlumKXZqaB_CS0Tjcf9rfIjcDcdD4PiNrgYyOh5V9tZQAfaEWtCPw7MiJMdqpv7O6dJcrXTmPENGpuuudJK_i2MHTEXeWwEzXllWN8hNu9VqE-BeXIYpph53b1ztMW0kgA-ttxbqorN61veCK1ISM7wkK3roY4PRrA6t9TNpQ0XSjFCc_WUSwFgcWkVDxxjMeXEba&click_t=1609145368653&s_info=1905_902&wd=&eqid=d3a7393400012193000000065fe99c14");
    }
}
