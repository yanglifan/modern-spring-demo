package com.github.yanglifan.workshop.spring.mock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yang Lifan
 */
@RequestMapping("/passport-service")
@RestController
public class PassportService {
    @RequestMapping(value = "is-invalid-user", method = RequestMethod.GET)
    public Boolean isValidUser(@RequestParam String userToken) {
        return StringUtils.isNoneBlank(userToken) && userToken.contains("a-valid-user");
    }
}
