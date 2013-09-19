
package com.google.cloud.demo.model.nosql;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.cloud.demo.model.Comment;

/**
 * Comment entity for NoSQL.

 */
public class CommentNoSql extends DemoEntityNoSql implements Comment {
  public static final String FIELD_NAME_PHOTO_ID = "photoId";
  public static final String FIELD_NAME_PHOTO_OWNER_ID = "ownerId";
  public static final String FIELD_NAME_TIMESTAMP = "timestamp";
  public static final String FIELD_NAME_CONTENT = "content";
  public static final String FIELD_NAME_COMMENT_OWNER_NAME = "owner";

  public CommentNoSql(Entity entity) {
    super(entity);
  }

  public CommentNoSql(Key parentKey, String kind) {
    super(parentKey, kind);
  }

  public static final String getKind() {
    return Comment.class.getSimpleName();
  }

  @Override
  public String getContent() {
    return (String) entity.getProperty(FIELD_NAME_CONTENT);
  }

  @Override
  public void setContent(String content) {
    entity.setProperty(FIELD_NAME_CONTENT, content);
  }

  @Override
  public long getTimestamp() {
    Long timestamp = (Long) entity.getProperty(FIELD_NAME_TIMESTAMP);
    return timestamp == null ? 0 : timestamp;
  }

  @Override
  public void setTimestamp(long timestamp) {
    entity.setProperty(FIELD_NAME_TIMESTAMP, timestamp);
  }

  @Override
  public long getPhotoId() {
    Long photoId = (Long) entity.getProperty(FIELD_NAME_PHOTO_ID);
    return photoId == null ? 0 : photoId;
  }

  @Override
  public void setPhotoId(long photoId) {
    entity.setProperty(FIELD_NAME_PHOTO_ID, photoId);
  }

  @Override
  public String getPhotoOwnerId() {
    return (String) entity.getProperty(FIELD_NAME_PHOTO_OWNER_ID);
  }

  @Override
  public void setPhotoOwnerId(String owner) {
    entity.setProperty(FIELD_NAME_PHOTO_OWNER_ID, owner);
  }

  @Override
  public String getCommentOwnerName() {
    return (String) entity.getProperty(FIELD_NAME_COMMENT_OWNER_NAME);
  }

  @Override
  public void setCommentOwnerName(String owner) {
    entity.setProperty(FIELD_NAME_COMMENT_OWNER_NAME, owner);
  }

  @Override
  public Long getId() {
    return entity.getKey().getId();
  }

  @Override
  public String getCommentOwnerId() {
    return entity.getParent().getName();
  }
}
