package controller

import play.api.mvc._

object SampleController extends Controller {
	def index = Action {
		Ok("Hello!")
	}
}