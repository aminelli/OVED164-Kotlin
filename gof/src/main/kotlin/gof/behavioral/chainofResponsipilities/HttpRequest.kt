package chainofResponsipilities

class HttpRequest(
    private val userName: String,
    private val password: String
) {
    fun getUsername() = userName
}
