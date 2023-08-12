package com.visenti.test.automation.constants;

/**
 * Class Description: This class hold all API Constants
 */
public class APIConstants {

    private APIConstants() {
        throw new IllegalStateException("API Constants Class");
    }

    public static final int STATUS_CODE_200 = 200;
    public static final int STATUS_CODE_400 = 400;
    public static final int STATUS_CODE_202 = 202;
    public static final int STATUS_CODE_201 = 201;
    public static final int STATUS_CODE_401 = 401;  //Status Code 401 Unauthorized
    public static final String STATUS_TEXT_ACCEPTED = "Accepted";
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_TEXT_CREATED = "Created";
    public static final String STATUS_LINE_200 = "HTTP/1.1 200 OK";
    public static final String CONTENT_TYPE_XML = "application/xml";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain;charset=UTF-8";
    public static final String CONTENT_TYPE_ONLY_TEXT_PLAIN = "text/plain";
    public static final String CONTENT_TYPE_TEXT_HTML = "text/html";
    public static final String RESPONSE_ERRORCODE_0 = "0";
    public static final String RESPONSE_META_ERROR_MESSAGE_NONE = "none";
    public static final String RESPONSE_META_ERROR_TYPE_NONE = "none";

    public static final String CONTENT_TYPE_CSV = "application/csv";

    //Request Payloads Path

    public static final String DEVICES_META_SEARCH_NETWORK_PAYLOAD_PATH = FrameworkConstants.REQUEST_PAYLOADS_PATH + "LoadDevices_Network_Payload.txt";
    public static final String AIMS_GET_INCIDENTS_PAYLOAD_PATH = FrameworkConstants.REQUEST_PAYLOADS_PATH + "GetIncidents_Payload.txt";
    public static final String AIMS_SAVE_PREFERENCE_PATH = FrameworkConstants.REQUEST_PAYLOADS_PATH + "SaveAIMSPreference_Payload.txt";
    public static final String DMA_DATA_PAYLOAD_PATH = FrameworkConstants.REQUEST_PAYLOADS_PATH + "LoadDmaData_Payload.txt";
    public static final String GIS_META_NON_PIPE_PAYLOAD_PATH = FrameworkConstants.REQUEST_PAYLOADS_PATH + "LoadGisNonPipe_Payload.txt";
    public static final String GIS_META_PIPE_PAYLOAD_PATH = FrameworkConstants.REQUEST_PAYLOADS_PATH + "LoadGisPipe_Payload.txt";

    public static final String GIS_META_SEARCH_ID_PAYLOAD_PATH = FrameworkConstants.REQUEST_PAYLOADS_PATH + "LoadMetaSearch_Payload.txt";

    public static final String SIMULATION_PIPE_ISOLATION_PAYLOAD_PATH = FrameworkConstants.REQUEST_PAYLOADS_PATH + "Simulation_Pipe_Isolation_Payload.txt";
    public static final String SIMULATION_VALVE_OPERATION_PAYLOAD_PATH = FrameworkConstants.REQUEST_PAYLOADS_PATH + "Simulation_Valve_Operation_Payload.txt";

    public static final String SIMULATION_NWD_PAYLOAD_PATH = FrameworkConstants.REQUEST_PAYLOADS_PATH + "Simulation_NWD_Payload.txt";

    public static final String SURGES_GET_RISK_BY_ZONE_PAYLOAD_PATH = FrameworkConstants.REQUEST_PAYLOADS_PATH + "GetRiskByZones_Payload.txt";

    public static final String SURGES_METRICS_PAYLOAD_PATH = FrameworkConstants.REQUEST_PAYLOADS_PATH + "SurgesMetrics_Payload.txt";
    public static final String DAMAGING_TRANSIENTS_PAYLOAD_PATH = FrameworkConstants.REQUEST_PAYLOADS_PATH + "Transients_Payload.txt";

    public static final String HM_USER_PREFERENCE_PAYLOAD_PATH = FrameworkConstants.REQUEST_PAYLOADS_PATH + "HMUserPreference_Payload.txt";
    //Common Response Object keys for most API's

    public static final String COMMON_RESPONSE_OBJECT_DATA_KEY = "data";
    public static final String COMMON_RESPONSE_OBJECT_COUNT_KEY = "count";
    public static final String COMMON_RESPONSE_OBJECT_STATUS_KEY = "status";
    public static final String COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY = "statusText";
    public static final String COMMON_RESPONSE_OBJECT_STATUS_CODE_KEY = "status_code";
    public static final String COMMON_RESPONSE_OBJECT_META_KEY = "meta";

    //Common Meta Map keys

    public static final String COMMON_META_ERROR_MESSAGE_KEY = "error_message";
    public static final String COMMON_META_ERROR_TYPE_KEY = "error_type";

    //PUB Specific Constants

    public static final String NETWORK_NAME_POTABLE_WATER = "potable_water";
    public static final String NETWORK_NAME_NEWATER = "newater";

    // Acoustic Theme Constants
    public static final int ACOUSTIC_STATISTIC_COUNT = 7;
    public static final String ACOUSTIC_AVERAGE_MIN_NIGHT = "avg_min_night_value";
    public static final String ACOUSTIC_DATE = "date";
    public static final String ACOUSTIC_AVERAGE_NOISE = "day_avg_noise";
    public static final String ACOUSTIC_LAST_MIN_NIGHT = "last_night_min_acoustic_value";
    public static final String ACOUSTIC_LATEST_VALUE_OVER_TIME = "latest_acoustic_value_over_time";
    public static final String ACOUSTIC_STN_ID = "stn_id";
    public static final String ACOUSTIC_WAV_FILE_PATH = "wav_file_path";

    //staistics/acoustic constants

    public static final String ACOUSTIC_THEME_STATISTICS_RESPONSE_DATA_KEY = "data";
    public static final String ACOUSTIC_THEME_STATISTICS_RESPONSE_COUNT_KEY = "count";

    // Customer Leaks Theme Constants

    // Customer Leaks Statistics Constants

    public static final String CUSTOMER_LEAKS_STATISTICS_LEAK_ACTIONS_KEY = "leakActions";
    public static final String CUSTOMER_LEAKS_STATISTICS_LEAK_CATEGORY_KEY = "leakCategory";
    public static final String CUSTOMER_LEAKS_STATISTICS_LEAK_STATUS_KEY = "leakStatus";
    public static final String CUSTOMER_LEAKS_STATISTICS_FLAG_START_KEY = "flagStart";
    public static final String CUSTOMER_LEAKS_STATISTICS_WATER_LOSS_KEY = "waterLoss";
    public static final String CUSTOMER_LEAKS_STATISTICS_METER_NUMBER_KEY = "meterNumber";
    public static final String CUSTOMER_LEAKS_STATISTICS_METER_ADDRESS_KEY = "meterAddress";
    public static final String CUSTOMER_LEAKS_STATISTICS_METER_SIZE_KEY = "meterSize";
    public static final String CUSTOMER_LEAKS_STATISTICS_PREMISE_ID_KEY = "premiseId";
    public static final String CUSTOMER_LEAKS_STATISTICS_CERTAINTY_KEY = "certainty";

    // Customer Leaks Trends Constants

    public static final String CUSTOMER_LEAKS_TRENDS_CSV_DATA_KEY = "csvData";
    public static final String CUSTOMER_LEAKS_TRENDS_CSV_HEADER_KEY = "csvHeader";
    public static final String CUSTOMER_LEAKS_TRENDS_META_LIST_KEY = "metaList";

    // Alerts Constants (/getIncidents API keys)

    public static final String ALERTS_INCIDENT_TYPE_KEY = "incidentType";
    public static final String ALERTS_ANOMALY_TYPE_KEY = "anamolyType";
    public static final String ALERTS_REPORTING_TIME_KEY = "reportingTime";
    public static final String ALERTS_INCIDENT_TIME_KEY = "incidentTime";
    public static final String ALERTS_DESCRIPTION_KEY = "description";
    public static final String ALERTS_STATUS_KEY = "status";
    public static final String ALERTS_STATE_KEY = "state";
    public static final String ALERTS_ASSIGNED_TO_KEY = "assignedTo";
    public static final String ALERTS_LAST_COMMENT_KEY = "lastComment";
    public static final String ALERTS_STATION_NAME_KEY = "stationName";
    public static final String ALERTS_GEO_KEY = "geo";
    public static final String ALERTS_ANOMALIES_KEY = "anamolies";
    public static final String ALERTS_INCIDENT_ID_KEY = "incidentId";
    public static final String ALERTS_RECOMMENDATION_KEY = "recommendation";
    public static final String ALERTS_ANOMALIES_ID_KEY = "anomalies_id";
    public static final String ALERTS_SENSOR_CATEGORY_LIST_KEY = "sensor_category_list";
    public static final String ALERTS_ANOMALIES_INFO_KEY = "anomaliesInfo";
    public static final String ALERTS_ANOMALIES_STATIONS_KEY = "anomaliesStations";
    public static final String ALERTS_STATION_DISPLAY_NAME_KEY = "stationDisplayName";


