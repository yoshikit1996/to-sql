package tosqlite.domain.model

import org.specs2.mutable.Specification

class SqliteSchemaTest extends Specification {
  "#toSql" >> {
    val schema = SqliteSchema(Map("name" -> SqliteType.SText, "age" -> SqliteType.SReal))
    schema.toCreateStatement("user") === """CREATE TABLE user(name TEXT,age REAL)"""
  }
}
