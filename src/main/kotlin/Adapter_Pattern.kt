/*
어답터 패턴
- 정의 : 한 클래스의 인터페이스를 클라이언트에서 사용하고자 하는 다른 인터페이스로 변환한다. 어답터를 이용하면 인터페이스 호환성 문제 때문에 같이
        쓸 수 없는 클래스들을 연결해서 쓸 수 있다
- 장점
    - 어답티를 새로 바뀐 인터페이스로 감쌀 때는 객체 구성(composition)을 사용한다 -> 어답티의 어떤 서브 클래스에 대해서도 어답터를 사용할 수 있게 된다
    - 특정 구현이 아닌 인터페이스를 기준으로 코딩 했기 때문에 타겟 인터페이스만 제대로 지키면 나중에 다른 구현을 추가하는 것도 가능 하다
- 종류
    - 객체 어답터
    - 클래스 어답터 : 이걸 사용하려면 다중 상속이 필요하기 때문에 필요 없다
문제상황
- 원래 Duckk클래스와 MallardDuckk 클래스가 존재
- Duckk 객체가 모자라서 Turkey 객체를 대신 사용해야 하는 상황
- 하지만 Turkey 인터페이스가 다르다
해결책
- 어답터를 만든다
 */
interface Duckk {
    fun quack()
    fun fly()
}

class MallardDuckk : Duckk {
    override fun quack() {
    }

    override fun fly() {
    }
}

interface Turckey {
    fun gobble()
    fun fly()
}

class WildTurkey : Turckey {
    override fun gobble() {
    }

    override fun fly() {
    }
}

class TurkeyAdapter(
    val turkey: Turckey // -> composition을 사용한다
) : Duckk {
    override fun quack() {
        turkey.gobble()
    }

    override fun fly() {
        turkey.fly()
    }
}

fun main() {
    val turkey: Turckey = WildTurkey()
    val turkeyAdapter: TurkeyAdapter = TurkeyAdapter(turkey) // Turey를 어답터로 감싸서 Duckk처럼 동작하게 한다
    turkeyAdapter.quack() // Duckk처럼 사용 가능
    turkeyAdapter.fly() // Duckk처럼 사용 가능
}