package http;

import entity.AmlProcessAfterHitReqDTO;
import entity.GenericDTO;
import entity.GetNonStandardPersonAddrRspDTO;
import entity.GetPersonAddrReqDTO;
import json.JsonUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * http调用它方接口
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-09-02
 */
public class HttpClientTest {
    private final static String host = "http://192.168.239.185:8830";
    private final static String path = "/cust/maintainPersonAddr/getNonStandardPersonAddr";
    //cnblogs.com/xiaoliangge/p/9535027.html、https://www.jianshu.com/p/b8734d8b0173
    //http会卡死，所以用线程：https://blog.csdn.net/haozi_e/article/details/74011107
    //如果现在运行测试方法，因为该接口需要很长时间后才会响应。那么HttpClient请求方是一直在等待着响应，从而证实了一点，HttpClient默认无响应超时
    //响应解决：设置connetTimeOut和SocketTimeOut时间

    /**
     * 通过HttpClient调用它方接口
     *
     * @param result 参数1
     * @param msgId 参数2
     * @return 返回响应结果
     */
    public static String synHttpClientToOther(String result, String msgId) {
        GenericDTO<AmlProcessAfterHitReqDTO> reqDtos = new GenericDTO<>();
        AmlProcessAfterHitReqDTO reqDto = new AmlProcessAfterHitReqDTO();
        if ("PASS".equals(result)) {
            //放行
            reqDto.setAmlResult("OVERRIDE");
        } else {
            //中黑名单，拒绝
            reqDto.setAmlResult("REJECT");
        }
        reqDto.setMsgId(msgId);
        reqDtos.setBody(reqDto);
        //通过http形式调用其它系统接口同步信息
        //调用接口
        String url = "http://192.168.239.93:8402/pays/swift/amlProcessAfterHit";
        //String url = "http://192.168.239.23:8080/aml/swiftRetrieval";
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //String token = getWebUser(user).getToken();
        //if(token!=null)post.addHeader("Authorization", "bearer "+token);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        String results = null;
        String parameter = JsonUtils.toJSON(reqDtos);
        try {
            StringEntity se = new StringEntity(parameter);
            se.setContentType("text/json");
            httpPost.setEntity(se);
            //设置超时
            //RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(2000).build();
            //httpPost.setConfig(requestConfig);

            //执行调用
            HttpResponse response = client.execute(httpPost);

            //返回信息
            HttpEntity entity = response.getEntity();
            results = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 通过HttpClient调用它方接口、没有封装的调用
     *
     * @return 返回响应结果
     */
    public static GenericDTO<GetNonStandardPersonAddrRspDTO> synHttpClientToOther(GenericDTO<GetPersonAddrReqDTO> reqDto) {
        //创建HttpClient对象
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            //设置请求路径，请求格式
            HttpPost httpPost = new HttpPost(host + path);
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");

            //格式化请求数据并设值
            String parameter = JsonUtils.toJSON(reqDto);
            StringEntity se = new StringEntity(parameter);
            se.setContentType("text/json");
            httpPost.setEntity(se);
            //设置超时
            //RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(2000).build();
            //httpPost.setConfig(requestConfig);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                //返回信息
                System.out.println(response.getStatusLine().getStatusCode());
                if (response.getStatusLine().getStatusCode() == 200) {
                    String results = EntityUtils.toString(response.getEntity(), "UTF-8");
                    //转成对象
                    GenericDTO rspDto = JsonUtils.toBean(results, GenericDTO.class);
                    rspDto.setBody(JsonUtils.toBean(rspDto.getBody(), GetNonStandardPersonAddrRspDTO.class));
                    return rspDto;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
        WebClient
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        https://www.cnblogs.com/grasp/p/12179906.html
        */
    /*public static String synWebClientToOther() {
        WebClient webClient = WebClient.create();
        Mono<String> tMono = webClient.get().uri("https://www.baidu.com").retrieve().bodyToMono(String.class);
        //webClient.get().uri("http://localhost:8081/user/{id}", 1);
        //mono = WebClient.create().post().uri(host + riskAssessUrl).
        //                    contentType(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(rskStr)).retrieve().bodyToMono(RiskRspDTO.class);
        //System.out.println(mono.block());
        return tMono.block();
    }*/


    @Test
    public void test1() {
        //通过HttpClient http调用它方接口
        //String result = synHttpClientToOther("PASS", "AML20090214122451002");
        //System.out.println(result);

        //通过WebClient http调用它方接口
        //String str = synWebClientToOther();
        //System.out.println(str);
    }


}
