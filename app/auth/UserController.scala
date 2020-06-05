package auth

import akka.dispatch.MessageDispatcher
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsValue, Json, Reads}
import play.api.mvc._
import utils.Contexts

import scala.concurrent.Future

case class User(email: String, password: String)

@Singleton
class UserController @Inject()(userService: UserService, contexts: Contexts, cc: ControllerComponents) extends AbstractController(cc) {
  implicit val UserReads: Reads[User] = Json.reads[User]

  implicit val executionContext: MessageDispatcher = contexts.cpuLookup

  def register(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[User].fold(
      error => Future.successful(BadRequest("Not a valid input format: " + error.mkString)),
      user =>
        userService.userExists(user.email).flatMap(userExists => {
          if (userExists) Future.successful(BadRequest(s"User already exists: ${user.email}. cannot register again"))
          else {
            userService.addUser(user).map(x => Ok(Json.obj("message" -> "success")))
          }
        })
    )
  }
}