package com.github.yanglifan.workshop.spring.tx;

import com.github.yanglifan.workshop.spring.User;
import com.github.yanglifan.workshop.spring.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

@Component
public class NestedTxRollbackDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(NestedTxRollbackDemo.class);

    private static final String USERNAME = "userForTxDemo";

    @Autowired
    private MainService mainService;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        try {
            mainService.demo();
        } catch (Exception e) {
            LOGGER.error("Current transaction should be rollback");
        }

        mainService.outTx();
        User user = userRepository.findByName(USERNAME);
        Assert.isNull(user, USERNAME + " should not be saved success");
    }

    @Component
    public static class MainService {
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ExceptionService exceptionService;

        public MainService() {
            System.out.println("demo");
        }

        @Transactional
        public void demo() {
            try {
                this.userRepository.save(new User(USERNAME));
                exceptionService.withException();
            } catch (Exception e) {
                LOGGER.error("Expected an exception");
            }

            Assert.isTrue(TransactionAspectSupport.currentTransactionStatus().isRollbackOnly(),
                    "Current transaction should be rollback");
        }

//        @Transactional
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
