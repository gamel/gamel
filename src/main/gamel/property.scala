package gamel;

// a property, such as HasObj and ActionObj
trait Property

// represents a "has" construct
case class HasObj(t: Symbol) extends Property{
    var entity_type = t
    var entity_name = 'Placeholder
    def > (n: Symbol): HasObj = {
        this.entity_name = n
        this
    }
}