    // Aims API /getAimsConfig?customerId={customerId}

    //QueryParam Name

    public static final String AIMS_CONFIG_QUERY_PARAM_NAME = "customerId";

    // data Map Key Constants:

    public static final String AIMS_CONFIG_DATA_RECOMMENDATION_KEY = "recommendation";
    public static final String AIMS_CONFIG_DATA_STATUS_KEY = "status";
    public static final String AIMS_CONFIG_DATA_SENSOR_KEY = "sensor";
    public static final String AIMS_CONFIG_DATA_TYPE_KEY = "type";
    public static final String AIMS_CONFIG_DATA_GROUP_BY_KEY = "groupBy";
    public static final String AIMS_CONFIG_DATA_RANK_KEY = "rank";
    public static final String AIMS_CONFIG_DATA_CATEGORY_KEY = "category";
    public static final String AIMS_CONFIG_DATA_ATTEND_KEY = "attend";
    public static final String AIMS_CONFIG_DATA_SENSOR_TYPES_LIST_KEY = "sensorTypesList";
    public static final String AIMS_CONFIG_DATA_ALERT_TYPES_LIST_KEY = "alertTypesList";
    public static final String AIMS_CONFIG_DATA_BEEP_SOUND_KEY = "beepSound";
    public static final String AIMS_CONFIG_DATA_INCIDENT_TYPE_KEY = "incidentType";
    public static final String AIMS_CONFIG_DATA_ONGOING_KEY = "ongoing";

    //Common AIMS key
    public static final String AIMS_INCIDENT_ID_KEY = "incidentId";
    public static final String AIMS_INCIDENT_TYPE_KEY = "incidentType";
    public static final String AIMS_LOCATION_KEY = "loc";
    public static final String AIMS_RECOMMENDATION_KEY = "recommendation";
    public static final String AIMS_STATUS_KEY = "status";
    public static final String AIMS_ONGOING_KEY = "ongoing";
    //Get incident specific key
    public static final String AIMS_EVENT_START_KEY = "event_start";
    public static final String AIMS_EVENT_END_KEY = "event_end";
    public static final String AIMS_ANOMALY_TYPE_KEY = "anamolyType";
    public static final String AIMS_STATE_KEY = "state";
    public static final String AIMS_TAGS_KEY = "tags";
    public static final String AIMS_TAG_NETWORK_NAME_KEY = "tag_network_name";


    //Common keys in each Map of all the List under Data Map

    //These two keys are present in each Map of 'recommendation' List ,'status' List ,
    //'sensor' List ,'type' List' ,'groupBy' List etc

    public static final String AIMS_CONFIG_TITLE_KEY = "title";
    public static final String AIMS_CONFIG_KEY_KEY = "key";

    public static final String AIMS_ANOMALY_LIST = "\"acoustic\", \"customer_meter\", \"flow\", \"pressure\", \"transient\", \"wqy\"";

    public static final String ZONE_ANOMALY_LIST = "\"zone1\", \"zone2\", \"zone3\", \"transmission\"";

    // AIMS Config recommendation Maps Title key ,Value constants

    public static final String AIMS_CONFIG_RECOMMENDATION_HIGH_VALUE = "high";
    public static final String AIMS_CONFIG_RECOMMENDATION_MEDIUM_VALUE = "medium";
    public static final String AIMS_CONFIG_RECOMMENDATION_LOW_VALUE = "low";

    // AIMS Config status Maps title key ,Value Constants

    public static final String AIMS_CONFIG_STATUS_OPEN_VALUE = "Open";
    public static final String AIMS_CONFIG_STATUS_CLOSE_VALUE = "Close";

    // AIMS Config attend Maps title key ,Value constants

    public static final String AIMS_CONFIG_ATTEND_ALL_VALUE = "All";
    public static final String AIMS_CONFIG_ATTEND_ATTENDED_VALUE = "Attended";
    public static final String AIMS_CONFIG_ATTEND_UNATTENDED_VALUE = "Un-Attended";
    public static final String AIMS_CONFIG_ATTEND_INVESTIGATION_COMPLETE_VALUE = "Investigation-Complete";
    public static final String AIMS_CONFIG_ATTEND_UNDER_INVESTIGATION_VALUE = "Under-Investigation";

    // AIMS Config category Maps ,title key ,value constants

    public static final String AIMS_CONFIG_CATEGORY_ALL_VALUE = "All";
    public static final String AIMS_CONFIG_CATEGORY_PUMP_START_VALUE = "Pump Start";
    public static final String AIMS_CONFIG_CATEGORY_VALVE_SHUT_VALUE = "Valve Shut";
    public static final String AIMS_CONFIG_CATEGORY_LEAK_VALUE = "leak";
    public static final String AIMS_CONFIG_CATEGORY_NORMAL_VALUE = "Normal";
    public static final String AIMS_CONFIG_CATEGORY_PUMP_STOP_VALUE = "Pump Stop";
    public static final String AIMS_CONFIG_CATEGORY_CONSUMPTION_START_VALUE = "Consumption Start";
    public static final String AIMS_CONFIG_CATEGORY_VIBRATION_VALUE = "Vibration (Construction etc.)";
    public static final String AIMS_CONFIG_CATEGORY_SUSPECTED_LEAK_VALUE = "Suspected Leak";
    public static final String AIMS_CONFIG_CATEGORY_UNKNOWN_BUT_IGNORE_VALUE = "Unknown-but Ignore";
    public static final String AIMS_CONFIG_CATEGORY_VALVE_OPEN_VALUE = "Valve Open";
    public static final String AIMS_CONFIG_CATEGORY_CONSUMPTION_END_VALUE = "Consumption End";
    public static final String AIMS_CONFIG_CATEGORY_NOISE_VALUE = "Noise";

    //AIMS Config beepSound Maps ,title key ,value constants

    public static final String AIMS_CONFIG_BEEP_SOUND_MUTE_VALUE = "Mute";
    public static final String AIMS_CONFIG_BEEP_SOUND_SINGLE_BEEP_VALUE = "Single Beep";
    public static final String AIMS_CONFIG_BEEP_SOUND_CONTINUOUS_BEEP_VALUE = "Continuous Beep";

    //AIMS Config 'title' key values for all 'ongoing' Maps

    public static final String AIMS_CONFIG_ONGOING_YES_VALUE = "Yes";
    public static final String AIMS_CONFIG_ONGOING_NO_VALUE = "No";


    //Aims API /getAIMSUserPreferences?userId={userId}&customerId={customerId}

    //QueryParam Names


    public static final String AIMS_USER_PREFERENCES_QUERY_PARAM_NAME_CUSTOMER_ID = "customerId";
    public static final String AIMS_USER_PREFERENCES_QUERY_PARAM_NAME_USER_ID = "userId";

    // GIS Hits Key constants

    public static final String GIS_HITS_LOCATION_KEY = "location";
    public static final String GIS_HITS_META_KEY = "meta";

    // GIS hits.location Key constants

    public static final String GIS_HITS_LOCATION_LONGITUDE_KEY = "lon";
    public static final String GIS_HITS_LOCATION_LATITUDE_KEY = "lat";
    public static final String GIS_HITS_LOCATION_SEQUENCE_KEY = "sequence";

    // GIS hits.meta key constants for all GIS types(Common)

    public static final String GIS_HITS_META_ID_KEY = "id";
    public static final String GIS_HITS_META_TYPE_KEY = "TYPE";
    public static final String GIS_HITS_META_ZONE_KEY = "zone";

    // GIS hits.meta key constants for pipe GIS type

    public static final String GIS_PIPE_HITS_META_PIPE_DIAMETER = "pipeDiameter";
    public static final String GIS_PIPE_HITS_META_PIPE_MATERIAL = "pipeMaterial";

