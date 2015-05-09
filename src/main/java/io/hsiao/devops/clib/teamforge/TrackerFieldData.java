package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.Logger;
import io.hsiao.devops.clib.logging.Logger.Level;
import io.hsiao.devops.clib.logging.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.collabnet.ce.soap60.webservices.cemain.TrackerFieldSoapDO;
import com.collabnet.ce.soap60.webservices.tracker.ArtifactSoapDO;
import com.collabnet.ce.soap60.webservices.tracker.TrackerFieldValueSoapDO;

public final class TrackerFieldData {
  TrackerFieldData(final TrackerFieldSoapDO trackerFieldSoapDO) {
    if (trackerFieldSoapDO == null) {
      throw new RuntimeException("argument 'trackerFieldSoapDO' is null");
    }

    this.trackerFieldSoapDO = trackerFieldSoapDO;
  }

  public String getDefaultTextValue() {
    return trackerFieldSoapDO.getDefaultTextValue();
  }

  public String[] getDefaultUserNames() {
    return trackerFieldSoapDO.getDefaultUsernames();
  }

  public boolean getDisabled() {
    return trackerFieldSoapDO.getDisabled();
  }

  public int getDisplayLines() {
    return trackerFieldSoapDO.getDisplayLines();
  }

  public int getDisplaySize() {
    return trackerFieldSoapDO.getDisplaySize();
  }

  public String getFieldType() {
    return trackerFieldSoapDO.getFieldType();
  }

  public List<TrackerFieldValueData> getFieldValues() {
    final TrackerFieldValueSoapDO[] trackerFieldValueSoapDOs = trackerFieldSoapDO.getFieldValues();

    final List<TrackerFieldValueData> trackerFieldValueDataList = new LinkedList<>();
    for (final TrackerFieldValueSoapDO trackerFieldValueSoapDO: trackerFieldValueSoapDOs) {
      trackerFieldValueDataList.add(new TrackerFieldValueData(trackerFieldValueSoapDO));
    }

    return trackerFieldValueDataList;
  }

  public String getHelpText() {
    return trackerFieldSoapDO.getHelpText();
  }

  public boolean getHiddenOnCreate() {
    return trackerFieldSoapDO.getHiddenOnCreate();
  }

  public String getId() {
    return trackerFieldSoapDO.getId();
  }

  public String getName() {
    return trackerFieldSoapDO.getName();
  }

  public String getParentFieldId() {
    return trackerFieldSoapDO.getParentFieldId();
  }

  public String getPattern() {
    return trackerFieldSoapDO.getPattern();
  }

  public boolean getRequired() {
    return trackerFieldSoapDO.getRequired();
  }

  public String getUserFilter() {
    return trackerFieldSoapDO.getUserFilter();
  }

  public String getValueType() {
    return trackerFieldSoapDO.getValueType();
  }

  public static Map<String, TrackerFieldData> toMap(final List<TrackerFieldData> trackerFieldDataList) {
    if (trackerFieldDataList == null) {
      throw new RuntimeException("argument 'trackerFieldDataList' is null");
    }

    final Map<String, TrackerFieldData> map = new LinkedHashMap<>();
    for (final TrackerFieldData trackerFieldData: trackerFieldDataList) {
      map.put(trackerFieldData.getName(), trackerFieldData);
    }

    return map;
  }

  public static String getFieldType(final Map<String, TrackerFieldData> map, final String name) throws Exception {
    if (map == null) {
      throw new RuntimeException("argument 'map' is null");
    }

    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    switch (name.toLowerCase()) {
      // System defined fields
      case "artifact id":
      case "artifactid":
        return FIELD_TYPE_TEXT;
      case "assigned to":
      case "assignedto":
        return FIELD_TYPE_SINGLE_SELECT;
      case "attachment":
        return FIELD_TYPE_TEXT;
      case "comment":
        return FIELD_TYPE_TEXT;
      case "description":
        return FIELD_TYPE_TEXT;
      case "id":
        return FIELD_TYPE_TEXT;
      case "priority":
        return FIELD_TYPE_SINGLE_SELECT;
      case "title":
        return FIELD_TYPE_TEXT;
      // Configurable and User defined fields
      default:
        final String internalName = toInternalName(name);

        if (!map.containsKey(internalName)) {
          final Exception exception = new Exception("failed to get field type (field not found) [" + name + "]");
          logger.log(Level.INFO, "failed to get field type (field not found) [" + name + "]", exception);
          throw exception;
        }

        return map.get(internalName).getFieldType();
    }
  }

