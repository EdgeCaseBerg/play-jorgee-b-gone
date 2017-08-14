import play.api.mvc._

import plugins.JorgeeBGonePlugin

object Global extends WithFilters(JorgeeBGonePlugin.filter)
