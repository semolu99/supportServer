package surport.supportServer.common.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import surport.supportServer.common.dto.BaseResponse

@Controller
class MainController {

    @GetMapping("/")
    fun mainP() : String {
        return "main";
    }
}