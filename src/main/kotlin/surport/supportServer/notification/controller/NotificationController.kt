package surport.supportServer.notification.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import surport.supportServer.notification.dto.NotificationDto
import surport.supportServer.notification.service.NotificationService

@RestController
class NotificationController(private val notificationService: NotificationService) {

    @GetMapping("/notification")
    fun notification():String{
        return "notification"
    }
    @PostMapping("/add")
    fun addNotification(@ModelAttribute notificationDto: NotificationDto, redirectAttributes: RedirectAttributes): String {
        notificationService.addNotification(notificationDto)
        return "redirect:/"
    }
}
