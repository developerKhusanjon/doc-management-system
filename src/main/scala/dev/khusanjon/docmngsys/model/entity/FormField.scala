package dev.khusanjon.docmngsys.model.entity

case class FormField(id: Option[Long], title: String, isRequired: Boolean, webFormId: Long)
