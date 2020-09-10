package com.serializator;

import com.domain.Announcement;
import com.domain.Author;
import com.domain.Heading;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * Class {@link AnnouncementSerializer} is used to show {@link Announcement} in "id, name, revelationText, serviceCost,
 * publicationDate, {@link Heading#id}, {@link Author#id} format
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public class AnnouncementSerializer extends StdSerializer<Announcement> {

    /**
     * Constructor creates {@link AnnouncementSerializer} without parameters
     */
    public AnnouncementSerializer() {
        this(null);
    }


    /**
     * Constructor.Creates {@link AnnouncementSerializer} object from its super class
     *
     * @param cl common parameter
     */
    public AnnouncementSerializer(Class<Announcement> cl) {
        super(cl);
    }


    /**
     * Method serializes {@link Announcement} to "id, name, revelationText, serviceCost, publicationDate,
     * {@link Heading#id},
     * {@link Author#id} format
     *
     * @param announcement {@link Announcement}
     * @param gen          is the instance object of class to define class that defines public API for writing
     *                     JSON content
     * @param provider     instance object of Class that defines API used by ObjectMapper and JsonSerializers
     *                     to obtain serializers capable of serializing instances of specific types; as well as
     *                     the default implementation of the functionality
     * @throws IOException exception is threw if I/O operations for Ad failed or interrupted
     */
    @Override
    public void serialize(Announcement announcement, JsonGenerator gen, SerializerProvider provider)
            throws IOException {


        String pattern = "yyyy-MM-dd";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        Author author = announcement.getAuthor();

        Heading heading = announcement.getHeading();

        gen.writeStartObject();

        gen.writeObjectField("id", announcement.getId());

        gen.writeStringField("name", announcement.getName());

        gen.writeObjectField("version", announcement.getVersion());

        gen.writeStringField("revelationText", announcement.getRevelationText());

        String date = formatter.format(announcement.getPublicationDate());

        gen.writeStringField("publicationDate", date);

        gen.writeObjectField("serviceCost", announcement.getServiceCost());

        gen.writeObjectField("active", announcement.isActive());

        gen.writeObjectField("heading", heading.getId());

        gen.writeObjectField("author", author.getId());

        gen.writeEndObject();
    }
}
