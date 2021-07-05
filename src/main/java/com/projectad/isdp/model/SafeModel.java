package com.projectad.isdp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SafeModel {

    private String hash1, hash2, hash3;

    public SafeModel(@JsonProperty("a") String hash1, @JsonProperty("b") String hash2,
            @JsonProperty("c") String hash3) {
        this.hash1 = hash1;
        this.hash2 = hash2;
        this.hash3 = hash3;
    }

    public String getHash1() {
        return hash1;
    }

    public String getHash2() {
        return hash2;
    }

    public String getHash3() {
        return hash3;
    }
}