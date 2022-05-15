/*
추상 팩토리 패턴 종합 정리
- 정의 : 추상 팩토리 패턴에서는 인터페이스를 이용하여 서로 연관된, 또는 의존하는 객체를 구상 클래스를 지정하지 않고도 생성할 수 있다
- 모양
    - 슈퍼클래스 팩토리가 있다 (PizzaIngredientFactory)
    - 위 클래스를 상속받는 구상 팩토리 클래스들이 있다 (ChicagePizzaIngredientFactory, NYPizzaIngredientFactory)
- 팩토리 패턴과의 차이
    - 팩토리패턴
        - 팩토리 패턴은 구상 팩토리가 여러개 존재하고, 객체를 생성한다
        - PizzaIngredientFactory 를 상속 받는 ChicagePizzaIngredientFactory, NYPizzaIngredientFactory
        - 팩토리패턴도 결국 create(객체를 생성하는 함수)와 같은 함수가 있기 때문에 팩토리 메소드 패턴을 포함 하고 있다고 할 수 있다
    - 팩토리 메소드 패턴
        - 팩토리 메서드 패턴은 팩토리가 여러개 있는게 아니라,
          예를 들면 객체생성을 위한 함수가 있는 인터페이스를 상속받은 구체 클래스들이 이 함수를 통해 객체를 생성한다
            - create함수를 가지고 있는 Pizza 인터페이스를 구현하고 있는 NYPizzaStore, ChicagoPizzaStore가 create함수를 통해 객체를 만든다
 */

/*
디자인 원칙
- 의존성 뒤비지기 원칙 (Dependency Inversion Principle)
    - 추상화 된것에 의존하도록 만들어라. 구상 클래스에 의존하게 하지 마라
- 위 까지의 해결책으로는 의존성이 맞지 않다
    - 고수준 : PizzaStore, 저수준 : Pizza
    - PizzaStore의 행동이 Pizza의 영향을 받고 있기 때문에 고수준이 저수준에 의존하고 있다
    - orderPizza()메소드에서 구성 형식의 인스턴스를 직접 만들고 있다 (고수준이 저수준에 의존)

해결책
- 팩토리 패턴 도입
    - 도입 이전 구조 : PizzaStore -> 구체피자들
    - 도입 이후 구조 : PizzaStore -> Pizza <- 구체피자들 (<- 이 화살표가 원래는 ->방향이다, 뒤집는 다고 말하는 이유)
 */
/*
문제상황
- 시카고피자, 뉴욕피자에서 같은피자라도 사용하는 재료가 다르다
- 원재료군을 관리할 방법이 필요하다
 */
interface PizzaIngredientFactory {
    fun createDough(): Dough
    fun createSauce(): Sauce
    fun createCheese(): Cheese
    fun createVeggis(): List<Viggie>
    fun createPepperoni(): Pepperoni
    fun createClam(): Clam
}

class ChicagePizzaIngredientFactory : PizzaIngredientFactory {
    override fun createDough(): Dough {
        return ThinCrustDough()
    }

    override fun createSauce(): Sauce {
        return MarinaraSauce()
    }

    override fun createCheese(): Cheese {
        return ReggianoCheese()
    }

    override fun createVeggis(): List<Viggie> {
        val veggies = [
            Garlic(), Onion(), Mushrrom(), RedPepper()
        ]
        return veggies
    }

    override fun createPepperoni(): Pepperoni {
        return SlicedPepperoni()
    }

    override fun createClam(): Clam {
        return FreshClams()
    }
}

abstract class Pizza2 {
    var name: String? = null
    var dough: Dough? = null
    var sauce: Sauce? = null
    var veggies: List<Veiggie>? = null
    var cheese: Cheese? = null
    var pepperoni: Pepperoni? = null
    var clam: Clam? = null

    abstract fun prepare()
    fun bake() {
        println("bake for 25min at 350")
    }

    fun cut() {
        println("cutting the pizza into diagonal slices")
    }

    fun box() {
        println("place pizza in official pizzasotre box")
    }

    override fun toString(): String {
        return name!!
    }
}

class CheesePizza2(
    val ingredientFactory: PizzaIngredientFactory
) : Pizza2() {
    override fun prepare() {
        dough = ingredientFactory.createDough()
        sauce = ingredientFactory.createSauce()
        cheese = ingredientFactory.createCheese()
    }
}

class ChicagoPizzaStore : PizzaStore() {
    fun createPizza(type: String): Pizza {
        val ingredientFactory: PizzaIngredientFactory = ChicagePizzaIngredientFactory()
        // 원재료 팩토리만 바꿔주면 시카고풍 피자, 뉴욕풍 피자 전부다 만들 수 있다
        var pizza: Pizza = null
        if (type.equals("cheese")) {
            pizza = CheesePizza2(ingredientFactory)
        } else if (type.equals("veggie")) {
            pizza = VeggiePizza(ingredientFactory)
        } else if (type.equals("clam")) {
            pizza = ClamPizza(ingredientFactory)
        } else if (type.equals("pepperoni")) {
            pizza = PepperoniPizza(ingredientFactory)
        }
        return pizza
    }
}