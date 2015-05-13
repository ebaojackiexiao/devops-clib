package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.Logger;
import io.hsiao.devops.clib.logging.Logger.Level;
import io.hsiao.devops.clib.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class FilterBuilder {
  private FilterBuilder() {}

  public static List<FilterElement> build(final String projectId, final String trackerId,
      final Teamforge teamforge, final Map<String, String> filterMap, final String delimiter, final boolean trim) throws Exception {
    if (projectId == null) {
      throw new RuntimeException("argument 'projectId' is null");
    }

    if (trackerId == null) {
      throw new RuntimeException("argument 'trackerId' is null");
    }

    if (teamforge == null) {
      throw new RuntimeException("argument 'teamforge' is null");
    }

    if (filterMap == null) {
      throw new RuntimeException("argument 'filterMap' is null");
    }

    if (delimiter == null) {
      throw new RuntimeException("argument 'delimiter' is null");
    }

    final Map<String, TrackerFieldData> trackerFieldNameDataMap = TrackerFieldData.toMap(teamforge.getTrackerFieldDataList(trackerId));

    final List<FilterElement> filters = new LinkedList<>();
    for (final Map.Entry<String, String> filterMapEntry: filterMap.entrySet()) {
      final String filterName = filterMapEntry.getKey();
      final String filterValue = filterMapEntry.getValue();

      if (!trackerFieldNameDataMap.containsKey(TrackerFieldData.toInternalName(filterName))) {
        final Exception exception = new Exception("failed to build filter list (invalid field name) [" + filterName + "]");
        logger.log(Level.INFO, "failed to build filter list (invalid field name) [" + filterName + "]", exception);
        throw exception;
      }

      final String fieldType = trackerFieldNameDataMap.get(TrackerFieldData.toInternalName(filterName)).getFieldType();

      if (fieldType.equals(TrackerFieldData.FIELD_TYPE_DATE) || fieldType.equals(TrackerFieldData.FIELD_TYPE_TEXT)) {
        filters.add(new FilterElement(filterName, filterValue));
        continue;
      }

      final List<String> valueList = new ArrayList<>();
      for (String value: filterValue.split(delimiter)) {
        value = trim ? value.trim() : value;
        if (!value.isEmpty()) {
          valueList.add(value);
        }
      }
      final String[] values = new String[valueList.size()];
      valueList.toArray(values);

      if (fieldType.equals(TrackerFieldData.FIELD_TYPE_FOLDER)) {
        for (final String value: values) {
          final String planningFolderId = teamforge.getPlanningFolderId(projectId, value, true);
          filters.add(new FilterElement(filterName, planningFolderId));
        }
      }

      if (fieldType.equals(TrackerFieldData.FIELD_TYPE_MULTISELECT) || fieldType.equals(TrackerFieldData.FIELD_TYPE_MULTISELECT_USER)) {
        for (final String value: values) {
          filters.add(new FilterElement(filterName, value));
        }
      }

      if (fieldType.equals(TrackerFieldData.FIELD_TYPE_SINGLE_SELECT)) {
        final boolean isUserDefined = TrackerFieldData.isUserDefined(filterName);

        final Map<String, TrackerFieldValueData> trackerFieldValueNameDataMap = TrackerFieldValueData.toMap(trackerFieldNameDataMap.get(TrackerFieldData.toInternalName(filterName)).getFieldValues());

        for (final String value: values) {
          if (!isUserDefined) {
            filters.add(new FilterElement(filterName, value));
            continue;
          }

          if (!trackerFieldValueNameDataMap.containsKey(value)) {
            final Exception exception = new Exception("failed to build filter list (invalid field value) [" + filterName + "] [" + value + "]");
            logger.log(Level.INFO, "failed to build filter list (invalid field value) [" + filterName + "] [" + value + "]", exception);
            throw exception;
          }

          final String valueId = trackerFieldValueNameDataMap.get(value).getId();
          filters.add(new FilterElement(filterName, valueId));
        }
      }
    }

    return filters;
  }

  private static final Logger logger = LoggerFactory.getLogger(FilterBuilder.class);
}
