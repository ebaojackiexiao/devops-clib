package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.cemain.UserSoapDO;

public final class UserData {
  UserData(final UserSoapDO userSoapDO) {
    if (userSoapDO == null) {
      throw new RuntimeException("argument 'userSoapDO' is null");
    }

    this.userSoapDO = userSoapDO;
  }

  UserSoapDO getUserSoapDO() {
    return userSoapDO;
  }

  public String getAlternateEmail1() {
    return userSoapDO.getAlternateEmail1();
  }

  public String getAlternateEmail2() {
    return userSoapDO.getAlternateEmail2();
  }

  public String getAlternateEmail3() {
    return userSoapDO.getAlternateEmail3();
  }

  public String getCreatedBy() {
    return userSoapDO.getCreatedBy();
  }

  public Date getCreatedDate() {
    return userSoapDO.getCreatedDate();
  }

  public String getEmail() {
    return userSoapDO.getEmail();
  }

  public String getFullName() {
    return userSoapDO.getFullName();
  }

  public String getId() {
    return userSoapDO.getId();
  }

  public Date getLastLogin() {
    return userSoapDO.getLastLogin();
  }

  public String getLastModifiedBy() {
    return userSoapDO.getLastModifiedBy();
  }

  public Date getLastModifiedDate() {
    return userSoapDO.getLastModifiedDate();
  }

  public String getLicenseType() {
    return userSoapDO.getLicenseType();
  }

  public String getLocale() {
    return userSoapDO.getLocale();
  }

  public String getOrganization() {
    return userSoapDO.getOrganization();
  }

  public boolean getRestrictedUser() {
    return userSoapDO.getRestrictedUser();
  }

  public String getStatus() {
    return userSoapDO.getStatus();
  }

  public boolean getSuperUser() {
    return userSoapDO.getSuperUser();
  }

  public String getTimeZone() {
    return userSoapDO.getTimeZone();
  }

  public String getUserName() {
    return userSoapDO.getUsername();
  }

  public int getVersion() {
    return userSoapDO.getVersion();
  }

  public void setAlternateEmail1(final String alternateEmail1) {
    if (alternateEmail1 == null) {
      throw new RuntimeException("argument 'alternateEmail1' is null");
    }

    userSoapDO.setAlternateEmail1(alternateEmail1);
  }

  public void setAlternateEmail2(final String alternateEmail2) {
    if (alternateEmail2 == null) {
      throw new RuntimeException("argument 'alternateEmail2' is null");
    }

    userSoapDO.setAlternateEmail1(alternateEmail2);
  }

  public void setAlternateEmail3(final String alternateEmail3) {
    if (alternateEmail3 == null) {
      throw new RuntimeException("argument 'alternateEmail3' is null");
    }

    userSoapDO.setAlternateEmail1(alternateEmail3);
  }

  public void setEmail(final String email) {
    if (email == null) {
      throw new RuntimeException("argument 'email' is null");
    }

    userSoapDO.setEmail(email);
  }

  public void setFullName(final String fullName) {
    if (fullName == null) {
      throw new RuntimeException("argument 'fullName' is null");
    }

    userSoapDO.setFullName(fullName);
  }

  public void setLastLogin(final Date lastLogin) {
    if (lastLogin == null) {
      throw new RuntimeException("argument 'lastLogin' is null");
    }

    userSoapDO.setLastLogin(lastLogin);
  }

  public void setLicenseType(final String licenseType) {
    if (licenseType == null) {
      throw new RuntimeException("argument 'licenseType' is null");
    }

    userSoapDO.setLicenseType(licenseType);
  }

  public void setLocale(final String locale) {
    if (locale == null) {
      throw new RuntimeException("argument 'locale' is null");
    }

    userSoapDO.setLocale(locale);
  }

  public void setOrganization(final String organization) {
    if (organization == null) {
      throw new RuntimeException("argument 'organization' is null");
    }

    userSoapDO.setOrganization(organization);
  }

  public void setRestrictedUser(final boolean restrictedUser) {
    userSoapDO.setRestrictedUser(restrictedUser);
  }

  public void setStatus(final String status) {
    if (status == null) {
      throw new RuntimeException("argument 'status' is null");
    }

    userSoapDO.setStatus(status);
  }

  public void setSuperUser(final boolean superUser) {
    userSoapDO.setSuperUser(superUser);
  }

  public void setTimeZone(final String timeZone) {
    if (timeZone == null) {
      throw new RuntimeException("argument 'timeZone' is null");
    }

    userSoapDO.setTimeZone(timeZone);
  }

  public void setUserName(final String userName) {
    if (userName == null) {
      throw new RuntimeException("argument 'userName' is null");
    }

    userSoapDO.setUsername(userName);
  }

  private final UserSoapDO userSoapDO;

  public static final String FILTER_EMAIL = UserSoapDO.FILTER_EMAIL;
  public static final String FILTER_FULL_NAME = UserSoapDO.FILTER_FULL_NAME;
  public static final String FILTER_LICENSE_TYPE = UserSoapDO.FILTER_LICENSE_TYPE;
  public static final String FILTER_STATUS = UserSoapDO.FILTER_STATUS;
  public static final String FILTER_USERNAME = UserSoapDO.FILTER_USERNAME;

  public static final String LICENSE_TYPE_ALM = UserSoapDO.LICENSE_TYPE_ALM;
  public static final String LICENSE_TYPE_SCM = UserSoapDO.LICENSE_TYPE_SCM;

  public static final String STATUS_ACTIVE = UserSoapDO.STATUS_ACTIVE;
  public static final String STATUS_DISABLED = UserSoapDO.STATUS_DISABLED;
  public static final String STATUS_PENDING = UserSoapDO.STATUS_PENDING;
  public static final String STATUS_REMOVED = UserSoapDO.STATUS_REMOVED;
}
