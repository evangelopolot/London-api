package uk.gov.dwp.codetest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
  @DisplayName("Given a user who lives within between 72 miles and 12 miles from London coordinates, return that user as Living 50 miles from london")
  public void givenAUserCoordinatesOf72MilesFromLondonReturnTheUserAsLiving50MilesFromLondon(){
    List<User> user = new ArrayList<>();
    user.add(new User(11, "John", "Doe", "johndoe@example.com", "123.456.789.0", 51.307f, -0.090f));
    when(apiServiceMock.getUsers()).thenReturn(user);
    List<User> usersNearLondon = londonService.getUsersWhoLiveNearLondon();
    assertEquals(1, usersNearLondon.size(), "Size should equal 1");
  }

  @Test
  @DisplayName("Given a user who live who lives approximately 72 miles from London coordinates, return that user as living 50 from London")
  public void givenAUserWhoLivesExactly50MilesFromLondon(){
    List<User> user = new ArrayList<>();
    user.add( new User(12, "Jane", "Smith", "janesmith@example.com", "987.654.321.0", 51.305f, -0.075f));
    when(apiServiceMock.getUsers()).thenReturn(user);
    List<User> usersNearLondon = londonService.getUsersWhoLiveNearLondon();
    assertEquals(1, usersNearLondon.size(), "Size should equal 1");
  }

  @Test
  void getUsersInLondon() {
    final List<User> users = londonService.getUsersInLondon();
    assertNotNull(users);
  }
}