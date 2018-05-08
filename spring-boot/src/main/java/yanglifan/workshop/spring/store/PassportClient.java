package yanglifan.workshop.spring.store;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Yang Lifan
 */
@Component
public class PassportClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(PassportClient.class);

    private static final HystrixCommandGroupKey PASSPORT_SERVICE_GROUP_KEY =
            HystrixCommandGroupKey.Factory.asKey("PassportService");

    private static final String PASSPORT_SERVICE_HOST = "http://localhost:8080/passport-service/";
    private static final String IS_INVALID_USER_URL = PASSPORT_SERVICE_HOST + "is-invalid-user?userToken={userToken}";

    private final RestTemplate restTemplate;

    public PassportClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Boolean isValidUser(String userToken) {
        LOGGER.info("Query the user status by the token {}", userToken);
        return new IsValidUserCommand(userToken).execute();
    }

    class IsValidUserCommand extends HystrixCommand<Boolean> {
        private final String userToken;

        IsValidUserCommand(String userToken) {
            super(Setter.withGroupKey(PASSPORT_SERVICE_GROUP_KEY));
            this.userToken = userToken;
        }

        @Override
        protected Boolean run() {
            LOGGER.info("Query the user status by the token {} in HystrixCommand", userToken);
            return restTemplate.getForObject(IS_INVALID_USER_URL, Boolean.class, userToken);
        }

        @Override
        protected Boolean getFallback() {
            return true;
        }
    }
}
