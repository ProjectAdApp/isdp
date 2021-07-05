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

@RequestMapping("/4327947293")
@RestController
public class IsdpController {

    private static String hashA =  System.getenv("HASH-A-128");
           
    private static String hashB = System.getenv("HASH-B-128");
           
    private static String hashC =  System.getenv("HASH-C-128");
            

    private static Long id0 =  Long.parseLong(System.getenv("LONG-ID-CONST"));
            

    private final IsdpService service;

    @Autowired
    public IsdpController(IsdpService sService) {
        this.service = sService;
    }

    @CrossOrigin
    @PostMapping("/{id}")
    public String callId(@PathVariable Long id, @RequestBody SafeModel model) {

        System.out.println(model.getHash1() == hashA);
        System.out.println(model.getHash2() == hashB);
        System.out.println(model.getHash3() == hashC);

        System.out.println(id0 == id);

        if (id.equals(id0) && model.getHash1().equals(hashA) && model.getHash2().equals(hashB)
                && model.getHash3().equals(hashC)) {
            System.out.println("call");
            return service.callId();
        } else {
            return "unknown";
        }

    }

}