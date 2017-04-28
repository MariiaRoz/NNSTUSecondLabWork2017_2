package org.nnstu2.server.entity;

/**
 * Created by rmv52 on 28.04.2017.
 */
public class Response <T>{

    private int code;
    public Response(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
