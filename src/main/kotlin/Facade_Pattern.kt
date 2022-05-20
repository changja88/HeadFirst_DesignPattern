/*
퍼사드(facade) 패턴
- facade 뜻 : 겉모양, 외관
    -> 인터페이스를 깔끔하게 다듬어 준다
- 인터페이스를 단순화시키기 위해서 인터페이스를 변경한다

정의
- 어떤 서브시스템의 일련의 인터페이스에 대한 통합된 인터페이스를 제공한다. 퍼사드에서 고수준 인터페이스를 정의하기 때문에 서브시스템을 더 쉽게 사용 할 수 있다

용도별 구별
- 데코레이터 -> 한 인터페이스를 다른 인터페이스로 변환 (커피를 휘핑으로 감싼다 -> 커피인터페이스에서 휘핑 인터페이스로 변경)
- 어답터 -> 인터페이스는 바꾸지 않고 책임(기능)만 추가
- 퍼사드 -> 인터페이스를 간단하게 바꿈 (기존에 사용하고 있는 인터페이스가 복잡하기 때문)
 */
// 이전 상황
fun watchMovie() {
    popper.on()
    lights.dim()
    screen.down()
    projector.on()
    dvd.on()
    // 줄줄이 엄청 많음
}

// 퍼사드 도입 이후
fun watchMovie2() {
    val homeTheater: HomeTheater = HomeTheater(popper, light, screen, projector, dvd)
    homeTheater.watchMovie()
}

/*
달성 할 수 있는 목표
- 최소 지식 원칙(데메테르의 법칙) : 정말 친한 친구하고만 얘기하자
- 장점
    - 클래스들이 복잡하게 얽혀서 시스템의 한 부분을 변경했을 때 다른 부분까지 줄ㅈ루이 고쳐야 되는 상황을 미리 방지할 수 있다
- 방법
    - 다음 네 종류의 객체의 메소드만을 호출한다
        - 객체 자체
        - 메소드에 매개변수로 전달된 객체
        - 그 메소드에서 생성하거나 인스턴스를 마든 객체
        - 그 객체에 속하는 구성요소
    - ex) 어떤 메소드를 호출한 결과로 리턴 받는 객체에 있는 메소드를 호출할 경우
          -> 다른 객체의 일부분에 대해서 요청을 하게 되는 꼴
          -> 직접적으로 알고 지내는 객체의 개수가 늘어난다
          -> 해결책 : 객체 쪽에서 대신 요청을 하도록 만들어야 한다

- 퍼사드가 어떻게 최소 지식 원칙 달성을 도와주나
    - A가 B,D,E,F를 모두 알고 사용을 하고 있었다
    - A'를 만들고 A는 A'와만 소통을 하고 A'가 B,D,E,F와 소통한다

 */
// 원칙을 따리지 않는 경우
fun getTemp() {
    // station으로부터 thermometer라는 객체를 받은 다음, 그 객체의 메소드를 직접 호출 한다
    val thermometer: Thermometer = station.getThermometer()
    return thermometer.getTemperature()
}

// 원칙을 따르는 경우
fun getTemp() {
    // 최소 지식 원칙을 적용하여 station클래스에 thermometer에 요청 해주는 메소드를 추가했다
    return station.getThermometer()
}

//상세 예시
class Car(
    val engine: Engine // 클래스의 구성요소 -> 이 구성요소의 메소드는 호출 해도 된다
) {
    fun start(key: Key) { // 매개변수로 전달된 객체의 메소드는 호출 해도 된다
        val doors: DDoors = Doors() // 새로운 객체 생성, 이 객체의 메소드는 호출해도 된다

        val authorized: Boolean = key.turns()

        if (authorized) {
            engine.start() // 클래스 구성 요소 -> 호출 가능
            updateDashboardDisplay() // 객체 내에 있는 메소드 -> 호출 가능
            doors.lock() // 직접 만든 객체 -> 호출 가능
        }
    }

    fun updateDashboardDisplay() {

    }
}
