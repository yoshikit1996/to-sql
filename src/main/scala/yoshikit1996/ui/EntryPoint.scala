package yoshikit1996.ui

import cats.effect.{ExitCode, IO, IOApp}
import com.typesafe.scalalogging.StrictLogging
import yoshikit1996.di.{ApplicationModule, InfrastructureModule}

object EntryPoint extends IOApp with StrictLogging {
  override def run(args: List[String]): IO[ExitCode] = {
    val infrastructureModule = new InfrastructureModule
    val applicationModule = new ApplicationModule(infrastructureModule)

    for {
      _ <- applicationModule.toSqliteService.convertToSql("sample.json")
    } yield ExitCode.Success
  }
}
