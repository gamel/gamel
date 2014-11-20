package gamel

protected[gamel] abstract class GamelException(msg: String = null, cause: Throwable = null)
  extends java.lang.Exception(msg, cause) {
    def this() = this(null,null)
    def this(msg: String) = this(msg, null)
    def this(cause: Throwable) = this(null,cause)
}

protected[gamel] class DefinitionException(msg: String = null, cause: Throwable = null)
  extends GamelException(msg, cause)

protected[gamel] class CreationException(msg: String = null, cause: Throwable = null)
  extends GamelException(msg, cause)

protected[gamel] class UndefinedInstanceException(msg: String = null, cause: Throwable = null)
  extends GamelException(msg, cause)

protected[gamel] class UndefinedActionException(msg: String = null, cause: Throwable = null)
  extends GamelException(msg, cause)
