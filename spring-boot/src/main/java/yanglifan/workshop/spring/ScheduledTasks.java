package yanglifan.workshop.spring;

import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.Format;
import java.util.Date;

@Component
public class ScheduledTasks {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final Format DATE_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    @Scheduled(fixedDelay = 5000)
    public void reportCurrentTime() {
        LOGGER.debug("Current time is {}", DATE_FORMATTER.format(new Date()));
    }
}
