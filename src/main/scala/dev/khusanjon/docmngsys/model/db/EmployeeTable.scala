package dev.khusanjon.docmngsys.model.db

import dev.khusanjon.docmngsys.model.entity.Employee
import slick.jdbc.PostgresProfile.api._

class EmployeeTable(tag: Tag) extends Table[Employee](tag, "employees") {
  def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name")

  def role: Rep[String] = column[String]("role")

  def companyId: Rep[Long] = column[Long]("company_id")

  def * = (id.?, name, role, companyId) <> ((Employee.apply _).tupled, Employee.unapply)
}
