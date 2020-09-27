package yoshikit1996.domain.model

abstract sealed class SqliteType(value: String) {
  override def toString: String = value
}

object SqliteType {
  case object SText extends SqliteType("TEXT")
  case object SReal extends SqliteType("REAL")
}