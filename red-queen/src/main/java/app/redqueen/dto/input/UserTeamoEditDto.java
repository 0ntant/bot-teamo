package app.redqueen.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserTeamoEditDto(@JsonProperty("bot_writable") Boolean botWritable) {
}
