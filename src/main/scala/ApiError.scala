import akka.http.scaladsl.model.{StatusCode, StatusCodes}

/**
 * Custom error for API
 * @param statusCode
 * @param message
 */
final case class ApiError private (statusCode: StatusCode, message: String)

object ApiError {

  private def apply(statusCode: StatusCode, message: String): ApiError = new ApiError(statusCode, message)

val generic: ApiError = new ApiError(StatusCodes.InternalServerError, message = "Unknown error.")

}