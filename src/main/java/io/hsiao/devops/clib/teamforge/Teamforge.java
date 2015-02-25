package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.logging.LoggerProxy;
import io.hsiao.devops.clib.utils.ZipUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;

import com.collabnet.ce.soap60.types.SoapFilter;
import com.collabnet.ce.soap60.webservices.ClientSoapStubFactory;
import com.collabnet.ce.soap60.webservices.cemain.AssociationSoapList;
import com.collabnet.ce.soap60.webservices.cemain.AssociationSoapRow;
import com.collabnet.ce.soap60.webservices.cemain.AttachmentSoapList;
import com.collabnet.ce.soap60.webservices.cemain.AttachmentSoapRow;
import com.collabnet.ce.soap60.webservices.cemain.CommentSoapList;
import com.collabnet.ce.soap60.webservices.cemain.CommentSoapRow;
import com.collabnet.ce.soap60.webservices.cemain.ICollabNetSoap;
import com.collabnet.ce.soap60.webservices.cemain.ProjectSoapList;
import com.collabnet.ce.soap60.webservices.cemain.ProjectSoapRow;
import com.collabnet.ce.soap60.webservices.cemain.TrackerFieldSoapDO;
import com.collabnet.ce.soap60.webservices.cemain.UserSoapDO;
import com.collabnet.ce.soap60.webservices.cemain.UserSoapList;
import com.collabnet.ce.soap60.webservices.cemain.UserSoapRow;
import com.collabnet.ce.soap60.webservices.filestorage.IFileStorageAppSoap;
import com.collabnet.ce.soap60.webservices.scm.Commit2SoapDO;
import com.collabnet.ce.soap60.webservices.scm.IScmAppSoap;
import com.collabnet.ce.soap60.webservices.tracker.ArtifactSoapDO;
import com.collabnet.ce.soap60.webservices.tracker.ITrackerAppSoap;

public final class Teamforge {
  public Teamforge(final String serverUrl, final int timeoutMs) throws Exception {
    if (serverUrl == null) {
      throw new Exception("argument 'serverUrl' is null");
    }

    this.serverUrl = serverUrl;

    try {
      cemainSoap = (ICollabNetSoap) ClientSoapStubFactory.getSoapStub(ICollabNetSoap.class, serverUrl, timeoutMs);
      fileStorageAppSoap = (IFileStorageAppSoap) ClientSoapStubFactory.getSoapStub(IFileStorageAppSoap.class, serverUrl, timeoutMs);
      scmAppSoap = (IScmAppSoap) ClientSoapStubFactory.getSoapStub(IScmAppSoap.class, serverUrl, timeoutMs);
      trackerAppSoap = (ITrackerAppSoap) ClientSoapStubFactory.getSoapStub(ITrackerAppSoap.class, serverUrl, timeoutMs);
    }
    catch (Throwable ex) {
      final Exception exception = new Exception("failed to instantiate soap objects");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to instantiate soap objects", exception);
      throw exception;
    }
  }

  public String getServerUrl() {
    return serverUrl;
  }

