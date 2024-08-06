package com.github.foodie.exceptions;

import java.util.HashMap;
import java.util.Map;

public interface Errors {

    String USER_NOT_FOUND = "FD-USR-001";

    String CUSTOMER_NOT_FOUND = "FD-CM-001";

    String RESTAURANT_NOT_FOUND = "FD-RS-001";

    String MENU_ITEM_NOT_FOUND = "FD-MN-001";

    String ORDER_REQUEST_NOT_FOUND = "FD-ORD-001";

    String OSRM_ROUTE_DISTANCE_ERROR = "FD-OSRM-001",
            OSRM_NO_ROUTE_FOUND = "FD-OSRM-002";


    Map<String, String> errorMap = new HashMap<String, String>() {{
        put(USER_NOT_FOUND, "User not found");
        put(CUSTOMER_NOT_FOUND, "Customer not found");
        put(RESTAURANT_NOT_FOUND, "Restaurant not found");
        put(MENU_ITEM_NOT_FOUND, "Menu item not found");
        put(ORDER_REQUEST_NOT_FOUND, "Order request not found");
        put(OSRM_ROUTE_DISTANCE_ERROR, "Error while getting route distance from OSRM");
        put(OSRM_NO_ROUTE_FOUND, "OSRM couldn't find any route");
    }};
}
