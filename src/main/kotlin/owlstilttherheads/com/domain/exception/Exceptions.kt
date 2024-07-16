package owlstilttherheads.com.domain.exception

class BadRequestException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)
class UnauthorizedException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)
class NotFoundException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)
class InternalServerException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)
class InfrastructureException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)


