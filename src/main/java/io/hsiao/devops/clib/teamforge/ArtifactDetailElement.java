package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.tracker.ArtifactDetailSoapRow;

public final class ArtifactDetailElement {
  ArtifactDetailElement(final ArtifactDetailSoapRow artifactDetailSoapRow) throws Exception {
    if (artifactDetailSoapRow == null) {
      throw new RuntimeException("argument 'artifactDetailSoapRow' is null");
    }

    this.artifactDetailSoapRow = artifactDetailSoapRow;
    this.artifactFieldValues = new ArtifactFieldValues(artifactDetailSoapRow.getFlexFields());
  }

  public int getActualEffort() {
    return artifactDetailSoapRow.getActualEffort();
  }

  public String getArtifactGroup() {
    return artifactDetailSoapRow.getArtifactGroup();
  }

  public String getAssignedToFullname() {
    return artifactDetailSoapRow.getAssignedToFullname();
  }

  public String getAssignedToUsername() {
    return artifactDetailSoapRow.getAssignedToUsername();
  }

  public boolean getAutosumming() {
    return artifactDetailSoapRow.getAutosumming();
  }

  public String getCategory() {
    return artifactDetailSoapRow.getCategory();
  }

  public Date getCloseDate() {
    return artifactDetailSoapRow.getCloseDate();
  }

  public String getCustomer() {
    return artifactDetailSoapRow.getCustomer();
  }

  public String getDescription() {
    return artifactDetailSoapRow.getDescription();
  }

  public int getEstimatedEffort() {
    return artifactDetailSoapRow.getEstimatedEffort();
  }

  public Object getFieldValue(final String name, final String type) throws Exception {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    if (type == null) {
      throw new RuntimeException("argument 'type' is null");
    }

    switch (name.toLowerCase()) {
      case "actualeffort":
      case "actual effort":
        return artifactDetailSoapRow.getActualEffort();
      case "artifactid":
      case "artifact id":
      case "id":
        return artifactDetailSoapRow.getId();
      case "assignedtofullname":
      case "assigned to fullname":
        return artifactDetailSoapRow.getAssignedToFullname();
      case "assignedtousername":
      case "assigned to username":
        return artifactDetailSoapRow.getAssignedToUsername();
      case "autosumming":
      case "auto summing":
      case "calculateeffort":
      case "calculate effort":
        return artifactDetailSoapRow.getAutosumming();
      case "category":
        return artifactDetailSoapRow.getCategory();
      case "closedate":
      case "close date":
      case "closeddate":
      case "closed date":
        return artifactDetailSoapRow.getCloseDate();
      case "customer":
        return artifactDetailSoapRow.getCustomer();
      case "description":
        return artifactDetailSoapRow.getDescription();
      case "estimatedeffort":
      case "estimated effort":
        return artifactDetailSoapRow.getEstimatedEffort();
      case "fixedinrelease":
      case "fixed in release":
        return artifactDetailSoapRow.getResolvedInReleaseTitle();
      case "group":
        return artifactDetailSoapRow.getArtifactGroup();
      case "lastmodified":
      case "last modified":
      case "lastmodifieddate":
      case "last modified date":
        return artifactDetailSoapRow.getLastModifiedDate();
      case "planningfolder":
      case "planning folder":
        return artifactDetailSoapRow.getPlanningFolderTitle();
      case "points":
        return artifactDetailSoapRow.getPoints();
      case "priority":
        return artifactDetailSoapRow.getPriority();
      case "remainingeffort":
      case "remaining effort":
        return artifactDetailSoapRow.getRemainingEffort();
      case "reportedinrelease":
      case "reported in release":
        return artifactDetailSoapRow.getReportedInReleaseTitle();
      case "submittedbyfullname":
      case "submitted by fullname":
        return artifactDetailSoapRow.getSubmittedByFullname();
      case "submittedbyusername":
      case "submitted by username":
        return artifactDetailSoapRow.getSubmittedByUsername();
      case "submitdate":
      case "submit date":
      case "submitteddate":
      case "submitted date":
        return artifactDetailSoapRow.getSubmittedDate();
      case "status":
        return artifactDetailSoapRow.getStatus();
      case "title":
        return artifactDetailSoapRow.getTitle();
      default:
        return artifactFieldValues.getFieldValue(name, type);
    }
  }

  public ArtifactFieldValues getFlexFields() throws Exception {
    return new ArtifactFieldValues(artifactDetailSoapRow.getFlexFields());
  }

  public String getFolderId() {
    return artifactDetailSoapRow.getFolderId();
  }

  public String getFolderPathString() {
    return artifactDetailSoapRow.getFolderPathString();
  }

  public String getFolderTitle() {
    return artifactDetailSoapRow.getFolderTitle();
  }

  public String getId() {
    return artifactDetailSoapRow.getId();
  }

  public Date getLastModifiedDate() {
    return artifactDetailSoapRow.getLastModifiedDate();
  }

  public String getPlanningFolderId() {
    return artifactDetailSoapRow.getPlanningFolderId();
  }

  public String getPlanningFolderTitle() {
    return artifactDetailSoapRow.getPlanningFolderTitle();
  }

  public int getPoints() {
    return artifactDetailSoapRow.getPoints();
  }

  public int getPriority() {
    return artifactDetailSoapRow.getPriority();
  }

  public String getPriorityText() {
    final int priority = getPriority();

    switch (priority) {
      case 1:
        return "1-Highest";
      case 2:
        return "2-High";
      case 3:
        return "3-Medium";
      case 4:
        return "4-Low";
      case 5:
        return "5-Lowest";
      default:
        throw new RuntimeException("invalid priority value, only 1-5 are allowed, found [" + priority + "]");
    }
  }

  public String getProjectId() {
    return artifactDetailSoapRow.getProjectId();
  }

  public String getProjectPathString() {
    return artifactDetailSoapRow.getProjectPathString();
  }

  public String getProjectTitle() {
    return artifactDetailSoapRow.getProjectTitle();
  }

  public int getRemainingEffort() {
    return artifactDetailSoapRow.getRemainingEffort();
  }

  public String getReportedInReleaseId() {
    return artifactDetailSoapRow.getReportedInReleaseId();
  }

  public String getReportedInReleaseTitle() {
    return artifactDetailSoapRow.getReportedInReleaseTitle();
  }

  public String getResolvedInReleaseId() {
    return artifactDetailSoapRow.getResolvedInReleaseId();
  }

  public String getResolvedInReleaseTitle() {
    return artifactDetailSoapRow.getResolvedInReleaseTitle();
  }

  public String getStatus() {
    return artifactDetailSoapRow.getStatus();
  }

  public String getStatusClass() {
    return artifactDetailSoapRow.getStatusClass();
  }

  public String getSubmittedByFullname() {
    return artifactDetailSoapRow.getSubmittedByFullname();
  }

  public String getSubmittedByUsername() {
    return artifactDetailSoapRow.getSubmittedByUsername();
  }

  public Date getSubmittedDate() {
    return artifactDetailSoapRow.getSubmittedDate();
  }

  public String getTitle() {
    return artifactDetailSoapRow.getTitle();
  }

  public String getTrackerIcon() {
    return artifactDetailSoapRow.getTrackerIcon();
  }

  public int getVersion() {
    return artifactDetailSoapRow.getVersion();
  }

  private final ArtifactDetailSoapRow artifactDetailSoapRow;
  private final ArtifactFieldValues artifactFieldValues;
}
