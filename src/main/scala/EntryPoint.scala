import cats.effect.{ExitCode, IO, IOApp}

object EntryPoint extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val dao = new Dao(SQLiteModule.transactor)
    dao.createTable().unsafeRunSync()
    IO(ExitCode.Success)
  }
}
