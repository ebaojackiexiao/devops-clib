package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.Exception;

import com.collabnet.ce.soap60.webservices.scm.ScmFile2SoapRow;

public final class ScmFileElement {
  ScmFileElement(final ScmFile2SoapRow scmFile2SoapRow) throws Exception {
    if (scmFile2SoapRow == null) {
      throw new Exception("argument 'scmFile2SoapRow' is null");
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
