package asi.challenge.http

import spray.routing.HttpServiceActor

class HttpApiActor extends HttpServiceActor {

  override def receive: Receive = runRoute(route)

  private val route = path("repos") {
    get {
      complete {
        "here repo"
      }
    }
  }
}