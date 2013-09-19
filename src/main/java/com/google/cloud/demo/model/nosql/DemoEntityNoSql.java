
package com.google.cloud.demo.model.nosql;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

/**
 * Base entity class for NoSQL.

 */
public abstract class DemoEntityNoSql {
  protected final Entity entity;

  protected DemoEntityNoSql(Entity entity) {
    this.entity = entity;
  }

  protected DemoEntityNoSql(Key parentKey, String kind) {
    this.entity = new Entity(kind, parentKey);
  }

  protected DemoEntityNoSql(String kind, String keyName) {
    this.entity = new Entity(kind, keyName);
  }

  public Entity getEntity() {
    return entity;
  }
}
