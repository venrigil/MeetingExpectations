
package com.google.cloud.demo.model.nosql;

import com.google.cloud.demo.ConfigManager;
import com.google.cloud.demo.model.CommentManager;
import com.google.cloud.demo.model.DemoEntityManagerFactory;
import com.google.cloud.demo.model.DemoUserManager;
import com.google.cloud.demo.model.PhotoManager;

/**
 * Entity manager factory implementation for NoSQL.

 */
public class DemoEntityManagerNoSqlFactory implements DemoEntityManagerFactory {
  private DemoUserManagerNoSql demoUserManager;
  private PhotoManagerNoSql photoManager;
  private CommentManagerNoSql commentManager;
  private boolean initialized;

  @Override
  public PhotoManager getPhotoManager() {
    return photoManager;
  }

  @Override
  public CommentManager getCommentManager() {
    return commentManager;
  }

  @Override
  public DemoUserManager getDemoUserManager() {
    return demoUserManager;
  }


  @Override
  public void init(ConfigManager configManager) {
    if (!initialized) {
      demoUserManager = new DemoUserManagerNoSql();
      photoManager = new PhotoManagerNoSql(demoUserManager);
      commentManager = new CommentManagerNoSql(demoUserManager);
      initialized = true;
    } else {
      throw new IllegalStateException("Should not initialize the factory more than once.");
    }
  }
}
