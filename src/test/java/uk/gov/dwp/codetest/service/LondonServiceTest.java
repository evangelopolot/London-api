package uk.gov.dwp.codetest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dwp.codetest.domain.User;

class LondonServiceTest {
  @Mock
  private APIServiceInterface apiServiceMock;
  private LondonService londonService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    londonService = new LondonService(apiServiceMock);
  }

  @Test
  void getUsers() {
    final List<User> users = londonService.getUsers();
    assertNotNull(users);
  }
  @Test
  @DisplayName("Given an empty list, return an empty list")
  public void givenAnEmptyListReturnAnEmptyString() {
    when(apiServiceMock.getUsers()).thenReturn(new ArrayList<>());
    List<User> usersNearLondon = londonService.getUsers();
    assertEquals(0, usersNearLondon.size(), "Size should equal 1");
  }

  @Test
  @DisplayName("Given a user who lives within 12 miles of London, return that user as living in London")
  public void givenAUserWhoLives12MilesFromLondonCoordinatesReturnThatUserAsLivingInLondon() {
    List<User> user = new ArrayList<>();
    user.add( new User(11, "Jack", "Boo", "jack.boo@mail.com", "192.57.252.111", 51.5175f, -0.1209f));
    when(apiServiceMock.getUsers()).thenReturn(user);
    List<User> usersInLondon = londonService.getUsersInLondon();
    assertEquals(1, usersInLondon.size(), "Size should equal 1");
  }

  @Test
  void getUsersInLondon() {
    final List<User> users = londonService.getUsersInLondon();
    assertNotNull(users);
  }
}