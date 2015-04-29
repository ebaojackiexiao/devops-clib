package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.cemain.AttachmentSoapRow;

public final class AttachmentElement {
  AttachmentElement(final AttachmentSoapRow attachmentSoapRow) {
    if (attachmentSoapRow == null) {
      throw new RuntimeException("argument 'attachmentSoapRow' is null");
    }

    this.attachmentSoapRow = attachmentSoapRow;
  }

  public String getAttachmentId() {
    return attachmentSoapRow.getAttachmentId();
  }

  public String getCreatedBy() {
    return attachmentSoapRow.getCreatedBy();
  }

  public String getCreatedByFullname() {
    return attachmentSoapRow.getCreatedByFullname();
  }

  public Date getDateCreated() {
    return attachmentSoapRow.getDateCreated();
  }

  public String getFileName() {
    return attachmentSoapRow.getFileName();
  }

  public String getFileSize() {
    return attachmentSoapRow.getFileSize();
  }

  public String getMimetype() {
    return attachmentSoapRow.getMimetype();
  }

  public String getRawFileId() {
    return attachmentSoapRow.getRawFileId();
  }

  public String getStoredFileId() {
    return attachmentSoapRow.getStoredFileId();
  }

  public String getTransactionId() {
    return attachmentSoapRow.getTransactionId();
  }

  private final AttachmentSoapRow attachmentSoapRow;
}