    // GIS hits.meta key constants for non pipe(hydrants,junctions,valves) GIS types

    public static final String GIS_HITS_META_DESCRIPTION_key = "DESCRIPTION";

    // GIS hits.meta key ,value to be validated

    public static final String GIS_HITS_META_ZONE_VALUE = "sewuk";

    // ---------------Zone Theme-------------------------
    // GetCustomerInfo api Constants
    // Response map keys
    public static final String ZONE_CUSTOMER_INFO_RESPONSE_STATUS_CODE_KEY = "status_code";
    public static final String ZONE_CUSTOMER_INFO_RESPONSE_DATA_KEY = "data";
    public static final String ZONE_CUSTOMER_INFO_RESPONSE_META_KEY = "meta";
    public static final String ZONE_CUSTOMER_INFO_RESPONSE_STATUS_KEY = "status";

    // $.data keys or Response data keys
    public static final String ZONE_CUSTOMER_INFO_DATA_ID_KEY = "_id";
    public static final String ZONE_CUSTOMER_INFO_DATA_NAME_KEY = "name";
    public static final String ZONE_CUSTOMER_INFO_DATA_NETWORK_KEY = "network";
    public static final String ZONE_CUSTOMER_INFO_DATA_ZONES_KEY = "zones";

    // $.data.network[0] keys or keys in network map

    public static final String ZONE_CUSTOMER_INFO_NETWORK_ID_KEY = "_id";
    public static final String ZONE_CUSTOMER_INFO_NETWORK_NAME_KEY = "networkName";
    public static final String ZONE_CUSTOMER_INFO_NETWORK_CUSTOMER_ID_KEY = "customerId";
    public static final String ZONE_CUSTOMER_INFO_NETWORK_DMA_KEY = "dma";

    // $.data.zones[*] keys , Keys in each zones Map

    public static final String ZONE_CUSTOMER_INFO_ZONES_ID_KEY = "_id";
    public static final String ZONE_CUSTOMER_INFO_ZONES_NETWORK_ID_KEY = "networkId";
    public static final String ZONE_CUSTOMER_INFO_ZONES_ZONE_TYPE_KEY = "zoneType";
    public static final String ZONE_CUSTOMER_INFO_ZONES_ZONE_NAME_KEY = "zoneName";
    public static final String ZONE_CUSTOMER_INFO_ZONES_HAS_PARENT_KEY = "hasParent";
    public static final String ZONE_CUSTOMER_INFO_ZONES_PARENT_ZONE_KEY = "parentZone";

    // Zone theme //getThemesLabelAllAPI Constants

    // Response map keys
    public static final String ZONE_THEMES_LABEL_ALL_RESPONSE_DATA_KEY = "data";
    public static final String ZONE_THEMES_LABEL_ALL_RESPONSE_STATUS_KEY = "status";
    public static final String ZONE_THEMES_LABEL_ALL_RESPONSE_COUNT_KEY = "count";
    public static final String ZONE_THEMES_LABEL_ALL_RESPONSE_REQUEST_ID_KEY = "requestId";
    public static final String ZONE_THEMES_LABEL_ALL_RESPONSE_REQUEST_TIME_KEY = "requestTime";
    public static final String ZONE_THEMES_LABEL_ALL_RESPONSE_RESPONSE_TIME_KEY = "responseTime";
    public static final String ZONE_THEMES_LABEL_ALL_RESPONSE_STATUS_TEXT_KEY = "statusText";
    public static final String ZONE_THEMES_LABEL_ALL_RESPONSE_TIME_KEY = "time";

    // $.data keys or Response data keys

    public static final String ZONE_THEMES_LABEL_ALL_DATA_CATEGORY_KEY = "category";
    public static final String ZONE_THEMES_LABEL_ALL_DATA_CUSTOMER_KEY = "customer";
    public static final String ZONE_THEMES_LABEL_ALL_DATA_CUSTOMER_ID_KEY = "customerId";
    public static final String ZONE_THEMES_LABEL_ALL_DATA_ID_KEY = "id";
    public static final String ZONE_THEMES_LABEL_ALL_DATA_KEY_KEY = "key";
    public static final String ZONE_THEMES_LABEL_ALL_DATA_NAME_KEY = "name";

    // $.data.customer keys ,keys in customer Map

    public static final String ZONE_THEMES_LABEL_ALL_CUSTOMER_ID_KEY = "id";
    public static final String ZONE_THEMES_LABEL_ALL_CUSTOMER_NAME_KEY = "name";

    // Water Balance API's constants
    // Response Map keys for all Water Balance API's
    public static final String ZONE_WATER_BALANCE_RESPONSE_DATA_KEY = "data";
    public static final String ZONE_WATER_BALANCE_RESPONSE_REQUEST_ID_KEY = "requestId";
    public static final String ZONE_WATER_BALANCE_RESPONSE_REQUEST_TIME_KEY = "requestTime";
    public static final String ZONE_WATER_BALANCE_RESPONSE_RESPONSE_TIME_KEY = "responseTime";
    public static final String ZONE_WATER_BALANCE_RESPONSE_STATUS_KEY = "status";
    public static final String ZONE_WATER_BALANCE_RESPONSE_STATUS_TEXT_KEY = "statusText";
    public static final String ZONE_WATER_BALANCE_RESPONSE_TIME_KEY = "time";

    // $.data keys for WaterBalance MetaDataAPI and Default API's

    public static final String ZONE_WATER_BALANCE_DATA_COMMON_CLASS_KEY = "class";
    public static final String ZONE_WATER_BALANCE_DATA_COMMON_KEY_KEY = "key";
    public static final String ZONE_WATER_BALANCE_DATA_METADATA_ID_KEY = "id";
    public static final String ZONE_WATER_BALANCE_DATA_METADATA_UI_ELEMENT_KEY = "ui_element";
    public static final String ZONE_WATER_BALANCE_DATA_COMMON_CUSTOMER_ID_KEY = "customerId";
    public static final String ZONE_WATER_BALANCE_DATA_DEFAULT_REQUEST_TYPE_KEY = "requestType";
    public static final String ZONE_WATER_BALANCE_DATA_COMMON_BOTTOM_UP_DATA_KEY = "bottom_up_data";
    public static final String ZONE_WATER_BALANCE_DATA_COMMON_TOP_TO_BOTTOM_DATA_KEY = "top_to_bottom_data";

    // $.data map keys specific to Water balance data api
    public static final String ZONE_WATER_BALANCE_DATA_ET_KEY = "et";
    public static final String ZONE_WATER_BALANCE_DATA_ST_KEY = "st";
    public static final String ZONE_WATER_BALANCE_DATA_DMS_IDS_KEY = "dms_ids";

    // $.data.ui_element[*] keys for WaterBalance metaData API

    public static final String ZONE_WATER_BALANCE_METADATA_UI_ELEMENT_KEY_KEY = "key";
    public static final String ZONE_WATER_BALANCE_METADATA_UI_ELEMENT_LABEL_KEY = "label";
    public static final String ZONE_WATER_BALANCE_METADATA_UI_ELEMENT_ROW_KEY = "row";
    public static final String ZONE_WATER_BALANCE_METADATA_UI_ELEMENT_STYLE_KEY = "style";
    public static final String ZONE_WATER_BALANCE_METADATA_UI_ELEMENT_UNIT_KEY = "unit";

    // $.data.top_to_bottom_data keys for Water balance default API for water model
    // top_to_bottom

    public static final String ZONE_WATER_BALANCE_DEFAULT_BILLED_METERED_CONSUMPTION_KEY = "billed_metered_consumption";
    public static final String ZONE_WATER_BALANCE_DEFAULT_BILLED_UNMETERED_CONSUMPTION_KEY = "billed_unmetered_consumption";
    public static final String ZONE_WATER_BALANCE_DEFAULT_CUSTOMER_METER_INNACURACIES_KEY = "customer_meter_inaccuracies";
    public static final String ZONE_WATER_BALANCE_DEFAULT_SYSTEM_INPUT_VOLUME_KEY = "system_input_volume";
    public static final String ZONE_WATER_BALANCE_DEFAULT_UNAUTHORIZED_CONSUMPTION_KEY = "unauthorized_consumption";
    public static final String ZONE_WATER_BALANCE_DEFAULT_UNBILLED_METERED_CONSUMPTION_KEY = "unbilled_metered_consumption";
    public static final String ZONE_WATER_BALANCE_DEFAULT_UNBILLED_UNMETERED_CONSUMPTION_KEY = "unbilled_unmetered_consumption";

