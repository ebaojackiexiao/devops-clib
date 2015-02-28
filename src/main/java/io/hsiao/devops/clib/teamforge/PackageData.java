package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.Exception;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.frs.PackageSoapDO;

public final class PackageData {
  PackageData(final PackageSoapDO packageSoapDO) throws Exception {
    if (packageSoapDO == null) {
      throw new Exception("argument 'packageSoapDO' is null");
    }

    this.packageSoapDO = packageSoapDO;
  }

  public String getCreatedBy() {
    return packageSoapDO.getCreatedBy();
  }

  public Date getCreatedDate() {
    return packageSoapDO.getCreatedDate();
  }

  public String getDescription() {
    return packageSoapDO.getDescription();
  }

  public int getDownloaded() {
    return packageSoapDO.getDownloaded();
  }

  public String getId() {
    return packageSoapDO.getId();
  }

  public boolean getIsPublished() {
    return packageSoapDO.getIsPublished();
  }

  public String getLastModifiedBy() {
    return packageSoapDO.getLastModifiedBy();
  }

  public Date getLastModifiedDate() {
    return packageSoapDO.getLastModifiedDate();
  }

  public String getParentFolderId() {
    return packageSoapDO.getParentFolderId();
  }

  public String getPath() {
    return packageSoapDO.getPath();
  }

  public String getProjectId() {
    return packageSoapDO.getProjectId();
  }

  public String getTitle() {
    return packageSoapDO.getTitle();
  }

  public int getVersion() {
    return packageSoapDO.getVersion();
  }

  private final PackageSoapDO packageSoapDO;
}
