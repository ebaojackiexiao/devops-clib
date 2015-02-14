package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.logging.LoggerProxy;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.collabnet.ce.soap60.webservices.cemain.TrackerFieldSoapDO;
import com.collabnet.ce.soap60.webservices.tracker.TrackerFieldValueSoapDO;

public final class TrackerFieldData {
  TrackerFieldData(final TrackerFieldSoapDO trackerFieldSoapDO) throws Exception {
    if (trackerFieldSoapDO == null) {
      throw new Exception("argument 'trackerFieldSoapDO' is null");
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

  public List<TrackerFieldValueData> getFieldValues() throws Exception {
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

  public static Map<String, TrackerFieldData> toMap(final List<TrackerFieldData> trackerFieldDataList) throws Exception {
    if (trackerFieldDataList == null) {
      throw new Exception("argument 'trackerFieldDataList' is null");
    }

    final Map<String, TrackerFieldData> map = new LinkedHashMap<>();
    for (final TrackerFieldData trackerFieldData: trackerFieldDataList) {
      map.put(trackerFieldData.getName(), trackerFieldData);
    }

    return map;
  }

  public static String getFieldType(final Map<String, TrackerFieldData> map, final String name) throws Exception {
    if (map == null) {
      throw new Exception("argument 'map' is null");
    }

    if (name == null) {
      throw new Exception("argument 'name' is null");
    }

    if (!map.containsKey(name)) {
      final Exception exception = new Exception("failed to get field type (field not found) [" + name + "]");
      LoggerProxy.getLogger().log(Level.INFO, "failed to get field type (field not found) [" + name + "]", exception);
      throw exception;
    }

    return map.get(name).getFieldType();
  }

  public static String getFieldType(final List<TrackerFieldData> trackerFieldDataList, final String name) throws Exception {
    if (trackerFieldDataList == null) {
      throw new Exception("argument 'trackerFieldDataList' is null");
    }

    if (name == null) {
      throw new Exception("argument 'name' is null");
    }

    final Map<String, TrackerFieldData> map = TrackerFieldData.toMap(trackerFieldDataList);

    return TrackerFieldData.getFieldType(map, name);
  }

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
