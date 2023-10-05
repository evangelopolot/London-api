package uk.gov.dwp.codetest.service;

import uk.gov.dwp.codetest.domain.User;

import java.util.List;

public interface APIServiceInterface {
    List<User> getUsers();
}
