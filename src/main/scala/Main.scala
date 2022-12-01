import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.Await
import scala.util.{Failure, Success}

object Main extends App {
  val host = "0.0.0.0"
  val port = 9001

  implicit val system: ActorSystem = ActorSystem(name = "todoapi")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  import akka.http.scaladsl.server.Directives._
  import system.dispatcher

  val todoRepository = new InMemoryTodoRepository(Seq(
    Todo("1", "Cook plain eggs", "Water-based egg meals", false),
    Todo("2", "Cook oily eggs", "Oil-based egg meals", true),
    Todo("3", "Shell cooked eggs", "Just cook the shells as meals", false),
    Todo("4", "Get laundry to Charles Centre", "Weekly laundry at great price and timing.", true),
  ))

  val router = new TodoRouter(todoRepository)
  val server = new Server(router, host, port)
  val binding = server.bind()

  binding.onComplete {
    case Success(_) => println("Success!")
    case Failure(error) => println(s"Failed: ${error.getMessage}")
  }

  import scala.concurrent.duration._

  Await.result(binding, 3.seconds)


}
