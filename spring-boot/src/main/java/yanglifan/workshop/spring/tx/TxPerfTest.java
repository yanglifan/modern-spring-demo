package yanglifan.workshop.spring.tx;

import yanglifan.workshop.spring.order.repository.VipOrderRepository;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Yang Lifan
 */
public class TxPerfTest {
    private final VipOrderRepository vipOrderRepository;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    public TxPerfTest(VipOrderRepository vipOrderRepository) {
        this.vipOrderRepository = vipOrderRepository;
    }
}
