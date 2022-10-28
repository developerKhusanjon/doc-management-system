package dev.khusanjon.docmngsys.model.db

import dev.khusanjon.docmngsys.model.entity.Company
import slick.jdbc.PostgresProfile.api._

class CompanyTable(tag: Tag) extends Table[Company](tag, "companies") {
  def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name")

  def address: Rep[String] = column[String]("address")

  def * = (id.?, name, address) <> ((Company.apply _).tupled, Company.unapply)
}