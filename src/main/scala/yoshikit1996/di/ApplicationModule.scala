package yoshikit1996.di

import cats.Monad
import yoshikit1996.application.ToSqliteService
import yoshikit1996.infrastructure.SqliteRowRepositoryImpl

class ApplicationModule[F[_]: Monad](infrastructureModule: InfrastructureModule[F]) {
  val toSqliteService = new ToSqliteService[F](infrastructureModule.dao, new SqliteRowRepositoryImpl)
}
