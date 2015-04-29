package io.hsiao.devops.clib.teamforge;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.Logger;
import io.hsiao.devops.clib.logging.Logger.Level;
import io.hsiao.devops.clib.logging.LoggerFactory;
import io.hsiao.devops.clib.utils.CommonUtils;
import io.hsiao.devops.clib.utils.FileUtils;
import io.hsiao.devops.clib.utils.ZipUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.rmi.RemoteException;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

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
import com.collabnet.ce.soap60.webservices.frs.IFrsAppSoap;
import com.collabnet.ce.soap60.webservices.frs.PackageSoapDO;
import com.collabnet.ce.soap60.webservices.frs.PackageSoapList;
import com.collabnet.ce.soap60.webservices.frs.PackageSoapRow;
import com.collabnet.ce.soap60.webservices.frs.ReleaseSoapDO;
import com.collabnet.ce.soap60.webservices.frs.ReleaseSoapList;
import com.collabnet.ce.soap60.webservices.frs.ReleaseSoapRow;
import com.collabnet.ce.soap60.webservices.scm.Commit2SoapDO;
import com.collabnet.ce.soap60.webservices.scm.IScmAppSoap;
import com.collabnet.ce.soap60.webservices.tracker.ArtifactSoapDO;
import com.collabnet.ce.soap60.webservices.tracker.ArtifactSoapList;
import com.collabnet.ce.soap60.webservices.tracker.ArtifactSoapRow;
import com.collabnet.ce.soap60.webservices.tracker.ITrackerAppSoap;

public final class Teamforge {
  public Teamforge(final String serverUrl, final int timeoutMs) throws Exception {
    if (serverUrl == null) {
      throw new RuntimeException("argument 'serverUrl' is null");
    }

    this.serverUrl = serverUrl;

    try {
      cemainSoap = (ICollabNetSoap) ClientSoapStubFactory.getSoapStub(ICollabNetSoap.class, serverUrl, timeoutMs);
      fileStorageAppSoap = (IFileStorageAppSoap) ClientSoapStubFactory.getSoapStub(IFileStorageAppSoap.class, serverUrl, timeoutMs);
      frsAppSoap = (IFrsAppSoap) ClientSoapStubFactory.getSoapStub(IFrsAppSoap.class, serverUrl, timeoutMs);
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
      throw new RuntimeException("argument 'username' is null");
    }

    if (password == null) {
      throw new RuntimeException("argument 'password' is null");
    }

    try {
      this.username = username;
      sessionKey = cemainSoap.login(username, password);
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to login [" + username + "]");
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
      final Exception exception = new Exception("failed to logoff [" + username + "]");
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
      throw new RuntimeException("argument 'username' is null");
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
      throw new RuntimeException("argument 'userData' is null");
    }

    try {
      cemainSoap.setUserData(sessionKey, userData.getUserSoapDO());
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to set user data [" + userData.getUserName() + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to set user data [" + userData.getUserName() + "]", exception);
      throw exception;
    }
  }

  public String getProjectId(final String name) throws Exception {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    final List<ProjectElement> projectList = getProjectList(false);
    for (final ProjectElement projectElement: projectList) {
      if (projectElement.getTitle().equals(name)) {
        return projectElement.getId();
      }
    }

    final Exception exception = new Exception("failed to get project id [" + name + "]");
    logger.log(Level.INFO, "failed to get project id [" + name + "]", exception);
    throw exception;
  }

