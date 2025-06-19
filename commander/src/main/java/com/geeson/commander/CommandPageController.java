package com.geeson.commander;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommandPageController {
    @Value("${host.order}")
    private String orderHost;

    @RequestMapping({"", "/"})
    public String index(Model model, HttpSession session) {
        // Check if user is authenticated
        if (session.getAttribute("token") == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("orderHost", orderHost);
        return "index";
    }
}
