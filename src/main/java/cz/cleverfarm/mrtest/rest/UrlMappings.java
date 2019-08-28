package cz.cleverfarm.mrtest.rest;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
public final class UrlMappings {
    private UrlMappings() {}

    public static final String FARMS_ENDPOINT = "/farm";
    public static final String FARM_ENDPOINT = FARMS_ENDPOINT + "/{farmId}";
    public static final String FIELDS_ENDPOINT = FARM_ENDPOINT + "/fields";
    public static final String FIELD_ENDPOINT = FIELDS_ENDPOINT + "/{fieldId}";
}
