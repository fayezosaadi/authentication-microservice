package auth

import com.google.inject.Inject
import javax.inject.Singleton
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.{Json, Reads}
import slick.jdbc.{GetResult, JdbcProfile}

import scala.concurrent.Future

case class UserAuth(email: String, passwdHash: String, creationTime: Long)

@Singleton
class UserDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  implicit val getUserResult: AnyRef with GetResult[UserAuth] = GetResult(r => UserAuth(r.nextString, r.nextString, r.nextLong()))

  def createUser(user: UserAuth): Future[Int] = {
    db.run(sqlu"insert into users (email, passwdHash, creationTime) values (${user.email}, ${user.passwdHash}, ${System.currentTimeMillis()})")
  }

  def getUserByEmail(email: String): Future[Option[UserAuth]] = {
    db.run(sql"select email, passwdHash, creationTime from users where email = $email".as[UserAuth].headOption)
  }
}