    // $.data.top_to_bottom_data keys for Water balance data API for water model top
    // to bottom
    // Here we are mentioning keys that are only present for data api ,since there
    // are some keys common to both
    // data and default apis

    public static final String ZONE_WATER_BALANCE_DATA_AUTHORIZED_CONSUMPTION_KEY = "authorized_consumption";
    public static final String ZONE_WATER_BALANCE_DATA_BILLED_AUTHORIZED_CONSUMPTION_KEY = "billed_authorized_consumption";
    public static final String ZONE_WATER_BALANCE_DATA_COMMERCIAL_LOSSES_KEY = "commercial_losses";
    public static final String ZONE_WATER_BALANCE_DATA_NON_REVENUE_WATER_KEY = "non_revenue_water";
    public static final String ZONE_WATER_BALANCE_DATA_PHYSICAL_LOSSES_KEY = "physical_losses";
    public static final String ZONE_WATER_BALANCE_DATA_REVENUE_WATER_KEY = "revenue_water";
    public static final String ZONE_WATER_BALANCE_DATA_UNBILLED_AUTHORIZED_CONSUMPTION_KEY = "unbilled_authorized_consumption";
    public static final String ZONE_WATER_BALANCE_DATA_WATER_LOSSES_KEY = "water_losses";

    // $.data.bottom_up_data keys for Water balance default API for water model
    // bottom_up
    public static final String ZONE_WATER_BALANCE_DEFAULT_ASSESSED_NON_RESIDENTIAL_NIGHT_USE_KEY = "assessed_non_residential_night_use";
    public static final String ZONE_WATER_BALANCE_DEFAULT_ASSESSED_RESIDENTIAL_NIGHT_USE_KEY = "assessed_residential_night_use";
    public static final String ZONE_WATER_BALANCE_DEFAULT_EXCEPTIONAL_NIGHT_USE_KEY = "exceptional_night_use";
    public static final String ZONE_WATER_BALANCE_DEFAULT_INSIDE_BUILDINGS_KEY = "inside_buildings";
    public static final String ZONE_WATER_BALANCE_DEFAULT_MINIMUM_NIGHT_FLOW_KEY = "minimum_night_flow";
    public static final String ZONE_WATER_BALANCE_DEFAULT_NIGHT_DAY_FACTOR_KEY = "night_day_factor";
    public static final String ZONE_WATER_BALANCE_DEFAULT_OUTSIDE_BUILDINGS_KEY = "outside_buildings";

    // $.data.bottom_up_data keys for Water balance data API for water model bottom
    // up
    // Here we are mentioning keys that are only present for data api ,since there
    // are some keys common to both
    // data and default apis

    public static final String ZONE_WATER_BALANCE_DATA_BACKGROUND_LEAKAGE_KEY = "background_leakage";
    public static final String ZONE_WATER_BALANCE_DATA_BURSTS_KEY = "bursts";
    public static final String ZONE_WATER_BALANCE_DATA_CUSTOMER_NIGHT_LEAKAGE_KEY = "customer_night_leakage";
    public static final String ZONE_WATER_BALANCE_DATA_NIGHT_CONSUMPTION_NC_KEY = "night_consumption_nc";
    public static final String ZONE_WATER_BALANCE_DATA_NIGHT_USE_KEY = "night_use";
    public static final String ZONE_WATER_BALANCE_DATA_ON_SERVICE_CONNS_ON_MAINS_KEY = "on_service_conns_on_mains";
    public static final String ZONE_WATER_BALANCE_DATA_REPORTED_BURSTS_KEY = "reported_bursts";
    public static final String ZONE_WATER_BALANCE_DATA_UNREPORTED_BURSTS_KEY = "unreported_bursts";
    public static final String ZONE_WATER_BALANCE_DATA_UTILITY_DAILY_LEAKAGE_KEY = "utility_daily_leakage";
    public static final String ZONE_WATER_BALANCE_DATA_UTILITY_NIGHT_LEAKAGE_KEY = "utility_night_leakage";

    // statistics/all API constants

    // Response map keys for all statistics/all API's
    // The Response map keys for statistics/all api is same as waterbalance api
    // still defining them as Separate constants

    public static final String ZONE_STATISTICS_ALL_RESPONSE_DATA_KEY = "data";
    public static final String ZONE_STATISTICS_ALL_RESPONSE_REQUEST_ID_KEY = "requestId";
    public static final String ZONE_STATISTICS_ALL_RESPONSE_REQUEST_TIME_KEY = "requestTime";
    public static final String ZONE_STATISTICS_ALL_RESPONSE_RESPONSE_TIME_KEY = "responseTime";
    public static final String ZONE_STATISTICS_ALL_RESPONSE_STATUS_KEY = "status";
    public static final String ZONE_STATISTICS_ALL_RESPONSE_STATUS_TEXT_KEY = "statusText";
    public static final String ZONE_STATISTICS_ALL_RESPONSE_TIME_KEY = "time";

    // $.data keys or Response data keys for statistics/all api

    public static final String ZONE_STATISTICS_ALL_DATA_CLASS_KEY = "class";
    public static final String ZONE_STATISTICS_ALL_DATA_COLUMN_BANDS_KEY = "columnBands";
    public static final String ZONE_STATISTICS_ALL_DATA_LABEL_MAPPING_KEY = "labelMapping";
    public static final String ZONE_STATISTICS_ALL_DATA_STATISTICS_LIST_KEY = "statisticsList";

    // $.data.columnBands keys or keys in each columnBands map

    public static final String ZONE_STATISTICS_ALL_COLUMN_BANDS_CHILDREN_KEY = "children";
    public static final String ZONE_STATISTICS_ALL_COLUMN_BANDS_JOIN_BY = "join_by";

    // $.data.columnBands[0].children values ,children List value constants

    public static final String ZONE_STATISTICS_ALL_CHILDREN_CURRENT_DEMAND_VALUE = "current_demand";
    public static final String ZONE_STATISTICS_ALL_CHILDREN_DEMAND_CONFIDENCE_VALUE = "demand_confidence";

    // $.data.labelMapping keys

    public static final String ZONE_STATISTICS_ALL_LABEL_MAPPING_CONSUMPTION_3_KEY = "Consumption_3";
    public static final String ZONE_STATISTICS_ALL_LABEL_MAPPING_DEMAND_4_KEY = "Demand_4";
    public static final String ZONE_STATISTICS_ALL_LABEL_MAPPING_FLOW_1_KEY = "Flow_1";
    public static final String ZONE_STATISTICS_ALL_LABEL_MAPPING_OTHERS_5_KEY = "Others_5";
    public static final String ZONE_STATISTICS_ALL_LABEL_MAPPING_PRESSURE_2_KEY = "Pressure_2";

    // $.data.labelMapping.Consumption_3 keys

    public static final String ZONE_STATISTICS_ALL_AGGREGATE_CONSUMPTION_MECHANCAL_KEY = "aggregate_consumption_mechanical";
    public static final String ZONE_STATISTICS_ALL_AGGREGATE_CONSUMPTION_RATE_SMART_KEY = "aggregate_consumption_rate_smart";

    // $.data.labelMapping.Demand_4 keys

    public static final String ZONE_STATISTICS_ALL_CURRENT_DEMAND_KEY = "current_demand";
    public static final String ZONE_STATISTICS_ALL_DEMAND_CONFIDENCE_KEY = "demand_confidence";
    public static final String ZONE_STATISTICS_ALL_MINIMUM_NIGHT_DEMAND_KEY = "minimum_night_demand";

    // $.data.labelMapping.Flow_1 keys

    public static final String ZONE_STATISTICS_ALL_MINIMUM_NIGHT_INFLOW_KEY = "minimum_night_inflow";
    public static final String ZONE_STATISTICS_ALL_MINIMUM_NIGHT_OUTFLOW_KEY = "minimum_night_outflow";
    public static final String ZONE_STATISTICS_ALL_TOTAL_INFLOW_KEY = "total_inflow";
    public static final String ZONE_STATISTICS_ALL_TOTAL_OUTFLOW_KEY = "total_outflow";

    // $.data.labelMapping.Others_5 keys

