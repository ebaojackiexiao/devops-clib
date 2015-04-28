package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.Exception;

import io.hsiao.devops.clib.logging.Logger;
import io.hsiao.devops.clib.logging.Logger.Level;
import io.hsiao.devops.clib.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.collabnet.ce.soap60.types.SoapFieldValues;

final class ArtifactFieldValues {
  ArtifactFieldValues(final SoapFieldValues soapFieldValues) throws Exception {
    if (soapFieldValues == null) {
      throw new Exception("argument 'soapFieldValues' is null");
    }

    this.soapFieldValues = soapFieldValues;
    soapFieldsNameIndexMap = initSoapFieldsNameIndexMap();
  }

  private Map<String, Object> initSoapFieldsNameIndexMap() throws Exception {
    final Map<String, Object> map = new HashMap<>();

    final String[] names = soapFieldValues.getNames();

    for (int idx = 0; idx < names.length; ++idx) {
      final String name = names[idx];

      if (!map.containsKey(name)) {
        map.put(name, idx);
      }
      else {
        if (map.get(name) instanceof List<?>) {
          @SuppressWarnings("unchecked")
          final List<Integer> mapValue = (List<Integer>) map.get(name);
          mapValue.add(idx);
        }
        else if (map.get(name) instanceof Integer) {
          final List<Integer> mapValue = new LinkedList<>();
          mapValue.add((Integer) map.get(name));
          mapValue.add(idx);
          map.put(name, mapValue);
        }
        else {
          final Exception exception = new Exception("invalid map value, only Integer or List<Integer> are allowed, found [" + map.get(name).getClass().getName() + "]");
          logger.log(Level.INFO, "invalid map value, only Integer or List<Integer> are allowed, found [" + map.get(name).getClass().getName() + "]", exception);
          throw exception;
        }
      }
    }

    return map;
  }

  SoapFieldValues getSoapFieldValues() {
    return soapFieldValues;
  }

  List<String> getFieldNames() {
    return new LinkedList<>(soapFieldsNameIndexMap.keySet());
  }

  Object getFieldValue(final String name, final String type) throws Exception {
    if (name == null) {
      throw new Exception("argument 'name' is null");
    }

    if (type == null) {
      throw new Exception("argument 'type' is null");
    }

    if (!soapFieldsNameIndexMap.containsKey(name)) {
      final Exception exception = new Exception("failed to get field value (field not found) [" + name + "]");
      logger.log(Level.INFO, "failed to get field value (field not found) [" + name + "]", exception);
      throw exception;
    }

    final Object[] values = soapFieldValues.getValues();

    final Object mapValue = soapFieldsNameIndexMap.get(name);
    if (mapValue instanceof Integer) {
      final int index = (Integer) mapValue;

      if (type.equals(TrackerFieldData.FIELD_TYPE_MULTISELECT) || type.equals(TrackerFieldData.FIELD_TYPE_MULTISELECT_USER)) {
        final List<Object> fieldValues = new LinkedList<>();
        if (values[index] != null) {
          fieldValues.add(values[index]);
        }
        return fieldValues;
      }

      return values[index];
    }
    else if (mapValue instanceof List<?>) {
      @SuppressWarnings("unchecked")
      final List<Integer> indexes = (List<Integer>) mapValue;
      final List<Object> fieldValues = new LinkedList<>();
      for (final Integer index: indexes) {
        fieldValues.add(values[index]);
      }
      return fieldValues;
    }
    else {
      final Exception exception = new Exception("invalid map value, only Integer or List<Integer> are allowed, found [" + mapValue.getClass().getName() + "]");
      logger.log(Level.INFO, "invalid map value, only Integer or List<Integer> are allowed, found [" + mapValue.getClass().getName() + "]", exception);
      throw exception;
    }
  }

