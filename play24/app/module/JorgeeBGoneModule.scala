package module

import play.api.Configuration
import play.api.Environment
import play.api.inject.Binding
import play.api.inject.Module
import play.api.http.HeaderNames.USER_AGENT
import play.api.http.Status.TEMPORARY_REDIRECT
import play.api.mvc.{ Result, RequestHeader, Filter, Results }

import scala.concurrent.Future

import javax.inject.{ Inject, Named }

import com.github.edgecaseberg.jorgeebgone._

class JorgeeBGoneUserAgentFilter @Inject() (
		blacklist: BlacklistProvider,
		@Named("jorgeebgone.redirectTo") redirectTo: String
) extends Filter {
	def apply(nextFilter: (RequestHeader) => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
		requestHeader.headers.get(USER_AGENT) match {
			case Some(userAgent) if blacklist.contains(userAgent) => Future.successful(Results.Redirect(redirectTo, TEMPORARY_REDIRECT))
			case _ => nextFilter(requestHeader)
		}
	}
}

class JorgeeBGoneModule extends Module {
	def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
		lazy val blacklist = new TypesafeBlacklistProvider(
			"jorgeebgone.blacklist",
			configuration.underlying
		)
		val redirectTo = configuration.getString("jorgeebgone.redirectTo").fold("https://github.com/")(identity)
		Seq(
			bind[BlacklistProvider].toInstance(blacklist),
			bind[String].qualifiedWith("jorgeebgone.redirectTo").toInstance(redirectTo)
		)
	}
}