    public static final String ZONE_STATISTICS_ALL_AGGREGATE_IN_VOLUME_KEY = "aggregate_in_volume";
    public static final String ZONE_STATISTICS_ALL_AGGREGATE_OUT_VOLUME_KEY = "aggregate_out_volume";
    public static final String ZONE_STATISTICS_ALL_AGGREGATE_TOTAL_VOLUME_KEY = "aggregate_total_volume";
    public static final String ZONE_STATISTICS_ALL_MASS_BALANCE_KEY = "mass_balance";
    public static final String ZONE_STATISTICS_ALL_NUMBER_OF_ALERTS_KEY = "number_of_alerts";
    public static final String ZONE_STATISTICS_ALL_NUMBER_OF_CONNECTIONS_KEY = "number_of_connections";
    public static final String ZONE_STATISTICS_ALL_TOTAL_IN_VOLUME_KEY = "total_in_volume";
    public static final String ZONE_STATISTICS_ALL_TOTAL_OUT_VOLUME_KEY = "total_out_volume";
    public static final String ZONE_STATISTICS_ALL_TOTAL_VOLUME_KEY = "total_volume";
    public static final String ZONE_STATISTICS_ALL_TOTAL_VOLUME_OVER_TIME_KEY = "total_volume_over_time";

    // $.data.labelMapping.Pressure_2 keys

    public static final String ZONE_STATISTICS_ALL_AVERAGE_PRESSURE_KEY = "average_pressure";
    public static final String ZONE_STATISTICS_ALL_MAX_NIGHT_AVERAGE_PRESSURE_KEY = "max_night_average_pressure";
    public static final String ZONE_STATISTICS_ALL_MAX_PRESSURE_KEY = "max_pressure";
    public static final String ZONE_STATISTICS_ALL_MIN_DAY_AVERAGE_PRESSURE_KEY = "min_day_average_pressure";
    public static final String ZONE_STATISTICS_ALL_MIN_PRESSURE_KEY = "min_pressure";

    // All statistics label values

    public static final String ZONE_STATISTICS_ALL_AGGREGATE_CONSUMPTION_MECHANCAL_LABEL_VALUE = "Aggregate Consumption Mechanical (L/s)";
    public static final String ZONE_STATISTICS_ALL_AGGREGATE_CONSUMPTION_RATE_SMART_LABEL_VALUE = "Aggregate Consumption Rate Smart (L/s)";
    public static final String ZONE_STATISTICS_ALL_CURRENT_DEMAND_LABEL_VALUE = "Current Demand (L/s)";
    public static final String ZONE_STATISTICS_ALL_DEMAND_CONFIDENCE_LABEL_VALUE = "Confidence (%)";
    public static final String ZONE_STATISTICS_ALL_MINIMUM_NIGHT_DEMAND_LABEL_VALUE = "Minimum Night Demand (L/s)";
    public static final String ZONE_STATISTICS_ALL_MINIMUM_NIGHT_INFLOW_LABEL_VALUE = "Minimum Night Inflow (L/s)";
    public static final String ZONE_STATISTICS_ALL_MINIMUM_NIGHT_OUTFLOW_LABEL_VALUE = "Minimum Night Outflow (L/s)";
    public static final String ZONE_STATISTICS_ALL_TOTAL_INFLOW_LABEL_VALUE = "Total Inflow (L/s)";
    public static final String ZONE_STATISTICS_ALL_TOTAL_OUTFLOW_LABEL_VALUE = "Total Outflow (L/s)";
    public static final String ZONE_STATISTICS_ALL_AGGREGATE_IN_VOLUME_LABEL_VALUE = "Aggregate In Volume (kL)";
    public static final String ZONE_STATISTICS_ALL_AGGREGATE_OUT_VOLUME_LABEL_VALUE = "Aggregate Out Volume (kL)";
    public static final String ZONE_STATISTICS_ALL_AGGREGATE_TOTAL_VOLUME_LABEL_VALUE = "Aggregate Total Volume (kL)";
    public static final String ZONE_STATISTICS_ALL_MASS_BALANCE_LABEL_VALUE = "Mass Balance (L/s)";
    public static final String ZONE_STATISTICS_ALL_NUMBER_OF_ALERTS_LABEL_VALUE = "Number of Alerts";
    public static final String ZONE_STATISTICS_ALL_NUMBER_OF_CONNECTIONS_LABEL_VALUE = "Number of Connections";
    public static final String ZONE_STATISTICS_ALL_TOTAL_IN_VOLUME_LABEL_VALUE = "Total In Volume (kL)";
    public static final String ZONE_STATISTICS_ALL_TOTAL_OUT_VOLUME_LABEL_VALUE = "Total Out Volume (kL)";
    public static final String ZONE_STATISTICS_ALL_TOTAL_VOLUME_LABEL_VALUE = "Total Volume (kL)";
    public static final String ZONE_STATISTICS_ALL_TOTAL_VOLUME_OVER_TIME_LABEL_VALUE = "Total Volume Over Time (kL/hr)";
    public static final String ZONE_STATISTICS_ALL_AVERAGE_PRESSURE_LABEL_VALUE = "Average Pressure (mH)";
    public static final String ZONE_STATISTICS_ALL_MAX_NIGHT_AVERAGE_PRESSURE_LABEL_VALUE = "Maximum Night Average Pressure (mH)";
    public static final String ZONE_STATISTICS_ALL_MAX_PRESSURE_LABEL_VALUE = "Maximum Pressure (mH)";
    public static final String ZONE_STATISTICS_ALL_MIN_DAY_AVERAGE_PRESSURE_LABEL_VALUE = "Minimum Day Average Pressure (mH)";
    public static final String ZONE_STATISTICS_ALL_MIN_PRESSURE_LABEL_VALUE = "Minimum Pressure (mH)";

    // Statistic map and label key

    public static final String ZONE_STATISTICS_ALL_MAP_KEY = "map";
    public static final String ZONE_STATISTICS_ALL_MAP_LABEL_KEY = "label";

    // statisticsList Map keys

    // $.data.statisticsList[*] keys

    public static final String ZONE_STATISTICS_ALL_STATISTICS_LIST_STATISTICS_KEY = "statistics";
    public static final String ZONE_STATISTICS_ALL_STATISTICS_LIST_ZONE_ID_KEY = "zone_id";

    // $.data.statisticsList[*].statistics keys

    public static final String ZONE_STATISTICS_ALL_SMART_METER_PERCENTAGE_KEY = "smart_meter_percentage";

    // getKML API Constants

    // Response Map keys for getKMLAPI

    public static final String ZONE_GET_KML_RESPONSE_STATUS_CODE_KEY = "status_code";
    public static final String ZONE_GET_KML_RESPONSE_DATA_KEY = "data";
    public static final String ZONE_GET_KML_RESPONSE_META_KEY = "meta";
    public static final String ZONE_GET_KML_RESPONSE_STATUS_KEY = "status";

    // status key value

    public static final String ZONE_GET_KML_RESPONSE_STATUS_VALUE = "success";

    // Data Map keys for getKMLAPI($.data)

    public static final String ZONE_GET_KML_DATA_ID_KEY = "_id";
    public static final String ZONE_GET_KML_DATA_ZONE_ID_KEY = "zoneId";
    public static final String ZONE_GET_KML_DATA_CUSTOMER_KEY = "customer";
    public static final String ZONE_GET_KML_DATA_ZONE_NAME_KEY = "zoneName";
    public static final String ZONE_GET_KML_DATA_FILE_NAME_KEY = "fileName";
    public static final String ZONE_GET_KML_DATA_GEO_JSON_KEY = "geoJson";

    // Response map keys for all Zone Trends API's
    // The Response map keys for Trends api is same as waterbalance api still
    // defining them as Separate constants

    public static final String ZONE_TRENDS_RESPONSE_DATA_KEY = "data";
    public static final String ZONE_TRENDS_RESPONSE_REQUEST_ID_KEY = "requestId";
    public static final String ZONE_TRENDS_RESPONSE_REQUEST_TIME_KEY = "requestTime";
    public static final String ZONE_TRENDS_RESPONSE_RESPONSE_TIME_KEY = "responseTime";
    public static final String ZONE_TRENDS_RESPONSE_STATUS_KEY = "status";
    public static final String ZONE_TRENDS_RESPONSE_STATUS_TEXT_KEY = "statusText";
    public static final String ZONE_TRENDS_RESPONSE_TIME_KEY = "time";
    public static final String ZONE_TRENDS_RESPONSE_COUNT_KEY = "count";

