package asi.challenge.client

import akka.actor.ActorSystem
import asi.challenge.config
import asi.challenge.github.User
import asi.challenge.json.JsonProtocol
import asi.challenge.model.Repo
import spray.client.pipelining
import spray.client.pipelining._

class GitHubClient(implicit val system: ActorSystem) {

  this: SendReceiveComponent =>

  import JsonProtocol._
  import system.dispatcher

  private val pipeline = sendReceive ~> unmarshal[List[Repo]]

  private val baseUri = config.github.apiBaseUri

  /**
    * @return 5 biggest (by size) repositories of the given `user` from GitHub API
    */
  def topFiveRepos(user: User) = pipeline(Get(s"$baseUri/users/${user.name}/repos")) map {
    _.sortBy(_.size)(Ordering[Long].reverse)
      .take(5)
  }
}

trait SendReceiveComponent {
  def sendReceive: SendReceive
}

object GitHubClient {
  def apply()(implicit system: ActorSystem): GitHubClient = {
    import system.dispatcher

    new GitHubClient with SendReceiveComponent {
      override def sendReceive = pipelining.sendReceive
    }
  }
}
