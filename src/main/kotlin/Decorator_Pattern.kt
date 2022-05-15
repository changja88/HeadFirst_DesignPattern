/*
데코레이터 패턴 종합정리
- 데코레이터가 객체를 감싼다
    - 감싸는게 가능한 이유는 데코레이터와 객체가 같은 수퍼클래스를 갖고 있기 때문
    - ex)커피를 모카가가 감싸고 다시 휘핑이 감싼다
- OCP(Open-closed principle)을 지킬 수 있다
    - 커피, 모카가 있을 경우 휘핑이 새롭게 추가되었다고 해서 커피와 모카의 변경이 필요 하지 않다
    - 휘핑을 추가할 때 쉽게 추가 할 수 있다
 */

/*
상황
- 스타벅스에서 커피를 구성할 때, Beverage 클래스를 최상위 클래스로 두고, 이를 상속 받는 클래스들이 존재 한다 (에스프레소, 디카페인 커피, 모카 커피 등등)
- 문제는 서브 클래스들이 많아도 너무 많다 (음료의 종류가 엄청 많기 때문, 각각의 커피 별로 두유 여부, 휘핑 여부 등등 옵션도 존재)

해결책 (bad case)
- 휘핑, 두유 등등의 추가 옵션 여부는 슈퍼 클래스에 만든다
-> 서브 클래스의 갯수를 획기적으로 줄일 수 있다
- 문제점
    - 옵션(첨가물)의 종류가 많아지면 새로운 메소드를 추가해야 하고, 슈퍼클래스의 cost()메소드도 고쳐야한다
    - 특정 옵션이 들어가면 안되는 새로운 음료가 나올경우 문제가 발생한다

디자인 원칙
- OCP (Open-closed Principle)
- 클래스는 확장에 대해서는 열려 있어야 하지만 코드 변경에 대해서는 닫혀 있어야 한다

데코레이터 패턴
- 객체에 추가적인 요건을 동적으로 첨가한다. 데코레이터는 서브클래스를 만드는 것을 통해서 기능을 유연하게 확장할 수 있는 방법을 제공한다
- 데코레이터의 수퍼클래스는 자신이 장식하고 있는 객체의 수퍼클래스와 같다
- 한 객체를 여러 개의 데코레이터로 감쌀 수 있다
- 데코레이터는 자신이 감싸고 있는 객체와 같은 수퍼클래스를 가지고 있기 때문에 원래 객체가 들어갈 자리에 데코레이터 객체를 집어 넣어도 상관 없다
- 데코레이터는 자신이 장식하고 있는 객체에게 어떤 행동을 위움하는 것 외에 원하는 추가적인 작업을 수행할 수 있다
- 객체는 언제든지 감쌀 수 있기 때문에 실행중에 필요한 데코레이터를 마음대로 적용할 수 있다
- 데코레이터 객체를 래퍼라고 생각하면 된다
    -> 일반커피 < 모카 < 휘핑
    - cost를 계산 할때는 휘핑의 cost()를 사용하면 된다
 */
abstract class Beverage(var description: String = "제목 없음") {

    abstract fun cost(): Double
}

abstract class CondimentDecorator : Beverage() {

}

class Espresso(description: String = "에스프레소") : Beverage() {
    override fun cost(): Double {
        return 1.99
    }
}

class HouseBlend(description: String = "하우스 블렌드 커피") : Beverage() {
    override fun cost(): Double {
        return 0.89
    }
}

class Mocha(val beverage: Beverage) : CondimentDecorator() {

    init {
        description = beverage.description + " 모카"
    }

    override fun cost(): Double {
        return 0.2 + beverage.cost()
    }
}

fun usage_main() {
    val beverage: Beverage = Espresso()
    println(beverage.description)

    var beverage2: Beverage = HouseBlend()
    beverage2 = Mocha(beverage2)
    println(beverage2.description)

}
