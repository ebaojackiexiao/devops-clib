package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.tracker.TrackerSoapRow;

public final class TrackerElement {
  TrackerElement(final TrackerSoapRow trackerSoapRow) {
    if (trackerSoapRow == null) {
      throw new RuntimeException("argument 'trackerSoapRow' is null");
    }

    this.trackerSoapRow = trackerSoapRow;
  }

  public String getCreatedBy() {
    return trackerSoapRow.getCreatedBy();
  }

  public Date getCreatedOn() {
    return trackerSoapRow.getCreatedOn();
  }

  public String getDescription() {
    return trackerSoapRow.getDescription();
  }

  public String getIcon() {
    return trackerSoapRow.getIcon();
  }

  public String getId() {
    return trackerSoapRow.getId();
  }

  public String getLastEditedByFullname() {
    return trackerSoapRow.getLastEditedByFullname();
  }

  public String getLastEditedByUsername() {
    return trackerSoapRow.getLastEditedByUsername();
  }

  public Date getLastEditedDate() {
    return trackerSoapRow.getLastEditedDate();
  }

  public String getLastModifiedBy() {
    return trackerSoapRow.getLastModifiedBy();
  }

  public Date getLastModifiedOn() {
    return trackerSoapRow.getLastModifiedOn();
  }

  public String getParentFolderId() {
    return trackerSoapRow.getParentFolderId();
  }

  public String getPath() {
    return trackerSoapRow.getPath();
  }

  public String getProjectId() {
    return trackerSoapRow.getProjectId();
  }

  public String getTitle() {
    return trackerSoapRow.getTitle();
  }

  private final TrackerSoapRow trackerSoapRow;
}
