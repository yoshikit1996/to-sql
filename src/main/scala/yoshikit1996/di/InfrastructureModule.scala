package yoshikit1996.di

import cats.effect.{Async, ConcurrentEffect, ContextShift}
import doobie.util.transactor.Transactor
import doobie.util.transactor.Transactor.Aux
import yoshikit1996.infrastructure.DaoImpl

class InfrastructureModule[F[_]: ContextShift: ConcurrentEffect]() {
  val dao = new DaoImpl[F](transactor[F])

  // CatsのResourceを使った方がいい？？
  def transactor[F[_]: Async](implicit cs: ContextShift[F]): Aux[F, Unit] =
    Transactor.fromDriverManager[F]("org.sqlite.JDBC", "jdbc:sqlite:default.db", "", "")
}
