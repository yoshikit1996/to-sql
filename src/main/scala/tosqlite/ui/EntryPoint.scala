package tosqlite.ui

import org.rogach.scallop._
import cats.effect.{ExitCode, IO, IOApp}
import com.typesafe.scalalogging.StrictLogging
import tosqlite.di.{ApplicationModule, InfrastructureModule}

object EntryPoint extends IOApp with StrictLogging {

  class CliArgs(args: Seq[String]) extends ScallopConf(args) {
    val input = opt[String](required = true)
     val output = opt[String](default = Some("/tmp/default.db"))
    verify()
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val cliArgs = new CliArgs(args)

    val infrastructureModule = new InfrastructureModule(pathToSqliteFile = cliArgs.output())
    val applicationModule = new ApplicationModule(infrastructureModule)

    for {
      _ <- applicationModule.toSqliteService.convertToSql(cliArgs.input())
    } yield ExitCode.Success
  }
}
