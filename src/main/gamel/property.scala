

package gamel;

// a property, such as HasObj and ActionObj
protected[gamel] trait Property {
  var name: Symbol = null
}

// represents a "has" construct
protected[gamel] case class HasObj(t: Symbol) extends Property {
    var entity_type = t
    def > (n: Symbol): HasObj = {
        this.name = n
        this
    }
}

// for people to implement for action call backs
trait ActionParam
trait ActionResult

protected[gamel] case class ActionObj(t: Symbol) extends Property {
    name = t
    var action_funcs: List[ActionParam => ActionResult] = List()
    def := (funcs: (ActionParam => ActionResult)*) = {
        this.action_funcs = List(funcs:_*)
        this
    }
}
