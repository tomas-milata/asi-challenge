package asi.challenge

import com.typesafe.config.ConfigFactory

package object config {
  private val rootConfig = ConfigFactory.load.getConfig("asi-challenge")

  object github {
    private val githubConfig = rootConfig.getConfig("github")
    val apiBaseUri = githubConfig.getString("api.base.uri")
  }

  object http {
    private val httpConfig = rootConfig.getConfig("http")
    val interface = httpConfig.getString("interface")
  }
}
