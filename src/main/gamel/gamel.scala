import scala.collection.mutable.{Map, HashMap}

/**
 * The gamel package object defines the syntax as a DSL,
 * along with needed definitions and implicit conversions
 *
 * The other files in the package define the internal
 * representation of the gamel constructs
 */
package object gamel {

    // base type of all gamel things
    protected[gamel] trait Thing;

    // contains global/shared storage for gamel runtime
    val all = new HashMap[Symbol, Thing]

    // handles the "has" construct
    object has {
        def - (s: Symbol): HasObj = {
            if(!(all contains s)){
                throw new ClassNotFoundException(s + " is not an entity-type")
            }
            all(s) match {
                case ent: Entity => new HasObj(s)
                case _ => throw new ClassNotFoundException(s + " is not an entity-type") 
            }
        }
    }

    // handles the "action" construct
    object action {
        def - (s: Symbol): ActionObj = new ActionObj(s)
    }

    // handles the "define entity" construct
    object define {
        def entity (ent: Symbol): Entity = {
            if(all contains ent){
                throw new InstantiationException(ent + " already exists")
            }
            println ("defining an entity " + ent)
            var newEntity = new Entity(ent)
            all(ent) = newEntity
            all(ent).asInstanceOf[Entity]
        }
    }

    // handles the "create" constructs
    object create {
        def entity (ent: Symbol): EntityInstance = {
            if (!(all contains ent)) {
                throw new InstantiationException(ent + " is not an entity-type")
            }
            all(ent) match {
                case ent_type: Entity => new EntityInstance(ent_type)
                case _ => throw new InstantiationException(ent + " is not an entity-type")
            }
        }

        def scene (sce: Symbol): Scene = {
            if(all contains sce){
                throw new InstantiationException(sce + " already exists")
            }
            println ("creating a scene " + sce)
            all(sce) = new Scene(sce)
            all(sce).asInstanceOf[Scene]
        }
    }


    // Some implicit conversions
    //implicit def symbolToEntity(s: Symbol): Entity = new Entity(s)
    //implicit def symbolToScene(s: Symbol): Scene = new Scene(s)

}
