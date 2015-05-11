package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.planning.PlanningFolder4SoapDO;

public final class PlanningFolderData {
  PlanningFolderData(final PlanningFolder4SoapDO planningFolderSoapDO) {
    if (planningFolderSoapDO == null) {
      throw new RuntimeException("argument 'planningFolderSoapDO' is null");
    }

    this.planningFolderSoapDO = planningFolderSoapDO;
  }

  public String getBurndownViewMode() {
    return planningFolderSoapDO.getBurndownViewMode();
  }

  public int getCapacity() {
    return planningFolderSoapDO.getCapacity();
  }

  public String getCreatedBy() {
    return planningFolderSoapDO.getCreatedBy();
  }

  public Date getCreatedDate() {
    return planningFolderSoapDO.getCreatedDate();
  }

  public String getDescription() {
    return planningFolderSoapDO.getDescription();
  }

  public Date getEndDate() {
    return planningFolderSoapDO.getEndDate();
  }

  public String getId() {
    return planningFolderSoapDO.getId();
  }

  public String getLastModifiedBy() {
    return planningFolderSoapDO.getLastModifiedBy();
  }

  public Date getLastModifiedDate() {
    return planningFolderSoapDO.getLastModifiedDate();
  }

  public String getParentFolderId() {
    return planningFolderSoapDO.getParentFolderId();
  }

  public String getPath() {
    return planningFolderSoapDO.getPath();
  }

  public int getPointsCapacity() {
    return planningFolderSoapDO.getPointsCapacity();
  }

  public String getProjectId() {
    return planningFolderSoapDO.getProjectId();
  }

  public String getReleaseId() {
    return planningFolderSoapDO.getReleaseId();
  }

  public Date getStartDate() {
    return planningFolderSoapDO.getStartDate();
  }

  public String getStatus() {
    return planningFolderSoapDO.getStatus();
  }

  public String getStatusClass() {
    return planningFolderSoapDO.getStatusClass();
  }

  public String getTitle() {
    return planningFolderSoapDO.getTitle();
  }

  public String getTrackerUnitId() {
    return planningFolderSoapDO.getTrackerUnitId();
  }

  public int getVersion() {
    return planningFolderSoapDO.getVersion();
  }

  public void setBurndownViewMode(final String burndownViewMode) {
    if (burndownViewMode == null) {
      throw new RuntimeException("argument 'burndownViewMode' is null");
    }

    planningFolderSoapDO.setBurndownViewMode(burndownViewMode);
  }

  public void setCapacity(final int capacity) {
    planningFolderSoapDO.setCapacity(capacity);
  }

  public void setDescription(final String description) {
    if (description == null) {
      throw new RuntimeException("argument 'description' is null");
    }

    planningFolderSoapDO.setDescription(description);
  }

  public void setEndDate(final Date endDate) {
    if (endDate == null) {
      throw new RuntimeException("argument 'endDate' is null");
    }

    planningFolderSoapDO.setEndDate(endDate);
  }

  public void setPointsCapacity(final int pointsCapacity) {
    planningFolderSoapDO.setPointsCapacity(pointsCapacity);
  }

  public void setReleaseId(final String releaseId) {
    if (releaseId == null) {
      throw new RuntimeException("argument 'releaseId' is null");
    }

    planningFolderSoapDO.setReleaseId(releaseId);
  }

  public void setStartDate(final Date startDate) {
    if (startDate == null) {
      throw new RuntimeException("argument 'startDate' is null");
    }

    planningFolderSoapDO.setStartDate(startDate);
  }

  public void setStatus(final String status) {
    if (status == null) {
      throw new RuntimeException("argument 'status' is null");
    }

    planningFolderSoapDO.setStatus(status);
  }

  public void setStatusClass(final String statusClass) {
    if (statusClass == null) {
      throw new RuntimeException("argument 'statusClass' is null");
    }

    planningFolderSoapDO.setStatusClass(statusClass);
  }

  public void setTitle(final String title) {
    if (title == null) {
      throw new RuntimeException("argument 'title' is null");
    }

    planningFolderSoapDO.setTitle(title);
  }

  public void setTrackerUnitId(final String trackerUnitId) {
    if (trackerUnitId == null) {
      throw new RuntimeException("argument 'trackerUnitId' is null");
    }

    planningFolderSoapDO.setTrackerUnitId(trackerUnitId);
  }

  private final PlanningFolder4SoapDO planningFolderSoapDO;
}
