package nohi.demo.service.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.reader.FieldReaderObject;
import com.alibaba.fastjson2.reader.ObjectReaders;
import com.alibaba.fastjson2.writer.ObjectWriters;
import lombok.extern.slf4j.Slf4j;
import nohi.demo.dto.json.*;
import nohi.demo.dto.rsa.RsaRespItemVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.alibaba.fastjson2.reader.ObjectReaders.*;

/**
 * <h3>nohi-web-native</h3>
 *
 * @author NOHI
 * @description <p>Fastjson测试</p>
 * @date 2023/07/08 15:03
 **/
@Slf4j
@Service
public class FastJsonService {

    private void registerReaderAndWriter() {
        JSON.register(MediaContent.class, ObjectWriters.objectWriter(MediaContent.class, ObjectWriters.fieldWriter("media", Media.class, MediaContent::getMedia), ObjectWriters.fieldWriterList("images", Image.class, MediaContent::getImages)));

        JSON.register(MediaContent.class, ObjectReaders.of(MediaContent::new, fieldReader("media", Media.class, MediaContent::setMedia), fieldReaderList("images", Image.class, ArrayList::new, MediaContent::setImages)));

        JSON.register(Media.class, ObjectWriters.objectWriter(Media.class, ObjectWriters.fieldWriter("bitrate", Media::getBitrate), ObjectWriters.fieldWriter("duration", Media::getDuration), ObjectWriters.fieldWriter("format", Media::getFormat), ObjectWriters.fieldWriter("height", Media::getHeight), ObjectWriters.fieldWriterList("persons", String.class, Media::getPersons), ObjectWriters.fieldWriter("player", Player.class, Media::getPlayer), ObjectWriters.fieldWriter("size", Media::getSize), ObjectWriters.fieldWriter("title", Media::getTitle), ObjectWriters.fieldWriter("uri", Media::getUri), ObjectWriters.fieldWriter("width", Media::getWidth), ObjectWriters.fieldWriter("copyright", Media::getCopyright)));

        JSON.register(Media.class, ObjectReaders.of(Media::new, fieldReaderInt("bitrate", Media::setBitrate), fieldReaderLong("duration", Media::setDuration), fieldReaderString("format", Media::setFormat), fieldReaderInt("height", Media::setHeight), fieldReaderList("persons", String.class, ArrayList::new, Media::setPersons), fieldReader("player", Player.class, Media::setPlayer), fieldReaderLong("size", Media::setSize), fieldReaderString("title", Media::setTitle), fieldReaderString("uri", Media::setUri), fieldReaderInt("width", Media::setWidth), fieldReaderString("copyright", Media::setCopyright)));

        JSON.register(Image.class, ObjectWriters.objectWriter(Image.class, ObjectWriters.fieldWriter("height", Image::getHeight), ObjectWriters.fieldWriter("size", Size.class, Image::getSize), ObjectWriters.fieldWriter("title", Image::getTitle), ObjectWriters.fieldWriter("uri", Image::getUri), ObjectWriters.fieldWriter("width", Image::getWidth)));

        JSON.register(Image.class, ObjectReaders.of(Image::new, fieldReaderInt("height", Image::setHeight), fieldReader("size", Size.class, Image::setSize), fieldReaderString("title", Image::setTitle), fieldReaderString("uri", Image::setUri), fieldReaderInt("width", Image::setWidth)));
    }

    public String toJson() {
        String str = "{\"images\": [{\n" + "      \"height\":768,\n" + "      \"size\":\"LARGE\",\n" + "      \"title\":\"Javaone Keynote\",\n" + "      \"uri\":\"http://javaone.com/keynote_large.jpg\",\n" + "      \"width\":1024\n" + "    }, {\n" + "      \"height\":240,\n" + "      \"size\":\"SMALL\",\n" + "      \"title\":\"Javaone Keynote\",\n" + "      \"uri\":\"http://javaone.com/keynote_small.jpg\",\n" + "      \"width\":320\n" + "    }\n" + "  ],\n" + "  \"media\": {\n" + "    \"bitrate\":262144,\n" + "    \"duration\":18000000,\n" + "    \"format\":\"video/mpg4\",\n" + "    \"height\":480,\n" + "    \"persons\": [\n" + "      \"Bill Gates\",\n" + "      \"Steve Jobs\"\n" + "    ],\n" + "    \"player\":\"JAVA\",\n" + "    \"size\":58982400,\n" + "    \"title\":\"Javaone Keynote\",\n" + "    \"uri\":\"http://javaone.com/keynote.mpg\",\n" + "    \"width\":640\n" + "  }\n" + "}";

        registerReaderAndWriter();

        MediaContent mediaContent = JSON.parseObject(str, MediaContent.class);
        log.info("===========================");
        String json = JSONObject.toJSONString(mediaContent);
        log.info("===========================json:{}", json);
        return json;
    }

    public void registerRsaRespItemVO() {
        log.info("===========================registerRsaRespItemVO======");
        JSON.register(RsaRespItemVO.class
                , ObjectWriters.objectWriter(RsaRespItemVO.class
                        , ObjectWriters.fieldWriter("acctNo", RsaRespItemVO::getAcctNo)
                        , ObjectWriters.fieldWriter("acctName", RsaRespItemVO::getAcctName)
                        , ObjectWriters.fieldWriter("dateTime", RsaRespItemVO::getDateTime)
                        , ObjectWriters.fieldWriter("amt", BigDecimal.class, RsaRespItemVO::getAmt)
                        , ObjectWriters.fieldWriter("balance", BigDecimal.class, RsaRespItemVO::getBalance)
                )
        );
        JSON.register(RsaRespItemVO.class, ObjectReaders.of(RsaRespItemVO::new
                , fieldReaderString("acctNo", RsaRespItemVO::setAcctName)
                , fieldReaderString("acctName", RsaRespItemVO::setAcctName)
                , fieldReaderString("dateTime", RsaRespItemVO::setDateTime)
                , fieldReader("amt", BigDecimal.class, RsaRespItemVO::setAmt)
                , fieldReader("balance", BigDecimal.class, RsaRespItemVO::setBalance)
        ));
    }
}
