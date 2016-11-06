package asi.challenge

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import asi.challenge.http.HttpApiActor
import spray.can.Http

import scala.concurrent.duration._

object AsiChallenge extends App {

  implicit val system = ActorSystem("AsiChallenge")
  implicit val timeout = Timeout(30.seconds)

  val apiActor = system.actorOf(Props[HttpApiActor], "httpApiActor")

  IO(Http) ? Http.Bind(apiActor, interface = config.http.interface)
}
