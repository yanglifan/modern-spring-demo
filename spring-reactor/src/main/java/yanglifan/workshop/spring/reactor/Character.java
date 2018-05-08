package yanglifan.workshop.spring.reactor;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author Yang Lifan
 */
@Getter
@AllArgsConstructor
public class Character {
    private Long id;
    private String name;
    private boolean isPlay;

    public String getName() {
        System.out.println("Call getName() for " + name);
        return name;
    }

    public static List<Character> captainAmerica3() {
        return Arrays.asList(
                new Character(1L, "stark", true),
                new Character(2L, "rogers", true),
                new Character(3L, "hulk", false)
        );
    }
}
