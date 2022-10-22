package dev.khusanjon.docmngsys.submission

import akka.actor.typed.ActorRef
import dev.khusanjon.docmngsys.form.WebForm

import scala.collection.immutable

final case class Submission(inputData: String, webForm: WebForm)
final case class Submissions(submissions: immutable.Seq[Submission])

object SubmissionRegistry {

  import dev.khusanjon.docmngsys.util.Utils._

  sealed trait Command
  final case class GetSubmissions(replyTo: ActorRef[Submission]) extends Command
  final case class CreateSubmission(submission: Submission, replyTo: ActorRef[ActionPerformed]) extends Command
  final case class GetSubmission()

}