    // keys in data map for Zone Trends

    public static final String ZONE_TRENDS_DATA_CSV_DATA_KEY = "csvData";
    public static final String ZONE_TRENDS_DATA_CSV_HEADER_KEY = "csvHeader";
    public static final String ZONE_TRENDS_DATA_META_LIST_KEY = "metaList";
    public static final String ZONE_TRENDS_DATA_SENSOR_TYPE_KEY = "sensorType";

    public static final String DATA_CSV_DATA_KEY = "csvData";
    public static final String DATA_CSV_HEADER_KEY = "csvHeader";
    public static final String DATA_META_LIST_KEY = "metaList";
    public static final String DATA_SENSOR_TYPE_KEY = "sensorType";

    // csvData List each Element size

    public static final int ZONE_TRENDS_CSV_DATA_ELEMENT_SIZE = 2;

    // csvHeader List size

    public static final int ZONE_TRENDS_CSV_HEADER_SIZE = 2;

    // csvHeader List value

    public static final String ZONE_TRENDS_CSV_HEADER_TIMESTAMP_VALUE = "timestamp";

    // Keys in meta List Map for Zone Trends

    public static final String ZONE_TRENDS_METALIST_SENSOR_ID_KEY = "sensorId";
    public static final String ZONE_TRENDS_METALIST_SENSOR_UNIT_KEY = "sensorUnit";

    // metaList size for Zone Trends

    public static final int ZONE_TRENDS_METALIST_SIZE = 1;

//meta Search

    // meta/search API constants :
    // object map keys
    public static final String META_SEARCH_OBJECT_ERROR_CODE_KEY = "errorCode";
    public static final String META_SEARCH_OBJECT_RESPONSE_KEY = "response";

    // response Map keys

    public static final String META_SEARCH_RESPONSE_HITS_KEY = "hits";

    // hits Map key (response.hits )keys

    public static final String META_SEARCH_HITS_HITS_KEY = "hits";

    // response.hits.hits keys

    public static final String META_SEARCH_HITS_TYPE_KEY = "_type";
    public static final String META_SEARCH_HITS_ID_KEY = "_id";
    public static final String META_SEARCH_HITS_LEVEL_KEY = "_level";
    public static final String META_SEARCH_HITS_SOURCE_KEY = "_source";

    // Transient Theme Constants

    // getRangedDeltaStatsAndCoverage API constants

    // object map keys

    public static final String TRANSIENT_DELTA_STATS_COVERAGE_OBJECT_ERROR_CODE_KEY = "errorCode";
    public static final String TRANSIENT_DELTA_STATS_COVERAGE_OBJECT_RESPONSE_KEY = "response";

    // response map keys

    public static final String TRANSIENT_DELTA_STATS_COVERAGE_RESPONSE_DELTA_STATS_KEY = "deltastats";
    public static final String TRANSIENT_DELTA_STATS_COVERAGE_RESPONSE_DELTA_COVERAGE_KEY = "coverage";

    // delta stats map keys response.deltastats[*] keys common keys in all Maps

    public static final String TRANSIENT_DELTA_STATS_STN_ID_KEY = "stn_id";
    public static final String TRANSIENT_DELTA_STATS_ZONE_ID_KEY = "zone_id";
    public static final String TRANSIENT_DELTA_STATS_STATISTICS_KEY = "statistics";
    public static final String TRANSIENT_DELTA_STATS_UNIT_KEY = "unit";
    public static final String TRANSIENT_DELTA_STATS_SUPPLY_ZONE_KEY = "supply_zone";
    public static final String TRANSIENT_DELTA_STATS_LOC_KEY = "loc";
    public static final String TRANSIENT_DELTA_STATS_STATION_KEY = "station";

    // getNearByLeaksByStationIds API constants

//object map keys

    public static final String TRANSIENT_NEAR_BY_LEAKS_OBJECT_ERROR_CODE_KEY = "errorCode";
    public static final String TRANSIENT_NEAR_BY_LEAKS_OBJECT_RESPONSE_KEY = "response";

    // Load Devices

// Load Devices API constants :

    public static final String ACOUSTIC_SENSORTYPE_ACTUAL_VALUE = "\"acoustic\",\"hydrophone\"";
    public static final String BTRY_SENSORTYPE_ACTUAL_VALUE = "\"btry\"";
    public static final String CUSTOMERMETER_SENSORTYPE_ACTUAL_VALUE = "\"consumption\",\"reading\"";
    public static final String FLOW_SENSORTYPE_ACTUAL_VALUE = "\"flow\"";
    public static final String LEVEL_SENSORTYPE_ACTUAL_VALUE = "\"level\"";
    public static final String TRANSIENT_SENSORTYPE_ACTUAL_VALUE = "\"pressure\"";
    public static final String TRANSIENT_TERM_VALUE = "\"highrate\":true";
    public static final String PRESSURE_SENSORTYPE_ACTUAL_VALUE = "\"pressure\"";
    public static final String PRESSURE_TERM_VALUE = "\"tag_datasource\":[\"aquanet\",\"visentifile\"]";
    public static final String WQY_SENSORTYPE_ACTUAL_VALUE = "\"ph\",\"cty\",\"chlorine\",\"turbidity\",\"temp\",\"oxygen\",\"orp\"";

    //PUB Network Constants for Load Devices meta/search api
    public static final String POTABLE_WATER_NETWORK_DMA_LIST = "\"fcph\",\"bbsr\",\"eastern\",\"krsr\",\"nysr\",\"qhpz\",\"bksr\",\"isr\",\"western\",\"bpsr\",\"mnsr\"";
    public static final String POTABLE_WATER_TAG_NETWORK_NAME = "\"potable_water\"";


    // object map keys
    public static final String DEVICES_OBJECT_ERROR_CODE_KEY = "errorCode";
    public static final String DEVICES_OBJECT_RESPONSE_KEY = "response";

    // response Map keys

    public static final String DEVICES_RESPONSE_HITS_KEY = "hits";

    // hits Map key (response.hits )keys

    public static final String DEVICES_HITS_HITS_KEY = "hits";

    // response.hits.hits keys

    public static final String DEVICES_HITS_TYPE_KEY = "_type";
    public static final String DEVICES_HITS_ID_KEY = "_id";
    public static final String DEVICES_HITS_LEVEL_KEY = "_level";
    public static final String DEVICES_HITS_SOURCE_KEY = "_source";

    // UMS /account?aggregated=true API constants

    public static final String UMS_ACCOUNT_QUERY_PARAM_NAME = "aggregated";
    public static final String UMS_ACCOUNT_QUERY_PARAM_VALUE = "true";

    // $.data Map keys in UMS /account api
    public static final String UMS_ACCOUNT_DATA_CUSTOMER_ID_KEY = "customer_id";
    public static final String UMS_ACCOUNT_DATA_ID_KEY = "id";
    public static final String UMS_ACCOUNT_DATA_MAIL_KEY = "mail";
    public static final String UMS_ACCOUNT_DATA_USER_PROFILE_KEY = "user_profile";
    public static final String UMS_ACCOUNT_DATA_USERNAME_KEY = "username";
    public static final String UMS_ACCOUNT_DATA_CUSTOMERS_KEY = "customers";
    public static final String UMS_ACCOUNT_DATA_PROFILES_KEY = "profiles";

    //$.data.customers List keys in UMS /account api

    public static final String UMS_ACCOUNT_CUSTOMERS_CUSTOMER_ID_KEY = "customer_id";
    public static final String UMS_ACCOUNT_CUSTOMERS_ID_KEY = "id";
    public static final String UMS_ACCOUNT_CUSTOMERS_TIMEZONE_KEY = "timezone";
    public static final String UMS_ACCOUNT_CUSTOMERS_APPLICATION_IDS_KEY = "application_ids";
    public static final String UMS_ACCOUNT_CUSTOMERS_MAP_KEY = "map";
    public static final String UMS_ACCOUNT_CUSTOMERS_NAME_KEY = "name";
    public static final String UMS_ACCOUNT_CUSTOMERS_LICENSE_KEY = "license";


    //UMS /serviceValidates API Constants

