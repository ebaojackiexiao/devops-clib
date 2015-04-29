package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.cemain.ProjectSoapRow;

public final class ProjectElement {
  ProjectElement(final ProjectSoapRow projectSoapRow) {
    if (projectSoapRow == null) {
      throw new RuntimeException("argument 'projectSoapRow' is null");
    }

    this.projectSoapRow = projectSoapRow;
  }

  public Date getDateCreated() {
    return projectSoapRow.getDateCreated();
  }

  public String getDescription() {
    return projectSoapRow.getDescription();
  }

  public String getHierarchyPath() {
    return projectSoapRow.getHierarchyPath();
  }

  public String getId() {
    return projectSoapRow.getId();
  }

  public boolean getLocked() {
    return projectSoapRow.getLocked();
  }

  public String getParentProjectId() {
    return projectSoapRow.getParentProjectId();
  }

  public String getPath() {
    return projectSoapRow.getPath();
  }

  public String getTitle() {
    return projectSoapRow.getTitle();
  }

  private final ProjectSoapRow projectSoapRow;
}
