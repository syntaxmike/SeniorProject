package no.bouvet.p2pcommunication.locationSocket;
/**
 * Created by sabamahbub on 11/3/17.
 */

public class Direction {
    double getDistance(double firstLong, double firstLat, double secondLong, double secondLat){
        double Radius =  6372.8;
        double lat1 = Math.toRadians(firstLat);
        double lat2 = Math.toRadians(secondLat);
        double distanceLat = Math.toRadians(lat2 - lat1);
        double distanceLong = Math.toRadians(secondLong - firstLong);

        double a = Math.pow(Math.sin(distanceLat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.pow(Math.sin(distanceLong/2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return Radius * c;
    }
}
