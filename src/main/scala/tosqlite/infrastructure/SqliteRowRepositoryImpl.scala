package tosqlite.infrastructure

import io.circe.parser.decode
import io.circe.{Decoder, DecodingFailure, Error}
import tosqlite.application.SqliteRowRepository
import tosqlite.domain.model.{SqliteRow, SqliteType, SqliteValue}

import scala.util.{Failure, Success, Try}

/**
 * Json文字列をMap[String, Any]型の値としてデコードして、
 * SqliteRowに変換する
 */
class SqliteRowRepositoryImpl extends SqliteRowRepository {

  private implicit val anyDecoder: Decoder[Any] = Decoder.instance(c => {
    c.focus match {
      case Some(x) => Right(x)
      case None => Left(DecodingFailure("Could not parse", Nil))
    }
  })

  override def fromJsonFile(path: String): Either[Error, Seq[SqliteRow]] = {
    val lines = scala.io.Source.fromFile(path).mkString.linesIterator

    lines.foldLeft(Right(Seq.empty): Either[Error, Seq[SqliteRow]]) { (acc, line) =>
      decode[Map[String, Any]](line) match {
        case Right(map) =>
          acc.map(_ :+ toSqliteRow(map))
        case Left(error) => Left(error)
      }
    }
  }

  private def toSqliteRow(map: Map[String, Any]): SqliteRow = {
    val values = map.map {
      case (key, value) =>
        SqliteValue(
          columnName = key,
          value = value,
          sqliteType = detectType(value)
        )
    }

    SqliteRow(values.toSeq)
  }

  /**
   * 引数valueをREAL型かTEXT型に振り分ける
   * TODO: NUMERIC, INTEGER, NONE型に対応する
   *
   * @param value
   * @tparam R
   * @return
   */
  private def detectType[R](value: Any): SqliteType = {
    Try(BigDecimal(value.toString)) match {
      case Success(_) => SqliteType.SReal
      case Failure(_) => SqliteType.SText
    }
  }
}
