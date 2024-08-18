package surport.supportServer.notification.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import surport.supportServer.notification.dto.NotificationDto
import surport.supportServer.notification.service.NotificationService

@Controller
@RequestMapping("/notifications")
class NotificationController(private val notificationService: NotificationService) {

    @PostMapping("/")
    fun addNotification(@ModelAttribute notificationDto: NotificationDto, redirectAttributes: RedirectAttributes): String {
        notificationService.addNotification(notificationDto)
        return "redirect:/"
    }
}
