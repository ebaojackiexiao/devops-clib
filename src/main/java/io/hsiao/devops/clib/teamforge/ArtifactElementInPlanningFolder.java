package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.planning.ArtifactsInPlanningFolderSoapRow;

public final class ArtifactElementInPlanningFolder {
  ArtifactElementInPlanningFolder(final ArtifactsInPlanningFolderSoapRow artifactsInPlanningFolderSoapRow) {
    if (artifactsInPlanningFolderSoapRow == null) {
      throw new RuntimeException("argument 'artifactsInPlanningFolderSoapRow' is null");
    }

    this.artifactsInPlanningFolderSoapRow = artifactsInPlanningFolderSoapRow;
  }

  public int getActualEffort() {
    return artifactsInPlanningFolderSoapRow.getActualEffort();
  }

  public String getArtifactGroup() {
    return artifactsInPlanningFolderSoapRow.getArtifactGroup();
  }

  public String getAssignedToFullname() {
    return artifactsInPlanningFolderSoapRow.getAssignedToFullname();
  }

  public String getAssignedToUsername() {
    return artifactsInPlanningFolderSoapRow.getAssignedToUsername();
  }

  public boolean getAutosumming() {
    return artifactsInPlanningFolderSoapRow.getAutosumming();
  }

  public String getCategory() {
    return artifactsInPlanningFolderSoapRow.getCategory();
  }

  public String[] getChildrenIds() {
    return artifactsInPlanningFolderSoapRow.getChildrenIds();
  }

  public Date getCloseDate() {
    return artifactsInPlanningFolderSoapRow.getCloseDate();
  }

  public String getCustomer() {
    return artifactsInPlanningFolderSoapRow.getCustomer();
  }

  public String getDescription() {
    return artifactsInPlanningFolderSoapRow.getDescription();
  }

  public int getEstimatedEffort() {
    return artifactsInPlanningFolderSoapRow.getEstimatedEffort();
  }

  public String getFolderId() {
    return artifactsInPlanningFolderSoapRow.getFolderId();
  }

  public String getFolderPathString() {
    return artifactsInPlanningFolderSoapRow.getFolderPathString();
  }

  public String getFolderTitle() {
    return artifactsInPlanningFolderSoapRow.getFolderTitle();
  }

  public boolean getHasChildren() {
    return artifactsInPlanningFolderSoapRow.getHasChildren();
  }

  public String getId() {
    return artifactsInPlanningFolderSoapRow.getId();
  }

  public Date getLastModifiedDate() {
    return artifactsInPlanningFolderSoapRow.getLastModifiedDate();
  }

  public String getParentId() {
    return artifactsInPlanningFolderSoapRow.getParentId();
  }

  public String getPlanningFolderId() {
    return artifactsInPlanningFolderSoapRow.getPlanningFolderId();
  }

  public String getPlanningFolderTitle() {
    return artifactsInPlanningFolderSoapRow.getPlanningFolderTitle();
  }

  public int getPoints() {
    return artifactsInPlanningFolderSoapRow.getPoints();
  }

  public int getPriority() {
    return artifactsInPlanningFolderSoapRow.getPriority();
  }

  public String getProjectId() {
    return artifactsInPlanningFolderSoapRow.getProjectId();
  }

  public String getProjectPathString() {
    return artifactsInPlanningFolderSoapRow.getProjectPathString();
  }

  public String getProjectTitle() {
    return artifactsInPlanningFolderSoapRow.getProjectTitle();
  }

  public int getRemainingEffort() {
    return artifactsInPlanningFolderSoapRow.getRemainingEffort();
  }

  public String getReportedInReleaseId() {
    return artifactsInPlanningFolderSoapRow.getReportedInReleaseId();
  }

  public String getReportedInReleaseTitle() {
    return artifactsInPlanningFolderSoapRow.getReportedInReleaseTitle();
  }

  public String getResolvedInReleaseId() {
    return artifactsInPlanningFolderSoapRow.getResolvedInReleaseId();
  }

  public String getResolvedInReleaseTitle() {
    return artifactsInPlanningFolderSoapRow.getResolvedInReleaseTitle();
  }

  public String getStatus() {
    return artifactsInPlanningFolderSoapRow.getStatus();
  }

  public String getStatusClass() {
    return artifactsInPlanningFolderSoapRow.getStatusClass();
  }

  public String getSubmittedByFullname() {
    return artifactsInPlanningFolderSoapRow.getSubmittedByFullname();
  }

  public String getSubmittedByUsername() {
    return artifactsInPlanningFolderSoapRow.getSubmittedByUsername();
  }

  public Date getSubmittedDate() {
    return artifactsInPlanningFolderSoapRow.getSubmittedDate();
  }

  public String getTitle() {
    return artifactsInPlanningFolderSoapRow.getTitle();
  }

  public String getTrackerIcon() {
    return artifactsInPlanningFolderSoapRow.getTrackerIcon();
  }

  public int getVersion() {
    return artifactsInPlanningFolderSoapRow.getVersion();
  }

  private final ArtifactsInPlanningFolderSoapRow artifactsInPlanningFolderSoapRow;
}
