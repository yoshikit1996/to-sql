package yoshikit1996.application

import yoshikit1996.domain.model.{SqliteRow, SqliteSchema}

trait Dao[F[_]] {
  def createTable(schema: SqliteSchema): F[Unit]
  def insert(rows: Seq[SqliteRow]): F[Unit]
}
