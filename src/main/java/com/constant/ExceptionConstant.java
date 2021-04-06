package com.constant;

/**
 * This class contains ExceptionConstant.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

public final class ExceptionConstant {

    /**
     * This is private constructor.
     */
    private ExceptionConstant() {
    }

    /**
     * This is constant ANNOUNCEMENT_GET_EXCEPTION.
     */
    public static final String ANNOUNCEMENT_GET_EXCEPTION =
            "Cannot find announcement by: ";

    /**
     * This is constant ANNOUNCEMENT_GET_ALL_BY_HEADING_ID_EXCEPTION.
     */
    public static final String ANNOUNCEMENT_GET_ALL_BY_HEADING_ID_EXCEPTION =
            "Cannot find announcements by heading id: ";

    /**
     * This is constant ANNOUNCEMENT_GET_ALL_BY_DATE_EXCEPTION.
     */
    public static final String ANNOUNCEMENT_GET_ALL_BY_DATE_EXCEPTION =
            "Cannot find announcements by date: ";

    /**
     * This is constant ANNOUNCEMENT_GET_ALL_BY_REVELATION_TEXT_EXCEPTION.
     */
    public static final String
            ANNOUNCEMENT_GET_ALL_BY_REVELATION_TEXT_EXCEPTION =
            "Cannot find announcements by revelationText: ";

    /**
     * This is constant ANNOUNCEMENT_GET_ALL_BY_PAGINATION_EXCEPTION.
     */
    public static final String ANNOUNCEMENT_GET_ALL_BY_PAGINATION_EXCEPTION =
            "Cannot pagination some announcements";

    /**
     * This is constant AUTHOR_GET_EXCEPTION.
     */
    public static final String AUTHOR_GET_EXCEPTION =
            "Cannot find author by id: ";

    /**
     * This is constant HEADING_GET_EXCEPTION.
     */
    public static final String HEADING_GET_EXCEPTION =
            "Cannot find heading by id: ";


    /**
     * This is constant HEADING_GET_ALL_ANNOUNCEMENTS
     * _FROM_SOME_HEADINGS_EXCEPTION.
     */
    public static final String
            HEADING_GET_ALL_ANNOUNCEMENTS_FROM_SOME_HEADINGS_EXCEPTION =
            "Cannot find some announcements from some Headings";

    /**
     * This is constant SUITABLE_AD_GET_EXCEPTION.
     */
    public static final String SUITABLE_AD_GET_EXCEPTION =
            "Cannot find SuitableAd by id: ";
}
