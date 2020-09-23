import cats.effect.{ContextShift, IO}
import doobie.util.transactor.Transactor

object SQLiteModule {
  def transactor(implicit cs: ContextShift[IO]) =
    Transactor.fromDriverManager[IO]("org.sqlite.JDBC", "jdbc:sqlite:default.db", "", "")
}
