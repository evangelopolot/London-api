package uk.gov.dwp.codetest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
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
    List<User> usersNearLondon = londonService.getUsersWhoLive50MilesFromLondon();
    assertEquals(1, usersNearLondon.size(), "Size should equal 1");
  }

  @Test
  @DisplayName("Given a user who live who lives approximately 72 miles from London coordinates, return that user as living 50 from London")
  public void givenAUserWhoLivesExactly50MilesFromLondonReturnUserAsLiving50MilesFromLondon(){
    List<User> user = new ArrayList<>();
    user.add( new User(12, "Jane", "Smith", "janesmith@example.com", "987.654.321.0", 51.305f, -0.075f));
    when(apiServiceMock.getUsers()).thenReturn(user);
    List<User> usersNearLondon = londonService.getUsersWhoLive50MilesFromLondon();
    assertEquals(1, usersNearLondon.size(), "Size should equal 1");
  }

  @Test
  @DisplayName("Given a list of users, return those who live 50 miles from or in london")
  public void givenAListOfUsersReturnThoseWhoLiveInLondonOrNearLondon() {
    List<User> user = new ArrayList<>(listOfUsers());
    User userLivesNearLondon = new User(12, "Jane", "Smith", "janesmith@example.com", "987.654.321.0", 51.305f, -0.075f);
    User userLivesInLondon = new User(11, "Jack", "Boo", "jack.boo@mail.com", "192.57.252.111", 51.5175f, -0.1209f);
    user.add(userLivesNearLondon);
    user.add(userLivesInLondon);
    when(apiServiceMock.getUsers()).thenReturn(user);
    List<User> usersNearLondon = londonService.getUsers();
    assertEquals(2, usersNearLondon.size(), "Size should equal 1");
  }

  @Test
  void getUsersInLondon() {
    final List<User> users = londonService.getUsersInLondon();
    assertNotNull(users);
  }

  private List<User> listOfUsers(){
    return new ArrayList<>(Arrays.asList(
            new User(1, "Maurise", "Shieldon", "mshieldon0@squidoo.com", "192.57.232.111", 34.003135f, -117.7228641f),
            new User(2, "Bendix", "Halgarth", "bhalgarth1@timesonline.co.uk", "4.185.73.82", -2.9623869f, 104.7399789f),
            new User(3, "Meghan", "Southall", "msouthall2@ihg.com", "21.243.184.215", 15.45033f, 44.12768f),
            new User(4, "Sidnee", "Silwood", "ssilwood3@gizmodo.com", "77.55.231.220", -26.94087f, 29.24905f),
            new User(5, "Rosita", "Ferrulli", "rferrulli4@unesco.org", "182.189.27.66", 33.5719791f, -84.3396421f),
            new User(6, "Ferdinand", "Dun", "fdun5@google.es", "219.245.140.98", 47.3879065f, 19.1150392f),
            new User(7, "Keven", "Paling", "kpaling6@alexa.com", "144.96.77.21", 9.9578348f, 124.1789637f),
            new User(8, "Carri", "Server", "cserver7@google.nl", "222.203.3.67", -6.1130137f, 106.1610343f),
            new User(9, "Keelia", "Gillian", "kgillian8@independent.co.uk", "153.44.232.220", 5.9630513f, 10.1591213f),
            new User(10, "Brennan", "Matej", "bmatej9@umich.edu", "252.214.166.100", 41.1086264f, -7.6901721f)
    ));
  }
}