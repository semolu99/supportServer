package surport.supportServer.notification.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class NotificationPageController {

    @GetMapping("/notifications")
    fun notification():String{
        return "notification";
    }
}