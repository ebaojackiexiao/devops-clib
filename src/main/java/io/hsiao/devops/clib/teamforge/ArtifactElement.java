package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.tracker.ArtifactSoapRow;

public final class ArtifactElement {
  ArtifactElement(final ArtifactSoapRow artifactSoapRow) {
    if (artifactSoapRow == null) {
      throw new RuntimeException("argument 'artifactSoapRow' is null");
    }

    this.artifactSoapRow = artifactSoapRow;
  }

  public int getActualEffort() {
    return artifactSoapRow.getActualEffort();
  }

  public String getArtifactGroup() {
    return artifactSoapRow.getArtifactGroup();
  }

  public String getAssignedToFullName() {
    return artifactSoapRow.getAssignedToFullname();
  }

  public String getAssignedToUserName() {
    return artifactSoapRow.getAssignedToUsername();
  }

  public boolean getAutoSumming() {
    return artifactSoapRow.getAutosumming();
  }

  public String getCategory() {
    return artifactSoapRow.getCategory();
  }

  public Date getCloseDate() {
    return artifactSoapRow.getCloseDate();
  }

  public String getCustomer() {
    return artifactSoapRow.getCustomer();
  }

  public String getDescription() {
    return artifactSoapRow.getDescription();
  }

  public int getEstimatedEffort() {
    return artifactSoapRow.getEstimatedEffort();
  }

  public String getFolderId() {
    return artifactSoapRow.getFolderId();
  }

  public String getFolderPathString() {
    return artifactSoapRow.getFolderPathString();
  }

  public String getFolderTitle() {
    return artifactSoapRow.getFolderTitle();
  }

  public String getId() {
    return artifactSoapRow.getId();
  }

  public Date getLastModifiedDate() {
    return artifactSoapRow.getLastModifiedDate();
  }

  public String getPlanningFolderId() {
    return artifactSoapRow.getPlanningFolderId();
  }

  public int getPoints() {
    return artifactSoapRow.getPoints();
  }

  public int getPriority() {
    return artifactSoapRow.getPriority();
  }

  public String getProjectId() {
    return artifactSoapRow.getProjectId();
  }

  public String getProjectPathString() {
    return artifactSoapRow.getProjectPathString();
  }

  public String getProjectTitle() {
    return artifactSoapRow.getProjectTitle();
  }

  public int getRemainingEffort() {
    return artifactSoapRow.getRemainingEffort();
  }

  public String getStatus() {
    return artifactSoapRow.getStatus();
  }

  public String getStatusClass() {
    return artifactSoapRow.getStatusClass();
  }

  public String getSubmittedByFullName() {
    return artifactSoapRow.getSubmittedByFullname();
  }

  public String getSubmittedByUserName() {
    return artifactSoapRow.getSubmittedByUsername();
  }

  public Date getSubmittedDate() {
    return artifactSoapRow.getSubmittedDate();
  }

  public String getTitle() {
    return artifactSoapRow.getTitle();
  }

  public int getVersion() {
    return artifactSoapRow.getVersion();
  }

  private final ArtifactSoapRow artifactSoapRow;
}
