package no.bouvet.p2pcommunication.locationSocket;


/**
 * Created by sabamahbub on 10/20/17.
 */

public class Locations {

  String deviceAddress;
  double[] locations = new double[6];
  double angle = 0;
  String heading = "";
  double distance = 0;

  public Locations(String device, double latitude, double longitude) {
    this.deviceAddress = device;
    this.locations[0] = latitude;
    this.locations[1] = longitude;
  }

  public Locations(String device) {
    this.deviceAddress = device;
  }

  public void update(String deviceAddress, double latitude, double longitude) {
    if (this.deviceAddress.equals(deviceAddress)) {
      this.locations[5] = this.locations[3];
      this.locations[4] = this.locations[2];
      this.locations[3] = this.locations[1];
      this.locations[2] = this.locations[0];
      this.locations[1] = longitude;
      this.locations[0] = latitude;
      updateAngle();
    }
  }

  public void update(String deviceAddress, double[] location) {
    if (this.deviceAddress.equals(deviceAddress) && location.length == 2) {
      this.locations[5] = this.locations[3];
      this.locations[4] = this.locations[2];
      this.locations[3] = this.locations[1];
      this.locations[2] = this.locations[0];
      this.locations[1] = location[1];
      this.locations[0] = location[0];
    }
  }

  public void updateAngle() {
    if (locations[2] != 0) {
      angle = Direction
          .getBearings(this.locations[2], this.locations[3], this.locations[0], this.locations[1]);
      heading = Direction.getBearingsString(angle);
    }
  }

  public double getCurrentLongitude() {
    return locations[1];
  }

  public double getCurrentLatitude() {
    return locations[0];
  }

  public double getPreviousLongitude() {
    return locations[3];
  }

  public double getPreviousLatitude() {
    return locations[2];
  }

  public double getOldestLongitude() {
    return locations[5];
  }

  public double getOldestLatitude() {
    return locations[4];
  }

  public String getCurrent() {
    return "CurrentLongitude: " + getCurrentLongitude() + " CurrentLatitude: "
        + getCurrentLatitude();
  }

  public double[] getCurrentArray() {
    double[] mLocation = new double[2];

    mLocation[0] = getCurrentLatitude();
    mLocation[1] = getCurrentLongitude();

    return mLocation;
  }

  public void setDistance(double oLat, double oLong, double uLat, double uLong) {
    this.distance = Direction.getDistance(oLat, oLong, uLat, uLong);

  }

  public String getPrevious() {
    return " PreviousLatitude: " + getPreviousLatitude() + "PreviousLongitude: "
        + getPreviousLongitude();
  }

  public String getOldest() {
    return " OldestLatitude: " + getOldestLatitude() + "OldestLongitude: " + getOldestLongitude();
  }

  public String getHeading() {
    return heading;
  }

  public double getAngle() {
    return angle;
  }

  public String getLocations() {
    return getOldest() + getPrevious() + getCurrent();
  }
}