  public static String getFieldType(final List<TrackerFieldData> trackerFieldDataList, final String name) throws Exception {
    if (trackerFieldDataList == null) {
      throw new RuntimeException("argument 'trackerFieldDataList' is null");
    }

    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    final Map<String, TrackerFieldData> map = TrackerFieldData.toMap(trackerFieldDataList);

    return TrackerFieldData.getFieldType(map, name);
  }

  public static boolean isSystemDefined(final String name) {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    switch (name.toLowerCase()) {
      case "artifact id":
      case "artifactid":
      case "assigned to":
      case "assignedto":
      case "attachment":
      case "comment":
      case "description":
      case "id":
      case "priority":
      case "title":
        return true;
      default:
        return false;
    }
  }

  public static boolean isConfigurable(final String name) {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    switch (name.toLowerCase()) {
      case "actual effort":
      case "actualeffort":
      case "auto summing":
      case "autosumming":
      case "calculate effort":
      case "calculateeffort":
      case "category":
      case "customer":
      case "estimated effort":
      case "estimatedeffort":
      case "fixed in release":
      case "fixedinrelease":
      case "group":
      case "planning folder":
      case "planningfolder":
      case "points":
      case "remaining effort":
      case "remainingeffort":
      case "reported in release":
      case "reportedinrelease":
      case "status":
        return true;
      default:
        return false;
    }
  }

  public static boolean isUserDefined(final String name) {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    return !(isSystemDefined(name) || isConfigurable(name));
  }

  static String toInternalName(final String name) {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    switch (name.toLowerCase()) {
      // Configurable fields
      case "actual effort":
      case "actualeffort":
        return ArtifactSoapDO.COLUMN_ACTUAL_EFFORT;
      case "auto summing":
      case "autosumming":
      case "calculate effort":
      case "calculateeffort":
        return ArtifactSoapDO.COLUMN_AUTOSUMMING;
      case "category":
        return ArtifactSoapDO.COLUMN_CATEGORY;
      case "customer":
        return ArtifactSoapDO.COLUMN_CUSTOMER;
      case "estimated effort":
      case "estimatedeffort":
        return ArtifactSoapDO.COLUMN_ESTIMATED_EFFORT;
      case "fixed in release":
      case "fixedinrelease":
        return ArtifactSoapDO.COLUMN_RESOLVED_IN_RELEASE_TITLE;
      case "group":
        return ArtifactSoapDO.COLUMN_ARTIFACT_GROUP;
      case "planning folder":
      case "planningfolder":
        return ArtifactSoapDO.COLUMN_PLANNING_FOLDER_TITLE;
      case "points":
        return ArtifactSoapDO.COLUMN_POINTS;
      case "remaining effort":
      case "remainingeffort":
        return ArtifactSoapDO.COLUMN_REMAINING_EFFORT;
      case "reported in release":
      case "reportedinrelease":
        return ArtifactSoapDO.COLUMN_REPORTED_IN_RELEASE_TITLE;
      case "status":
        return ArtifactSoapDO.COLUMN_STATUS;
      default:
        // System defined and User defined fields return intact
        return name;
    }
  }

  private static final Logger logger = LoggerFactory.getLogger(TrackerFieldData.class);

  private final TrackerFieldSoapDO trackerFieldSoapDO;

  public static final String FIELD_TYPE_DATE = TrackerFieldSoapDO.FIELD_TYPE_DATE;
  public static final String FIELD_TYPE_FOLDER = TrackerFieldSoapDO.FIELD_TYPE_FOLDER;
  public static final String FIELD_TYPE_MULTISELECT = TrackerFieldSoapDO.FIELD_TYPE_MULTISELECT;
  public static final String FIELD_TYPE_MULTISELECT_USER = TrackerFieldSoapDO.FIELD_TYPE_MULTISELECT_USER;
  public static final String FIELD_TYPE_SINGLE_SELECT = TrackerFieldSoapDO.FIELD_TYPE_SINGLE_SELECT;
  public static final String FIELD_TYPE_TEXT = TrackerFieldSoapDO.FIELD_TYPE_TEXT;

  public static final String FIELD_VALUE_TYPE_DATE = TrackerFieldSoapDO.FIELD_VALUE_TYPE_DATE;
  public static final String FIELD_VALUE_TYPE_INTEGER = TrackerFieldSoapDO.FIELD_VALUE_TYPE_INTEGER;
  public static final String FIELD_VALUE_TYPE_STRING = TrackerFieldSoapDO.FIELD_VALUE_TYPE_STRING;
  public static final String FIELD_VALUE_TYPE_USER = TrackerFieldSoapDO.FIELD_VALUE_TYPE_USER;
}
