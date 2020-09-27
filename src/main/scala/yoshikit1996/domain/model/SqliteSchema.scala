package yoshikit1996.domain.model

/**
 * カラム名と型のマップを保持するクラス
 * @param value
 */
case class SqliteSchema(value: Map[String, SqliteType]) {
  /**
   * SQL化するメソッドをドメインモデルに持たせているが、
   * ドメインモデルで
   *
   * @param tableName
   * @return
   */
  def toCreateStatement(tableName: String): String = {
    val cols = value.map {
      case (columnName, sqliteType) =>
        s"$columnName ${sqliteType.toString}"
    }.mkString(",")

    s"CREATE TABLE $tableName($cols)"
  }
}
