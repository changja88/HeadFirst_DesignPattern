/*
커맨드 패턴 정리
- 정의 : 요구사항을 객체로 캡슐화 할 수 있으며, 매개변수를 써써 여러 가지 다른 요구 사항을 집어 넣을 수 있다 (커맨드 객체 안으로 리시버가 들어간 형태로 캡슐화)
        이로 인해서 요청 내역을 큐에 저장하거나 로그로 기록할 수도 있으며, 작업취소도 기능도 지원 가능하다

- 인보커, 리시버, 커맨드 객체 3가지 조합으로 사용된다
    - 인보커 -> 커맨드 객체를 실행 시킨다
    - 커맨드 객체 -> 리시버를 동작 시킨다
    - 리시버 -> 실제 기능을 가지고 있다 (command 명령을 받는다)
- 사용 순서
    - 사전 준비
        - 1> 인보커를 만든다 (remote)
        - 2> 리시버를 만든다 (light)
        - 3> 리시버를 인자로 넘겨서 커맨드 객체를 만든다 (lightOnCommand)
        - 4> 인보커에 커맨드를 등록 시킨다
    - 실행
        - 인보커가 커맨드 객체를 실행 시킨다 (buttonWasPressed)
        - 커맨드 객체가 리시버를 실행 시킨다 (excute)
        - 리시버가 일을 한다 (on)
 */

/*
- 메소드 호출을 캡슐화
    - 메소드 호출을 캡슐화하면 계산 과정의 각 부분들을 결정화시킬 수 있기 때문에,
      계산하는 코드를 호출 한 객체에서는 어떤 식으로 일을 처리해야 하는지에 대해 전혀 신경쓰지 않아도 된다
- 커맨드 패턴
    - 1> 클라이언트에서 커맨드 객체 생성
    - 2> setCommand()를 호출하여 인보커에 커맨드 객체를 저장
    - 3> 나중에 클라이언트에서 인보커한테 그 명령을 싱행시켜 달라는 요청을 함
- 메타 커맨드 패턴
    - 명령들로 이루어진 매크로를 만들어서 여러 개의 명령을 한 번에 실행할 수 있다
 */
class Light() {
    fun on() {}
    fun off() {}
}

interface Command {
    fun excute()
}

class LightOnCommand(
    val light: Light // 이 요청에 대한 Receiver 이다
) : Command {
    override fun excute() {
        light.on()
    }
    fun undo(){
        light.off()
    }
}


class SimpleRemoteControl() {
    var slot: Command? = null

    fun setCommand(command: Command) {
        slot = command
    }

    fun buttonWasPressed() {
        slot!!.excute()
    }
}

fun main() { // main 함수가 커맨드패턴에서 클라이언트에 해당 하는 부분
    val remote: SimpleRemoteControl = SimpleRemoteControl() // 인보커, 필요한 작업을 요청할 때 사용할 커맨드 객체를 인자로 전달 받는다
    val light: Light = Light() // 리시버 객체 생성
    val lightOn: LightOnCommand = LightOnCommand(light) // 커맨드 객체 생성, 이때 리시버를 전달 한다

    remote.setCommand(lightOn) // 커맨드 객체를 인보커에게 전달
    remote.buttonWasPressed() // 커맨드 실행
}

