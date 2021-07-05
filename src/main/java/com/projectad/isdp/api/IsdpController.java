package com.projectad.isdp.api;

import com.projectad.isdp.model.SafeModel;
import com.projectad.isdp.service.IsdpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/-")
@RestController
public class IsdpController {

   //security reasons
            

    private final IsdpService service;

    @Autowired
    public IsdpController(IsdpService sService) {
        this.service = sService;
    }

    @CrossOrigin
    @PostMapping("/{id}")
    public String callId(@PathVariable Long id, @RequestBody SafeModel model) {

      

        if (true) {
            System.out.println("call");
            return service.callId();
        } else {
            return "unknown";
        }

    }

}
