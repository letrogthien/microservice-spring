package gin.product_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/product")
public class Hello {
    @GetMapping("hello")
    String helloMethod(){
        return "hello bro";
    }
}
