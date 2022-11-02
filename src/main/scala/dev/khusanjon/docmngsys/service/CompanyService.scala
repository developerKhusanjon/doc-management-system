package dev.khusanjon.docmngsys.service

import dev.khusanjon.docmngsys.model.dao.CompanyDao
import dev.khusanjon.docmngsys.model.entity.Company
import spray.json.{DefaultJsonProtocol, RootJsonFormat, _}

import scala.concurrent.ExecutionContext.Implicits.global

trait JsonMapping extends DefaultJsonProtocol {
  implicit val customerFormat: RootJsonFormat[Company] = jsonFormat3(Company)
}

object CompanyService extends JsonMapping {

  def getAll() = CompanyDao.findAll().map(_.toJson)

  def getOne(id: Long) = CompanyDao.findById(id).map(_.toJson)

  def create(company: Company) = CompanyDao.create(company).map(_.toJson)

  def update(id: Long, newCompany: Company) = CompanyDao.update(id, newCompany).map(_.toJson)

  def delete(id: Long) = CompanyDao.delete(id).map(_.toJson)
}
