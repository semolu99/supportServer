package surport.supportServer.common.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AdminController {
    @GetMapping("/admin")
    fun adminP() : String {
        return "admin";
    }
}