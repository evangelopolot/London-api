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
}