  public long getProjectDiskUsage(final String name) throws Exception {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    final String projectId = getProjectId(name);

    try {
      return cemainSoap.getProjectDiskUsage(sessionKey, projectId);
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get project disk usage [" + name + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get project disk usage [" + name + "]", exception);
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
      throw new RuntimeException("argument 'artifactId' is null");
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
      throw new RuntimeException("argument 'artifactData' is null");
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
      throw new RuntimeException("argument 'trackerId' is null");
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
      throw new RuntimeException("argument 'objectId' is null");
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
      throw new RuntimeException("argument 'objectId' is null");
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
      throw new RuntimeException("argument 'artifactId' is null");
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
      throw new RuntimeException("argument 'commitId' is null");
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
      throw new RuntimeException("argument 'objectId' is null");
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
      throw new RuntimeException("argument 'username' is null");
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

  public String getPackageId(final String projectId, final String name) throws Exception {
    if (projectId == null) {
      throw new RuntimeException("argument 'projectId' is null");
    }

    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    final List<PackageElement> packageList = getPackageList(projectId);
    for (final PackageElement packageElement: packageList) {
      if (packageElement.getTitle().equals(name)) {
        return packageElement.getId();
      }
    }

    final Exception exception = new Exception("failed to get package id [" + projectId + "] [" + name + "]");
    logger.log(Level.INFO, "failed to get package id [" + projectId + "] [" + name + "]", exception);
    throw exception;
  }

  public List<PackageElement> getPackageList(final String projectId) throws Exception {
    if (projectId == null) {
      throw new RuntimeException("argument 'projectId' is null");
    }

    try {
      final PackageSoapList packageSoapList = frsAppSoap.getPackageList(sessionKey, projectId);
      final PackageSoapRow[] packageSoapRows = packageSoapList.getDataRows();

      final List<PackageElement> packageList = new LinkedList<>();
      for (final PackageSoapRow packageSoapRow: packageSoapRows) {
        packageList.add(new PackageElement(packageSoapRow));
      }

      return packageList;
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get package list [" + projectId + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get package list [" + projectId + "]", exception);
      throw exception;
    }
  }

  public PackageData getPackageData(final String packageId) throws Exception {
    if (packageId == null) {
      throw new RuntimeException("argument 'packageId' is null");
    }

    try {
      final PackageSoapDO packageSoapDO = frsAppSoap.getPackageData(sessionKey, packageId);
      return new PackageData(packageSoapDO);
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get package data [" + packageId + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get package data [" + packageId + "]", exception);
      throw exception;
    }
  }

  public void setPackageData(final PackageData packageData) throws Exception {
    if (packageData == null) {
      throw new RuntimeException("argument 'packageData' is null");
    }

    try {
      frsAppSoap.setPackageData(sessionKey, packageData.getPackageSoapDO());
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to set package data [" + packageData.getTitle() + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to set package data [" + packageData.getTitle() + "]", exception);
      throw exception;
    }
  }

  public String getReleaseId(final String packageId, final String name) throws Exception {
    if (packageId == null) {
      throw new RuntimeException("argument 'packageId' is null");
    }

    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    final List<ReleaseElement> releaseList = getReleaseList(packageId);
    for (final ReleaseElement releaseElement: releaseList) {
      if (releaseElement.getTitle().equals(name)) {
        return releaseElement.getId();
      }
    }

    final Exception exception = new Exception("failed to get release id [" + packageId + "] [" + name + "]");
    logger.log(Level.INFO, "failed to get release id [" + packageId + "] [" + name + "]", exception);
    throw exception;
  }

  public List<ReleaseElement> getReleaseList(final String packageId) throws Exception {
    if (packageId == null) {
      throw new RuntimeException("argument 'packageId' is null");
    }

    try {
      final ReleaseSoapList releaseSoapList = frsAppSoap.getReleaseList(sessionKey, packageId);
      final ReleaseSoapRow[] releaseSoapRows = releaseSoapList.getDataRows();

      final List<ReleaseElement> releaseList = new LinkedList<>();
      for (final ReleaseSoapRow releaseSoapRow: releaseSoapRows) {
        releaseList.add(new ReleaseElement(releaseSoapRow));
      }

      return releaseList;
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get release list [" + packageId + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get release list [" + packageId + "]", exception);
      throw exception;
    }
  }

  public ReleaseData getReleaseData(final String releaseId) throws Exception {
    if (releaseId == null) {
      throw new RuntimeException("argument 'releaseId' is null");
    }

    try {
      final ReleaseSoapDO releaseSoapDO = frsAppSoap.getReleaseData(sessionKey, releaseId);
      return new ReleaseData(releaseSoapDO);
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get release data [" + releaseId + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get release data [" + releaseId + "]", exception);
      throw exception;
    }
  }

  public void setReleaseData(final ReleaseData releaseData) throws Exception {
    if (releaseData == null) {
      throw new RuntimeException("argument 'releaseData' is null");
    }

    try {
      frsAppSoap.setReleaseData(sessionKey, releaseData.getReleaseSoapDO());
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to set release data [" + releaseData.getTitle() + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to set release data [" + releaseData.getTitle() + "]", exception);
      throw exception;
    }
  }

  public List<ArtifactElement> getArtifactListReportedInRelease(final String releaseId) throws Exception {
    if (releaseId == null) {
      throw new RuntimeException("argument 'releaseId' is null");
    }

    try {
      final ArtifactSoapList artifactSoapList = frsAppSoap.getArtifactListReportedInRelease(sessionKey, releaseId);
      final ArtifactSoapRow[] artifactSoapRows = artifactSoapList.getDataRows();

      final List<ArtifactElement> artifactList = new LinkedList<>();
      for (final ArtifactSoapRow artifactSoapRow: artifactSoapRows) {
        artifactList.add(new ArtifactElement(artifactSoapRow));
      }

      return artifactList;
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get artifact list reported in release [" + releaseId + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get artifact list reported in release [" + releaseId + "]", exception);
      throw exception;
    }
  }

  public List<ArtifactElement> getArtifactListResolvedInRelease(final String releaseId) throws Exception {
    if (releaseId == null) {
      throw new RuntimeException("argument 'releaseId' is null");
    }

    try {
      final ArtifactSoapList artifactSoapList = frsAppSoap.getArtifactListResolvedInRelease(sessionKey, releaseId);
      final ArtifactSoapRow[] artifactSoapRows = artifactSoapList.getDataRows();

      final List<ArtifactElement> artifactList = new LinkedList<>();
      for (final ArtifactSoapRow artifactSoapRow: artifactSoapRows) {
        artifactList.add(new ArtifactElement(artifactSoapRow));
      }

      return artifactList;
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to get artifact list resolved in release [" + releaseId + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to get artifact list resolved in release [" + releaseId + "]", exception);
      throw exception;
    }
  }

  public List<AttachmentElement> listAttachments(final String objectId) throws Exception {
    if (objectId == null) {
      throw new RuntimeException("argument 'objectId' is null");
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

  public void downloadAttachments(final String objectId, final String url, final boolean verbose,
    final boolean compress, final boolean compressEmpty) throws Exception {
    if (objectId == null) {
      throw new RuntimeException("argument 'objectId' is null");
    }

    if (url == null) {
      throw new RuntimeException("argument 'url' is null");
    }

    final File folder = new File(url + File.separator + objectId);
    FileUtils.rmdir(folder, false);
    FileUtils.mkdir(folder);

    final List<AttachmentElement> attachmentList = listAttachments(objectId);
    for (final AttachmentElement attachment: attachmentList) {
      final String filename = attachment.getFileName();
      final String rawFileId = attachment.getRawFileId();

      if (verbose) {
        logger.log(Level.INFO, "downloading attachment [" + objectId + "] [" + filename + "]");
      }

      final File file = new File(folder.toString() + File.separator + filename);
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
      ZipUtils.pack(folder, new File(folder.toString() + ".zip"), verbose, compressEmpty);
      FileUtils.rmdir(folder, false);
    }
  }

  public void uploadAttachments(final File source, final String objectId, final String comment, final boolean verbose) throws Exception {
    if (source == null) {
      throw new RuntimeException("argument 'source' is null");
    }

    if (objectId == null) {
      throw new RuntimeException("argument 'objectId' is null");
    }

    try {
      Files.walkFileTree(source.toPath(), EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
        new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            try {
              if (verbose) {
                logger.log(Level.INFO, "uploading attachment [" + objectId + "] [" + file + "]");
              }

              final DataHandler handler = new DataHandler(new FileDataSource(file.toFile()));
              final String storedFileId = uploadFile(handler);

              final ArtifactData artifactData = getArtifactData(objectId);
              setArtifactData(artifactData, comment, handler.getName(), handler.getContentType(), storedFileId);
            }
            catch (Exception exception) {
              logger.log(Level.INFO, "failed to upload attachment [" + objectId + "] [" + file + "]", exception);
              CommonUtils.<RuntimeException>throwAs(exception);
            }

            return FileVisitResult.CONTINUE;
          }
      });
    }
    catch (IOException ex) {
      final Exception exception = new Exception("failed to upload attachments [" + source + "] for [" + objectId + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to upload attachments [" + source + "] for [" + objectId + "]", exception);
      throw exception;
    }
  }

  public String uploadFile(final DataHandler handler) throws Exception {
    if (handler == null) {
      throw new RuntimeException("argument 'handler' is null");
    }

    try {
      final String storedFileId = fileStorageAppSoap.uploadFile(sessionKey, handler);
      logger.log(Level.INFO, "uploaded file [" + handler.getName() + "] [" + handler.getContentType() + "] [" + storedFileId + "]");
      return storedFileId;
    }
    catch (RemoteException ex) {
      final Exception exception = new Exception("failed to upload file [" + handler.getName() + "] [" + handler.getContentType() + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to upload file [" + handler.getName() + "] [" + handler.getContentType() + "]", exception);
      throw exception;
    }
  }

  private static final Logger logger = LoggerFactory.getLogger(Teamforge.class);

  private final String serverUrl;
  private final ICollabNetSoap cemainSoap;
  private final IFileStorageAppSoap fileStorageAppSoap;
  private final IFrsAppSoap frsAppSoap;
  private final IScmAppSoap scmAppSoap;
  private final ITrackerAppSoap trackerAppSoap;
  private String username;
  private String sessionKey;
}
