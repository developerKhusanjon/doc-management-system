package dev.khusanjon.docmngsys.company

import dev.khusanjon.docmngsys.company.CompanyRegistry.ActionPerformed

object JsonFormats {

  import spray.json.DefaultJsonProtocol._

  implicit val companyJsonFormat = jsonFormat2(Company)
  implicit val companiesJsonFormat = jsonFormat1(Companies)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
}
