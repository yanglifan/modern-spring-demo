package com.github.yanglifan.workshop.spring.store;

import com.github.yanglifan.workshop.spring.User;
import com.github.yanglifan.workshop.spring.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @author Yang Lifan
 */
@DependsOn("userDataInitializer")
@Component
public class OrderDataInitializer implements InitializingBean {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderDataInitializer(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        User stark = userRepository.findByName("stark");
        User rogers = userRepository.findByName("rogers");
        orderRepository.save(new Order("2017011313591000001", stark.getId()));
        orderRepository.save(new Order("2017011313591000002", rogers.getId()));
    }
}
