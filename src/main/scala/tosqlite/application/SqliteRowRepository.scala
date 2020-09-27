package tosqlite.application

import io.circe.Error
import tosqlite.domain.model.SqliteRow

trait SqliteRowRepository {
  def fromJsonFile(path: String): Either[Error, Seq[SqliteRow]]
}
