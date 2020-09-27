package yoshikit1996.application

import io.circe.Error
import yoshikit1996.domain.model.SqliteRow

trait SqliteRowRepository {
  def fromJsonFile(path: String): Either[Error, Seq[SqliteRow]]
}
