package uk.gov.dwp.codetest.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
public class User {

  private Long id;
  private String first_name;
  private String last_name;
  private String email;
  private String ip_address;
  private Float latitude;
  private Float longitude;

  public User(long id, String first_name, String last_name, String email, String ip_address, float latitude, float longitude) {
    this.id = id;
    this.first_name = first_name;
    this.last_name = last_name;
    this.email = email;
    this.ip_address = ip_address;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