    public static final String UMS_SERVICE_VALIDATES_QUERY_PARAM_NAME = "groupby";
    public static final String UMS_SERVICE_VALIDATES_QUERY_PARAM_VALUE = "resource";

    public static final String UMS_SERVICE_VALIDATES_APPLICATION_NAME = "dataintel";


    //Response Map $ keys in UMS  /serviceValidates API

    //$.data List Keys in Each Map

    public static final String UMS_SERVICE_VALIDATES_DATA_BY_RESOURCES_KEY = "by_resources";
    public static final String UMS_SERVICE_VALIDATES_DATA_CUSTOMER_ID_KEY = "customer_id";
    public static final String UMS_SERVICE_VALIDATES_DATA_MAIL_KEY = "mail";
    public static final String UMS_SERVICE_VALIDATES_DATA_PROFILES_KEY = "profiles";
    public static final String UMS_SERVICE_VALIDATES_DATA_USERNAME_KEY = "username";


    //$.data[0].by_resources Map Keys

    public static final String UMS_SERVICE_VALIDATES_BY_RESOURCES_APPS_KEY = "apps";
    public static final String UMS_SERVICE_VALIDATES_BY_RESOURCES_SCRIPTS_KEY = "scripts";
    public static final String UMS_SERVICE_VALIDATES_BY_RESOURCES_SENSORS_KEY = "sensors";
    public static final String UMS_SERVICE_VALIDATES_BY_RESOURCES_ZONES_KEY = "zones";

    //DMA /commattributes/all API Constants

    //Data List keys in each Map for DMA /commattributes/all API

    public static final String DMA_COMM_ATTRIBUTE_DATA_BASE_PROPERTIES_KEY = "baseProperties";
    public static final String DMA_COMM_ATTRIBUTE_DATA_CUSTOMER_KEY = "customer";
    public static final String DMA_COMM_ATTRIBUTE_DATA_CUSTOMER_ID_KEY = "customerId";
    public static final String DMA_COMM_ATTRIBUTE_DATA_ELASTIC_PROPERTIES_KEY = "elasticProperties";
    public static final String DMA_COMM_ATTRIBUTE_DATA_ID_KEY = "id";
    public static final String DMA_COMM_ATTRIBUTE_DATA_VIEW_PROPERTIES_KEY = "viewProperties";

    //$.data[0].customer Map Keys

    public static final String DMA_COMM_ATTRIBUTE_CUSTOMER_ID_KEY = "id";
    public static final String DMA_COMM_ATTRIBUTE_CUSTOMER_NAME_KEY = "name";


    //$.data[0].elasticProperties Map Keys

    public static final String DMA_COMM_ATTRIBUTE_ELASTIC_PROPERTIES_INDEX_KEY = "index";
    public static final String DMA_COMM_ATTRIBUTE_ELASTIC_PROPERTIES_LAYERS_INDEX_KEY = "layersIndex";
    public static final String DMA_COMM_ATTRIBUTE_ELASTIC_PROPERTIES_PIPE_INDEX_KEY = "pipeIndex";
    public static final String DMA_COMM_ATTRIBUTE_ELASTIC_PROPERTIES_SENSOR_TYPE_KEY = "sensorType";
    public static final String DMA_COMM_ATTRIBUTE_ELASTIC_PROPERTIES_STATION_TYPE_KEY = "stationType";

    //$.data[0].viewProperties Map Keys

    public static final String DMA_COMM_ATTRIBUTE_VIEW_PROPERTIES_CENTRE_KEY = "centre";
    public static final String DMA_COMM_ATTRIBUTE_VIEW_PROPERTIES_ZOOM_KEY = "zoom";


    //Data displayunit/all API Constants

    //Keys in each Map of $.data List
    public static final String DATA_DISPLAY_UNIT_DATA_ID_KEY = "id";
    public static final String DATA_DISPLAY_UNIT_DATA_SENSOR_UNIT_DISPLAY_KEY = "sensor_unit_display";
    public static final String DATA_DISPLAY_UNIT_DATA_SENSOR_TYPE_ACTUAL_KEY = "sensortype_actual";
    public static final String DATA_DISPLAY_UNIT_DATA_SENSOR_TYPE_BACKEND_KEY = "sensortype_backend";


    //GIS /get/customerInfo?customer={customerName} API Constants

    public static final String GIS_CUSTOMER_INFO_QUERY_PARAM_NAME = "customer";


    //Keys in data Map($.data)

    public static final String GIS_CUSTOMER_INFO_DATA_ID_KEY = "_id";
    public static final String GIS_CUSTOMER_INFO_DATA_NAME_KEY = "name";
    public static final String GIS_CUSTOMER_INFO_DATA_NETWORK_KEY = "network";
    public static final String GIS_CUSTOMER_INFO_DATA_ZONES_KEY = "zones";


    //Keys in each Map in $.data.network List

    public static final String GIS_CUSTOMER_INFO_NETWORK_ID_KEY = "_id";
    public static final String GIS_CUSTOMER_INFO_NETWORK_NETWORK_NAME_KEY = "networkName";
    public static final String GIS_CUSTOMER_INFO_NETWORK_CUSTOMER_ID_KEY = "customerId";
    public static final String GIS_CUSTOMER_INFO_NETWORK_LABEL_KEY = "label";

    //For 'potable_water' network name ,we have dma key in the $.data.network List
    //For 'newater' network name we have dmz key in the $.data.network List

    public static final String GIS_CUSTOMER_INFO_NETWORK_DMA_KEY = "dma";
    public static final String GIS_CUSTOMER_INFO_NETWORK_DMZ_KEY = "dmz";


    //Keys in each Map in $.data.zones List

    public static final String GIS_CUSTOMER_INFO_ZONES_ID_KEY = "_id";
    public static final String GIS_CUSTOMER_INFO_ZONES_NETWORK_ID_KEY = "networkId";
    public static final String GIS_CUSTOMER_INFO_ZONES_ZONE_TYPE_KEY = "zoneType";
    public static final String GIS_CUSTOMER_INFO_ZONES_ZONE_NAME_KEY = "zoneName";
    public static final String GIS_CUSTOMER_INFO_ZONES_HAS_PARENT_KEY = "hasParent";
    public static final String GIS_CUSTOMER_INFO_ZONES_PARENT_ZONE_KEY = "parentZone";

    //Keys in meta Map ($.meta)
	
	/*public static final String GIS_CUSTOMER_INFO_META_ERROR_MESSAGE_KEY="error_message";
	public static final String GIS_CUSTOMER_INFO_META_ERROR_TYPE_KEY="error_type";*/

    //Data Api /healthmonitor/livestatus?type=["station"] API constants

    public static final String DATA_LIVE_STATUS_QUERY_PARAM_NAME = "type";
    //public static final String DATA_LIVE_STATUS_QUERY_PARAM_VALUE_STATION_ENCODED="[%22station%22]";
    public static final String DATA_LIVE_STATUS_QUERY_PARAM_VALUE_STATION = "\"OFFLINE\"";

    //Keys in each Map of data List

    public static final String DATA_LIVE_STATUS_CUSTOMER_ID_KEY = "customer_id";
    public static final String DATA_LIVE_STATUS_INACTIVE_SENSOR_KEY = "inactive_out_of_total_sensor";
    public static final String DATA_LIVE_STATUS_LATITUDE_KEY = "latitude";
    public static final String DATA_LIVE_STATUS_LONGITUDE_KEY = "longitude";
    public static final String DATA_LIVE_STATUS_MAXIMUM_LATENCY_KEY = "maximum_latency";
    public static final String DATA_LIVE_STATUS_NAME_KEY = "name";
    public static final String DATA_LIVE_STATUS_SOURCE_ID_KEY = "source_id";
    public static final String DATA_LIVE_STATUS_STATION_ID_KEY = "stationId";
    public static final String DATA_LIVE_STATUS_STATUS_KEY = "status";
    public static final String DATA_LIVE_STATUS_TYPE_KEY = "type";
    public static final String DATA_LIVE_STATUS_ZONE_KEY = "zone";


    //Common base path for all cas api's
    public static final String CAS_BASE_PATH = "/cas";


    //cas oauth2.0/accessToken API constants

