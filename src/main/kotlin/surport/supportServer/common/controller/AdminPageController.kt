package surport.supportServer.common.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import java.util.SimpleTimeZone

@Controller
class AdminPageController {
    @GetMapping("/admin")
    fun adminP() : String {
        return "admin";
    }

    @PostMapping("/login")
    fun loginP():String{
        return "login"
    }
}