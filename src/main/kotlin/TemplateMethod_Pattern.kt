/*
템플릿 메소드 패턴
- 정의 : 메소드에서 알고리즘의 골격을 정의한다. 알고리즘의 여러 단계 중 일부는 서브클래스에서 구현할 수 있다.
        템플릿 메소드를 이용하면 알고리즘의 구조는 그대로 유지하면서 서브클래스에서 특정 단계를 재정의할 수 있다
- 단점
    - 추상 메소드가 너무 많아지면 안 좋아 질 수 있다
    - 알고리즘의 단계를 너무 잘개 쪼개면 서브클래스에서 구현 할 게 많아 진다

디자인 원칙
- 헐리우드 원칙 : 먼저 연락하지 마세요, 저희가 연락 드리겠습니다
    - 의존성 부패(dependency rot)를 방지 하 ㄹ수 있따
    - 어떤 고수준 구성요소가 저수준 구성요소에 의존하고, 그 저수준 구성요소는 다시 고수준 구성요소에 의존하고, 그 고수준 요소는 다른 것에 의존하고...
    - CaffeinBeverage(고수준) Coffee, Tea(저수준)
        - Coffee, Tea (저수준)는 호출 당하기 전까지는 절대로 추상 클래스를 직접 호출 하지 않는다
    - "의존성 뒤집기"와의 관계
        - 객체를 분리시킨다는 하나의 목표를 공유하고 있긴하지만, 디자상의 의전송을 피하는 방법에 있어서 의존성 뒤집기 원칙이 훨씬 더 강하고 일반적인 내용을 담고 있따
        - 의존성 뒤집기는 될 수 있으면 구상 클래스 사용을 줄이고 대신 추상화된 것을 사용해야 한다는 원칙이다
          할리우드 원칙은 저수준 구성요소들을 다양하게 사용할 수 있으면서도, 다른 클래스가 그러한 구성요소에 너무 의존하지 않게 만들어주는 디자인 기법이다

다른 패턴과의 관계
- 템플릿 메소드 패턴 -> 바꿔 쓸 수 있는 행동을 캡슐화하고, 어떤 행동을 사용할지는 서브클래스에게 맡긴다
- 스트래티지 패턴 -> 알고리즘의 일부 단계를 구현하는 것을 서브클래스에서 처리한다
- 팩토리 메소드 패턴 -> 어떤 구상 클래스를 생성할지를 서브클래스에서 결정한다
 */

/*
문제 상황
- Coffee, Tea가 거의 유사하지만 몇가지가 달라서 캡슐화가 어렵다
 */
class Coffee() {
    fun prepareRecipe() {
        boilWater()
        brewCoffeeGrinds()
        pourInCup()
        addSugarAndMilk()
    }

    fun boilWater() {

    }

    fun brewCoffeeGrinds() {

    }

    fun pourInCup() {

    }

    fun addSugarAndMilk() {

    }
}

class Tea() {
    fun prepareRecipe() {
        boilWater()
        steepTeaBag()
        addLemon()
        pourInCup()
    }

    fun boilWater() {

    }

    fun steepTeaBag() {

    }

    fun addLemon() {

    }

    fun pourInCup() {

    }
}

/*
해결책
- CaffeinBeverage를 만든다
- Coffee와 Tea가 살짝 다르지만 유사한 부분은 함수명을 바꿔서 통일 시킨다 (addCondiments) -> 일반화 + 템플릿 메소드 도입
 */
abstract class CaffeinBeverage {
    fun prepareRecipe() {
        // 템플릿 메소드 이다
        // - 어떤 알고리즘에 대한 템플릿(틀) 역할을 한다.
        // - 이 경우에는 음료를 마들기 위한 알고리즘에 대한 템플릿이다
        boilWater()
        brew()
        pourInCup()
        addCondiments()
    }

    abstract fun brew()
    abstract fun addCondiments()

    fun pourInCup() {
        println("컵에 따르는중")
    }

    fun boilWater() {
        println("물을 끓이는 중")
    }
}

class Coffee2 : CaffeinBeverage() {
    override fun brew() {
    }

    override fun addCondiments() {
    }
}

class Tea2 : CaffeinBeverage() {
    override fun brew() {
    }

    override fun addCondiments() {
    }
}

/*
후크(hook)메소드
- 템플릿 메소드가 정의 되어있는 추상 클래스에 존재하지만, 기본적인 내용만 구현되어 있거나 아무 코드도 들어있지 않은 메소드이다
- 때문에, 서브클래스 입장에서는 다양한 위치에서 알고리즘에 끼어들 수 있고, 무시 할 수도 있다

언제 후크 메소드를 써야하고, 추상 메소드를 써야 하는가?
- 알고리즘의 특정 단계를 제공해야만 하는 경우에는 추상 메소드를 써야 한다
- 알고리즘의 특정 부분이 선택적으로 적용된다든가 하는 경우에는 후크를 사용하면 된다 (서브클래스에서 반드시 구현 해야하는 것은 아니기 때문)
 */
abstract class CaffeinBeverageWithHook {
    fun prepareRecipe() {
        boilWater()
        brew()
        pourInCup()
        if (customerWantsCondiments()) {
            addCondiments()
        }
    }

    abstract fun brew()
    abstract fun addCondiments()

    fun pourInCup() {
        println("컵에 따르는중")
    }

    fun boilWater() {
        println("물을 끓이는 중")
    }

    open fun customerWantsCondiments(): Boolean {
        // 후크 메소드이다
        // - 별 구현이 없는 기본 메소드를 구현해 놓는다 (true만 리턴할 뿐 아무런 일도 하지 않는다)
        // - 서브 클래스에서 필요에 따라 오버라이드 할 수 있는 메소드이므로 후크 메소드이다
        return true
    }
}

class CoffeeWithHook : CaffeinBeverageWithHook() {
    override fun brew() {
    }

    override fun addCondiments() {
    }

    override fun customerWantsCondiments(): Boolean {
        val answer: String = getUserInput()
        if (answer.startsWith("Y")) return true
        else return false
    }
}

