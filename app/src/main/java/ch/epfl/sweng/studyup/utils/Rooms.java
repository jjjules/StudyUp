package ch.epfl.sweng.studyup.utils;

import android.app.backup.SharedPreferencesBackupHelper;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.studyup.map.Room;

public class Rooms {
    private final static double RADIUS_ROOM = 40.0;
    public final static List<String> ROOMS_AVAILABLE;

    static {
        List<String> rooms = new ArrayList<String>() {
            {
                add("CE_1_1");
                add("CE_1_2");
                add("CE_1_3");
                add("CE_1_4");
                add("CE_1_5");
                add("CE_1_6");

                add("CM_1_1");
                add("CM_1_2");
                add("CM_1_3");
                add("CM_1_4");
                add("CM_1_5");

                add("CO_1_1");
                add("CO_1_2");
                add("CO_1_3");
                add("CO_1_4");
                add("CO_1_5");
                add("CO_1_6");

                add("INN_3_26");

                add("INR_0_11");
            }
        };
        ROOMS_AVAILABLE = Collections.unmodifiableList(rooms);
    }

    public final static Map<String, Room> ROOMS_LOCATIONS;

    static {
        HashMap<String, Room> locations = new HashMap<String, Room>() {
            {
                put("CE_1_1", new Room(46.5206650, 6.5693740));
                put("CE_1_2", new Room(46.5204350, 6.5693790));
                put("CE_1_3", new Room(46.5206680, 6.5697490));
                put("CE_1_4", new Room(46.5204010, 6.5697540));
                put("CE_1_5", new Room(46.5203640, 6.5705050));
                put("CE_1_6", new Room(46.5204850, 6.5707300));

                put("CM_1_1", new Room(46.5206550, 6.5674980));
                put("CM_1_2", new Room(46.5203840, 6.5675020));
                put("CM_1_3", new Room(46.5203810, 6.5671270));
                put("CM_1_4", new Room(46.5204150, 6.5665410));
                put("CM_1_5", new Room(46.5204140, 6.5664010));

                put("CO_1_1", new Room(46.5199270, 6.5653150));
                put("CO_1_2", new Room(46.5199070, 6.5644510));
                put("CO_1_3", new Room(46.5199070, 6.5644510));
                put("CO_1_4", new Room(46.5201620, 6.5648470));
                put("CO_1_5", new Room(46.5201610, 6.5646430));
                put("CO_1_6", new Room(46.5201480, 6.5643660));

                put("INN_3_26", new Room(46.5187270, 6.5625000));

                put("INR_0_11", new Room(46.5184010, 6.5627920));
            }
        };
        ROOMS_LOCATIONS = Collections.unmodifiableMap(locations);
    }

    //https://stackoverflow.com/questions/8832071/how-can-i-get-the-distance-between-two-point-by-latlng
    public static double distanceBetweenTwoLatLng(LatLng latLng1, LatLng latLng2){
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(latLng2.latitude - latLng1.latitude);
        double lngDiff = Math.toRadians(latLng2.longitude - latLng1.longitude);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(latLng1.latitude)) * Math.cos(Math.toRadians(latLng2.latitude)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Double(distance * meterConversion).doubleValue();
    }

    public static boolean checkIfUserIsInRoom(String room) {
        if(Utils.position == null || !ROOMS_LOCATIONS.containsKey(room)){
            return false;
        }
        return distanceBetweenTwoLatLng(ROOMS_LOCATIONS.get(room).getLocation(), Utils.position) <= RADIUS_ROOM;
    }
}
