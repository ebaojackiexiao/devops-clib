package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.frs.ReleaseSoapRow;

public final class ReleaseElement {
  ReleaseElement(final ReleaseSoapRow releaseSoapRow) {
    if (releaseSoapRow == null) {
      throw new RuntimeException("argument 'releaseSoapRow' is null");
    }

    this.releaseSoapRow = releaseSoapRow;
  }

  public String getCreatedBy() {
    return releaseSoapRow.getCreatedBy();
  }

  public Date getCreatedOn() {
    return releaseSoapRow.getCreatedOn();
  }

  public String getDescription() {
    return releaseSoapRow.getDescription();
  }

  public String getId() {
    return releaseSoapRow.getId();
  }

  public String getLastModifiedBy() {
    return releaseSoapRow.getLastModifiedBy();
  }

  public Date getLastModifiedOn() {
    return releaseSoapRow.getLastModifiedOn();
  }

  public String getMaturity() {
    return releaseSoapRow.getMaturity();
  }

  public String getParentFolderId() {
    return releaseSoapRow.getParentFolderId();
  }

  public String getPath() {
    return releaseSoapRow.getPath();
  }

  public String getProjectId() {
    return releaseSoapRow.getProjectId();
  }

  public String getStatus() {
    return releaseSoapRow.getStatus();
  }

  public String getTitle() {
    return releaseSoapRow.getTitle();
  }

  private final ReleaseSoapRow releaseSoapRow;
}
