package com.example.demo;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class ScheduledTasks {
    private static String consumerKeyStr = "";
    private static String consumerSecretStr = "";
    private static String accessTokenStr = "";
    private static String accessTokenSecretStr = "";
    RestTemplate restTemplate = new RestTemplate();


    public static void getTweets() throws Exception {
        OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(consumerKeyStr, consumerSecretStr);
        oAuthConsumer.setTokenWithSecret(accessTokenStr, accessTokenSecretStr);
        HttpGet httpGet = new HttpGet("https://api.twitter.com/1.1/search/tweets.json?q=%23Starbucks%20coffee&count=10");
        oAuthConsumer.sign(httpGet);
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = httpClient.execute(httpGet);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(IOUtils.toString(httpResponse.getEntity().getContent()));

    }

    @Scheduled(cron = "*/30 * * * * *")
    public static void postExample() throws Exception {

        OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(consumerKeyStr, consumerSecretStr);
        oAuthConsumer.setTokenWithSecret(accessTokenStr, accessTokenSecretStr);
        String str = RandomStringUtils.randomAlphabetic(10);
        HttpPost httpPost = new HttpPost("https://api.twitter.com/1.1/statuses/update.json?status=" + str);
        oAuthConsumer.sign(httpPost);
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = httpClient.execute(httpPost);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode + ':' + httpResponse.getStatusLine().getReasonPhrase());
        System.out.println(IOUtils.toString(httpResponse.getEntity().getContent()));
    }


    //@Scheduled(cron = "*/10 * * * * *")
    public Greeting createGreeting() {
        Greeting g = new Greeting(0, RandomStringUtils.randomAlphabetic(10));
        String postUrl = "http://localhost:8080/createGreeting";
        restTemplate.postForObject(postUrl, g, Greeting.class);
        return g;
    }

    //@Scheduled(cron = "*/30 * * * * *")
    public Greeting getGreeting() {
        int id = RandomUtils.nextInt(0, 100);
        String getUrl = "http://localhost:8080/getGreeting/" + id;
        Greeting g = restTemplate.getForObject(getUrl, Greeting.class);
        System.out.println(g.getContent());
        return g;
    }


    @Scheduled (fixedRate = 5000)
    public void periodicTask1() {
        System.out.println("The time now is " + new Date());
    }



    //@Scheduled(cron = "*/45 * * * * *")
    public void updateGreeting() {
        String putURL = "http://localhost:8080/updateGreeting";
        if (getGreeting().getContent().equals("bye")) {
            restTemplate.put(putURL, "hello");
        } else {
            restTemplate.put(putURL, "bye");
        }
        Greeting g = restTemplate.getForObject("http://localhost:8080/greeting", Greeting.class);
        System.out.println(g.getContent());
    }


}