  String getFieldValueType(final String name) throws Exception {
    if (name == null) {
      throw new Exception("argument 'name' is null");
    }

    if (!soapFieldsNameIndexMap.containsKey(name)) {
      final Exception exception = new Exception("failed to get field value type (field not found) [" + name + "]");
      logger.log(Level.INFO, "failed to get field value type (field not found) [" + name + "]", exception);
      throw exception;
    }

    final String[] types = soapFieldValues.getTypes();

    final Object mapValue = soapFieldsNameIndexMap.get(name);
    if (mapValue instanceof Integer) {
      final int index = (Integer) mapValue;
      return types[index];
    }
    else if (mapValue instanceof List<?>) {
      @SuppressWarnings("unchecked")
      final List<Integer> indexes = (List<Integer>) mapValue;
      return types[indexes.get(0)];
    }
    else {
      final Exception exception = new Exception("invalid map value, only Integer or List<Integer> are allowed, found [" + mapValue.getClass().getName() + "]");
      logger.log(Level.INFO, "invalid map value, only Integer or List<Integer> are allowed, found [" + mapValue.getClass().getName() + "]", exception);
      throw exception;
    }
  }

  void setFieldValue(final String name, final String type, final Object value) throws Exception {
    if (name == null) {
      throw new Exception("argument 'name' is null");
    }

    if (type == null) {
      throw new Exception("argument 'type' is null");
    }

    if (!soapFieldsNameIndexMap.containsKey(name)) {
      final Exception exception = new Exception("failed to set field value (field not found) [" + name + "]");
      logger.log(Level.INFO, "failed to set field value (field not found) [" + name + "]", exception);
      throw exception;
    }

    String[] names = soapFieldValues.getNames();
    String[] types = soapFieldValues.getTypes();
    Object[] values = soapFieldValues.getValues();

    boolean isMapOutdated = false;

    if (type.equals(TrackerFieldData.FIELD_TYPE_MULTISELECT) || type.equals(TrackerFieldData.FIELD_TYPE_MULTISELECT_USER)) {
      final List<String> nameList = new ArrayList<>();
      final List<String> typeList = new ArrayList<>();
      final List<Object> valueList = new ArrayList<>();

      String valueType = TrackerFieldData.FIELD_VALUE_TYPE_STRING;

      for (int idx = 0; idx < names.length; ++idx) {
        if (names[idx].equals(name)) {
          valueType = types[idx];
          continue;
        }
        nameList.add(names[idx]);
        typeList.add(types[idx]);
        valueList.add(values[idx]);
      }

      if (value == null) {
        nameList.add(name);
        typeList.add(valueType);
        valueList.add(null);
      }
      else if (value instanceof List<?>) {
        @SuppressWarnings("unchecked")
        final List<Object> newValues = (List<Object>) value;

        if (newValues.isEmpty()) {
          nameList.add(name);
          typeList.add(valueType);
          valueList.add(null);
        }

        for (final Object newValue: newValues) {
          nameList.add(name);
          typeList.add(valueType);
          valueList.add(newValue);
        }
      }
      else {
        final Exception exception = new Exception("invalid field value, only List<Object> or null are allowed, found [" + value.getClass().getName() + "]");
        logger.log(Level.INFO, "invalid field value, only List<Object> or null are allowed, found [" + value.getClass().getName() + "]", exception);
        throw exception;
      }

      names = nameList.toArray(new String[nameList.size()]);
      types = typeList.toArray(new String[typeList.size()]);
      values = valueList.toArray();

      isMapOutdated = true;
    }
    else {
      final Object mapValue = soapFieldsNameIndexMap.get(name);
      if (mapValue instanceof Integer) {
        final int index = (Integer) mapValue;
        values[index] = value;
      }
      else {
        final Exception exception = new Exception("invalid map value, only Integer is allowed, found [" + mapValue.getClass().getName() + "]");
        logger.log(Level.INFO, "invalid map value, only Integer is allowed, found [" + mapValue.getClass().getName() + "]", exception);
        throw exception;
      }

      isMapOutdated = false;
    }

    soapFieldValues.setNames(names);
    soapFieldValues.setTypes(types);
    soapFieldValues.setValues(values);

    if (isMapOutdated) {
      soapFieldsNameIndexMap = initSoapFieldsNameIndexMap();
    }
  }

  private static final Logger logger = LoggerFactory.getLogger(ArtifactFieldValues.class);

  private final SoapFieldValues soapFieldValues;
  private Map<String, Object> soapFieldsNameIndexMap;
}
