package io.hsiao.devops.clib.teamforge;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.frs.ReleaseSoapDO;

import io.hsiao.devops.clib.exception.RuntimeException;

public final class ReleaseData {
  public static final String MATURITY_ALPHA = ReleaseSoapDO.MATURITY_ALPHA;
  public static final String MATURITY_BETA = ReleaseSoapDO.MATURITY_BETA;
  public static final String MATURITY_DEVELOPMENT_BUILD = ReleaseSoapDO.MATURITY_DEVELOPMENT_BUILD;
  public static final String MATURITY_EARLY_ACCESS = ReleaseSoapDO.MATURITY_EARLY_ACCESS;
  public static final String MATURITY_GENERAL_AVAILABILITY = ReleaseSoapDO.MATURITY_GENERAL_AVAILABILITY;
  public static final String MATURITY_OBSOLETE = ReleaseSoapDO.MATURITY_OBSOLETE;
  public static final String MATURITY_PRE_GENERAL_AVAILABILITY = ReleaseSoapDO.MATURITY_PRE_GENERAL_AVAILABILITY;
  public static final String MATURITY_PROTOTYPE = ReleaseSoapDO.MATURITY_PROTOTYPE;
  public static final String MATURITY_STABLE = ReleaseSoapDO.MATURITY_STABLE;

  public static final String STATUS_ACTIVE = ReleaseSoapDO.STATUS_ACTIVE;
  public static final String STATUS_PENDING = ReleaseSoapDO.STATUS_PENDING;

  ReleaseData(final ReleaseSoapDO releaseSoapDO) {
    if (releaseSoapDO == null) {
      throw new RuntimeException("argument 'releaseSoapDO' is null");
    }

    this.releaseSoapDO = releaseSoapDO;
  }

  public String getCreatedBy() {
    return releaseSoapDO.getCreatedBy();
  }

  public Date getCreatedDate() {
    return releaseSoapDO.getCreatedDate();
  }

  public String getDescription() {
    return releaseSoapDO.getDescription();
  }

  public int getDownloaded() {
    return releaseSoapDO.getDownloaded();
  }

  public String getId() {
    return releaseSoapDO.getId();
  }

  public String getLastModifiedBy() {
    return releaseSoapDO.getLastModifiedBy();
  }

  public Date getLastModifiedDate() {
    return releaseSoapDO.getLastModifiedDate();
  }

  public String getMaturity() {
    return releaseSoapDO.getMaturity();
  }

  public String getParentFolderId() {
    return releaseSoapDO.getParentFolderId();
  }

  public String getPath() {
    return releaseSoapDO.getPath();
  }

  public String getProjectId() {
    return releaseSoapDO.getProjectId();
  }

  public String getStatus() {
    return releaseSoapDO.getStatus();
  }

  public String getTitle() {
    return releaseSoapDO.getTitle();
  }

  public int getVersion() {
    return releaseSoapDO.getVersion();
  }

  public void setDescription(final String description) {
    if (description == null) {
      throw new RuntimeException("argument 'description' is null");
    }

    releaseSoapDO.setDescription(description);
  }

  public void setDownloaded(final int downloaded) {
    releaseSoapDO.setDownloaded(downloaded);
  }

  public void setMaturity(final String maturity) {
    if (maturity == null) {
      throw new RuntimeException("argument 'maturity' is null");
    }

    releaseSoapDO.setMaturity(maturity);
  }

  public void setStatus(final String status) {
    if (status == null) {
      throw new RuntimeException("argument 'status' is null");
    }

    releaseSoapDO.setStatus(status);
  }

  public void setTitle(final String title) {
    if (title == null) {
      throw new RuntimeException("argument 'title' is null");
    }

    releaseSoapDO.setTitle(title);
  }

  ReleaseSoapDO getReleaseSoapDO() {
    return releaseSoapDO;
  }

  private final ReleaseSoapDO releaseSoapDO;
}
