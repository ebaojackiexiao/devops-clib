package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.cemain.UserSoapRow;

public final class UserElement {
  UserElement(final UserSoapRow userSoapRow) {
    if (userSoapRow == null) {
      throw new RuntimeException("argument 'userSoapRow' is null");
    }

    this.userSoapRow = userSoapRow;
  }

  public String getAlternateEmail1() {
    return userSoapRow.getAlternateEmail1();
  }

  public String getAlternateEmail2() {
    return userSoapRow.getAlternateEmail2();
  }

  public String getAlternateEmail3() {
    return userSoapRow.getAlternateEmail3();
  }

  public String getEmail() {
    return userSoapRow.getEmail();
  }

  public String getFullName() {
    return userSoapRow.getFullName();
  }

  public Date getLastLogin() {
    return userSoapRow.getLastLogin();
  }

  public String getLicenseType() {
    return userSoapRow.getLicenseType();
  }

  public String getLocale() {
    return userSoapRow.getLocale();
  }

  public String getOrganization() {
    return userSoapRow.getOrganization();
  }

  public boolean getRestrictedUser() {
    return userSoapRow.getRestrictedUser();
  }

  public String getStatus() {
    return userSoapRow.getStatus();
  }

  public boolean getSuperUser() {
    return userSoapRow.getSuperUser();
  }

  public String getTimeZone() {
    return userSoapRow.getTimeZone();
  }

  public String getUserName() {
    return userSoapRow.getUserName();
  }

  private final UserSoapRow userSoapRow;
}
