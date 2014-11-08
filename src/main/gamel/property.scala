package gamel;

// a property, such as HasObj and ActionObj
protected[gamel] trait Property

// represents a "has" construct
protected[gamel] case class HasObj(t: Symbol) extends Property{
    var entity_type = t
    var entity_name = 'Placeholder
    def > (n: Symbol): HasObj = {
        this.entity_name = n
        this
    }
}

// for people to implement for action call backs
trait ActionParam
trait ActionResult

protected[gamel] case class ActionObj(t: Symbol) extends Property{
    var action_name = t
    var action_funcs: List[ActionParam => ActionResult] = List()
    def := (funcs: (ActionParam => ActionResult)*) = {
        this.action_funcs = List(funcs:_*)
        this
    }
}
