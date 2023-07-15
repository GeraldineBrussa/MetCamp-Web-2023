package org.metcamp.web.model.response;

import lombok.*;
@Getter@Setter

public class Response {
    private int code;
    private String message;

  public Response(int code, String message) {
          this.code = code;
          this.message = message;
      }

    @Override
    public String toString(){
    return String.format("{\"code\": %s, \"message\": \"%s\"}", code, message);
}
}

