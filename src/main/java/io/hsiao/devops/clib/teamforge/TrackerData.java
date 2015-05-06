package io.hsiao.devops.clib.teamforge;

import java.util.Date;

import io.hsiao.devops.clib.exception.RuntimeException;

import com.collabnet.ce.soap60.webservices.tracker.TrackerSoapDO;

public final class TrackerData {
  TrackerData(final TrackerSoapDO trackerSoapDO) {
    if (trackerSoapDO == null) {
      throw new RuntimeException("argument 'trackerSoapDO' is null");
    }

    this.trackerSoapDO = trackerSoapDO;
  }

  public String getAutoAssignField() {
    return trackerSoapDO.getAutoAssignField();
  }

  public String getCreatedBy() {
    return trackerSoapDO.getCreatedBy();
  }

  public Date getCreatedDate() {
    return trackerSoapDO.getCreatedDate();
  }

  public String getDescription() {
    return trackerSoapDO.getDescription();
  }

  public String getIcon() {
    return trackerSoapDO.getIcon();
  }

  public String getId() {
    return trackerSoapDO.getId();
  }

  public String getLastModifiedBy() {
    return trackerSoapDO.getLastModifiedBy();
  }

  public Date getLastModifiedDate() {
    return trackerSoapDO.getLastModifiedDate();
  }

  public String getParentFolderId() {
    return trackerSoapDO.getParentFolderId();
  }

  public String getPath() {
    return trackerSoapDO.getPath();
  }

  public String getProjectId() {
    return trackerSoapDO.getProjectId();
  }

  public String getTitle() {
    return trackerSoapDO.getTitle();
  }

  public int getVersion() {
    return trackerSoapDO.getVersion();
  }

  public void setAutoAssignField(final String autoAssignField) {
    if (autoAssignField == null) {
      throw new RuntimeException("argument 'autoAssignField' is null");
    }

    trackerSoapDO.setAutoAssignField(autoAssignField);
  }

  public void setDescription(final String description) {
    if (description == null) {
      throw new RuntimeException("argument 'description' is null");
    }

    trackerSoapDO.setDescription(description);
  }

  public void setIcon(final String icon) {
    if (icon == null) {
      throw new RuntimeException("argument 'icon' is null");
    }

    trackerSoapDO.setIcon(icon);
  }

  public void setTitle(final String title) {
    if (title == null) {
      throw new RuntimeException("argument 'title' is null");
    }

    trackerSoapDO.setTitle(title);
  }

  TrackerSoapDO getTrackerSoapDO() {
    return trackerSoapDO;
  }

  private final TrackerSoapDO trackerSoapDO;
}