    public static final String CAS_ACCESS_TOKEN_RESOURCE_PATH = "/oauth2.0/accessToken";
    public static final String CAS_ACCESS_TOKEN_QUERY_PARAM_CLIENT_SECRET_NAME = "client_secret";
    public static final String CAS_ACCESS_TOKEN_QUERY_PARAM_CLIENT_ID_NAME = "client_id";
    public static final String CAS_ACCESS_TOKEN_QUERY_PARAM_GRANT_TYPE_NAME = "grant_type";
    public static final String CAS_ACCESS_TOKEN_QUERY_PARAM_GRANT_TYPE_VALUE = "client_credentials";


    //The AccessToken Key Prefix would be the Customer name
    //Example if the Customer name passed from pom.xml or Cmd is 'pub' ,the key in corresponding Env properties file would be saved as 'pub.cas.access.token'
    //Similarly if the Customer if 'yw' the key in Env properties file would be saved as yw.cas.access.token

    public static final String ACCESS_TOKEN_KEY_SUFFIX_IN_PROPERTY_FILE = "cas.access.token";

    //cas/oauth2.0/profile API Constants


    public static final String CAS_PROFILE_RESOURCE_PATH = "/oauth2.0/profile";
    public static final String CAS_PROFILE_QUERY_PARAM_ACCESS_TOKEN_NAME = "access_token";


    public static final String CAS_PROFILE_VALID_RESPONSE_KEY = "id";
    public static final String CAS_PROFILE_INVALID_RESPONSE_KEY = "error";

    //Health monitor
    public static final String DATA_STATION_SENSOR_ONLINE_OFFLINE_KEY = "status";
    public static final String DATA_STATION_SENSOR_ONLINE_OFFLINE_VALUE = "online,offline";

    public static final String DATA_STATION_ID_KEY = "stationId";
    public static final String DATA_STATION_SENSOR_LIVE_STATUS_ID_KEY = "liveStatusId";
    public static final String DATA_STATION_SENSOR_COUNT = "no_of_offline_count";
    public static final String DATA_LAST_MODIFIED = "lastmod";

    public static final String DATA_ZONE_KEY = "zone";
    public static final String DATA_STATION_NAME_KEY = "name";
    public static final String DATA_SOURCE_ID_KEY = "source_id";
    public static final String DATA_TYPE_KEY = "type";
    public static final String DATA_PROJECT_KEY = "project";

    public static final String DATA_ASSET_TYPE_KEY = "asset_type";

    //Keys for Data status API public static final String DATA_ZONE_KEY="zone";

    public static final String DATA_STATUS_REMARK_KEY = "data_remark";
    public static final String DATA_STATUS_STREAM_KEY = "data_stream";
    public static final String DATA_STATUS_SENSOR_TYPE_KEY = "db_ingestion";
    public static final String DATA_STATUS_LAST_MODIFIED_KEY = "last_mod";
    public static final String DATA_STATUS_MODE_KEY = "mode";
    public static final String DATA_STATUS_ONLINE_PERCENTAGE_KEY = "online_percentage";
    public static final String DATA_STATUS_ONLINE_STATIONS_COUNT_KEY = "online_stations";
    public static final String DATA_STATUS_SOURCE_KEY = "source";
    public static final String DATA_STATUS_SOURCE_REMARK_KEY = "source_remark";
    public static final String DATA_STATUS_KEY = "status";
    public static final String DATA_STATUS_TOTAL_STATIONS_COUNT_KEY = "total_stations";

    public static final String HM_MAINTENANCE_ACTIVITY_COLUMNS_LIST = "\"activitiesAction\", \"activitiesZone\", \"activitiesReported\", \"stationName\", \"id\", \"activitiesStationStatus\", \"activitiesTeamName\", \"lastBateryVolt\", \"sub_vendor_name\", \"activitiesDateOfOps\", \"activitiesIssueType\", \"activitiesStatus\", \"activitiesIssueDetails\", \"initialAssignDate\", \"comment\"";

    // GIS /get/devices/config API Constants

    // Keys in each Map in $.data List

    public static final String GIS_CONFIG_DATA_ACL_KEY = "acl";
    public static final String GIS_CONFIG_DATA_DEVICE_ID_KEY = "deviceId";
    public static final String GIS_CONFIG_DATA_ENABLED_KEY = "enabled";
    public static final String GIS_CONFIG_DATA_FILTERS_KEY = "filters";
    public static final String GIS_CONFIG_DATA_ICON_KEY = "icon";
    public static final String GIS_CONFIG_DATA_KEY_KEY = "key";
    public static final String GIS_CONFIG_DATA_NAME_KEY = "name";
    public static final String GIS_CONFIG_DATA_TYPE_KEY = "type";

    public static final String GIS_CONFIG_PARAM_SPECIAL_KEY = "special";
    public static final String GIS_CONFIG_PARAM_SEARCH_KEY = "search";

    // Keys in each Map in $.data.acl

    public static final String GIS_CONFIG_ACL_HEATMAP_KEY = "heatmap";
    public static final String GIS_CONFIG_ACL_MARKER_KEY = "marker";

    // Keys in each Map in $.data.acl.heatmap

    public static final String GIS_CONFIG_ACL_HEATMAP_OPERATION_KEY = "operation";
    public static final String GIS_CONFIG_ACL_HEATMAP_TYPE_KEY = "type";

    // Keys in each Map in $.data.acl.marker

    public static final String GIS_CONFIG_ACL_MARKER_OPERATION_KEY = "operation";
    public static final String GIS_CONFIG_ACL_MARKER_TYPE_KEY = "type";

    //zoneboundaries get/kml?zoneId={zoneId}
    //QueryParam Name

    public static final String GIS_KML_QUERY_PARAM_NAME = "zoneId";

    //Keys for KML API

    public static final String GIS_KML_DATA_ID_KEY = "_id";
    public static final String GIS_KML_DATA_ZONEID_KEY = "zoneId";
    public static final String GIS_KML_DATA_CUSTOMER_KEY = "customer";
    public static final String GIS_KML_DATA_ZONENAME_KEY = "zoneName";
    public static final String GIS_KML_DATA_FILENAME_KEY = "fileName";
    public static final String GIS_KML_DATA_GEOJSON_KEY = "geoJson";

    //Keys for Contextual Data API's
    public static final String CONTEXTUAL_DATA_COLUMN_KEY = "columns";
    public static final String CONTEXTUAL_DATA_RECORD_KEY = "records";

    // Widget stats key

    public static final String WIDGET_STATISTICS_LIST_KEY = "widgetStatisticsList";
    public static final String WIDGET_DATA_OFFLINE_KEY = "offline";
    public static final String WIDGET_DATA_PERCENT_KEY = "percent";
    public static final String WIDGET_DATA_TOTAL_KEY = "total";
    public static final String WIDGET_DATA_TYPE_KEY = "type";
    public static final String WIDGET_DATA_VARIATION_RATE_KEY = "variation_rate";

    // Issue type data values.
    public static final String DATA_TYPE_VALUE = "offline,sensor,data,rtu,battery,intrusion,diagnostics";

    //Response Data map keys for pipes zoom api
    public static final String GIS_CUSTOMER_ID_KEY = "customerId";
    public static final String GIS_PREF_NAME_KEY = "prefName";
    public static final String GIS_REGION_NAME_KEY = "regionName";
    public static final String GIS_SERVICE_NAME_KEY = "serviceName";
    public static final String GIS_ZOOM_KEY = "zoom";
    public static final String GIS_ZOOM_DIAMETER_KEY = "diameter";
    public static final String GIS_ZOOM_RANGE_KEY = "range";


    //Response Data map keys for DMA load all themes api
    public static final String DMA_THEMES_ALL_DATA_NAME_KEY = "name";
    public static final String DMA_THEMES_ALL_DATA_CATEGORY_KEY = "category";
    public static final String DMA_THEMES_ALL_DATA_COMPONENTS_KEY = "components";
    public static final String DMA_THEMES_ALL_DATA_CUSTOMER_ID_KEY = "customerId";
    public static final String DMA_THEMES_ALL_DATA_ID_KEY = "id";
    public static final String DMA_THEMES_ALL_DATA_KEY_KEY = "key";

    //Envelope API keys
    public static final String  DATA_REQUEST_ID="requestId";
    public static final String  SERVICE_TICKET_OR_TOKEN= "serviceTicketOrToken";
    public static final String MAX_PRESSURE="max";
    public static final String MIN_PRESSURE="min";
    public static final String PIPECENTER="pipeCenter";
    public static final String STATIONIDS="stationIds";
    public static final String STATION_POSITION="stationPos";

}
