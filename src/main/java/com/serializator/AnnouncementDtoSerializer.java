package com.serializator;

import com.domain.Announcement;
import com.domain.Author;
import com.domain.Heading;
import com.dto.AnnouncementDto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Class {@link AnnouncementDtoSerializer} is used to show {@link AnnouncementDto} in "id, name, revelationText,
 * serviceCost,publicationDate,
 * {@link Heading#id},
 * {@link Author#id} format
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public class AnnouncementDtoSerializer extends StdSerializer<AnnouncementDto> {


    /**
     * Constructor creates {@link AnnouncementDtoSerializer} without parameters
     */
    public AnnouncementDtoSerializer() {
        this(null);
    }


    /**
     * Constructor.Creates {@link AnnouncementDtoSerializer} object from its super class
     *
     * @param cl common parameter
     */
    public AnnouncementDtoSerializer(Class<AnnouncementDto> cl) {
        super(cl);
    }


    /**
     * Method serializes {@link AnnouncementDto} to "id, name, revelationText, serviceCost, publicationDate,
     * {@link Heading#id},
     * {@link Author#id} format
     *
     * @param announcementDto {@link Announcement}
     * @param gen             is the instance object of class to define class that defines public API for writing
     *                        JSON content
     * @param provider        instance object of Class that defines API used by ObjectMapper and JsonSerializers
     *                        to obtain serializers capable of serializing instances of specific types; as well as
     *                        the default implementation of the functionality
     * @throws IOException exception is threw if I/O operations for Ad failed or interrupted
     */
    @Override
    public void serialize(AnnouncementDto announcementDto, JsonGenerator gen, SerializerProvider provider)
            throws IOException {

        String pattern = "yyyy-MM-dd";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        String date = formatter.format(announcementDto.getPublicationDate());

        Heading heading = announcementDto.getHeading();

        gen.writeStartObject();

        gen.writeObjectField("id", announcementDto.getId());

        gen.writeStringField("name", announcementDto.getName());

        gen.writeObjectField("version", announcementDto.getVersion());

        gen.writeStringField("revelationText", announcementDto.getRevelationText());

        gen.writeStringField("publicationDate", date);

        gen.writeObjectField("serviceCost", announcementDto.getServiceCost());

        gen.writeObjectField("active", announcementDto.isActive());

        gen.writeObjectFieldStart("heading");

        gen.writeObjectField("id", heading.getId());

        gen.writeObjectField("name", heading.getName());

        gen.writeEndObject();

        gen.writeObjectField("author", announcementDto.getAuthor());

        gen.writeEndObject();
    }
}
