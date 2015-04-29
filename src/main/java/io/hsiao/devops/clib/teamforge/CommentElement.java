package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.RuntimeException;

import java.util.Date;

import com.collabnet.ce.soap60.webservices.cemain.CommentSoapRow;

public final class CommentElement {
  CommentElement(final CommentSoapRow commentSoapRow) {
    if (commentSoapRow == null) {
      throw new RuntimeException("argument 'commentSoapRow' is null");
    }

    this.commentSoapRow = commentSoapRow;
  }

  public String getCreatedBy() {
    return commentSoapRow.getCreatedBy();
  }

  public String getCreatedByFullName() {
    return commentSoapRow.getCreatedByFullname();
  }

  public Date getDateCreated() {
    return commentSoapRow.getDateCreated();
  }

  public String getDescription() {
    return commentSoapRow.getDescription();
  }

  public String getId() {
    return commentSoapRow.getId();
  }

  public String getTransactionId() {
    return commentSoapRow.getTransactionId();
  }

  private final CommentSoapRow commentSoapRow;
}
