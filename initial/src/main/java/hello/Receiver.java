package hello;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import reactor.event.Event;
import reactor.function.Consumer;

@Service
class Receiver implements Consumer<Event<Integer>> {

    @Autowired
    CountDownLatch latch;

    RestTemplate restTemplate = new RestTemplate();

    public void accept(Event<Integer> ev) {
        JokeResource jokeResource = restTemplate.getForObject("http://www.iheartquotes.com/api/v1/random", JokeResource.class);
        System.out.println("Joke " + ev.getData() + ": " + jokeResource.getValue().getJoke());
        latch.countDown();
    }

}