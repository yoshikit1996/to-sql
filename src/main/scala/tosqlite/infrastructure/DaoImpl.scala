package tosqlite.infrastructure

import cats.Monad
import cats.effect.{Bracket, Effect}
import cats.implicits._
import doobie.implicits._
import doobie.util.fragment.Fragment
import doobie.util.transactor.Transactor
import tosqlite.application.Dao
import tosqlite.domain.model.{SqliteRow, SqliteSchema}

class DaoImpl[F[_]: Monad: Effect](transactor: Transactor[F])(implicit bracket: Bracket[F, Throwable]) extends Dao[F] {

  private val TableName = "main"

  override def createTable(schema: SqliteSchema): F[Unit] = {
    // 既にテーブルが存在する場合はDROPしてから、CREATEする
    val drop = (fr"DROP TABLE IF EXISTS" ++ Fragment.const(TableName)).update
    val create = Fragment.const(schema.toCreateStatement(TableName)).update

    for {
      _ <- drop.run.transact(transactor)
      _ <- create.run.transact(transactor)
    } yield ()
  }

  override def insert(rows: Seq[SqliteRow]): F[Unit] = {
    // TODO: rowsがemptyのときも考慮、あるいは引数をNonEmptyListにする
    val sqls = rows.map(_.toInsertStatement(TableName))

    sqls.map(sql => Fragment.const(sql).update.run.transact(transactor))
      .iterator.reduce((a, b) => a.flatMap(_ => b)) // Catsのシンタックスとかでもっとシンプルにかけるかも
      .map(_ => ()) // これとかもリファクタできそう
  }
}
