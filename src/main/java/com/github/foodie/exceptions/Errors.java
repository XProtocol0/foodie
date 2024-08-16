package com.github.foodie.exceptions;

import java.util.HashMap;
import java.util.Map;

public interface Errors {
    String INTERNAL_SERVER_ERROR = "FD-IN-001";

    String USER_NOT_FOUND = "FD-USR-001";

    String CUSTOMER_NOT_FOUND = "FD-CM-001";

    String RESTAURANT_NOT_FOUND = "FD-RS-001";

    String SHIPPER_NOT_FOUND = "FD-SH-001",
            NO_SHIPPER_AVAILABLE = "FD-SH-002",
            SHIPPER_NOT_AVAILABLE = "FD-SH-003",
            SHIPPER_NOT_ASSIGNED_TO_ORDER = "FD-SH-004";

    String MENU_ITEM_NOT_FOUND = "FD-MN-001";

    String ORDER_REQUEST_NOT_FOUND = "FD-ORD-001",
            ORDER_REQUEST_NOT_PENDING = "FD-ORD-002";

    String ORDER_NOT_FOUND = "FD-OR-001",
            ORDER_NOT_ACCEPTED = "FD-OR-002",
            ORDER_NOT_PICKED_UP = "FD-OR-003",
            NO_ORDER_FOUND = "FD-OR-004";

    String OSRM_ROUTE_DISTANCE_ERROR = "FD-OSRM-001",
            OSRM_NO_ROUTE_FOUND = "FD-OSRM-002";

    String WALLET_NOT_FOUND = "FD-WA-001";

    String PAYMENT_NOT_FOUND = "FD-PY-001";


    Map<String, String> errorMap = new HashMap<String, String>() {{
        put(USER_NOT_FOUND, "User not found");
        put(CUSTOMER_NOT_FOUND, "Customer not found");
        put(RESTAURANT_NOT_FOUND, "Restaurant not found");
        put(SHIPPER_NOT_FOUND, "Shipper not found");
        put(NO_SHIPPER_AVAILABLE, "No shipper is available. Please try again after sometime.");
        put(MENU_ITEM_NOT_FOUND, "Menu item not found");
        put(ORDER_REQUEST_NOT_FOUND, "Order request not found");
        put(OSRM_ROUTE_DISTANCE_ERROR, "Error while getting route distance from OSRM");
        put(OSRM_NO_ROUTE_FOUND, "OSRM couldn't find any route");
        put(ORDER_REQUEST_NOT_PENDING, "Order is not in pending state");
        put(ORDER_NOT_FOUND, "Order not found");
        put(WALLET_NOT_FOUND, "Wallet not found");
        put(SHIPPER_NOT_ASSIGNED_TO_ORDER, "Shipper is not assigned to given order");
        put(PAYMENT_NOT_FOUND, "Payment not found");
        put(ORDER_NOT_PICKED_UP, "Order is not picked up");
        put(NO_ORDER_FOUND, "No order found");
    }};
}
