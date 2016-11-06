package asi.challenge.http

import asi.challenge.client.GitHubClient
import asi.challenge.github.User
import asi.challenge.json.JsonProtocol
import spray.routing.HttpServiceActor

class HttpApiActor extends HttpServiceActor {

  private implicit val actorSystem = context.system

  import JsonProtocol._
  import actorSystem.dispatcher

  private val client = GitHubClient()

  override def receive: Receive = runRoute(route)

  private val route = path("user" / Segment / "repos") { userName =>
    get {
      val response = client.topFiveRepos(User(userName))
      complete(response)
    }
  }
}