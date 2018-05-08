package yanglifan.workshop.spring.tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import yanglifan.workshop.spring.GroovyDemoService;
import yanglifan.workshop.spring.User;
import yanglifan.workshop.spring.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class NestedTxRollbackDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(NestedTxRollbackDemo.class);

    private static final String USERNAME = "userForTxDemo";
    private static final String USERNAME2 = "userForTxDemo2";
    private static final String USERNAME3 = "userForTxDemo3";

    @Autowired
    private MainService mainService;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        try {
            mainService.demo();
        } catch (Exception e) {
            LOGGER.error("Current tx should be rollback");
        }

        mainService.outTx();
        User user = userRepository.findByName(USERNAME);
        Assert.isNull(user, USERNAME + " should not be saved success");

        try {
            mainService.demo2();
        } catch (Exception e) {
            LOGGER.error("Current tx should not be rollback");
        }

        User user2 = userRepository.findByName(USERNAME2);
        Assert.notNull(user2, USERNAME2 + " should be saved success");

        try {
            mainService.demoGroovy();
        } catch (Exception e) {
            LOGGER.error("Current tx should not be rollback");
        }

        User user3 = userRepository.findByName(USERNAME3);
        Assert.notNull(user3, USERNAME3 + " should be saved success");
    }

    @Component
    public static class MainService {
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private GroovyDemoService groovyDemoService;

        public MainService() {
            System.out.println("demo");
        }

        @Transactional
        public void demo() {
            try {
                this.userRepository.save(new User(USERNAME));
                throw new Exception("test");
            } catch (Exception e) {
                LOGGER.error("Expected an exception");
                Assert.isTrue(TransactionAspectSupport.currentTransactionStatus().isRollbackOnly(),
                        "Current transaction should be rollback");
            }
        }

        @Transactional
        public void demo2() throws Exception {
            this.userRepository.save(new User(USERNAME2));
            throw new Exception("test");
        }

        @Transactional
        public void demoGroovy() throws Exception {
            this.userRepository.save(new User(USERNAME3));
            groovyDemoService.withException();
        }

        public void outTx() {
            this.inTx();
        }

        @Transactional
        public void inTx() {
            System.out.println("inTX");
        }
    }

    @Component
    public static class ExceptionService {
        @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
        public void withException() {
            throw new RuntimeException();
        }
    }
}
