package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.planning.PlanningFolder4SoapRow;

public final class PlanningFolderElement {
  PlanningFolderElement(final PlanningFolder4SoapRow planningFolderSoapRow) {
    if (planningFolderSoapRow == null) {
      throw new RuntimeException("argument 'planningFolderSoapRow' is null");
    }

    this.planningFolderSoapRow = planningFolderSoapRow;
  }

  public String getBurndownViewMode() {
    return planningFolderSoapRow.getBurndownViewMode();
  }

  public int getCapacity() {
    return planningFolderSoapRow.getCapacity();
  }

  public String getCreatedBy() {
    return planningFolderSoapRow.getCreatedBy();
  }

  public Date getCreatedOn() {
    return planningFolderSoapRow.getCreatedOn();
  }

  public String getDescription() {
    return planningFolderSoapRow.getDescription();
  }

  public Date getEndDate() {
    return planningFolderSoapRow.getEndDate();
  }

  public String getId() {
    return planningFolderSoapRow.getId();
  }

  public String getLastModifiedBy() {
    return planningFolderSoapRow.getLastModifiedBy();
  }

  public Date getLastModifiedOn() {
    return planningFolderSoapRow.getLastModifiedOn();
  }

  public String getParentFolderId() {
    return planningFolderSoapRow.getParentFolderId();
  }

  public String getPath() {
    return planningFolderSoapRow.getPath();
  }

  public int getPointsCapacity() {
    return planningFolderSoapRow.getPointsCapacity();
  }

  public String getProjectId() {
    return planningFolderSoapRow.getProjectId();
  }

  public String getReleaseId() {
    return planningFolderSoapRow.getReleaseId();
  }

  public String getReleaseTitle() {
    return planningFolderSoapRow.getReleaseTitle();
  }

  public Date getStartDate() {
    return planningFolderSoapRow.getStartDate();
  }

  public String getStatus() {
    return planningFolderSoapRow.getStatus();
  }

  public String getStatusClass() {
    return planningFolderSoapRow.getStatusClass();
  }

  public String getTitle() {
    return planningFolderSoapRow.getTitle();
  }

  public String getTrackerUnitId() {
    return planningFolderSoapRow.getTrackerUnitId();
  }

  public void setBurndownViewMode(final String burndownViewMode) {
    if (burndownViewMode == null) {
      throw new RuntimeException("argument 'burndownViewMode' is null");
    }

    planningFolderSoapRow.setBurndownViewMode(burndownViewMode);
  }

  public void setCapacity(final int capacity) {
    planningFolderSoapRow.setCapacity(capacity);
  }

  public void setDescription(final String description) {
    if (description == null) {
      throw new RuntimeException("argument 'description' is null");
    }

    planningFolderSoapRow.setDescription(description);
  }

  public void setEndDate(final Date endDate) {
    if (endDate == null) {
      throw new RuntimeException("argument 'endDate' is null");
    }

    planningFolderSoapRow.setEndDate(endDate);
  }

  public void setPointsCapacity(final int pointsCapacity) {
    planningFolderSoapRow.setPointsCapacity(pointsCapacity);
  }

  public void setReleaseId(final String releaseId) {
    if (releaseId == null) {
      throw new RuntimeException("argument 'releaseId' is null");
    }

    planningFolderSoapRow.setReleaseId(releaseId);
  }

  public void setReleaseTitle(final String releaseTitle) {
    if (releaseTitle == null) {
      throw new RuntimeException("argument 'releaseTitle' is null");
    }

    planningFolderSoapRow.setReleaseTitle(releaseTitle);
  }

  public void setStartDate(final Date startDate) {
    if (startDate == null) {
      throw new RuntimeException("argument 'startDate' is null");
    }

    planningFolderSoapRow.setStartDate(startDate);
  }

  public void setStatus(final String status) {
    if (status == null) {
      throw new RuntimeException("argument 'status' is null");
    }

    planningFolderSoapRow.setStatus(status);
  }

  public void setStatusClass(final String statusClass) {
    if (statusClass == null) {
      throw new RuntimeException("argument 'statusClass' is null");
    }

    planningFolderSoapRow.setStatusClass(statusClass);
  }

  public void setTitle(final String title) {
    if (title == null) {
      throw new RuntimeException("argument 'title' is null");
    }

    planningFolderSoapRow.setTitle(title);
  }

  public void setTrackerUnitId(final String trackerUnitId) {
    if (trackerUnitId == null) {
      throw new RuntimeException("argument 'trackerUnitId' is null");
    }

    planningFolderSoapRow.setTrackerUnitId(trackerUnitId);
  }

  private final PlanningFolder4SoapRow planningFolderSoapRow;
}
