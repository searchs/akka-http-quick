import scala.concurrent.{ExecutionContext, Future}

trait TodoRepository {
def all(): Future[Seq[Todo]]
def done(): Future[Seq[Todo]]
def pending(): Future[Seq[Todo]]
}

class InMemoryTodoRepository(initTodos: Seq[Todo] = Seq.empty)(implicit ec: ExecutionContext) extends TodoRepository {
  private var todos: Vector[Todo] = initTodos.toVector
  override def all(): Future[Seq[Todo]] = Future.successful(todos)

  override def done(): Future[Seq[Todo]] = Future.successful(todos.filter(_.done))

  override def pending(): Future[Seq[Todo]] = Future.successful(todos.filterNot(_.done))
}
