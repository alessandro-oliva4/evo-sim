package evo_sim.model

import evo_sim.model.Constants._
import evo_sim.model.Entities.{BaseBlob, CannibalBlob, PoisonBlob, SlowBlob}
import evo_sim.model.effects.DegradationEffect
import org.scalatest.FunSpec

class BlobTests extends FunSpec {
  private val base: BaseBlob = BaseBlob(
    name = "blob1",
    boundingBox = BoundingBox.Circle.apply(point = Point2D(100, 100), radius = 10),
    life = 100,
    velocity = 3,
    degradationEffect = DegradationEffect.standardDegradation,
    fieldOfViewRadius = 10,
    movementStrategy = MovingStrategies.baseMovement,
    direction = Direction(0, 15))
  private val cannibal: CannibalBlob = CannibalBlob(
    name = "blob2",
    boundingBox = BoundingBox.Circle.apply(point = Point2D(100, 100), radius = 100),
    life = 100,
    velocity = 3,
    degradationEffect = DegradationEffect.standardDegradation,
    fieldOfViewRadius = 10,
    movementStrategy = MovingStrategies.baseMovement,
    direction = Direction(0, 15))
  private val poisonBlob: PoisonBlob = PoisonBlob(
    name = "blob3",
    boundingBox = BoundingBox.Circle.apply(point = Point2D(100, 100), radius = 10),
    life = 100,
    velocity = 3,
    degradationEffect = DegradationEffect.standardDegradation,
    fieldOfViewRadius = 10,
    movementStrategy = MovingStrategies.baseMovement,
    direction = Direction(0, 15),
    cooldown = 0)
  private val slowBlob: SlowBlob = SlowBlob(
    name = "blob4",
    boundingBox = BoundingBox.Circle.apply(point = Point2D(100, 100), radius = 10),
    life = 100,
    velocity = 3,
    degradationEffect = DegradationEffect.standardDegradation,
    fieldOfViewRadius = 10,
    movementStrategy = MovingStrategies.baseMovement,
    direction = Direction(0, 15),
    cooldown = 0,
    initialVelocity = 10)
  private val world: World = World.apply(temperature = DEF_TEMPERATURE, luminosity = DEF_LUMINOSITY, width = WORLD_WIDTH, height = WORLD_HEIGHT,
    currentIteration = 0, entities = Set(base, poisonBlob, cannibal, slowBlob), totalIterations = DEF_DAYS * ITERATIONS_PER_DAY)

  describe("A BaseBlob with BaseBlobBehaviour") {
    describe("when updating a blob") {
      it("the new blob should have less life") {
        val updatedBlob: BaseBlob = base.updated(world).head.asInstanceOf[BaseBlob]
        assert(base.life > updatedBlob.life)
      }
    }
  }
   describe("when a cannibal blob collide with a base blob") {
     it("the cannibal blob should eat the base blob"){
       val blobShouldBeEaten = base.collided(cannibal).head.asInstanceOf[BaseBlob]
       val newCannibal = cannibal.collided(base).head.asInstanceOf[CannibalBlob]
       assert(blobShouldBeEaten.life==0)
       assert(newCannibal.life > cannibal.life)
     }
   }

  describe("a temporary blob with expiring cooldown") {
    it("the temporary blob should return a blob"){
      val blobFromSlowBlob = slowBlob.updated(world).head
      val blobFromPoisonBlob = poisonBlob.updated(world).head
      assert(blobFromSlowBlob.isInstanceOf[BaseBlob])
      assert(blobFromPoisonBlob.isInstanceOf[BaseBlob])
    }
  }

}
