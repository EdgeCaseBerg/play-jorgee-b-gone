package module

import play.api.{ Application, Mode, Configuration, ApplicationLoader }
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.routing.Router
import play.api.inject._
import play.api.inject.guice._
import play.api.mvc._
import org.scalatest._
import play.api.test._
import play.api.test.Helpers._
import play.api.http.HttpFilters
import play.api.http.Status.TEMPORARY_REDIRECT
import org.scalatestplus.play._

import javax.inject.Inject
import java.io.File

import scala.collection.JavaConverters._
/** Can't nest this in the other test otherwise you get [info] 1) Injecting into inner classes is not supported.  Please use a 'static' class (top-level or nested) instead of module.JorgeeBGoneUserAgentFilterTest$Filters.
 *  this class just makes sure that our fake app is using the filter we want it to.
 */
class Filters @Inject() (jorgeeFilter: JorgeeBGoneUserAgentFilter) extends HttpFilters {
	def filters = Seq(jorgeeFilter)
}

class JorgeeBGoneUserAgentFilterTest extends PlaySpec with OneAppPerSuite with OptionValues {

	/** This is our fake application that is configured to load the Jorgee Filter */
	class CustomApplicationLoader extends GuiceApplicationLoader() {
		override def builder(context: ApplicationLoader.Context): GuiceApplicationBuilder = {
			/** If you don't use the initial configuration for the play.modules.enabled you lost the
			 *  module which provides a binding for play.api.Application, so you have to make sure to
			 *  merge the two configurations together in code. This is the same as using += in a conf
			 *  for the key.
			 */
			val fixedModules: List[String] = context
				.initialConfiguration
				.getStringList("play.modules.enabled").fold(List.empty[String]) { enabledModules =>
					enabledModules.asScala.toList :+ "module.JorgeeBGoneModule"
				}
			val extra = Configuration("play.modules.enabled" -> fixedModules)
			initialBuilder
				.in(context.environment)
				.loadConfig(context.initialConfiguration ++ extra)
				.overrides(overrides(context): _*)
				.overrides(
					bind[Router].to(Router.from {
						case _ => Action(Results.Ok("!"))
					}),
					bind[HttpFilters].to[Filters]
				)
		}
	}

	val context = play.api.ApplicationLoader.createContext(play.api.Environment.simple())

	implicit override lazy val app: Application = new CustomApplicationLoader().load(context)

	"The JorgeeBGoneUserAgentFilter" must {
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