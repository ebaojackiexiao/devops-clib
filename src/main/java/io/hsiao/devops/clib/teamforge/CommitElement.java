package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.scm.CommitSoapRow;

public final class CommitElement {
  CommitElement(final CommitSoapRow commitSoapRow) {
    if (commitSoapRow == null) {
      throw new RuntimeException("argument 'commitSoapRow' is null");
    }

    this.commitSoapRow = commitSoapRow;
  }

  public String getCommitMessage() {
    return commitSoapRow.getCommitMessage();
  }

  public String getCreatedBy() {
    return commitSoapRow.getCreatedBy();
  }

  public String getCreatedByFullName() {
    return commitSoapRow.getCreatedByFullname();
  }

  public Date getDateCreated() {
    return commitSoapRow.getDateCreated();
  }

  public String getFolderPathString() {
    return commitSoapRow.getFolderPathString();
  }

  public String getId() {
    return commitSoapRow.getId();
  }

  public String getProjectId() {
    return commitSoapRow.getProjectId();
  }

  public String getProjectPathString() {
    return commitSoapRow.getProjectPathString();
  }

  public String getProjectTitle() {
    return commitSoapRow.getProjectTitle();
  }

  public String getTitle() {
    return commitSoapRow.getTitle();
  }

  private final CommitSoapRow commitSoapRow;
}
