package uk.gov.dwp.codetest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import uk.gov.dwp.codetest.domain.User;

public class LondonService {

  private APIServiceInterface apiService;

  public LondonService(APIServiceInterface apiService){
    this.apiService = apiService;
  }

  public List<User> getUsers() {
    List<User> users = new ArrayList<>();
    // TODO : get all users
    return users;
  }

  public List<User> getUsersInLondon() {
    // TODO : get all users in London
    List<User> users = apiService.getUsers();
    List<User> usersInLondon = new ArrayList<>();

    for (User user : users) {
      double distance = haversineDistance(user.getLatitude(), user.getLongitude());
      if(distance <= 12){
        usersInLondon.add(user);

        System.out.println("User " + user.getFirst_name() + " lives in London");
      }
    }
    return usersInLondon;
  }

  private double haversineDistance(double lat1, double lon1) {
    final double EARTH_RADIUS = 3958.8;

    double latDistance = Math.toRadians(51.509865 - lat1);
    double lonDistance = Math.toRadians(-0.118092 - lon1);

    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(51.509865))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return EARTH_RADIUS * c;

  }
}
