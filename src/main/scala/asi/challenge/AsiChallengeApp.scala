package asi.challenge

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.util.Timeout
import akka.pattern.ask
import asi.challenge.http.HttpApiActor
import spray.can.Http

import scala.concurrent.duration._

object AsiChallengeApp extends scala.App {

  implicit val system = ActorSystem("AsiChallengeApp")
  implicit val timeout = Timeout(30.seconds)

  val apiActor = system.actorOf(Props[HttpApiActor], "httpApiActor")

  IO(Http) ? Http.Bind(apiActor, interface = "0.0.0.0")
}
