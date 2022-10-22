package dev.khusanjon.docmngsys.form

import dev.khusanjon.docmngsys.form.WebFormRegistry.ActionPerformed

object JsonFormats {

  import spray.json.DefaultJsonProtocol._

  implicit val webFormJsonFormat = jsonFormat1(WebForm)
  implicit val webFormsJsonFormat = jsonFormat1(WebForms)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
}
