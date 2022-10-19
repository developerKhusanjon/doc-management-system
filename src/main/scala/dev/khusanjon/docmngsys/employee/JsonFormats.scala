package dev.khusanjon.docmngsys.employee

import dev.khusanjon.docmngsys.employee.EmployeeRegistry.ActionPerformed

object JsonFormats {

  import spray.json.DefaultJsonProtocol._
  import dev.khusanjon.docmngsys.company.JsonFormats._

  implicit val employeeJspmFormat = jsonFormat3(Employee)
  implicit val employeesJsoFormat = jsonFormat1(Employees)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
}
