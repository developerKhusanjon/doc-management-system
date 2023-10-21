package dev.khusanjon.docmngsys.service

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import dev.khusanjon.docmngsys.model.dao.FormSubmissionDao
import dev.khusanjon.docmngsys.model.entity.FormSubmission
import spray.json.{DefaultJsonProtocol, RootJsonFormat, _}

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.concurrent.ExecutionContext.Implicits.global

trait JsonMapping extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val formSubmissionFormat: RootJsonFormat[FormSubmission] = jsonFormat3(FormSubmission)

  implicit val localDateTimeFormat: JsonFormat[LocalDateTime] = new JsonFormat[LocalDateTime] {
    private val iso_date_time = DateTimeFormatter.ISO_DATE_TIME

    def write(x: LocalDateTime) = JsString(iso_date_time.format(x))

    def read(value: JsValue) = value match {
      case JsString(x) => LocalDateTime.parse(x, iso_date_time)
      case x => throw new RuntimeException(s"Unexpected type ${x.getClass.getName} when trying to parse LocalDateTime")
    }
  }
}

object FormSubmissionService extends JsonMapping {

  def getAll() = FormSubmissionDao.findAll().map(_.toJson)

  def getOne(id: Long) = FormSubmissionDao.findById(id).map(_.toJson)

  def create(formField: FormSubmission) = FormSubmissionDao.create(formField).map(_.toJson)

  def update(id: Long, newFormField: FormSubmission) = FormSubmissionDao.update(id, newFormField).map(_.toJson)

  def delete(id: Long) = FormSubmissionDao.delete(id).map(_.toJson)
}
