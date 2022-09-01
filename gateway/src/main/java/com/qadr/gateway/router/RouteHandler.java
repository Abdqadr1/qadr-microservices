package com.qadr.gateway.router;

import com.qadr.gateway.repo.ClientRepo;
import com.qadr.gateway.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteHandler  {
    @Autowired
    private ClientService clientService;




}
