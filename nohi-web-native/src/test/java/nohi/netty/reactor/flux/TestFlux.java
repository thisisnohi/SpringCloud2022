package nohi.netty.reactor.flux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>flux</p>
 * @date 2023/08/25 09:13
 **/
@Slf4j
public class TestFlux {

    @Data
    @AllArgsConstructor
    class DataMeta {
        private String title;
        private String name;
    }

    @Test
    public void flux(){
        // 生产
        Flux<DataMeta> flux =  Flux.just(new DataMeta("1", "1"), new DataMeta("2", "2"));
        // 消费
        flux.subscribe( data -> {
            log.info("消费 data:{}", data);
        });

        Flux f = Flux.merge(flux, flux);
        // 消费
        f.subscribe( data -> {
            log.info("111 data:{}", data);
        });

        log.info("=================");
        // Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);
        Flux.range(1, 1000).takeWhile(i -> i < 10).reduce((a,b) -> a+b).subscribe(System.out::println);
        log.info("=================");
        Flux.range(1, 1).take(21).reduce((a,b) -> {
            log.info("a+b= {},{}", a, b);
            return a+b;
        }).subscribe(data -> {

            System.out.println(data);
        })
        ;
        log.info("=================");
        Flux.range(1, 1).reduce((a,b) -> {
            log.info("a+b= {},{}", a, b);
            return a+b;
        }).subscribe(data -> {
            System.out.println(data);
        });



    }
    @Test
    public void testMono(){
        Flux.just(5, 10)
                .flatMap(x -> Flux.range(x * 10, 100).take(x))
                .toStream()
                .forEach(System.out::println);
    }
}
