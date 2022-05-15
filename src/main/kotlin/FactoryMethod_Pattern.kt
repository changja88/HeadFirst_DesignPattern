/*
인터페이스에 맞춰서 코딩을 하면 시스템에서 일어날 수 있는 여러 변화를 이겨낼 수 있다
- 다형성 덕분에 어떤 클래스든 특정 인터페이스만 구현하면 사용할 수 있기 떄문
- 구상 클래스를 많이 사용하면 새로운 구상 클래스가 추가될 때마다 코드를 고쳐야 한다 (변화에 닫혀 있다)
 */
interface Pizza {
    fun prepare() {}
    fun bake() {}
    fun box() {}
}

class CheesePizza() : Pizza {}
class GreekPizza() : Pizza {}
class PepperoniPizza() : Pizza {}
class ClamPizza() : Pizza {}
class VeggiePizza() : Pizza {}

//- 문제 상황
fun odrerPizza(type: String): Pizza {
    var pizza: Pizza? = null
    if (type.equals("cheese")) pizza = CheesePizza()
    else if (type.equals("greek")) pizza = GreekPizza()
    else if (type.equals("pepperoni")) pizza = PepperoniPizza()
    else if (type.equals("greek")) pizza = GreekPizza()

    pizza!!.prepare()
    pizza!!.bake()
    pizza!!.box()
    return pizza
}

//- 피자 종류 변경
fun oderPizza(type: String): Pizza {
    var pizza: Pizza? = null
    if (type.equals("cheese")) pizza = CheesePizza()
    else if (type.equals("greek")) pizza = GreekPizza() // 삭제
    else if (type.equals("pepperoni")) pizza = PepperoniPizza()
    else if (type.equals("greek")) pizza = GreekPizza()
    else if (type.equals("clam")) pizza = ClamPizza() // 추가
    else if (type.equals("veggie")) pizza = VeggiePizza() // 추가

    pizza!!.prepare()
    pizza!!.bake()
    pizza!!.box()
    return pizza!!
}
//문제점
//- 인스턴스를 만들 구상 클래스를 선택하는 부분이 문제이다
//- 상황에 따라서 코드를 변경할 수 밖에 없다

//- 해결책
//- 1> 바뀌지않는 부분을 남기고 변경되는 부분을 캡슐화 한다
class SimplePizzaFactory {
    fun createPizza(type: String): Pizza {
        var pizza: Pizza? = null
        if (type.equals("cheese")) pizza = CheesePizza()
        else if (type.equals("pepperoni")) pizza = PepperoniPizza()
        else if (type.equals("greek")) pizza = GreekPizza()
        else if (type.equals("clam")) pizza = ClamPizza() // 추가
        else if (type.equals("veggie")) pizza = VeggiePizza() // 추가

        return pizza!!
    }
}

class PizzaStore(val factory: SimplePizzaFactory) {
    fun orderPizza(type: String): Pizza {
        val pizza: Pizza = factory.createPizza(type) // -> New 연산자 대신에 팩토리 사용(구상 클래스의 인스턴스를 만들 필요가 없음)
        pizza.prepare()
        pizza.bake()
        pizza.box()
        return pizza
    }
}
//문제 발생
//  - 분점들이 생기기 시작했다
//  - 처음에는 NYPizzaFactory, ChicagoPizzaFactory 를 만들어서 사용 해서 해결 되었지만,
//  - 분점들이 독자적인 방법을 사용해서 피자를 만들기 시작했다 -> 피자 가게와 피자 제작 과정 전체를 하나로 묶어주는 프레임워크의 필요성이 생김

//해결책
// - createPizza 메소드를 추상메소드로 만들고, 각 피자스토어마다 고유의 스타일에 맞게 작성한다
// - 팩토리 메소드 패턴 도입
//  - 정의 : 팩토리 메소드 패턴에서는 객체를 생성하기 위한 인터페이스를 정의하는데, 어떤 클래스의 인스턴스를 만들지는 서브클래스에서 결정하게 만든다
//          팩토리 메소드 패턴을 이용하면 클래스의 인스턴스를 만드는 일을 서브클래스에게 맡길 수 있다
//  - 팩토리 메소드는 객체 생성을 처리하며, 팩토리 메소드를 이용하면 객체를 생성하는 작업을 서브클래스에 캡슐화시킬 수 있다
//  - 이렇게 하면 수퍼클래스에 있는 클라이언트 코드와 서브클래스에 있는 객체 생성 코드를 분리시킬 수 있다
abstract class PizzaStore2 {
    fun orderPizza(type: String): Pizza {
        /*
        피자 객체를 가지고 여러가지 작업을 하지만 피자는 추상 클래스이기 때문에 실제로 어떤 구상 클래스에서 작업이 처리되는지 알 수 없다 (분리 되어 있다)
         */
        val pizza = createPizza(type)
        pizza.prepare()
        pizza.bake()
        pizza.box()

        return pizza
    }

    abstract fun createPizza(type: String): Pizza //팩토리 메소드가 추상 메소드로 변경되었다 (추상 팩토리 메소드)
}

class NYPizzaStore : PizzaStore2() {
    // PizzaStore를 상속 받은 분점들이 자기 스타일의 피자를 만들수 있게 되었다
    override fun createPizza(type: String): Pizza {
        var pizza: Pizza? = null
        if (type.equals("cheese")) pizza = NYCheesePizza()
        else if (type.equals("pepperoni")) pizza = NYPepperoniPizza()
        else if (type.equals("greek")) pizza = NYGreekPizza()
        else if (type.equals("clam")) pizza = NYClamPizza() // 추가
        else if (type.equals("veggie")) pizza = NYVeggiePizza() // 추가
    }
}

// 사용예시
fun usage_example() {
    val nyPizzaStore = NYPizzaStore()
    nyPizzaStore.orderPizza("cheese")
}

