package tosqlite.di

import cats.effect.{Async, ConcurrentEffect, ContextShift}
import doobie.util.transactor.Transactor
import doobie.util.transactor.Transactor.Aux
import tosqlite.infrastructure.DaoImpl

class InfrastructureModule[F[_]: ContextShift: ConcurrentEffect](pathToSqliteFile: String) {
  val dao = new DaoImpl[F](transactor[F])

  // CatsのResourceを使った方がいい？？
  def transactor[F[_]: Async](implicit cs: ContextShift[F]): Aux[F, Unit] =
    Transactor.fromDriverManager[F]("org.sqlite.JDBC", s"jdbc:sqlite:$pathToSqliteFile", "", "")
}
