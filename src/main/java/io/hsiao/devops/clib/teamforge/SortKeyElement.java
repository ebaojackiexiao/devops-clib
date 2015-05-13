package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.collabnet.ce.soap60.types.SoapSortKey;
import com.collabnet.ce.soap60.webservices.tracker.ArtifactSoapDO;

public final class SortKeyElement {
  public SortKeyElement(final String name, final boolean ascending) {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    switch (name.toLowerCase()) {
      case "actualeffort":
      case "actual effort":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_ACTUAL_EFFORT, ascending);
        break;
      case "assignedtofullname":
      case "assigned to fullname":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_ASSIGNED_TO_FULLNAME, ascending);
        break;
      case "assignedtousername":
      case "assigned to username":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_ASSIGNED_TO_USERNAME, ascending);
        break;
      case "category":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_CATEGORY, ascending);
        break;
      case "closedate":
      case "close date":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_CLOSE_DATE, ascending);
        break;
      case "customer":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_CUSTOMER, ascending);
        break;
      case "description":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_DESCRIPTION, ascending);
        break;
      case "estimatedeffort":
      case "estimated effort":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_ESTIMATED_EFFORT, ascending);
        break;
      case "group":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_ARTIFACT_GROUP, ascending);
        break;
      case "id":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_ID, ascending);
        break;
      case "lastmodifieddate":
      case "last modified date":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_LAST_MODIFIED_DATE, ascending);
        break;
      case "planningfolderid":
      case "planning folder id":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_PLANNING_FOLDER_ID, ascending);
        break;
      case "planningfolder":
      case "planning folder":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_PLANNING_FOLDER_TITLE, ascending);
        break;
      case "points":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_POINTS, ascending);
        break;
      case "priority":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_PRIORITY, ascending);
        break;
      case "remainingeffort":
      case "remaining effort":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_REMAINING_EFFORT, ascending);
        break;
      case "reportedinrelease":
      case "reported in release":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_REPORTED_IN_RELEASE_TITLE, ascending);
        break;
      case "fixedinrelease":
      case "fixed in release":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_RESOLVED_IN_RELEASE_TITLE, ascending);
        break;
      case "status":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_STATUS, ascending);
        break;
      case "statusclass":
      case "status class":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_STATUS_CLASS, ascending);
        break;
      case "submitteddate":
      case "submitted date":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_SUBMITTED_DATE, ascending);
        break;
      case "submittedbyfullname":
      case "submitted by fullname":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_SUBMITTED_BY_FULLNAME, ascending);
        break;
      case "submittedbyusername":
      case "submitted by username":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_SUBMITTED_BY_USERNAME, ascending);
        break;
      case "title":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_TITLE, ascending);
        break;
      case "version":
        soapSortKey = new SoapSortKey(ArtifactSoapDO.COLUMN_VERSION, ascending);
        break;
      default:
        // until core product bug [CNSC-172126] is fixed, sorting won't work for flex fields
        soapSortKey = new SoapSortKey(name, ascending);
        break;
    }
  }

  public String getName() {
    return soapSortKey.getName();
  }

  public boolean isAscending() {
    return soapSortKey.isAscending();
  }

  public void setAscending(final boolean ascending) {
    soapSortKey.setAscending(ascending);
  }

  public void setName(final String name) {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    soapSortKey.setName(name);
  }

  public static List<SortKeyElement> get(final Map<String, Boolean> sortKeys) {
    if (sortKeys == null) {
      throw new RuntimeException("argument 'sortKeys' is null");
    }

    final List<SortKeyElement> sortKeyList = new LinkedList<>();
    for (final Map.Entry<String, Boolean> sortKey: sortKeys.entrySet()) {
      sortKeyList.add(new SortKeyElement(sortKey.getKey(), sortKey.getValue()));
    }

    return sortKeyList;
  }

  SoapSortKey getSoapSortKey() {
    return soapSortKey;
  }

  private final SoapSortKey soapSortKey;
}
