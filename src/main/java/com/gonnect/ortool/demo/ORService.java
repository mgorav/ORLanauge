package com.gonnect.ortool.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/lp")
public class ORService {

    @CrossOrigin
    @PostMapping(value = "/submit")
    public LinearProgramingOutput submit(@RequestBody LinearProgramingInput input) {
        return ORParserExecutor.parse(input);
    }
}
