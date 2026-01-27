package com.pdfgate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import java.time.Instant;

final class PdfGateJson {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (json, type, ctx) -> {
                if (json == null || json.isJsonNull()) {
                    return null;
                }
                String value = json.getAsString();
                if (value == null || value.isBlank()) {
                    return null;
                }
                return Instant.parse(value);
            })
            .create();

    static Gson gson() {
        return GSON;
    }

    private PdfGateJson() {
    }
}
