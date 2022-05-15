/*
싱글턴 패턴
- 정의 : 클래스의 인스턴스가 하나만 만들어지고, 어디서든지 그 인스턴스에 접근할 수 있도록 하기 위한 패턴이다
- 전역 변수를 사용할 때와 마찬가지로 객체 인스턴스를 어디서든지 접근 할 수 있지만, 전역 변수의 단점은 없다
    - 전역변수에 어떤 객체를 담아놓고 프로그램이 끝날 동안 사용하지 않는다면, 그냥 자원 낭비이다
    - 하지만 싱글턴은 필요할 때만 객체를 만들 수 있다
 */

//고전적인 싱글턴 패턴 구현 방법
class Singleton private constructor() {
    companion object {
        var uniqueInstance: Singleton? = null
        fun getSingleton(): Singleton {
            if (uniqueInstance == null) {
                uniqueInstance = Singleton()
            }
            return uniqueInstance!!
        }
    }
}

/*
위 방식에는 문제가 있다
- 멀티쓰레드로 사용 했을 경우 첫번째 쓰레드에서 인스턴스를 만들어내기전에 두번째 쓰레드에서도 인스턴스 생성을 요청 할 수 있다
- 결과적으로 인스턴스가 여러개 생길 수 있다
*/
class Singleton2 private constructor() {
    companion object {
        var uniqueInstance: Singleton2? = null

        @Synchronized // 이걸 붙여서 동시성 문제를 해결 할 수 있다
        fun getSingleton(): Singleton2 {
            if (uniqueInstance == null) {
                uniqueInstance = Singleton2()
            }
            return uniqueInstance!!
        }
    }
}

/*
위 방법에도 문제가 있다
- 동기화를 하면 속도에 문제가 될 수 있다
- 해결책
    - 1> 속도가 중요하지 않다면 그냥 둔다
    - 2> 처음에 객체를 null로 두는게 아니라 만들어 준다
    - 3> DCL(Double-Checking Locking)을 사용해서 getInstance에서 동기화 되는 부부분을 줄인다
        - DCL을 사용하면, 일단 인스턴스가 생성되어 있는지 확인한 다음, 생성되어 있지 않았을 때만 동기화를 할 수 있다
        - 처음에만 동기화를 하고 나중에는 동기화를 하지 않아도 된다 -> 속도 상승
        - Volatile 사용
 */
class Singleton3 private constructor() {
    companion object {

        @Volatile
        var uniqueInstance: Singleton3? = null

        @Synchronized // 이걸 붙여서 동시성 문제를 해결 할 수 있다
        fun getSingleton(): Singleton3 {
            if (uniqueInstance == null) {
                uniqueInstance = Singleton3()
            }
            return uniqueInstance!!
        }
    }
}

/*
Synchronized 설명
- 쓰레드간의 동기화 문제를 해결 하기 위함
- 이 키워드를 사용하면 한개의 쓰레드가 이 부분을 차지 하고 있으면 다른 쓰레드는 접근 할 수 없도록 막는다

Volatile 설명
- 뜻 자체는 휘발성을 의미
- 변수 선언시 메인 메모리에만 적재하게 된다 (캐쉬에 저장하지 않는다)
    - 멀티쓰레드를 사용하는 경우 각 쓰레드에서 캐쉬한 값이 다를 수 있기 때문에 메인 메모리에만 적재
 */