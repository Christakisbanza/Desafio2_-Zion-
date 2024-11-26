package com.example.microservico_b.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "jsonPlaceholderClient", url = "https://jsonplaceholder.typicode.com")
public interface JsonPlaceholderClient {

}