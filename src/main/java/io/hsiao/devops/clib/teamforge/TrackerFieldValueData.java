package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.collabnet.ce.soap60.webservices.tracker.TrackerFieldValueSoapDO;

public final class TrackerFieldValueData {
  TrackerFieldValueData(final TrackerFieldValueSoapDO trackerFieldValueSoapDO) {
    if (trackerFieldValueSoapDO == null) {
      throw new RuntimeException("argument 'trackerFieldValueSoapDO' is null");
    }

    this.trackerFieldValueSoapDO = trackerFieldValueSoapDO;
  }

  public String getId() {
    return trackerFieldValueSoapDO.getId();
  }

  public boolean getIsDefault() {
    return trackerFieldValueSoapDO.getIsDefault();
  }

  public String getValue() {
    return trackerFieldValueSoapDO.getValue();
  }

  public String getValueClass() {
    return trackerFieldValueSoapDO.getValueClass();
  }

  public static Map<String, TrackerFieldValueData> toMap(final List<TrackerFieldValueData> trackerFieldValueDataList) {
    if (trackerFieldValueDataList == null) {
      throw new RuntimeException("argument 'trackerFieldValueDataList' is null");
    }

    final Map<String, TrackerFieldValueData> map = new LinkedHashMap<>();
    for (final TrackerFieldValueData trackerFieldValueData: trackerFieldValueDataList) {
      map.put(trackerFieldValueData.getValue(), trackerFieldValueData);
    }

    return map;
  }

  private final TrackerFieldValueSoapDO trackerFieldValueSoapDO;

  public static final String VALUE_CLASS_CLOSED = TrackerFieldValueSoapDO.VALUE_CLASS_CLOSED;
  public static final String VALUE_CLASS_OPEN = TrackerFieldValueSoapDO.VALUE_CLASS_OPEN;
}
