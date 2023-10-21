package dev.khusanjon.docmngsys.service

import dev.khusanjon.docmngsys.model.dao.WebFormDao
import dev.khusanjon.docmngsys.model.entity.WebForm
import spray.json.{DefaultJsonProtocol, RootJsonFormat, _}

import scala.concurrent.ExecutionContext.Implicits.global

trait JsonMapping extends DefaultJsonProtocol {
  implicit val webFormFormat: RootJsonFormat[WebForm] = jsonFormat2(WebForm)
}

object WebFormService extends JsonMapping {

  def getAll() = WebFormDao.findAll().map(_.toJson)

  def getOne(id: Long) = WebFormDao.findById(id).map(_.toJson)

  def create(webForm: WebForm) = WebFormDao.create(webForm).map(_.toJson)

  def update(id: Long, newWebForm: WebForm) = WebFormDao.update(id, newWebForm).map(_.toJson)

  def delete(id: Long) = WebFormDao.delete(id).map(_.toJson)
}
