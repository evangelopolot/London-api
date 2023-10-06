package uk.gov.dwp.codetest.service;

import static org.junit.jupiter.api.Assertions.*;
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
  private void setupApiServiceWithResponse(String responseBody) {
    when(apiServiceMock.getUsers()).thenReturn(responseBody);
  }

  @Test
  void getUsers() {
    when(apiServiceMock.getUsers()).thenReturn(responseBody());
    final List<User> users = londonService.getUsers();
    assertNotNull(users);
  }
  @Test
  @DisplayName("Test given an empty list, return an RuntimeException")
  public void givenAnEmptyListReturnAnEmptyString() {
    when(apiServiceMock.getUsers()).thenReturn("");
    RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> {
              londonService.getUsers();
            }
    );
    assertEquals("Error fetching or parsing user data", exception.getMessage());
  }

  @Test
  @DisplayName("Test user within 12 miles from London is considered in London")
  public void testUserWhoLives12MilesFromLondonCoordinatesReturnThatUserAsLivingInLondon() {
    List<User> user = new ArrayList<>();
    user.add( new User(11, "Jack", "Boo", "jack.boo@mail.com", "192.57.252.111", 51.5175f, -0.1209f));

    setupApiServiceWithResponse(responseBodyUserInLondon());
    List<User> usersInLondon = londonService.getUsersInLondon();

    assertTrue(usersInLondon.contains(user.get(0)));
    assertEquals(1, usersInLondon.size(), "Size should equal 1");
  }

  @Test
  @DisplayName("Test user between 72 miles and 12 miles from London coordinates is considered living 50 miles from London")
  public void testUserBetween72And12MilesFromLondonReturnUserAsLiving50MilesFromLondon(){
    List<User> user = new ArrayList<>();
    user.add(new User(11, "John", "Doe", "johndoe@example.com", "123.456.789.0", 51.307f, -0.090f));

    setupApiServiceWithResponse(responseBodyUserBetween72MilesAnd12Miles());
    List<User> usersNearLondon = londonService.getUsersWhoLive50MilesFromLondon();
    assertTrue(usersNearLondon.contains(user.get(0)));
    assertEquals(1, usersNearLondon.size(), "Size should equal 1");
  }

  @Test
  @DisplayName("Test user who lives approximately 72 miles from London coordinates, return user as living within 50 miles from London")
  public void testUserWhoLivesExactly50MilesFromLondonReturnUserAsLiving50MilesFromLondon(){
    List<User> user = new ArrayList<>();
    user.add( new User(11, "Jane", "Smith", "janesmith@example.com", "987.654.321.0", 51.305f, -0.075f));

    setupApiServiceWithResponse(responseBodyUser72MilesFromLondon());
    List<User> usersNearLondon = londonService.getUsersWhoLive50MilesFromLondon();

    assertTrue(usersNearLondon.contains(user.get(0)));
    assertEquals(1, usersNearLondon.size(), "Size should equal 1");
  }

  @Test
  @DisplayName("test when given a list of users, return those who live 50 miles from or in london")
  public void testGivenAListOfUsersReturnUsersInLondonOrNearLondon() {
    List<User> users = new ArrayList<>();
    User userLivesNearLondon = new User(11, "Jane", "Smith", "janesmith@example.com", "987.654.321.0", 51.305f, -0.075f);
    User userLivesInLondon = new User(12, "John", "Doe","johndoe@example.com", "123.456.789.0", 51.307f, -0.09f);
    users.add(userLivesNearLondon);
    users.add(userLivesInLondon);
    setupApiServiceWithResponse(responseBody50miles());

    List<User> usersInOrNearLondon = londonService.usersInOrNearLondon();

    assertTrue(usersInOrNearLondon.contains(users.get(0)));
    assertTrue(usersInOrNearLondon.contains(users.get(1)));
    assertEquals(2, usersInOrNearLondon.size(), "Size should equal 1");
  }
  
  @Test
  @DisplayName("Given a user who lives exactly at London Coordinates, return the user as living in London")
  public void givenAUserWhoLivesExactlyAtTheLondonCoordinatesReturnUserAsLivingInLondon(){
    List<User> users = new ArrayList<>();
    User user = new User(11, "Jacob", "Reel", "jack.boo@mail.com", "192.57.252.111", 51.509865f, -0.118092f);
    users.add(user);
    setupApiServiceWithResponse(responseBodyUserExactlyAtLondonCoordinates());

    List<User> usersInLondon = londonService.getUsersWhoLive50MilesFromLondon();

    assertTrue(usersInLondon.contains(users.get(0)));
    assertEquals(1, usersInLondon.size(),"Should return users in London as 1");
  }

  private String responseBody() {
    return "[" +
            "{\"id\": 1, \"first_name\": \"Maurise\", \"last_name\": \"Shieldon\", \"email\": \"mshieldon0@squidoo.com\", \"ip_address\": \"192.57.232.111\", \"latitude\": 34.003135, \"longitude\": -117.7228641}," +
            "{\"id\": 2, \"first_name\": \"Bendix\", \"last_name\": \"Halgarth\", \"email\": \"bhalgarth1@timesonline.co.uk\", \"ip_address\": \"4.185.73.82\", \"latitude\": -2.9623869, \"longitude\": 104.7399789}," +
            "{\"id\": 3, \"first_name\": \"Meghan\", \"last_name\": \"Southall\", \"email\": \"msouthall2@ihg.com\", \"ip_address\": \"21.243.184.215\", \"latitude\": 15.45033, \"longitude\": 44.12768}," +
            "{\"id\": 4, \"first_name\": \"Sidnee\", \"last_name\": \"Silwood\", \"email\": \"ssilwood3@gizmodo.com\", \"ip_address\": \"77.55.231.220\", \"latitude\": -26.94087, \"longitude\": 29.24905}," +
            "{\"id\": 5, \"first_name\": \"Rosita\", \"last_name\": \"Ferrulli\", \"email\": \"rferrulli4@unesco.org\", \"ip_address\": \"182.189.27.66\", \"latitude\": 33.5719791, \"longitude\": -84.3396421}," +
            "{\"id\": 6, \"first_name\": \"Ferdinand\", \"last_name\": \"Dun\", \"email\": \"fdun5@google.es\", \"ip_address\": \"219.245.140.98\", \"latitude\": 47.3879065, \"longitude\": 19.1150392}," +
            "{\"id\": 7, \"first_name\": \"Keven\", \"last_name\": \"Paling\", \"email\": \"kpaling6@alexa.com\", \"ip_address\": \"144.96.77.21\", \"latitude\": 9.9578348, \"longitude\": 124.1789637}," +
            "{\"id\": 8, \"first_name\": \"Carri\", \"last_name\": \"Server\", \"email\": \"cserver7@google.nl\", \"ip_address\": \"222.203.3.67\", \"latitude\": -6.1130137, \"longitude\": 106.1610343}," +
            "{\"id\": 9, \"first_name\": \"Keelia\", \"last_name\": \"Gillian\", \"email\": \"kgillian8@independent.co.uk\", \"ip_address\": \"153.44.232.220\", \"latitude\": 5.9630513, \"longitude\": 10.1591213}," +
            "{\"id\": 10, \"first_name\": \"Brennan\", \"last_name\": \"Matej\", \"email\": \"bmatej9@umich.edu\", \"ip_address\": \"252.214.166.100\", \"latitude\": 41.1086264, \"longitude\": -7.6901721}," +
            "{\"id\": 11, \"first_name\": \"Jack\", \"last_name\": \"Boo\", \"email\": \"jack.boo@mail.com\", \"ip_address\": \"192.57.252.111\", \"latitude\": 51.5175, \"longitude\": -0.1209}" +
            "]";
  }
  private String responseBodyUser72MilesFromLondon() {
    return "[" +
            "{\"id\": 1, \"first_name\": \"Maurise\", \"last_name\": \"Shieldon\", \"email\": \"mshieldon0@squidoo.com\", \"ip_address\": \"192.57.232.111\", \"latitude\": 34.003135, \"longitude\": -117.7228641}," +
            "{\"id\": 2, \"first_name\": \"Bendix\", \"last_name\": \"Halgarth\", \"email\": \"bhalgarth1@timesonline.co.uk\", \"ip_address\": \"4.185.73.82\", \"latitude\": -2.9623869, \"longitude\": 104.7399789}," +
            "{\"id\": 3, \"first_name\": \"Meghan\", \"last_name\": \"Southall\", \"email\": \"msouthall2@ihg.com\", \"ip_address\": \"21.243.184.215\", \"latitude\": 15.45033, \"longitude\": 44.12768}," +
            "{\"id\": 4, \"first_name\": \"Sidnee\", \"last_name\": \"Silwood\", \"email\": \"ssilwood3@gizmodo.com\", \"ip_address\": \"77.55.231.220\", \"latitude\": -26.94087, \"longitude\": 29.24905}," +
            "{\"id\": 5, \"first_name\": \"Rosita\", \"last_name\": \"Ferrulli\", \"email\": \"rferrulli4@unesco.org\", \"ip_address\": \"182.189.27.66\", \"latitude\": 33.5719791, \"longitude\": -84.3396421}," +
            "{\"id\": 6, \"first_name\": \"Ferdinand\", \"last_name\": \"Dun\", \"email\": \"fdun5@google.es\", \"ip_address\": \"219.245.140.98\", \"latitude\": 47.3879065, \"longitude\": 19.1150392}," +
            "{\"id\": 7, \"first_name\": \"Keven\", \"last_name\": \"Paling\", \"email\": \"kpaling6@alexa.com\", \"ip_address\": \"144.96.77.21\", \"latitude\": 9.9578348, \"longitude\": 124.1789637}," +
            "{\"id\": 8, \"first_name\": \"Carri\", \"last_name\": \"Server\", \"email\": \"cserver7@google.nl\", \"ip_address\": \"222.203.3.67\", \"latitude\": -6.1130137, \"longitude\": 106.1610343}," +
            "{\"id\": 9, \"first_name\": \"Keelia\", \"last_name\": \"Gillian\", \"email\": \"kgillian8@independent.co.uk\", \"ip_address\": \"153.44.232.220\", \"latitude\": 5.9630513, \"longitude\": 10.1591213}," +
            "{\"id\": 10, \"first_name\": \"Brennan\", \"last_name\": \"Matej\", \"email\": \"bmatej9@umich.edu\", \"ip_address\": \"252.214.166.100\", \"latitude\": 41.1086264, \"longitude\": -7.6901721}," +
            "{\"id\": 11, \"first_name\": \"Jane\", \"last_name\": \"Smith\", \"email\": \"janesmith@example.com\", \"ip_address\": \"987.654.321.0\", \"latitude\": 51.305, \"longitude\": -0.075}" +
            "]";
  }
  private String responseBodyUserExactlyAtLondonCoordinates() {
    return "[" +
            "{\"id\": 1, \"first_name\": \"Maurise\", \"last_name\": \"Shieldon\", \"email\": \"mshieldon0@squidoo.com\", \"ip_address\": \"192.57.232.111\", \"latitude\": 34.003135, \"longitude\": -117.7228641}," +
            "{\"id\": 2, \"first_name\": \"Bendix\", \"last_name\": \"Halgarth\", \"email\": \"bhalgarth1@timesonline.co.uk\", \"ip_address\": \"4.185.73.82\", \"latitude\": -2.9623869, \"longitude\": 104.7399789}," +
            "{\"id\": 3, \"first_name\": \"Meghan\", \"last_name\": \"Southall\", \"email\": \"msouthall2@ihg.com\", \"ip_address\": \"21.243.184.215\", \"latitude\": 15.45033, \"longitude\": 44.12768}," +
            "{\"id\": 4, \"first_name\": \"Sidnee\", \"last_name\": \"Silwood\", \"email\": \"ssilwood3@gizmodo.com\", \"ip_address\": \"77.55.231.220\", \"latitude\": -26.94087, \"longitude\": 29.24905}," +
            "{\"id\": 5, \"first_name\": \"Rosita\", \"last_name\": \"Ferrulli\", \"email\": \"rferrulli4@unesco.org\", \"ip_address\": \"182.189.27.66\", \"latitude\": 33.5719791, \"longitude\": -84.3396421}," +
            "{\"id\": 6, \"first_name\": \"Ferdinand\", \"last_name\": \"Dun\", \"email\": \"fdun5@google.es\", \"ip_address\": \"219.245.140.98\", \"latitude\": 47.3879065, \"longitude\": 19.1150392}," +
            "{\"id\": 7, \"first_name\": \"Keven\", \"last_name\": \"Paling\", \"email\": \"kpaling6@alexa.com\", \"ip_address\": \"144.96.77.21\", \"latitude\": 9.9578348, \"longitude\": 124.1789637}," +
            "{\"id\": 8, \"first_name\": \"Carri\", \"last_name\": \"Server\", \"email\": \"cserver7@google.nl\", \"ip_address\": \"222.203.3.67\", \"latitude\": -6.1130137, \"longitude\": 106.1610343}," +
            "{\"id\": 9, \"first_name\": \"Keelia\", \"last_name\": \"Gillian\", \"email\": \"kgillian8@independent.co.uk\", \"ip_address\": \"153.44.232.220\", \"latitude\": 5.9630513, \"longitude\": 10.1591213}," +
            "{\"id\": 10, \"first_name\": \"Brennan\", \"last_name\": \"Matej\", \"email\": \"bmatej9@umich.edu\", \"ip_address\": \"252.214.166.100\", \"latitude\": 41.1086264, \"longitude\": -7.6901721}," +
            "{\"id\": 11, \"first_name\": \"Jacob\", \"last_name\": \"Reel\", \"email\": \"jack.boo@mail.com\", \"ip_address\": \"192.57.252.111\", \"latitude\": 51.509865, \"longitude\": -0.118092}" +
            "]";
  }

  private String responseBodyUserInLondon() {
      return "[" +
            "{\"id\": 1, \"first_name\": \"Maurise\", \"last_name\": \"Shieldon\", \"email\": \"mshieldon0@squidoo.com\", \"ip_address\": \"192.57.232.111\", \"latitude\": 34.003135, \"longitude\": -117.7228641}," +
            "{\"id\": 2, \"first_name\": \"Bendix\", \"last_name\": \"Halgarth\", \"email\": \"bhalgarth1@timesonline.co.uk\", \"ip_address\": \"4.185.73.82\", \"latitude\": -2.9623869, \"longitude\": 104.7399789}," +
            "{\"id\": 3, \"first_name\": \"Meghan\", \"last_name\": \"Southall\", \"email\": \"msouthall2@ihg.com\", \"ip_address\": \"21.243.184.215\", \"latitude\": 15.45033, \"longitude\": 44.12768}," +
            "{\"id\": 4, \"first_name\": \"Sidnee\", \"last_name\": \"Silwood\", \"email\": \"ssilwood3@gizmodo.com\", \"ip_address\": \"77.55.231.220\", \"latitude\": -26.94087, \"longitude\": 29.24905}," +
            "{\"id\": 5, \"first_name\": \"Rosita\", \"last_name\": \"Ferrulli\", \"email\": \"rferrulli4@unesco.org\", \"ip_address\": \"182.189.27.66\", \"latitude\": 33.5719791, \"longitude\": -84.3396421}," +
            "{\"id\": 6, \"first_name\": \"Ferdinand\", \"last_name\": \"Dun\", \"email\": \"fdun5@google.es\", \"ip_address\": \"219.245.140.98\", \"latitude\": 47.3879065, \"longitude\": 19.1150392}," +
            "{\"id\": 7, \"first_name\": \"Keven\", \"last_name\": \"Paling\", \"email\": \"kpaling6@alexa.com\", \"ip_address\": \"144.96.77.21\", \"latitude\": 9.9578348, \"longitude\": 124.1789637}," +
            "{\"id\": 8, \"first_name\": \"Carri\", \"last_name\": \"Server\", \"email\": \"cserver7@google.nl\", \"ip_address\": \"222.203.3.67\", \"latitude\": -6.1130137, \"longitude\": 106.1610343}," +
            "{\"id\": 9, \"first_name\": \"Keelia\", \"last_name\": \"Gillian\", \"email\": \"kgillian8@independent.co.uk\", \"ip_address\": \"153.44.232.220\", \"latitude\": 5.9630513, \"longitude\": 10.1591213}," +
            "{\"id\": 10, \"first_name\": \"Brennan\", \"last_name\": \"Matej\", \"email\": \"bmatej9@umich.edu\", \"ip_address\": \"252.214.166.100\", \"latitude\": 41.1086264, \"longitude\": -7.6901721}," + "{\"id\": 11, \"first_name\": \"Jack\", \"last_name\": \"Boo\", \"email\": \"jack.boo@mail.com\", \"ip_address\": \"192.57.252.111\", \"latitude\": 51.5175, \"longitude\": -0.1209}" +
            "]";
  }
  private String responseBodyUserBetween72MilesAnd12Miles() {
    return "[" +
            "{\"id\": 1, \"first_name\": \"Maurise\", \"last_name\": \"Shieldon\", \"email\": \"mshieldon0@squidoo.com\", \"ip_address\": \"192.57.232.111\", \"latitude\": 34.003135, \"longitude\": -117.7228641}," +
            "{\"id\": 2, \"first_name\": \"Bendix\", \"last_name\": \"Halgarth\", \"email\": \"bhalgarth1@timesonline.co.uk\", \"ip_address\": \"4.185.73.82\", \"latitude\": -2.9623869, \"longitude\": 104.7399789}," +
            "{\"id\": 3, \"first_name\": \"Meghan\", \"last_name\": \"Southall\", \"email\": \"msouthall2@ihg.com\", \"ip_address\": \"21.243.184.215\", \"latitude\": 15.45033, \"longitude\": 44.12768}," +
            "{\"id\": 4, \"first_name\": \"Sidnee\", \"last_name\": \"Silwood\", \"email\": \"ssilwood3@gizmodo.com\", \"ip_address\": \"77.55.231.220\", \"latitude\": -26.94087, \"longitude\": 29.24905}," +
            "{\"id\": 5, \"first_name\": \"Rosita\", \"last_name\": \"Ferrulli\", \"email\": \"rferrulli4@unesco.org\", \"ip_address\": \"182.189.27.66\", \"latitude\": 33.5719791, \"longitude\": -84.3396421}," +
            "{\"id\": 6, \"first_name\": \"Ferdinand\", \"last_name\": \"Dun\", \"email\": \"fdun5@google.es\", \"ip_address\": \"219.245.140.98\", \"latitude\": 47.3879065, \"longitude\": 19.1150392}," +
            "{\"id\": 7, \"first_name\": \"Keven\", \"last_name\": \"Paling\", \"email\": \"kpaling6@alexa.com\", \"ip_address\": \"144.96.77.21\", \"latitude\": 9.9578348, \"longitude\": 124.1789637}," +
            "{\"id\": 8, \"first_name\": \"Carri\", \"last_name\": \"Server\", \"email\": \"cserver7@google.nl\", \"ip_address\": \"222.203.3.67\", \"latitude\": -6.1130137, \"longitude\": 106.1610343}," +
            "{\"id\": 9, \"first_name\": \"Keelia\", \"last_name\": \"Gillian\", \"email\": \"kgillian8@independent.co.uk\", \"ip_address\": \"153.44.232.220\", \"latitude\": 5.9630513, \"longitude\": 10.1591213}," +
            "{\"id\": 10, \"first_name\": \"Brennan\", \"last_name\": \"Matej\", \"email\": \"bmatej9@umich.edu\", \"ip_address\": \"252.214.166.100\", \"latitude\": 41.1086264, \"longitude\": -7.6901721}," +
            "{\"id\": 11, \"first_name\": \"John\", \"last_name\": \"Doe\", \"email\": \"johndoe@example.com\", \"ip_address\": \"123.456.789.0\", \"latitude\": 51.307, \"longitude\": -0.090}" +
            "]";
  }

  private String responseBody50miles() {
      return "[" +
            "{\"id\": 1, \"first_name\": \"Maurise\", \"last_name\": \"Shieldon\", \"email\": \"mshieldon0@squidoo.com\", \"ip_address\": \"192.57.232.111\", \"latitude\": 34.003135, \"longitude\": -117.7228641}," +
            "{\"id\": 2, \"first_name\": \"Bendix\", \"last_name\": \"Halgarth\", \"email\": \"bhalgarth1@timesonline.co.uk\", \"ip_address\": \"4.185.73.82\", \"latitude\": -2.9623869, \"longitude\": 104.7399789}," +
            "{\"id\": 3, \"first_name\": \"Meghan\", \"last_name\": \"Southall\", \"email\": \"msouthall2@ihg.com\", \"ip_address\": \"21.243.184.215\", \"latitude\": 15.45033, \"longitude\": 44.12768}," +
            "{\"id\": 4, \"first_name\": \"Sidnee\", \"last_name\": \"Silwood\", \"email\": \"ssilwood3@gizmodo.com\", \"ip_address\": \"77.55.231.220\", \"latitude\": -26.94087, \"longitude\": 29.24905}," +
            "{\"id\": 5, \"first_name\": \"Rosita\", \"last_name\": \"Ferrulli\", \"email\": \"rferrulli4@unesco.org\", \"ip_address\": \"182.189.27.66\", \"latitude\": 33.5719791, \"longitude\": -84.3396421}," +
            "{\"id\": 6, \"first_name\": \"Ferdinand\", \"last_name\": \"Dun\", \"email\": \"fdun5@google.es\", \"ip_address\": \"219.245.140.98\", \"latitude\": 47.3879065, \"longitude\": 19.1150392}," +
            "{\"id\": 7, \"first_name\": \"Keven\", \"last_name\": \"Paling\", \"email\": \"kpaling6@alexa.com\", \"ip_address\": \"144.96.77.21\", \"latitude\": 9.9578348, \"longitude\": 124.1789637}," +
            "{\"id\": 8, \"first_name\": \"Carri\", \"last_name\": \"Server\", \"email\": \"cserver7@google.nl\", \"ip_address\": \"222.203.3.67\", \"latitude\": -6.1130137, \"longitude\": 106.1610343}," +
            "{\"id\": 9, \"first_name\": \"Keelia\", \"last_name\": \"Gillian\", \"email\": \"kgillian8@independent.co.uk\", \"ip_address\": \"153.44.232.220\", \"latitude\": 5.9630513, \"longitude\": 10.1591213}," +
            "{\"id\": 10, \"first_name\": \"Brennan\", \"last_name\": \"Matej\", \"email\": \"bmatej9@umich.edu\", \"ip_address\": \"252.214.166.100\", \"latitude\": 41.1086264, \"longitude\": -7.6901721}," +
            "{\"id\": 11, \"first_name\": \"Jane\", \"last_name\": \"Smith\", \"email\": \"janesmith@example.com\", \"ip_address\": \"987.654.321.0\", \"latitude\": 51.305, \"longitude\": -0.075}," +
            "{\"id\": 12, \"first_name\": \"John\", \"last_name\": \"Doe\", \"email\": \"johndoe@example.com\", \"ip_address\": \"123.456.789.0\", \"latitude\": 51.307, \"longitude\": -0.090}" +
            "]";
  }
}