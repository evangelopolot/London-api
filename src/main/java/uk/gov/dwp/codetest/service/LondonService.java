package uk.gov.dwp.codetest.service;

import java.util.ArrayList;
import java.util.List;
import uk.gov.dwp.codetest.domain.User;

public class LondonService {

  public static final int FIFTY_MILES_RADIUS = 72;
  public static final double LONDON_LATITUDE = 51.509865;
  public static final double LONDON_LONGITUDE = -0.118092;
  private final APIServiceInterface apiService;

  public LondonService(APIServiceInterface apiService){
    this.apiService = apiService;
  }

  public List<User> getUsers() {
    List<User> users = new ArrayList<>();
    users.addAll(getUsersInLondon());
    users.addAll(getUsersWhoLive50MilesFromLondon());
    return users;
  }


  public List<User> getUsersInLondon() {
    List<User> users = apiService.getUsers();
    List<User> usersInLondon = new ArrayList<>();

    for (User user : users) {
      double distance = haversineDistance(user.getLatitude(), user.getLongitude());
      if(distance <= 12){
        usersInLondon.add(user);
      }
    }
    return usersInLondon;
  }

  public List<User> getUsersWhoLive50MilesFromLondon() {
    List<User> users = apiService.getUsers();
    List<User> usersNearLondon = new ArrayList<>();

    for(User user : users) {
      double distance = haversineDistance(user.getLatitude(), user.getLongitude());
      if(distance <= FIFTY_MILES_RADIUS  && distance >= 12){
        usersNearLondon.add(user);
      }
    }
    return usersNearLondon;
  }

  private double haversineDistance(double lat1, double lon1) {
    final double EARTH_RADIUS = 3958.8;

    double latDistance = Math.toRadians(LONDON_LATITUDE - lat1);
    double lonDistance = Math.toRadians(LONDON_LONGITUDE - lon1);

    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(LONDON_LATITUDE))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return EARTH_RADIUS * c;

  }

}
