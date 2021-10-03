package test.surf.moviedb.utils.states

class UiState private constructor(val status: Status, val msg: String? = null) {
    companion object {
        val LOADED = UiState(Status.SUCCESS)
        val FOUND = UiState(Status.FOUND)
        val UPDATING = UiState(Status.UPDATING)
        fun error(msg: String?) = UiState(Status.FAILED, msg)
    }

    enum class Status {
        UPDATING,
        SUCCESS,
        FOUND,
        FAILED
    }
}