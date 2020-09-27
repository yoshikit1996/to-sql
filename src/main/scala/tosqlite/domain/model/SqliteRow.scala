package tosqlite.domain.model

case class SqliteRow(values: Seq[SqliteValue]) {
  def toInsertStatement(tableName: String): String =
    s"INSERT INTO $tableName VALUES(${values.map(_.value.toString).mkString(",")})"
}