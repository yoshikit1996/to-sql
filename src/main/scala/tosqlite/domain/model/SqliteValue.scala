package tosqlite.domain.model

case class SqliteValue(columnName: String, value: Any, sqliteType: SqliteType)
