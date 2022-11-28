import scala.concurrent.Future
trait TodoMocks {

  class FailingRepository extends  TodoRepository {
    override def all(): Future[Seq[Todo]] = Future.failed(new Exception("Mocked Exception"))

    override def done(): Future[Seq[Todo]] = Future.failed(new Exception("Mocked Exception"))

    override def pending(): Future[Seq[Todo]] = Future.failed(new Exception("Mocked Exception"))
  }

}
