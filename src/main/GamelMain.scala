import gamel._

object GamelCode extends App {

    define entity 'Foo as ()

    define entity 'Tianyu as (
        has - 'Foo > 'foo
        //, action - 'Fall := () 
    ) 

    create scene 'Room as (
        has - 'Foo > 'buzz
    )

    create entity 'Tianyu called 'tianyu

}
