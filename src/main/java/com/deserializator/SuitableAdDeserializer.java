package com.deserializator;

import com.domain.Author;
import com.domain.SuitableAd;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Class {@link SuitableAdDeserializer} is used to convert from Json format to {@link SuitableAd}object format
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public class SuitableAdDeserializer extends StdDeserializer<SuitableAd> {


    /**
     * Constructor {@link SuitableAdDeserializer} without parameters
     */
    public SuitableAdDeserializer() {
        this(null);
    }


    /**
     * Constructor.Creates {@link SuitableAdDeserializer} object from its super class
     */
    protected SuitableAdDeserializer(Class<?> vc) {
        super(vc);
    }


    /**
     * @param jsonParser             is an instance object of class to define public API for reading Json content
     * @param deserializationContext is an instance object of a class used to allow passing in configuration
     *                               settings and reusable temporary objects
     * @return {@link SuitableAd}
     * @throws IOException             exception throws if I/O operations for LocalDate failed or interrupted
     * @throws JsonProcessingException exception throws for all problems encountered when processing (parsing,
     *                                 generating) JSON content that are not pure I/O problems
     */
    @Override
    public SuitableAd deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws
            IOException, JsonProcessingException {

        SuitableAd suitableAd = new SuitableAd();

        ObjectCodec code = jsonParser.getCodec();

        JsonNode node = code.readTree(jsonParser);

        suitableAd.setId(node.get("id").asInt());

        suitableAd.setVersion(node.get("version").asInt());

        suitableAd.setTitle(node.get("title").asText());

        suitableAd.setPriceFrom(new BigDecimal(node.get("priceFrom").asDouble()));

        suitableAd.setPriceTo(new BigDecimal(node.get("priceTo").asDouble()));

        suitableAd.setCategory(node.get("category").asText());


        JsonNode nodeAuthor = node.get("author");

        Author author = new Author();

        author.setId(nodeAuthor.asInt());

        suitableAd.setAuthor(author);

        return suitableAd;
    }
}
