package yanglifan.workshop.spring.async;

import yanglifan.workshop.spring.User;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.Map;

@Component
public class AsyncHttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncHttpClient.class);

    private AsyncRestOperations asyncRestOperations;

    public AsyncHttpClient() {
        AsyncClientHttpRequestFactory asyncClientHttpRequestFactory =
                new Netty4ClientHttpRequestFactory();
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
        asyncRestTemplate.setAsyncRequestFactory(asyncClientHttpRequestFactory);
        asyncRestOperations = asyncRestTemplate;
    }

    public void getUserByUsername(String username) {
        Map<String, String> variables = Maps.newHashMap();
        variables.put("username", username);
        this.asyncRestOperations
                .getForEntity("http://localhost:8080/users/{username}", User.class, variables)
                .addCallback(new ListenableFutureCallback<ResponseEntity<User>>() {
                    @Override
                    public void onFailure(Throwable ex) {

                    }

                    @Override
                    public void onSuccess(ResponseEntity<User> result) {
                        LOGGER.info("Get the user by username async {}", result.getBody().getName());
                    }
                });
    }
}
