package dev.khusanjon.docmngsys.model.dao

import dev.khusanjon.docmngsys.model.entity.Employee

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

object EmployeeDao extends BaseDao {
  def findAll(): Future[Seq[Employee]] = employeeTable.result

  def findById(id: Long): Future[Employee] = employeeTable.filter(_.id === id).result.head

  def create(employee: Employee): Future[Long] = employeeTable returning employeeTable.map(_.id) += employee

  def update(id: Long, newEmployee: Employee): Future[Int] = {
    employeeTable.filter(_.id === id).map(employee => (employee.name, employee.role, employee.companyId))
  .update((newEmployee.name, newEmployee.role, newEmployee.companyId))
}


  def delete(id: Long): Future[Int] = {
    employeeTable.filter(_.id === id).delete
}
}
