package plugins

import play.api.DefaultGlobal
import play.api.mvc._
import org.scalatest._
import play.api.test._
import play.api.test.Helpers._
import play.api.http.Status.TEMPORARY_REDIRECT
import org.scalatestplus.play._

class JorgeeBGonePluginTest extends PlaySpec with OneAppPerSuite with OptionValues {
	object global extends WithFilters(JorgeeBGonePlugin.filter)
	implicit override lazy val app = FakeApplication(
		additionalPlugins = Seq("plugins.JorgeeBGonePlugin"),
		withGlobal = Some(global),
		withRoutes = {
			case ("GET", "/") => Action { Results.Ok("ok") }
		}
	)

	"The JorgeeBGonePlugin" must {
		"provide a filter that denys blacklisted agents" in {
			assertResult(TEMPORARY_REDIRECT) {
				status(
					route(
					FakeRequest(GET, "/")
						.withHeaders(("User-Agent", "Mozilla/Jorgee"))
				).value
				)
			}
		}
		"allow non-blacklisted agents through to routes" in {
			assertResult(200) {
				status(
					route(
					FakeRequest(GET, "/")
						.withHeaders("User-Agent" -> "Mozilla/Firefox")
				).value
				)
			}
		}
	}
}