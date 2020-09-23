import cats.effect.Bracket
import doobie.implicits._
import doobie.util.transactor.Transactor

class Dao[F[_]](transactor: Transactor[F])(implicit bracket: Bracket[F, Throwable]) {
  def createTable(): F[Int] = {
    val create = sql"create table users(id integer, name text)".update
    create.run.transact(transactor)
  }
}
