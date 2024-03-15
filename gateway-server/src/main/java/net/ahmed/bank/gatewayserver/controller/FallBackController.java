package net.ahmed.bank.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @RequestMapping("/contact-support")
    public Mono<String> contactSupport(){
        return Mono.just("An error occurred");
    }
}
