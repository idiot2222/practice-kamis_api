package me.bogeun.kamis.controller;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Controller
public class TestController {

    @ResponseBody
    @GetMapping("/test")
    public Map<String, Object> getTest() throws IOException {
        // String을 받아서 Json 형태의 List나 Map으로 바꿔주는 Spring boot의 JsonParser.
        JsonParser jsonParser = JsonParserFactory.getJsonParser();

        // 요청 URL
        URL url = new URL("https://www.kamis.or.kr/service/price/xml.do?action=periodProductList&p_productclscode=02&p_startday=2015-10-01&p_endday=2015-12-01&p_itemcategorycode=200&p_itemcode=212&p_kindcode=00&p_productrankcode=04&p_countrycode=1101&p_convert_kg_yn=Y&p_cert_key=111&p_cert_id=222&p_returntype=json");

        // url.openStream()으로 요청 결과를 stream으로 받음.
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

        // JsonParser를 사용하기 위해 stream을 하나의 String 객체로 묶음.
        String line = br.readLine();
        StringBuilder json = new StringBuilder();
        while (line != null && !line.equals("")) {
            json.append(line);

            line = br.readLine();
        }

        // JsonParser를 통해 요청 결과를 Map<String, Object>로 변환하여 반환.
        return jsonParser.parseMap(json.toString());
    }

}
