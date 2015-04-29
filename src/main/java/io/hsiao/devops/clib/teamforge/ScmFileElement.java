package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import com.collabnet.ce.soap60.webservices.scm.ScmFile2SoapRow;

public final class ScmFileElement {
  ScmFileElement(final ScmFile2SoapRow scmFile2SoapRow) {
    if (scmFile2SoapRow == null) {
      throw new RuntimeException("argument 'scmFile2SoapRow' is null");
    }

    this.scmFile2SoapRow = scmFile2SoapRow;
  }

  public String getFileName() {
    return scmFile2SoapRow.getFilename();
  }

  public String getId() {
    return scmFile2SoapRow.getId();
  }

  public String getRefFileName() {
    return scmFile2SoapRow.getRefFilename();
  }

  public String getStatus() {
    return scmFile2SoapRow.getStatus();
  }

  public String getVersion() {
    return scmFile2SoapRow.getVersion();
  }

  private final ScmFile2SoapRow scmFile2SoapRow;
}
