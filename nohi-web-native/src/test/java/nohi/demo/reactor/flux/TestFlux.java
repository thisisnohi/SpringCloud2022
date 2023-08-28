package nohi.demo.reactor.flux;

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
    }
}
