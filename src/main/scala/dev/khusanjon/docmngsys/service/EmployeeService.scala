package dev.khusanjon.docmngsys.service

import dev.khusanjon.docmngsys.model.dao.EmployeeDao
import dev.khusanjon.docmngsys.model.entity.Employee
import spray.json.{DefaultJsonProtocol, RootJsonFormat, _}

import scala.concurrent.ExecutionContext.Implicits.global

trait JsonMapping extends DefaultJsonProtocol {
  implicit val employeeFormat: RootJsonFormat[Employee] = jsonFormat4(Employee)
}

object EmployeeService extends JsonMapping {

  def getAll() = EmployeeDao.findAll().map(_.toJson)

  def getOne(id: Long) = EmployeeDao.findById(id).map(_.toJson)

  def create(employee: Employee) = EmployeeDao.create(employee).map(_.toJson)

  def update(id: Long, newEmployee: Employee) = EmployeeDao.update(id, newEmployee).map(_.toJson)

  def delete(id: Long) = EmployeeDao.delete(id).map(_.toJson)
}
