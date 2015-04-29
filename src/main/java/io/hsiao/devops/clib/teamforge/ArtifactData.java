package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;
import java.util.List;

import com.collabnet.ce.soap60.webservices.tracker.ArtifactSoapDO;

public final class ArtifactData {
  ArtifactData(final ArtifactSoapDO artifactSoapDO) throws Exception {
    if (artifactSoapDO == null) {
      throw new RuntimeException("argument 'artifactSoapDO' is null");
    }

    this.artifactSoapDO = artifactSoapDO;
    artifactFieldValues = new ArtifactFieldValues(artifactSoapDO.getFlexFields());
  }

  ArtifactSoapDO getArtifactSoapDO() {
    return artifactSoapDO;
  }

  public int getActualEffort() {
    return artifactSoapDO.getActualEffort();
  }

  public String getAssignedTo() {
    return artifactSoapDO.getAssignedTo();
  }

  public boolean getAutoSumming() {
    return artifactSoapDO.getAutosumming();
  }

  public String getCategory() {
    return artifactSoapDO.getCategory();
  }

  public Date getCloseDate() {
    return artifactSoapDO.getCloseDate();
  }

  public String getCreatedBy() {
    return artifactSoapDO.getCreatedBy();
  }

  public String getCustomer() {
    return artifactSoapDO.getCustomer();
  }

  public String getDescription() {
    return artifactSoapDO.getDescription();
  }

  public int getEstimatedEffort() {
    return artifactSoapDO.getEstimatedEffort();
  }

  public String getFolderId() {
    return artifactSoapDO.getFolderId();
  }

  public String getGroup() {
    return artifactSoapDO.getGroup();
  }

  public String getId() {
    return artifactSoapDO.getId();
  }

  public String getPlanningFolderId() {
    return artifactSoapDO.getPlanningFolderId();
  }

  public int getPoints() {
    return artifactSoapDO.getPoints();
  }

  public int getPriority() {
    return artifactSoapDO.getPriority();
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

  public int getRemainingEffort() {
    return artifactSoapDO.getRemainingEffort();
  }

  public String getReportedReleaseId() {
    return artifactSoapDO.getReportedReleaseId();
  }

  public String getResolvedReleaseId() {
    return artifactSoapDO.getResolvedReleaseId();
  }

  public String getStatus() {
    return artifactSoapDO.getStatus();
  }

  public String getStatusClass() {
    return artifactSoapDO.getStatusClass();
  }

  public String getTitle() {
    return artifactSoapDO.getTitle();
  }

  public List<String> getFieldNames() {
    return artifactFieldValues.getFieldNames();
  }

  public Object getFieldValue(final String name, final String type) throws Exception {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    if (type == null) {
      throw new RuntimeException("argument 'type' is null");
    }

    return artifactFieldValues.getFieldValue(name, type);
  }

  public String getFieldValueType(final String name) throws Exception {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    return artifactFieldValues.getFieldValueType(name);
  }

  public void setActualEffort(final int actualEffort) {
    artifactSoapDO.setActualEffort(actualEffort);
  }

  public void setAssignedTo(final String assignedTo) {
    if (assignedTo == null) {
      throw new RuntimeException("argument 'assignedTo' is null");
    }

    artifactSoapDO.setAssignedTo(assignedTo);
  }

  public void setAutoSumming(final boolean autoSumming) {
    artifactSoapDO.setAutosumming(autoSumming);
  }

  public void setCategory(final String category) {
    if (category == null) {
      throw new RuntimeException("argument 'category' is null");
    }

    artifactSoapDO.setCategory(category);
  }

  public void setCloseDate(final Date closeDate) {
    if (closeDate == null) {
      throw new RuntimeException("argument 'closeDate' is null");
    }

    artifactSoapDO.setCloseDate(closeDate);
  }

  public void setCustomer(final String customer) {
    if (customer == null) {
      throw new RuntimeException("argument 'customer' is null");
    }

    artifactSoapDO.setCustomer(customer);
  }

  public void setDescription(final String description) {
    if (description == null) {
      throw new RuntimeException("argument 'description' is null");
    }

    artifactSoapDO.setDescription(description);
  }

  public void setEstimatedEffort(final int estimatedEffort) {
    artifactSoapDO.setEstimatedEffort(estimatedEffort);
  }

  public void setFolderId(final String folderId) {
    if (folderId == null) {
      throw new RuntimeException("argument 'folderId' is null");
    }

    artifactSoapDO.setFolderId(folderId);
  }

  public void setGroup(final String group) {
    if (group == null) {
      throw new RuntimeException("argument 'group' is null");
    }

    artifactSoapDO.setGroup(group);
  }

  public void setPlanningFolderId(final String planningFolderId) {
    if (planningFolderId == null) {
      throw new RuntimeException("argument 'planningFolderId' is null");
    }

    artifactSoapDO.setPlanningFolderId(planningFolderId);
  }

  public void setPoints(final int points) {
    artifactSoapDO.setPoints(points);
  }

  public void setPriority(final int priority) {
    artifactSoapDO.setPriority(priority);
  }

  public void setRemainingEffort(final int remainingEffort) {
    artifactSoapDO.setRemainingEffort(remainingEffort);
  }

  public void setReportedReleaseId(final String reportedReleaseId) {
    if (reportedReleaseId == null) {
      throw new RuntimeException("argument 'reportedReleaseId' is null");
    }

    artifactSoapDO.setReportedReleaseId(reportedReleaseId);
  }

  public void setResolvedReleaseId(final String resolvedReleaseId) {
    if (resolvedReleaseId == null) {
      throw new RuntimeException("argument 'resolvedReleaseId' is null");
    }

    artifactSoapDO.setResolvedReleaseId(resolvedReleaseId);
  }

  public void setStatus(final String status) {
    if (status == null) {
      throw new RuntimeException("argument 'status' is null");
    }

    artifactSoapDO.setStatus(status);
  }

  public void setStatusClass(final String statusClass) {
    if (statusClass == null) {
      throw new RuntimeException("argument 'statusClass' is null");
    }

    artifactSoapDO.setStatusClass(statusClass);
  }

  public void setTitle(final String title) {
    if (title == null) {
      throw new RuntimeException("argument 'title' is null");
    }

    artifactSoapDO.setTitle(title);
  }

  public void setFieldValue(final String name, final String type, final Object value) throws Exception {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    if (type == null) {
      throw new RuntimeException("argument 'type' is null");
    }

    artifactFieldValues.setFieldValue(name, type, value);

    artifactSoapDO.setFlexFields(artifactFieldValues.getSoapFieldValues());
  }

  private final ArtifactSoapDO artifactSoapDO;
  private final ArtifactFieldValues artifactFieldValues;
}
