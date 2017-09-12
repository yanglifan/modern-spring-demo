package com.github.yanglifan.workshop.spring.mock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(PassportService.class);

    @RequestMapping(value = "is-invalid-user", method = RequestMethod.GET)
    public Boolean isValidUser(@RequestParam String userToken) {
        LOGGER.info("Receive a request to check the user {} is valid or not", userToken);
        return StringUtils.isNoneBlank(userToken) && userToken.contains("a-valid-user");
    }
}
