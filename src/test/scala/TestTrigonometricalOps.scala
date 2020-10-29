import evo_sim.model.Constants
import evo_sim.model.World.TrigonometricalOps.Sinusoidal.Curried._
import evo_sim.model.World.TrigonometricalOps.Sinusoidal.sinusoidal
import org.scalacheck.{Arbitrary, Prop, Properties}
import org.scalacheck.Prop.{exists, forAll}
import org.scalactic.TolerantNumerics
import org.scalatest.PropSpec
import org.scalatest.prop.Checkers


//run with: test;  from Intellij sbt shell
//or: sbt test     from cmd from the the folder cotaining build.sbt file

class SinusoidalSpecifications extends PropSpec with Checkers { //extends Properties("Sinusoidal") {
  //signature: sinusoidal(yDilatation: Float)(x:Float)(phase: Int)(yTranslation: Int)

  //1. check codomain is respected (max = |amplitude| * 1 + ytranslation, min = |amplitude| * -1 + ytraslation)
  property("max and min value respected") {
    check {
      forAll {
        (yDilatation: Float, x: Float, phase: Int, yTranslation: Int) => {
          val actualValue = sinusoidal(yDilatation)(x)(phase)(yTranslation: Int)
          val max = Math.abs(yDilatation) + yTranslation
          val min = - Math.abs(yDilatation) + yTranslation
          actualValue <= max && actualValue >= min
        }
      }
    }
  }


  //binary:
  //3. check reversing phase sign results in reversed wave

  //4. check amplitude change results in proportional result change

  /* object DoubleEqualityImplicits {
     val epsilon = 1e-4f
     implicit val doubleEq = TolerantNumerics.tolerantDoubleEquality(epsilon)
   }*/
}


