package com.vcs.bogdan.spring.resttest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestService {

    @RequestMapping("/")
    public String index() {
        return "{\"msg\" : \"I'm alive!!!\"}";
    }

        @Value("${appName}")
        private String name;

        @RequestMapping(value = "/{text}", method = RequestMethod.GET)
        public String webExample(@PathVariable("text") String textFromRequest, HttpServletRequest request) {
            return webExampleHtml(textFromRequest, request.getRemoteAddr());
        }

        @RequestMapping(value = "/", method = RequestMethod.GET)
        public String webExampleEmpty(HttpServletRequest request) {
            return webExampleHtml(null, request.getRemoteAddr());
        }

        public String webExampleHtml(String textFromRequest, String host) {

            String result = (null != textFromRequest && !textFromRequest.isEmpty() ? textFromRequest : "anonime");

            return "<!DOCTYPE html><html><body><h1>Tavo IP : " + host + "</h1>" + "<p>Sveikas <b>" + result
                    + "</b></p></body></html>";
        }

        @RequestMapping(method = RequestMethod.POST)
        public String dssadasdfdsfdsf() {

            return "Nesvarbu: " + name;
        }

    }