package dev.khusanjon.docmngsys.service

import dev.khusanjon.docmngsys.model.dao.FormFieldDao
import dev.khusanjon.docmngsys.model.entity.FormField
import spray.json.{DefaultJsonProtocol, RootJsonFormat, _}

import scala.concurrent.ExecutionContext.Implicits.global

trait JsonMapping extends DefaultJsonProtocol {
  implicit val formFieldFormat: RootJsonFormat[FormField] = jsonFormat4(FormField)
}

object FormFieldService extends JsonMapping {

  def getAll() = FormFieldDao.findAll().map(_.toJson)

  def getOne(id: Long) = FormFieldDao.findById(id).map(_.toJson)

  def create(formField: FormField) = FormFieldDao.create(formField).map(_.toJson)

  def update(id: Long, newFormField: FormField) = FormFieldDao.update(id, newFormField).map(_.toJson)

  def delete(id: Long) = FormFieldDao.delete(id).map(_.toJson)
}
