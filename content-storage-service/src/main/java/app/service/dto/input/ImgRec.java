package app.service.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record ImgRec(@JsonProperty("name") String name,
                     @JsonProperty("gender") String gender) implements Serializable {
}
