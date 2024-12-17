package com.inpocket.vitaverse.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private String name;
    private float latitude;
    private float longitude;
    public static Location parseFromString(String data) // eg: "Latitude: 40.7128, Longitude: -74.006"
    {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null");
        }

        // Initialize latitude and longitude variables
        float latitude = 0.0f;
        float longitude = 0.0f;

        // Define regular expression pattern to match latitude and longitude
        String pattern = "Latitude: ([-+]?[0-9]*\\.?[0-9]+), Longitude: ([-+]?[0-9]*\\.?[0-9]+)";

        // Match the pattern against the input string
        java.util.regex.Pattern regex = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = regex.matcher(data);

        // If a match is found, extract latitude and longitude
        if (matcher.find()) {
            latitude = Float.parseFloat(matcher.group(1));
            longitude = Float.parseFloat(matcher.group(2));
        } else {
            throw new IllegalArgumentException("Invalid string format for parsing location.");
        }

        // Create and return a new Location object
        return new Location(null, latitude, longitude);
    }

    // Calculate distance between two locations in kilometers
    public static float calcDistance(Location location1, Location location2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(location2.latitude - location1.latitude);
        double lonDistance = Math.toRadians(location2.longitude - location1.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(location1.latitude)) * Math.cos(Math.toRadians(location2.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float distance = (float) (R * c); // convert to km
        return distance;
    }
}
