/*
Duck이라는 슈퍼 클래스를 만들었다
1. 다른 duck 클래스들이 위 슈퍼클래스를 상속 받고 있다
2. duck 슈퍼클래스에 fly 기능을 추가 하였다
3. duck 슈퍼클래스를 상속 받은 모든 서브 클래스들이 fly 기능을 갖게 되었다
4. 문제 발생 -> 모든 서브 클래스 들이 fly 기능을 갖아서는 안된다 (고무 오리는 날지 못한다)
5. 상속을 활용한 임시 해결책 : fly 기능을 할 수 없는 서브 클래스에서느 fly를 오버라이드 하여 못 날게 한다

위 처럼 문제를 해결 했을 때의 문제점
1. 서브 클래스에서 코드가 중복된다
3. 모든 오리의 행동을 알기가 어렵다
4. 슈퍼클래스를 변경 해씅ㄹ 때 다른 오리들한테 원치 않은 영향을 줄 수 있다

해결책
- 달라지는 부분을 찾아 내고, 달라지지 않는 부분으로부터 분리 시킨다 (캡슐화)
    -> 오리들의 행동을 duck 클래스에 뽑아 낸다
    -> fly, quack 을 제외하면 문제가 없음(바뀌지 않음)으로 이 두개를 뽑아 낸다
- 위에서 뽑아낸 fly, quack 클래스는 어떻게 디자인 해야 할까?
    -> 구현이 아닌 인터페이스에 맞춰서 프로그래밍 해야 한다
    -> 이전 구현 방식, Duck 클래스에 구체적으로 구현한 방식은 좋지 않다
- 상위 형식에 맞춰서 프로그래밍 한다
    - 변수를 선언할 때는 보통 추상 클래스나 인터페이스 같은 상위 형식으로 선언해야 한다
    - 객체를 변수에 대입할 때 상위 형식을 구체적으로 구현한 형식이라면 어떤 객체든 집어넣을 수 있기 때문
        -> 변수를 선언하는 클래스에서 실제 객체의 형식을 몰라도 된다
    - 구현에 맞춰서 한 프로그래밍 예제 (bad case)
        var d: Dog = Dog();
        d.bark()
        - Dog 형식으로 선언하면 어떤 구체적인 구현에 맞춰서 코딩해야한다
    - 인터페이스/ 상위 형식에 맞춰서 프로그래밍한 예제 (good case)
        animal : Animal = new Dog()
        animal.makeSound();
        - Dog 라는 걸 알고 있지만 다형성을 활용하여 Animal에 대한 레퍼런스를 써도 된다
    - 상위 형식의 인스턴스를 만드는 과정을 (new Dog()같은 식으로) 직접 코드로 만드는 대신 구체적으로 구현된 객체를 실행시에 대입하는 것이다 (best case)
        a = getAnimal();
        a.makeSound();
        - animal의 하위 형식 가운데 어떤 형식인지는 모른다. 단지 makeSound()에 대해 올바른 반응을 할 수만 있으면 된다
*/


// 객체의 종류에는 전형 신경쓸 필요가 없다 -> quack()을 실행시킬 줄 알다는 것이 중요하다
interface FlyBehavior {
    fun fly()
}

class FlyWithWings : FlyBehavior {
    override fun fly() {
        println("날고 있어요!")
    }
}

class FlayNoWay : FlyBehavior {
    override fun fly() {
        println("저는 못 날아요!")
    }
}

interface QuackBehavior {
    fun quack()
}

class Quack : QuackBehavior {
    override fun quack() {
        println("꽥")
    }
}

class MuteQuack : QuackBehavior {
    override fun quack() {
        println("~~조용~~")
    }
}

class Squeak : QuackBehavior {
    override fun quack() {
        println("삑")
    }
}

abstract class Duck(
    var quackBehavior: QuackBehavior, // 모든 Duck에는 QuackBehavior인터페이스를 구현하는 것에 대한 레퍼런스가 있다
    var flyBehavior: FlyBehavior
) {

    abstract fun display()

    fun performQuack() {
        quackBehavior.quack() // quack을 직접 처리 하는 대신 quackBehavior로 참조되는 객체에 그 행동을 위임한다
    }

    fun performFly() {
        flyBehavior.fly()
    }

    fun swim() {
        println("모든 오리는 물에 뜹니다. 가짜 오리도 뜨죠")
    }

}

class MallardDuck(
    /*
    - 꽥꽥거리는 소리를 처리 할때는 Quack클래스를 사용하기 때문에 performQuack()이 호출 되면 꽥꽥거리는 행동은 Quack객체에게 위임된다
     */
    quackBehavior: QuackBehavior = Quack(),
    flyBehavior: FlyWithWings = FlyWithWings()
) : Duck(quackBehavior, flyBehavior) {

    override fun display() {
        TODO("Not yet implemented")
    }
}

/*
  val mallard = MallardDuck()
    mallard.performQuack()
    mallard.performFly()

    mallard.flyBehavior= FlayNoWay() -> 동적으로 변경도 가능하다
    mallard.performFly()
 */

/*
- A는 B이다 보다 A에는 B가 있다 가 더 좋을 수 있다
    - A에는 B가 있다
    - 각 오리에는 FlyBehavior 와 QuackBehavior가 있으며, 각각 행동을 위임 받는다
    - 이런 방식을 구성(Composition)이라고 한다
    -> 상속보다는 구성을 활용한다

- 스트래티지 패턴(Strategy Pattern)
    - 위 예제가 스트래티지 패턴이다
    - 알고리즘군을 정의하고 각각을 캡슐화하여 교환해서 사용할 수 있도록 만든다
    - 스트래티지를 활용하면 알고리즘을 사용하는 클러이언트와는 독립적으로 알고리즘을 변경할 수 있다
 */
