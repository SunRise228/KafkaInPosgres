package com.test.test.controller;

import com.test.test.model.NeuroStat;
import com.test.test.service.NeuroNetworkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@Slf4j
@RequestMapping("/neuro")
@CrossOrigin(origins = "*")
public class NeuroNetworkController {

    @Autowired
    NeuroNetworkService neuroNetworkService;

    @CrossOrigin(origins = "*")
    @PostMapping
    public HttpStatus takeStat(@RequestBody NeuroStat neuroStat) throws IOException {
        neuroNetworkService.takeStat(neuroStat);
        return HttpStatus.OK;
    }

}
