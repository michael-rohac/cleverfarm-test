package cz.cleverfarm.mrtest.rest;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
public final class UrlMappings {

    private UrlMappings() {}


    private static final String API_ENDPOINT = "/api-v0";

    public static final String FARM_ID = "farmId";
    public static final String FIELD_ID = "fieldId";

    public static final String FARMS_ENDPOINT = API_ENDPOINT + "/farm";
    public static final String FARM_ENDPOINT = FARMS_ENDPOINT + "/{" + FARM_ID + "}";
    public static final String FIELDS_ENDPOINT = FARM_ENDPOINT + "/fields";
    public static final String FIELD_ENDPOINT = FIELDS_ENDPOINT + "/{" +  FIELD_ID + "}";
}
