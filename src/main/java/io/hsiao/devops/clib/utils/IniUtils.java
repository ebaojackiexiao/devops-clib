package io.hsiao.devops.clib.utils;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.logging.LoggerProxy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class IniUtils {
  public IniUtils() {
    iniMap = new LinkedHashMap<>();
  }

  private String escape(final String data) throws Exception {
    if (data == null) {
      throw new Exception("argument 'data' is null");
    }

    String escaped = data;

    if (escaped.startsWith("\"") && escaped.endsWith("\"") && !escaped.endsWith("\\\"")) {
      escaped = escaped.substring(1, escaped.length() - 1);
    }

    if (escaped.startsWith("\"") || escaped.matches("\\A.*[^\\\\]\\\".*\\Z")) {
      final Exception exception = new Exception("failed to load ini source, invalid data [" + data + "]");
      LoggerProxy.getLogger().log(Level.INFO, "failed to load ini source, invalid data [" + data + "]", exception);
      throw exception;
    }

    return escaped.replaceAll("\\\\\"", "\"");
  }

  public void load(final File source, final boolean simpleMode) throws Exception {
    if (source == null) {
      throw new Exception("argument 'source' is null");
    }

    try (final FileInputStream fis = new FileInputStream(source)) {
      load(fis, simpleMode);
    }
    catch (IOException ex) {
      final Exception exception = new Exception("failed to load ini source");
      exception.initCause(ex);
      LoggerProxy.getLogger().log(Level.INFO, "failed to load ini source", exception);
      throw exception;
    }
  }

  public void load(final InputStream source, final boolean simpleMode) throws Exception {
    if (source == null) {
      throw new Exception("argument 'source' is null");
    }

    String majorKey = null;
    String minorKey = null;
    boolean inSection = false;

    final Pattern ptnSection = Pattern.compile("\\A\\s*\\[\\s*([^\"]+?)(?:\\s+\\\"\\s*([^\"]+?)\\s*\\\")?\\s*\\]\\s*\\Z");
    final Scanner sin = new Scanner(source);

    while(sin.hasNextLine()) {
      final String line = sin.nextLine().trim();

      if (line.startsWith(";") || line.isEmpty()) {
        continue;
      }

      final Matcher mtrSection = ptnSection.matcher(line);
      if (mtrSection.matches()) {
        majorKey = mtrSection.group(1);
        minorKey = mtrSection.group(2);

        if (minorKey == null) {
          minorKey = "";
        }

        inSection = true;
      }
      else {
        if (!inSection) {
          final Exception exception = new Exception("failed to load ini source, invalid data [" + line + "]");
          LoggerProxy.getLogger().log(Level.INFO, "failed to load ini source, invalid data [" + line + "]", exception);
          sin.close();
          throw exception;
        }

        final Section section = new Section(majorKey, minorKey);
        final Map<String, String> properties;
        if (iniMap.containsKey(section)) {
          properties = iniMap.get(section);
        }
        else {
          properties = new LinkedHashMap<>();
        }

        if (simpleMode) {
          properties.put(escape(line), "");
        }
        else {
          final Pattern ptnProperty = Pattern.compile("\\A\\s*(.+?)\\s*=\\s*(.+?)\\s*\\Z");
          final Matcher mtrProperty = ptnProperty.matcher(line);

          if (mtrProperty.matches()) {
            final String name = escape(mtrProperty.group(1));
            final String value = escape(mtrProperty.group(2));

            if (!name.isEmpty()) {
              properties.put(name, value);
            }
            else {
              final Exception exception = new Exception("failed to load ini source, invalid data [" + line + "]");
              LoggerProxy.getLogger().log(Level.INFO, "failed to load ini source, invalid data [" + line + "]", exception);
              sin.close();
              throw exception;
            }
          }
          else {
            final Exception exception = new Exception("failed to load ini source, invalid data [" + line + "]");
            LoggerProxy.getLogger().log(Level.INFO, "failed to load ini source, invalid data [" + line + "]", exception);
            sin.close();
            throw exception;
          }
        }

        iniMap.put(section, properties);
      }
    }

    sin.close();

    LoggerProxy.getLogger().log(Level.INFO, iniMap.toString());
  }

  public List<Section> getSections() {
    final List<Section> sections = new LinkedList<>();

    for (final Map.Entry<Section, Map<String, String>> entry: iniMap.entrySet()) {
      sections.add(entry.getKey());
    }

    return sections;
  }

  public String getProperty(final Section section, final String name) throws Exception {
    if (section == null) {
      throw new Exception("argument 'section' is null");
    }

    if (name == null) {
      throw new Exception("argument 'name' is null");
    }

    final Map<String, String> properties = iniMap.get(section);
    if (properties == null) {
      return null;
    }

    return properties.get(name);
  }

  public String getProperty(final String majorKey, final String minorKey, final String name) throws Exception {
    if (majorKey == null) {
      throw new Exception("argument 'majorKey' is null");
    }

    if (minorKey == null) {
      throw new Exception("argument 'minorKey' is null");
    }

    if (name == null) {
      throw new Exception("argument 'name' is null");
    }

    return getProperty(new Section(majorKey, minorKey), name);
  }

  @SuppressWarnings("unchecked")
  public Map<String, String> getProperties(final Section section) throws Exception {
    if (section == null) {
      throw new Exception("argument 'section' is null");
    }

    if (iniMap.containsKey(section)) {
      return (Map<String, String>) ((LinkedHashMap<String, String>) iniMap.get(section)).clone();
    }

    return new LinkedHashMap<>();
  }

  public Map<String, String> getProperties(final String majorKey, final String minorKey) throws Exception {
    if (majorKey == null) {
      throw new Exception("argument 'majorKey' is null");
    }

    if (minorKey == null) {
      throw new Exception("argument 'minorKey' is null");
    }

    return getProperties(new Section(majorKey, minorKey));
  }

  public static final class Section {
    public Section(final String majorKey, final String minorKey) throws Exception {
      if (majorKey == null) {
        throw new Exception("argument 'majorKey' is null");
      }

      if (minorKey == null) {
        throw new Exception("argument 'minorKey' is null");
      }

      this.majorKey = majorKey;
      this.minorKey = minorKey;
    }

    public String getMajorKey() {
      return majorKey;
    }

    public String getMinorKey() {
      return minorKey;
    }

    @Override
    public boolean equals(final Object otherObject) {
      if (this == otherObject) {
        return true;
      }

      if (otherObject == null) {
        return false;
      }

      if (getClass() != otherObject.getClass()) {
        return false;
      }

      final Section other = (Section) otherObject;

      return Objects.equals(majorKey, other.getMajorKey()) && Objects.equals(minorKey, other.getMinorKey());
    }

    @Override
    public int hashCode() {
      return Objects.hash(majorKey, minorKey);
    }

    private final String majorKey;
    private final String minorKey;
  }

  private final Map<Section, Map<String, String>> iniMap;
}
