package `2`

/*
문제 상황
- WeatherData 객체를 사용하여 현재 조건, 기상 통계, 기상 예측, 이렇게 세 항목을 디스플레이 장비에서 갱신해 가면서 보여줘야 한다
    - WeatherData클래스에는 세가 측정값을 알아내기 위한 게터 메소드가 있다
    - 새로운 기상 측정 데이터가 나올 때마다 measurementsChanges()메소드가 호출된다 -> 어떤 원리로 동작하는지는 알 필요가 없다
    - 기상 데이터를 사용하는 세 개의 디스플레이 항목을 구현해야 하며, 새로운 측정값이 들어올 때마다 디스플레이를 갱신해야 한다
    - 시스템이 확장 가능해야한다. 다른 개발자들이 별도의 디스플레이 항목을 만들수도 있도록 해야하고, 사용자들이 애플리케이션에서 마음대로
      디스플레이 항목을 추가/제거 할 수 있도록 해야 한다
 */
/*
문제가 있는 구현
    class WeatherData() {
        fun measuremenetsChanged() {
            val temp : Float = getTemperature();
            val humidity : Float = getHumidity();
            val pressure : Float = getPressure();

            currentConditionDisplay.update(temp, humidity, pressure)
            statisticsDisplay.update(temp, humidity, pressure)
            forecastDisplay.update(temp, humidity, pressure)
            -> 바뀔 수 있는 부분은 캡슐화를 해야한다
            -> 구체적인 구현에 맞춰서 코딩했기 때문에 프로그램을 고치지 않고는 다른 디스플레이 항목을 추가/제거 할 수 없다
        }
    }
    - 옵저버 패턴을 이용하면 위 문제를 해결 할 수 있다
 */
/*
옵저버 패턴
- 출판사 + 구독자 = 옵저버 패턴
- 한 객체의 상태가 바뀌면 그 객체ㅔ 의존하는 다른 객체들한테 연락이 가고 자동으로 내용이 갱신되는 방식으로 일대다(one-to-many)의존성을 정의한다
- 느슨한 결합 (Loose coupling)
    - 두 객체가 상호작용을 하긴 하지만 서로에 대해 잘 모르는 것을 의미
- 디자인 원칙 : 서로 상호작용을 하는 객체 사이에서는 가능하면 느슨하게 결합하는 디자인을 사용해야 한다
 */

// 구현
interface Subject {
    fun registerObserver(o: Observer)
    fun removeObserver(o: Observer)
    fun notifyObservers()
}

interface Observer {
    fun update(temp: Float, humidity: Float, pressure: Float)
}

interface DisplayElement {
    fun display()
}

class WeatherData(
    var observers: ArrayList<Observer> = ArrayList()
) : Subject {
    var temperature: Float = 0f
    var humidity: Float = 0f
    var pressure: Float = 0f


    override fun notifyObservers() {
        for (i in 0..observers.size - 1) {
            val observer = observers.get(i)
            observer.update(temp = temperature, humidity = humidity, pressure = pressure)
        }
    }

    fun measurementsChanged() {
        notifyObservers()
    }

    fun setMeasurements(temperature: Float, humidity: Float, pressure: Float) {
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        measurementsChanged()
    }

    override fun removeObserver(o: Observer) {
        val i: Int = observers.indexOf(o)
        if (i > 0) {
            observers.removeAt(i)
        }
    }

    override fun registerObserver(o: Observer) {
        observers.add(o)
    }

}

class CurrentConditionsDisplay(val weatherData: Subject) : Observer, DisplayElement {
    var temperature: Float = 0f
    var humidity: Float = 0f
    var pressure: Float = 0f

    init {
        weatherData.registerObserver(this)
    }


    override fun update(temp: Float, humidity: Float, pressure: Float) {
        this.temperature = temperature
        this.humidity = humidity
        display()
    }

    override fun display() {
        println("Current conditions : " + temperature + humidity + pressure)
    }
}

fun usage_main() {
    val weatherData = WeatherData()
    val currentDisplay = CurrentConditionsDisplay(weatherData)
    weatherData.setMeasurements(80f, 65f, 30.4f);
}

