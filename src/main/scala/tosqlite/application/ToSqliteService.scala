package tosqlite.application

import cats.Monad
import cats.implicits._
import com.typesafe.scalalogging.StrictLogging
import tosqlite.domain.model.SqliteSchema

class ToSqliteService[F[_]: Monad](dao: Dao[F], sqliteRowFactory: SqliteRowRepository) extends StrictLogging {
  def convertToSql(pathToJson: String): F[Unit] = {
    val rows = sqliteRowFactory.fromJsonFile(pathToJson) match {
      case Right(rs) =>
        rs
      case Left(e) =>
        logger.error(s"Failed to parse json, $e")
        Seq.empty
    }

    val createTable: Option[F[Unit]] = rows.headOption.map { row =>
      val schema = SqliteSchema(row.values.map(v => v.columnName -> v.sqliteType).toMap)
      dao.createTable(schema)
    }

    createTable match {
      case Some(creatTable) =>
        for {
          _ <- creatTable
          _ <- dao.insert(rows)
        } yield ()
      case None =>
        ().pure[F]
    }
  }
}
