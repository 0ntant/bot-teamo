package integration.dto.ImgAvaDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImgAvaDto implements Serializable
{
    @JsonProperty("operation")
    Operation operation;

    @JsonProperty("name")
    String name;

    @JsonProperty("gender")
    String gender;

    @JsonProperty("base64")
    String base64;
}