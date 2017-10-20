package yanglifan.workshop.spring

import org.springframework.stereotype.Component

@Component
class GroovyDemoService {
    void withException() {
        throw new Exception("An expected exception")
    }
}
