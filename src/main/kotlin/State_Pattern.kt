/*
스테이드(State)패턴
- 스트레티지 패턴이 알고리즘을 바꿔 쓸 수 있었다면, 스테이트 패턴은 상태를 바꿈으로써 객체의 행동을 바꿔준다
- 정의
    - 객체의 내부 상태가 바뀜에 다라서 객체의 행동을 바꿀 수 있다. 마치 객체의 클래스가 바뀌는 것과 같은 결과를 얻을 수 있따
- 동작 방식
    - 상태를 별도의 클래스로 캡슐화한 다음 현재 상태를 나타내는 객체에게 행동을 위임한다.
    - 때문에 내부 상태가 바뀜에 따라서 행동이 달라지게 된다는 것을 알 수 있다
- 스트레티지 패턴과의 차이
    - 스트레티지 패턴은 객체가 전략을 능동적으로 선택해서 사용한다
    - 스테이트 패턴은 객체와 상관없이 상태가 상태를 알아서 바꾼다

- 차이
    - 스테이트 패턴 -> 바꿔 쓸 수 있는 행동을 캡슐화한 다음, 실제 행동은 다른 객체에 위임한다
    - 스트레티지 패턴 -> 알고리즘의 각 단게를 구현하는 방법을 서브클래스에서 구현한다
    - 템플릿 메소드 패턴 -> 상태를 기반으로 하는 행동을 캡슐화하고 행동을 현재 상태한테 위임한다
 */

/*
문제 상황
- 1> 뽑기 기계를 만든다
- 2> 뽑기 기계의 상태는, 동전 없음, 동전 있음, 알맹이 매진, 알맹이 판매 총 4가지 이다
- 3> 뽑기 기계의 행동은, 동전 투입, 동전 반환, 손잡이 돌림, 알맹이 내보냄 총 4가지 이다
 */
class GumballMachine(
    var count: Int
) {
    val SOLD_OUT: Int = 0
    val NO_QUARTER: Int = 1
    val HAS_QUATER: Int = 2
    val SOLD: Int = 3

    var state: Int = SOLD_OUT

    init {
        if (count > 0) state = NO_QUARTER
    }

    fun insertQuater() {
        if (state == HAS_QUATER) print("동전은 한 개만 넣어 주세요.")
        else if (state == NO_QUARTER) print("동전을 넣으셨습니다")
        else if (state == SOLD_OUT) print("매진 되었습니다. 다음 기회에 이용해주세요")
        else if (state == SOLD) print("잠깐만 기다려주세요. 알맹이가 나가고 있습니다")
    }

    fun ejectQuarter() {
        if (state == HAS_QUATER) {
            print("동전이 반환됩니다")
            state = NO_QUARTER
        } else if (state == NO_QUARTER) print("동전을 넣어주세요")
        else if (state == SOLD_OUT) print("이미 알맹이를 뽑으셨습니다")
        else if (state == SOLD) print("동전을 넣지 않으셨습니다. 동전이 반환되지 않습니다.")
    }

    fun trunCrank() {
        if (state == HAS_QUATER) print("손잡이는 한 번만 돌려주세요")
        else if (state == NO_QUARTER) print("동전을 넣어주세요")
        else if (state == SOLD_OUT) print("매진되었습니다")
        else if (state == SOLD) {
            print("손잡이를 돌리셨습니다")
            dispense()
        }
    }

    fun dispense() {
        if (state == HAS_QUATER) {
            print("알맹이가 나가고 있습니다")
            if (count == 0) {
                print("더 이상 알맹이가 없습니다")
                state = SOLD_OUT
            } else state = NO_QUARTER
        } else if (state == NO_QUARTER) print("동전을 넣어주세요")
        else if (state == SOLD_OUT) print("매진입니다")
        else if (state == SOLD) print("알맹이가 나갈 수 없습니다")
    }
}

/*
추가 상황 발생
- 위 코드에 게임 기능을 추가 하고 싶다

해결책
- 스테이트 패턴 도입
- 모든 상태를 캡슐화시켜서 State 인터페이스를 구현하는 클래스를 만든다
- 동전 없음, 동전 있음, 알맹이 매진, 알맹이 판매

장점
- 각 상태의 행동을 별개의 클래스로 국지화 시켰다
- 관리하기 힘든 if문 들을 지웠다
- 각 상태의 변경에 대해서는 닫혀 있도록 하면서도 GumballMachine자체는 새로운 상태 클래스를 추가하는 확장에 대해서 열려 있다
 */
interface State {
    fun insertQuarter()
    fun ejectQuarter()
    fun turnCrank()
    fun dispense()
}

class NoQuarterState(val gumballMachine: GumballMachine) : State {
    override fun insertQuarter() {
        print("동전을 넣으셨습니다")
        gumballMachine.state = gumballMachine.HAS_QUATER
//      각각의 상태들이 state를 바꿔준다
    }

    override fun ejectQuarter() {
        print("동전을 넣어주세요")
    }

    override fun turnCrank() {
        print("동전을 넣어주세요")
    }

    override fun dispense() {
        print("동전을 넣어주세요")
    }
}

class GumballMachine(
    var count: Int,
    var state: State
) {
    init {
        val soldOutState: State = SoldOutState(this)
        val noQuarterState: State = NoQuarterState(this)
        val hasQuarterState: State = HasQuarterState(this)
        val soldState: State = SoldState(this)
        var state: State = soldOutState
        if (count > 0) {
            state = noQuarterState
        }
    }

    fun insertQuarter() {
        state.insertQuarter()
    }

    fun ejectQuarter() {
        state.ejectQuarter()
    }

    fun turnCrank() {
        state.turnCrank()
    }

    fun releaseBall() {
        if (count != 0) count -= 1
    }
}