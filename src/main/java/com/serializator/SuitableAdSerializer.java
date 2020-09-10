package com.serializator;

import com.domain.Author;
import com.domain.SuitableAd;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Class {@link SuitableAdSerializer} is used to show {@link SuitableAd} in "id, category, title, version,
 * priceFrom, priceTo, {@link Author#id} format
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public class SuitableAdSerializer extends StdSerializer<SuitableAd> {


    /**
     * Constructor {@link SuitableAdSerializer} without parameters
     */
    public SuitableAdSerializer() {
        this(null);
    }


    /**
     * Constructor.Creates {@link SuitableAdSerializer} object from its super class
     *
     * @param t common parameter
     */
    public SuitableAdSerializer(Class<SuitableAd> t) {
        super(t);
    }


    /**
     * Method serializes {@link SuitableAd} to "id, category, title, version, priceFrom, priceTo,
     * {@link Author#id} format
     *
     * @param suitableAd         {@link SuitableAd}
     * @param jsonGenerator      is the instance object of class to define class that defines public API for writing
     *                           JSON content
     * @param serializerProvider instance object of Class that defines API used by ObjectMapper and JsonSerializers
     *                           to obtain serializers capable of serializing instances of specific types; as well as
     *                           the default implementation of the functionality
     * @throws IOException exception is threw if I/O operations for Ad failed or interrupted
     */
    @Override
    public void serialize(SuitableAd suitableAd, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeObjectField("id", suitableAd.getId());

        jsonGenerator.writeObjectField("category", suitableAd.getCategory());

        jsonGenerator.writeObjectField("title", suitableAd.getTitle());

        jsonGenerator.writeObjectField("version", suitableAd.getVersion());

        jsonGenerator.writeObjectField("priceFrom", suitableAd.getPriceFrom());

        jsonGenerator.writeObjectField("priceTo", suitableAd.getPriceTo());

        jsonGenerator.writeObjectField("author", suitableAd.getAuthor().getId());

        jsonGenerator.writeEndObject();
    }
}
