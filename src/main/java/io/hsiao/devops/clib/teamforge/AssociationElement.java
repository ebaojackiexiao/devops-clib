package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.cemain.AssociationSoapRow;

public final class AssociationElement {
  AssociationElement(final AssociationSoapRow associationSoapRow) {
    if (associationSoapRow == null) {
      throw new RuntimeException("argument 'associationSoapRow' is null");
    }

    this.associationSoapRow = associationSoapRow;
  }

  public String getCreatedBy() {
    return associationSoapRow.getCreatedBy();
  }

  public String getCreatorFullName() {
    return associationSoapRow.getCreatorFullName();
  }

  public Date getDateCreated() {
    return associationSoapRow.getDateCreated();
  }

  public Date getDateLastModified() {
    return associationSoapRow.getDateLastModified();
  }

  public String getDescription() {
    return associationSoapRow.getDescription();
  }

  public String getOriginId() {
    return associationSoapRow.getOriginId();
  }

  public String getOriginTitle() {
    return associationSoapRow.getOriginTitle();
  }

  public int getOriginVersion() {
    return associationSoapRow.getOriginVersion();
  }

  public String getTargetId() {
    return associationSoapRow.getTargetId();
  }

  public String getTargetTitle() {
    return associationSoapRow.getTargetTitle();
  }

  public int getTargetVersion() {
    return associationSoapRow.getTargetVersion();
  }

  private final AssociationSoapRow associationSoapRow;
}
