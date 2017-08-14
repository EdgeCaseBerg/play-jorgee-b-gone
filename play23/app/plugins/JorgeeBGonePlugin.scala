package plugins

import scala.concurrent.Future
import play.api.{ Plugin, Application, Play }
import play.api.mvc._
import play.api.http.HeaderNames.USER_AGENT
import play.api.http.Status.TEMPORARY_REDIRECT

import com.github.edgecaseberg.jorgeebgone._

/** This is likely how you'll interact with this plugin. In your global object use WithFilters(JorgeeBGonePlugin.filter)
 */
object JorgeeBGonePlugin {
	def filter: Filter = UserAgentFilter
}

/** The plugin class for JorgeeBGone, create a blacklist that loads its configuration from the underlying configuration file
 *
 *  To control the plugin set the jorgeebgone.blacklist to a list of strings to prevent from reaching your application
 *  Then if you'd like to send them somewhere specific, like a 10 hour HE-man video, set the jorgeebgone.redirectTo key to your url
 */
class JorgeeBGonePlugin(implicit app: Application) extends Plugin {

	private val blacklist = new TypesafeBlacklistProvider("jorgeebgone.blacklist", app.configuration.underlying)

	def getBlacklist() = blacklist

	def getRedirectBadAgentsTo(): String = {
		app.configuration.getString("jorgeebgone.redirectTo").fold("https://github.com")(identity)
	}
}

/** Filter that redirects a request based on the user agent and the blacklist loaded from the configuration */
object UserAgentFilter extends Filter {

	/** This needs to be lazy otherwise a Global trying to use this won't start up */
	private lazy val plugin = Play.current.plugin[JorgeeBGonePlugin].getOrElse(throw new RuntimeException("JorgeeBGonePlugin plugin not loaded"))
	/** Needs to be lazy otherwise using the filter within a WithFilters mixin will fail */
	private lazy val blacklist = plugin.getBlacklist()
	/** Needs to be lazy otherwise using the filter within a WithFilters mixin will fail */
	private lazy val redirectTo = plugin.getRedirectBadAgentsTo()

	def apply(nextFilter: (RequestHeader) => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
		requestHeader.headers.get(USER_AGENT) match {
			case Some(userAgent) if blacklist.contains(userAgent) => Future.successful(Results.Redirect(redirectTo, TEMPORARY_REDIRECT))
			case _ => nextFilter(requestHeader)
		}
	}
}

