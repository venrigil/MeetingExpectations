
package com.google.cloud.demo.model.nosql;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.cloud.demo.model.DemoModelException;
import com.google.cloud.demo.model.DemoUser;
import com.google.cloud.demo.model.DemoUserManager;
import com.google.cloud.demo.model.Utils;

/**
 * User manager class for NoSQL.

 */
public class DemoUserManagerNoSql extends DemoEntityManagerNoSql<DemoUser>
    implements DemoUserManager {
  public DemoUserManagerNoSql() {
    super(DemoUser.class);
  }

  public Key createDemoUserKey(String userId) {
    return KeyFactory.createKey(getKind(), userId);
  }

  @Override
  protected DemoUserNoSql fromParentKey(Key parentKey) {
    throw new DemoModelException("Demo User is entity group root, so it cannot have parent key");
  }

  @Override
  protected DemoUserNoSql fromEntity(Entity entity) {
    return new DemoUserNoSql(entity);
  }

  @Override
  public DemoUser getUser(String userId) {
    Utils.assertTrue(userId != null, "userId is null!");
    return getEntity(createDemoUserKey(userId));
  }

  @Override
  public DemoUser newUser(String userId) {
    return new DemoUserNoSql(getKind(), userId);
  }
}
