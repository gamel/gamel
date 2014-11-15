package idea.gamel

class DefinitionException(msg: String = null, cause: Throwable = null) 
  extends java.lang.Exception(msg, cause) {
    def this() = this(null,null)
    def this(msg: String) = this(msg, null)
    def this(cause: Throwable) = this(null,cause)
}

class CreationException(msg: String = null, cause: Throwable = null) 
  extends java.lang.Exception(msg, cause) {
    def this() = this(null,null)
    def this(msg: String) = this(msg, null)
    def this(cause: Throwable) = this(null,cause)
}
