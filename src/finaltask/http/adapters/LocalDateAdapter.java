package finaltask.http.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import static finaltask.file.CSVFormatHandler.FORMATTER;

public class LocalDateAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return LocalDateTime.parse(jsonElement.getAsString(), FORMATTER);
    }

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(localDateTime.format(FORMATTER));
    }
}
