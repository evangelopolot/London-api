package uk.gov.dwp.codetest;

import org.junit.jupiter.api.Test;

class ApplicationTest {

  @Test
  public void contextLoads() {
    // TODO: add relevant assertions here
  }

  @Test
  void main() {
    Application.main(new String[]{"--spring.main.web-environment=false",});
  }

}