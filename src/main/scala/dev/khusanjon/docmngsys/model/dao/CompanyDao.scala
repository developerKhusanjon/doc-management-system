package dev.khusanjon.docmngsys.model.dao

import dev.khusanjon.docmngsys.model.entity.Company

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

object CompanyDao extends BaseDao {
  def findAll(): Future[Seq[Company]] = companyTable.result

  def findById(id: Long): Future[Company] = companyTable.filter(_.id === id).result.head

  def create(company: Company): Future[Long] = companyTable returning companyTable.map(_.id) += company

  def update(id: Long, newCompany: Company): Future[Int] = {
    companyTable.filter(_.id === id).map(company => (company.name, company.address))
      .update((newCompany.name, newCompany.address))
  }


  def delete(id: Long): Future[Int] = {
    companyTable.filter(_.id === id).delete
  }
}
