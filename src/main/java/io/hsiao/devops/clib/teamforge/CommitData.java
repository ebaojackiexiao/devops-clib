package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.Exception;

import java.util.LinkedList;
import java.util.List;

import com.collabnet.ce.soap60.webservices.scm.Commit2SoapDO;
import com.collabnet.ce.soap60.webservices.scm.ScmFile2SoapList;
import com.collabnet.ce.soap60.webservices.scm.ScmFile2SoapRow;

public final class CommitData {
  CommitData(final Commit2SoapDO commit2SoapDO) throws Exception {
    if (commit2SoapDO == null) {
      throw new Exception("argument 'commit2SoapDO' is null");
    }

    this.commit2SoapDO = commit2SoapDO;
  }

  public String getCommitMessage() {
    return commit2SoapDO.getCommitMessage();
  }

  public String getCreatedByFullName() {
    return commit2SoapDO.getCreatedByFullname();
  }

  public List<ScmFileElement> getFiles() throws Exception {
    final ScmFile2SoapList scmFile2SoapList = commit2SoapDO.getFiles();
    final ScmFile2SoapRow[] scmFile2SoapRows = scmFile2SoapList.getDataRows();

    final List<ScmFileElement> scmFileList = new LinkedList<>();
    for (final ScmFile2SoapRow scmFile2SoapRow: scmFile2SoapRows) {
      scmFileList.add(new ScmFileElement(scmFile2SoapRow));
    }

    return scmFileList;
  }

  private final Commit2SoapDO commit2SoapDO;
}
