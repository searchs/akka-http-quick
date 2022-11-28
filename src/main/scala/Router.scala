import akka.http.scaladsl.server.{Directives, Route}

import scala.util.{Failure, Success}

trait Router {
  def route: Route
}


class TodoRouter(todoRepository: TodoRepository) extends Router with Directives {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  override def route: Route = pathPrefix("todos") {
    pathEndOrSingleSlash {
      get {
        onComplete(todoRepository.all()) {
          case Success(todos) => complete(todos)
          case Failure(exception) =>
            println(exception.getMessage)
            complete(ApiError.generic.statusCode, ApiError.generic.message)
        }
      }
    } ~ path("done") {
      get {
        onComplete(todoRepository.done()) {
          case Success(todos) => complete(todos)
          case Failure(exception) =>
            println(exception.getMessage)
            complete(ApiError.generic.statusCode, ApiError.generic.message)
        }
      }
    } ~ path("pending") {

      get {
        onComplete(todoRepository.pending()) {
          case Success(todos) => complete(todos)
          case Failure(exception) =>
            println(exception.getMessage)
            complete(ApiError.generic.statusCode, ApiError.generic.message)
        }
      }
    }
  }
}