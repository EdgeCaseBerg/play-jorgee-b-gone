package controllers 

import play.api.mvc._

class SampleController() extends Controller {
	def index = Action {
		Ok("hello!")
	}
}