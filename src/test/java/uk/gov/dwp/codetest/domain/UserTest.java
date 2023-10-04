package uk.gov.dwp.codetest.domain;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class UserTest {
  @Test
  public void shouldHaveANoArgsConstructor() {
    assertThat(User.class, hasValidBeanConstructor());
  }

  @Test
  public void gettersAndSettersShouldWorkForEachProperty() {
    assertThat(User.class, hasValidGettersAndSetters());
  }

  @Test
  public void allPropertiesShouldInfluenceHashCode() {
    assertThat(User.class, hasValidBeanHashCode());
  }

  @Test
  public void allPropertiesShouldBeComparedDuringEquals() {
    assertThat(User.class, hasValidBeanEquals());
  }

  @Test
  public void allPropertiesShouldBeRepresentedInToStringOutput() {
    assertThat(User.class, hasValidBeanToString());
  }

}