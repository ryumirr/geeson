package com.geeson.commander;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommandPageController {
    @Value("${host.order}")
    private String orderHost;

    @RequestMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("orderHost", orderHost);
        return "index";
    }
}
