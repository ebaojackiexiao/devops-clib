package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.frs.ReleaseSoapDO;

public final class ReleaseData {
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
