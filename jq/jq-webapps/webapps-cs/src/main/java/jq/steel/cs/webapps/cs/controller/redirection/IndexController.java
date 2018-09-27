package jq.steel.cs.webapps.cs.controller.redirection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: RedirectionController
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/9/27 18:32
 */
@Controller
public class IndexController {

    @RequestMapping({"/", "/index", ""})
    public String redirection(HttpServletRequest request, HttpServletResponse response) {

        return "login";
    }
}
