package test.surf.moviedb.utils.states

class UiState private constructor(val status: Status, val msg: String? = null) {
    companion object {
        val LOADED = UiState(Status.SUCCESS)
        fun found(msg: String?) = UiState(Status.FOUND, msg=msg)
        fun notFound(msg: String?) = UiState(Status.NOT_FOUND, msg=msg)
        val UPDATING = UiState(Status.UPDATING)
        fun error(msg: String?) = UiState(Status.FAILED, msg)
    }

    enum class Status {
        UPDATING,
        SUCCESS,
        FOUND,
        NOT_FOUND,
        FAILED
    }
}