package tosqlite.di

import cats.Monad
import tosqlite.application.ToSqliteService
import tosqlite.infrastructure.SqliteRowRepositoryImpl

class ApplicationModule[F[_]: Monad](infrastructureModule: InfrastructureModule[F]) {
  val toSqliteService = new ToSqliteService[F](infrastructureModule.dao, new SqliteRowRepositoryImpl)
}
