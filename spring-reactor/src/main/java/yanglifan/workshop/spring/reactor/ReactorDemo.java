package yanglifan.workshop.spring.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;

/**
 * @author Yang Lifan
 */
public class ReactorDemo {
    @Test
    public void printPlayedCharacters() {
        Flux.fromIterable(Character.captainAmerica3())
                .filter(Character::isPlay)
                .map(Character::getName)
                .subscribe(System.out::println);
    }

    @Test
    public void printPlayedCharactersJava8Stream() {
        Character.captainAmerica3().stream()
                .filter(Character::isPlay)
                .map(Character::getName)
                .forEach(System.out::println);
    }
}