  public void login(final String username, final String password) throws Exception {
    if (username == null) {
      throw new Exception("argument 'username' is null");
    }

    if (password == null) {
      throw new Exception("argument 'password' is null");
    }

    try {
      this.username = username;
      sessionKey = cemainSoap.login(username, password);
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to login");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to login [" + username + "]", exception);
      throw exception;
    }
  }

  public void logoff() throws Exception {
    try {
      cemainSoap.logoff(username, sessionKey);
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to logoff");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to logoff [" + username + "]", exception);
      throw exception;
    }
  }

  public String getApiVersion() throws Exception {
    try {
      return cemainSoap.getApiVersion();
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get API version");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get API version", exception);
      throw exception;
    }
  }

  public String getVersion() throws Exception {
    try {
      return cemainSoap.getVersion(sessionKey);
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get version");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get version", exception);
      throw exception;
    }
  }

  public String getBroadCastMessage() throws Exception {
    try {
      return cemainSoap.getBroadCastMessage(sessionKey);
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get broadcast message");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get broadcast message", exception);
      throw exception;
    }
  }

  public UserData getUserData(final String username) throws Exception {
    if (username == null) {
      throw new Exception("argument 'username' is null");
    }

    try {
      final UserSoapDO userSoapDO = cemainSoap.getUserData(sessionKey, username);
      return new UserData(userSoapDO);
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get user data [" + username + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get user data [" + username + "]", exception);
      throw exception;
    }
  }

  public void setUserData(final UserData userData) throws Exception {
    if (userData == null) {
      throw new Exception("argument 'userData' is null");
    }

    try {
      cemainSoap.setUserData(sessionKey, userData.getUserSoapDO());
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to set user data");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to set user data [" + userData.getUserName() + "]", exception);
      throw exception;
    }
  }

  private String getProjectIdByName(final String projectName) throws Exception {
    if (projectName == null) {
      throw new Exception("argument 'projectName' is null");
    }

    try {
      final ProjectSoapList projectSoapList = cemainSoap.getProjectList(sessionKey, false);
      final ProjectSoapRow[] projectSoapRows = projectSoapList.getDataRows();
      for (final ProjectSoapRow projectSoapRow: projectSoapRows) {
        if (projectSoapRow.getTitle().equals(projectName)) {
          return projectSoapRow.getId();
        }
      }
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get project Id [" + projectName + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get project Id [" + projectName + "]", exception);
      throw exception;
    }

    final Exception exception = new Exception("invalid project name (project not found) [" + projectName + "]");
    logger.log(Level.INFO, "invalid project name (project not found) [" + projectName + "]", exception);
    throw exception;
  }

  public long getProjectDiskUsage(final String projectName) throws Exception {
    if (projectName == null) {
      throw new Exception("argument 'projectName' is null");
    }

    final String projectId = getProjectIdByName(projectName);

    try {
      final long projectDiskUsage = cemainSoap.getProjectDiskUsage(sessionKey, projectId);
      return projectDiskUsage;
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get project disk usage [" + projectName + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get project disk usage [" + projectName + "]", exception);
      throw exception;
    }
  }

  public List<UserElement> getUserList(final String filterKey, final String filterValue) throws Exception {
    SoapFilter soapFilter = new SoapFilter();

    if (filterKey == null || filterValue == null) {
      soapFilter = null;
    }
    else {
      soapFilter.setName(filterKey);
      soapFilter.setValue(filterValue);
    }

    try {
      final UserSoapList userSoapList = cemainSoap.getUserList(sessionKey, soapFilter);
      final UserSoapRow[] userSoapRows = userSoapList.getDataRows();

      final List<UserElement> userList = new LinkedList<>();
      for (final UserSoapRow userSoapRow: userSoapRows) {
        userList.add(new UserElement(userSoapRow));
      }

      return userList;
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get user list");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get user list", exception);
      throw exception;
    }
  }

  public List<ProjectElement> getProjectList(final boolean fetchHierarchyPath) throws Exception {
    try {
      final ProjectSoapList projectSoapList = cemainSoap.getProjectList(sessionKey, fetchHierarchyPath);
      final ProjectSoapRow[] projectSoapRows = projectSoapList.getDataRows();

      final List<ProjectElement> projectList = new LinkedList<>();
      for (final ProjectSoapRow projectSoapRow: projectSoapRows) {
        projectList.add(new ProjectElement(projectSoapRow));
      }

      return projectList;
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get project list");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get project list", exception);
      throw exception;
    }
  }

  public ArtifactData getArtifactData(final String artifactId) throws Exception {
    if (artifactId == null) {
      throw new Exception("argument 'artifactId' is null");
    }

    try {
      final ArtifactSoapDO artifactSoapDO = trackerAppSoap.getArtifactData(sessionKey, artifactId);
      return new ArtifactData(artifactSoapDO);
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get artifact data [" + artifactId + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get artifact data [" + artifactId + "]", exception);
      throw exception;
    }
  }

  public void setArtifactData(final ArtifactData artifactData, final String comment,
    final String attachmentFileName, final String attachmentMimeType, final String attachmentFileId) throws Exception {
    if (artifactData == null) {
      throw new Exception("argument 'artifactData' is null");
    }

    try {
      final ArtifactSoapDO artifactSoapDO = artifactData.getArtifactSoapDO();
      trackerAppSoap.setArtifactData(sessionKey, artifactSoapDO, comment, attachmentFileName, attachmentMimeType, attachmentFileId);
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to set artifact data [" + artifactData.getId() + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to set artifact data [" + artifactData.getId() + "]", exception);
      throw exception;
    }
  }

  public List<TrackerFieldData> getTrackerFieldDataList(final String trackerId) throws Exception {
    if (trackerId == null) {
      throw new Exception("argument 'trackerId' is null");
    }

    try {
      final TrackerFieldSoapDO[] trackerFieldSoapDOs = trackerAppSoap.getFields(sessionKey, trackerId);

      final List<TrackerFieldData> trackerFieldDataList = new LinkedList<>();
      for (final TrackerFieldSoapDO trackerFieldSoapDO: trackerFieldSoapDOs) {
        trackerFieldDataList.add(new TrackerFieldData(trackerFieldSoapDO));
      }

      return trackerFieldDataList;
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get tracker field data list [" + trackerId + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get tracker field data list [" + trackerId + "]", exception);
      throw exception;
    }
  }

  public List<AssociationElement> getAssociationList(final String objectId) throws Exception {
    if (objectId == null) {
      throw new Exception("argument 'objectId' is null");
    }

    try {
      final AssociationSoapList associationSoapList = cemainSoap.getAssociationList(sessionKey, objectId);
      final AssociationSoapRow[] associationSoapRows = associationSoapList.getDataRows();

      final List<AssociationElement> associationList = new LinkedList<>();
      for (final AssociationSoapRow associationSoapRow: associationSoapRows) {
        associationList.add(new AssociationElement(associationSoapRow));
      }

      return associationList;
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get association list [" + objectId + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get association list [" + objectId + "]", exception);
      throw exception;
    }
  }

  public List<String> getScmAssociationList(final String objectId) throws Exception {
    if (objectId == null) {
      throw new Exception("argument 'objectId' is null");
    }

    final List<String> scmAssociationList = new LinkedList<>();

    final List<AssociationElement> associationList = getAssociationList(objectId);
    for (final AssociationElement associationElement: associationList) {
      if (associationElement.getTargetId().matches("cmmt\\d+")) {
        scmAssociationList.add(associationElement.getTargetId());
      }
    }

    return scmAssociationList;
  }

  public List<ScmFileElement> getScmFileList(final String artifactId) throws Exception {
    if (artifactId == null) {
      throw new Exception("argument 'artifactId' is null");
    }

    final List<ScmFileElement> scmFileList = new LinkedList<>();

    final List<String> scmAssociationList = getScmAssociationList(artifactId);
    for (final String scmAssociationId: scmAssociationList) {
      final CommitData commitData = getCommitData(scmAssociationId);
      scmFileList.addAll(commitData.getFiles());
    }

    return scmFileList;
  }

  public CommitData getCommitData(final String commitId) throws Exception {
    if (commitId == null) {
      throw new Exception("argument 'commitId' is null");
    }

    try {
      final Commit2SoapDO commit2SoapDO = scmAppSoap.getCommitData2(sessionKey, commitId);
      return new CommitData(commit2SoapDO);
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get commit data [" + commitId + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get commit data [" + commitId + "]", exception);
      throw exception;
    }
  }

  public List<CommentElement> getCommentList(final String objectId) throws Exception {
    if (objectId == null) {
      throw new Exception("argument 'objectId' is null");
    }

    try {
      final CommentSoapList commentSoapList = cemainSoap.getCommentList(sessionKey, objectId);
      final CommentSoapRow[] commentSoapRows = commentSoapList.getDataRows();

      final List<CommentElement> commentList = new LinkedList<>();
      for (final CommentSoapRow commentSoapRow: commentSoapRows) {
        commentList.add(new CommentElement(commentSoapRow));
      }

      return commentList;
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get comment list [" + objectId + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get comment list [" + objectId + "]", exception);
      throw exception;
    }
  }

  public List<ProjectElement> getProjectListForUser(final String username,
      final boolean fetchHierarchyPath, final boolean includeGroupMembership) throws Exception {
    if (username == null) {
      throw new Exception("argument 'username' is null");
    }

    try {
      final ProjectSoapList projectSoapList = cemainSoap.getProjectListForUser(sessionKey, username, fetchHierarchyPath, includeGroupMembership);
      final ProjectSoapRow[] projectSoapRows = projectSoapList.getDataRows();

      final List<ProjectElement> projectList = new LinkedList<>();
      for (final ProjectSoapRow projectSoapRow: projectSoapRows) {
        projectList.add(new ProjectElement(projectSoapRow));
      }

      return projectList;
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get project list for user [" + username + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get project list for user [" + username + "]", exception);
      throw exception;
    }
  }

  public List<AttachmentElement> listAttachments(final String objectId) throws Exception {
    if (objectId == null) {
      throw new Exception("argument 'objectId' is null");
    }

    try {
      final AttachmentSoapList attachmentSoapList = cemainSoap.listAttachments(sessionKey, objectId);
      final AttachmentSoapRow[] attachmentSoapRows = attachmentSoapList.getDataRows();

      final List<AttachmentElement> attachmentList = new LinkedList<>();
      for (final AttachmentSoapRow attachmentSoapRow: attachmentSoapRows) {
        attachmentList.add(new AttachmentElement(attachmentSoapRow));
      }

      return attachmentList;
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to list attachments [" + objectId + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to list attachments [" + objectId + "]", exception);
      throw exception;
    }
  }

  public void downloadAttachments(final String objectId, final boolean verbose, final boolean compress, final boolean compressEmpty) throws Exception {
    if (objectId == null) {
      throw new Exception("argument 'objectId' is null");
    }

    final File folder = new File(objectId);
    try {
      org.apache.commons.io.FileUtils.deleteDirectory(folder);
      org.apache.commons.io.FileUtils.forceMkdir(folder);
    }
    catch (java.lang.Exception ex) {
      final Exception exception = new Exception("failed to initialize download directory [" + folder + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to initialize download directory [" + folder + "]", exception);
      throw exception;
    }

    final List<AttachmentElement> attachmentList = listAttachments(objectId);
    for (final AttachmentElement attachment: attachmentList) {
      final String filename = attachment.getFileName();
      final String rawFileId = attachment.getRawFileId();

      if (verbose) {
        LoggerProxy.getGlobal().log(Level.INFO, "downloading attachment [" + objectId + "] [" + filename + "]");
      }
      logger.log(Level.INFO, "downloading attachment [" + objectId + "] [" + filename + "] [" + rawFileId + "]");

      final File file = new File(objectId + File.separator + filename);
      try (final FileOutputStream fos = new FileOutputStream(file)) {
        final DataHandler dataHandler = fileStorageAppSoap.downloadFileDirect(sessionKey, objectId, rawFileId);
        dataHandler.writeTo(fos);
      }
      catch (IOException ex) {
        final Exception exception = new Exception("failed to download attachment [" + objectId + "] [" + filename + "]");
        exception.initCause(ex);
        logger.log(Level.INFO, "failed to download attachment [" + objectId + "] [" + filename + "] [" + rawFileId + "]", exception);
        throw exception;
      }
    }

    if (compress) {
      ZipUtils.pack(folder, new File(objectId + ".zip"), verbose, compressEmpty);
      try {
        org.apache.commons.io.FileUtils.deleteDirectory(folder);
      }
      catch (java.lang.Exception ex) {
        final Exception exception = new Exception("failed to delete temporary download directory [" + folder + "]");
        exception.initCause(ex);
        logger.log(Level.INFO, "failed to delete temporary download directory [" + folder + "]", exception);
        throw exception;
      }
    }
  }

  private final Logger logger = LoggerProxy.getLogger();
  private final String serverUrl;
  private final ICollabNetSoap cemainSoap;
  private final IFileStorageAppSoap fileStorageAppSoap;
  private final IScmAppSoap scmAppSoap;
  private final ITrackerAppSoap trackerAppSoap;
  private String username;
  private String sessionKey;
}
