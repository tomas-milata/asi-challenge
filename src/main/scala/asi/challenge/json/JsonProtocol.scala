package asi.challenge.json

import asi.challenge.model.Repo
import spray.httpx.SprayJsonSupport
import spray.json.CollectionFormats
import spray.json.DefaultJsonProtocol._

object JsonProtocol extends SprayJsonSupport with CollectionFormats {
  implicit val repoFormat = jsonFormat4(Repo)
  implicit val reposFormat = listFormat[Repo]
}
