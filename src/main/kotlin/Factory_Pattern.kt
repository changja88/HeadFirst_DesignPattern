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
- 원재로군을 관리할 방법이 필요하다
 */
interface PizzaIngredientFactory{
    fun createDough(): Dough
    fun createSauce(): Sauce
    fun createCheese(): Cheese
    fun createVeggis() : List<Viggie>
    fun createPepperoni() : Pepperoni
    fun createClam() : Clam
}
class NYPizzaIngredientFactory : PizzaIngredientFactory{
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
        val veggies = {Garlic(), Onion()}, Mushrrom(), RedPepper()}
    return veggies
}

override fun createPepperoni(): Pepperoni {
    return SlicedPepperoni()
}

override fun createClam(): Clam {
    return FreshClams()
}
}