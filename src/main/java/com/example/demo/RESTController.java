package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class RESTController {

    private int count = 1;

    @Autowired
    private GreetingDao greetingDao;

    @RequestMapping(value = "/createGreeting", method = RequestMethod.POST)
    public Greeting createGreeting(@RequestBody Greeting g) throws IOException {
        greetingDao.create(g);
        return g;
    }

    @RequestMapping(value = "/getGreeting/{id}", method = RequestMethod.GET)
    public Greeting getGreeting(@PathVariable("id") int id) throws IOException {
        return greetingDao.getById(id);
    }


    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting greeting() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File("./message.txt"), Greeting.class);
    }

    @RequestMapping(value = "/createGreeting1", method = RequestMethod.POST)
    public Greeting createGreeting1(@RequestBody String content) throws IOException {
        Greeting newGreeting = new Greeting(count++, content);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("./message.txt"), newGreeting);
        return newGreeting;
    }

    @RequestMapping(value = "/createGreeting3", method = RequestMethod.POST)
    public ResponseEntity<Void> createGreeting3(@RequestBody String content)
            throws IOException, URISyntaxException {
        //create new greeting...

        //should not be hardcoded! Only an example...
        final URI location = new URI("http://localhost:8080/greeting/123");

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        final ResponseEntity<Void> entity = new ResponseEntity<Void>(headers,
                HttpStatus.CREATED);
        return entity;
    }

    @RequestMapping(value = "/updateGreeting", method = RequestMethod.PUT)
    public Greeting updateGreeting(@RequestBody String newMessage) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String message = FileUtils.readFileToString(new File("./message.txt"), StandardCharsets.UTF_8.name());

        Greeting greeting = mapper.readValue(message, Greeting.class);

        greeting.setContent(newMessage);

        mapper.writeValue(new File("./message.txt"), greeting);

        return greeting;

    }

}
