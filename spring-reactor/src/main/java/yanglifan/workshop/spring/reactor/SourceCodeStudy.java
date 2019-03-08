package yanglifan.workshop.spring.reactor;

import reactor.core.publisher.Flux;

public class SourceCodeStudy {
    public static void main(String[] args) {
        Flux.just("tom", "jack", "allen")
                .filter(s -> s.length() > 3)
                .map(s -> s.concat("@qq.com"))
                .subscribe(System.out::println);
    }
}
