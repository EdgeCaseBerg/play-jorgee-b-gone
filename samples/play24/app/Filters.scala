import javax.inject.Inject
import play.api.http.HttpFilters

import module.JorgeeBGoneUserAgentFilter

class Filters @Inject() (
	jorgeeBGoneUserAgentFilter: JorgeeBGoneUserAgentFilter
) extends HttpFilters {

  val filters = Seq(jorgeeBGoneUserAgentFilter)
}