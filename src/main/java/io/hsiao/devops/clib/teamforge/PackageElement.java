package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.frs.PackageSoapRow;

public final class PackageElement {
  PackageElement(final PackageSoapRow packageSoapRow) {
    if (packageSoapRow == null) {
      throw new RuntimeException("argument 'packageSoapRow' is null");
    }

    this.packageSoapRow = packageSoapRow;
  }

  public String getCreatedBy() {
    return packageSoapRow.getCreatedBy();
  }

  public Date getCreatedOn() {
    return packageSoapRow.getCreatedOn();
  }

  public String getDescription() {
    return packageSoapRow.getDescription();
  }

  public String getId() {
    return packageSoapRow.getId();
  }

  public String getLastModifiedBy() {
    return packageSoapRow.getLastModifiedBy();
  }

  public Date getLastModifiedOn() {
    return packageSoapRow.getLastModifiedOn();
  }

  public String getParentFolderId() {
    return packageSoapRow.getParentFolderId();
  }

  public String getPath() {
    return packageSoapRow.getPath();
  }

  public String getProjectId() {
    return packageSoapRow.getProjectId();
  }

  public String getStatus() {
    return packageSoapRow.getStatus();
  }

  public String getTitle() {
    return packageSoapRow.getTitle();
  }

  private final PackageSoapRow packageSoapRow;
}
