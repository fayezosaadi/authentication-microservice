package auth

import javax.inject.{Inject, Singleton}
import org.mindrot.jbcrypt.BCrypt

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserService @Inject()(userDao: UserDao) {

  def userExists(email: String)(implicit exec: ExecutionContext): Future[Boolean] = {
    for {
      user <- userDao.getUserByEmail(email)
    } yield {
      user.isDefined
    }
  }

  def addUser(user: User)(implicit exec:ExecutionContext): Future[Int] = {
    userExists(user.email).flatMap(userExists =>
      if (userExists) throw new IllegalArgumentException("User already exists")
      else userDao.createUser(UserAuth(user.email, hashPassword(user.password), System.currentTimeMillis()))
    )
  }

  def hashPassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt(11))

}
