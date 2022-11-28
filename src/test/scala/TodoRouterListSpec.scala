import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

class TodoRouterListSpec extends WordSpec with Matchers with ScalatestRouteTest {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  private val doneTodo = Todo("1", "Buy cheese", "Food for the cat", true)
  private val pendingTodo = Todo("2", "Drop laundry", "Weekly drop-off", false)
  private val todos = Seq(doneTodo, pendingTodo)

  "TodoRouter" should {
    "return all the todos" in {
    val repository = new InMemoryTodoRepository(todos)
    val router = new TodoRouter(repository)

      Get("/todos") ~>  router.route ~> check {
        status  shouldBe StatusCodes.OK
        val  response = responseAs[Seq[Todo]]
        response shouldBe todos
      }

    }

    "return only PENDING todos" in {
      val repository = new InMemoryTodoRepository(todos)
      val router = new TodoRouter(repository)
      Get("/todos/pending") ~> router.route ~> check {
        status shouldBe StatusCodes.OK
        val resp = responseAs[Seq[Todo]]
        resp shouldBe Seq(pendingTodo)
      }
    }

    "return all DONE todos" in {
      val repository = new InMemoryTodoRepository(todos)
      val router = new TodoRouter(repository)

      Get("/todos/done") ~> router.route ~> check {
        status shouldBe StatusCodes.OK
        val resp = responseAs[Seq[Todo]]
        resp shouldBe Seq(doneTodo)
      }
    }
  }

}
