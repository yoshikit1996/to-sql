package tosqlite.application

import tosqlite.domain.model.{SqliteRow, SqliteSchema}

trait Dao[F[_]] {
  def createTable(schema: SqliteSchema): F[Unit]
  def insert(rows: Seq[SqliteRow]): F[Unit]
}
