package com.test.contactregistry2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.xml.ws.Endpoint;

@WebServlet(urlPatterns = {"/ContactService"}, loadOnStartup = 1)
public class ContactServicePublisher extends HttpServlet {
    @Override
    public void init() throws ServletException {
        // Publish the ContactService at the relative URL "/ContactService"
        Endpoint.publish("http://localhost:8089/ContactRegistry2_war_exploded/ContactService", new ContactService());
    }
}
