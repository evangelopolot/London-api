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
    return Collections.emptyList();
  }
}
