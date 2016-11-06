package asi.challenge.json

import akka.actor.ActorSystem
import asi.challenge.client.{GitHubClient, SendReceiveComponent}
import asi.challenge.github.User
import asi.challenge.model.Repo
import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}
import spray.client.pipelining.{SendReceive, _}
import spray.http.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}

import scala.concurrent.Future


@RunWith(classOf[JUnitRunner])
class GitHubClientTest extends FlatSpec with Matchers with MockFactory with ScalaFutures {

  private implicit val system = ActorSystem()

  import system.dispatcher

  val sendReceiveMock = mock[SendReceive]

  val client = new GitHubClient with SendReceiveComponent {
    override def sendReceive = sendReceiveMock
  }

  "GitHub client" should "deserialize a reponse" in {

    (sendReceiveMock.apply _).expects(Get("https://api.github.com/users/torvalds/repos")).once.returning(
      """
        |[
        |  {
        |    "id": 2325298,
        |    "name": "linux",
        |    "size": 1967498,
        |    "html_url": "https://github.com/torvalds/linux"
        |  }
        |]
      """.response)

    client.topFiveRepos(User("torvalds")).futureValue shouldBe List(Repo(
      id = 2325298L,
      name = "linux",
      size = 1967498L,
      html_url = "https://github.com/torvalds/linux"
    ))
  }

  it should "sort results by size" in {

    (sendReceiveMock.apply _).expects(Get("https://api.github.com/users/user/repos")).once.returning(
      """
        |[
        |  {"id": 2, "name": "repo2", "size": 2, "html_url": "https://github.com/user/repo2"},
        |  {"id": 3, "name": "repo3", "size": 3, "html_url": "https://github.com/user/repo3"},
        |  {"id": 1, "name": "repo1", "size": 1, "html_url": "https://github.com/user/repo1"}
        |]
      """.response)

    client.topFiveRepos(User("user")).futureValue.map(_.name) shouldBe List("repo3", "repo2", "repo1")
  }

  implicit class StringToResponse(val json: String) {
    def response = Future(
      HttpResponse(StatusCodes.OK, HttpEntity(ContentTypes.`application/json`, json.stripMargin.getBytes))
    )
  }
}
