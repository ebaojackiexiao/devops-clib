package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import com.collabnet.ce.soap60.types.SoapFilter;
import com.collabnet.ce.soap60.webservices.tracker.ArtifactSoapDO;
import com.collabnet.ce.soap60.webservices.tracker.TrackerFieldValueSoapDO;

public final class FilterElement {
  public FilterElement(final String name, final String value) {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    if (value == null) {
      throw new RuntimeException("argument 'value' is null");
    }

    switch (name.toLowerCase()) {
      case "actualeffort":
      case "actual effort":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_ACTUAL_EFFORT, value);
        break;
      case "assignedto":
      case "assigned to":
      case "assignee":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_ASSIGNED_TO, value);
        break;
      case "autosumming":
      case "auto summing":
      case "calculateeffort":
      case "calculate effort":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_AUTOSUMMING, value);
        break;
      case "category":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_CATEGORY, value);
        break;
      case "customer":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_CUSTOMER, value);
        break;
      case "estimatedeffort":
      case "estimated effort":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_ESTIMATED_EFFORT, value);
        break;
      case "group":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_GROUP, value);
        break;
      case "modifiedafter":
      case "modified after":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_MODIFIED_AFTER, value);
        break;
      case "modifiedbefore":
      case "modified before":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_MODIFIED_BEFORE, value);
        break;
      case "planningfolderid":
      case "planning folder id":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_PLANNING_FOLDER_ID, value);
        break;
      case "points":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_POINTS, value);
        break;
      case "priority":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_PRIORITY, value);
        break;
      case "remainingeffort":
      case "remaining effort":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_REMAINING_EFFORT, value);
        break;
      case "reportedinreleaseid":
      case "reported in release id":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_REPORTED_IN_RELEASE_ID, value);
        break;
      case "reportedinrelease":
      case "reported in release":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_REPORTED_IN_RELEASE_TITLE, value);
        break;
      case "fixedinreleaseid":
      case "fixed in release id":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_RESOLVED_IN_RELEASE_ID, value);
        break;
      case "fixedinrelease":
      case "fixed in release":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_RESOLVED_IN_RELEASE_TITLE, value);
        break;
      case "status":
        if (value.equalsIgnoreCase("all open") || value.equalsIgnoreCase("all opened")) {
          soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_STATUS_CLASS, TrackerFieldValueSoapDO.VALUE_CLASS_OPEN);
          break;
        }
        if (value.equalsIgnoreCase("all close") || value.equalsIgnoreCase("all closed")) {
          soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_STATUS_CLASS, TrackerFieldValueSoapDO.VALUE_CLASS_CLOSED);
          break;
        }
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_STATUS, value);
        break;
      case "statusclass":
      case "status class":
        if (value.equalsIgnoreCase("open")) {
          soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_STATUS_CLASS, TrackerFieldValueSoapDO.VALUE_CLASS_OPEN);
          break;
        }
        if (value.equalsIgnoreCase("close") || value.equalsIgnoreCase("closed")) {
          soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_STATUS_CLASS, TrackerFieldValueSoapDO.VALUE_CLASS_CLOSED);
          break;
        }
      case "submittedafter":
      case "submitted after":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_SUBMITTED_AFTER, value);
        break;
      case "submittedbefore":
      case "submitted before":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_SUBMITTED_BEFORE, value);
        break;
      case "submittedby":
      case "submitted by":
      case "submitter":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_SUBMITTED_BY, value);
        break;
      case "title":
        soapFilter = new SoapFilter(ArtifactSoapDO.FILTER_TITLE, value);
        break;
      default:
        // ##### flex field #####
        // CAVEATS:
        // - for text fields, filter values must be plain string values, e.g, "i am a text field value"
        // - for single-select fields, filter values must be "value id"s, e.g, "fldv69806" instead of "Production"
        // - for multiple-select fields, filter values must be select option values, e.g, "Production" instead of "fldv69806"
        // - for user-select fields, filter values must be user names rather than full names, e.g, "john.doe" instead of "John Doe"
        // - for date-select fields, filter values must be date strings, e.g, "2015-01-01" or "2005-01-31 02:45:01.123 +0200"
        soapFilter = new SoapFilter(name, value);
        break;
    }
  }

  public String getName() {
    return soapFilter.getName();
  }

  public String getValue() {
    return soapFilter.getValue();
  }

  SoapFilter getSoapFilter() {
    return soapFilter;
  }

  private final SoapFilter soapFilter;
}
