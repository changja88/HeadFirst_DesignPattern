/*
컴포지트(복합객체) 패턴
- 정의 : 객체들을 트리 구조로 구성하여 부분과 전체를 나타내는 게층 구조로 마들 수 있다.
        이 패턴을 이용하면 클라이언트에서 개별 객체와 다른 객체들로 구성된 복합객체(composite)를 똑같은 방법으로 다룰 수 있다
- 복합 객체
    - 부분계층 구조 (part-whole hierarchy)
        - 부분들이 모여 있지만 모든 것을 하나로 묶어서 전체로 다룰 수 있는 구조
        -> 부분이나 전체나 똑같다 (부분이나 전체나 똑같이 다룰 수 있다)
    - 복합 객체
        -ex> 메뉴, 서브 메뉴, 서브서브메뉴 모두 복합객체(메뉴) 이다
        - 각 메뉴 안에 다른 메뉴가 있을 수 있기 때문
        - 개별 객체도 결국 메뉴라고 할 수 있다 (다른 객체가 들어 있지 않을 뿐)
- 정리
    - 트리 구조에 있어서 맨 위던, 중간 이던, 마지막에 있던 (자식 노드가 있던 없던) 똑같은 객체로 다룰 수 있다
 */
open abstract class MenuComponent {
    open fun add(menuComponent: MenuComponent) {
        throw java.lang.UnsupportedOperationException()
    }

    open fun remove(menuComponent: MenuComponent) {
        throw java.lang.UnsupportedOperationException()
    }

    open fun getChile(i: Int): MenuComponent {
        throw java.lang.UnsupportedOperationException()
    }

    open fun print() {
        throw java.lang.UnsupportedOperationException()
    }

    open fun isVegetarian(): Boolean {
        throw java.lang.UnsupportedOperationException()
    }

    val name: String
        get() {
            throw UnsupportedOperationException()
        }
    val description: String
        get() {
            throw UnsupportedOperationException()
        }

    val price: Double
        get() {
            throw UnsupportedOperationException()
        }
}

class MenuItem(
    name: String, description: String, val vegetarian: Boolean, price: Double
) : MenuComponent() {

    override fun isVegetarian(): Boolean {
        return vegetarian
    }

    override fun print() {
        if (isVegetarian()) println("v")
    }
}

class Menu(name: String, description: String) : MenuComponent() {
    val menuComponents: ArrayList<MenuComponent> = ArrayList<MenuComponent>()


    override fun add(menuComponent: MenuComponent) {
        menuComponents.add(menuComponent)
    }

    override fun remove(menuComponent: MenuComponent) {
        menuComponents.remove(menuComponent)
    }

    override fun getChile(i: Int): MenuComponent {
        return menuComponents.get(i)
    }

    override fun print() {
        val iterator = menuComponents.iterator()
        while (iterator.hasNext()) {
            val menuComponent: MenuComponent = iterator.next()
            menuComponent.print()
        }
    }
}

class Waitress(
    val allMenues: MenuComponent
) {
    fun printMenu() {
        allMenues.print()
    }
}

class MenueTest {

    fun main() {
        val pancakeHouseMenue = Menu("팬케이크 하우스 메뉴", "아침 메뉴")
        val dinerMenu = Menu("객체마을 식당 메뉴", "점심 메뉴")
        val cafeMenu = Menu("카페 메뉴", "저녁 메뉴")
        val desertMenu = Menu("디저트 메뉴", "디저트를 즐겨보세요!")
        val allMenus = Menu("전체 메뉴", "전체 메뉴")

        allMenus.add(pancakeHouseMenue)
        allMenus.add(dinerMenu)
        allMenus.add(cafeMenu)
        allMenus.add(desertMenu)

        dinerMenu.add(MenuItem("파스타", "마리나라 소스 스파게티", false, 3.89))
        desertMenu.add(MenuItem("애플 파이", "바삭바삭", true, 1.59))

        val waitress: Waitress = Waitress(allMenus)
        waitress.printMenu()
    }
}
/*
단일 역할 원칙을 지키고 있지 않다
- 컴포지트 패턴은 단일 역할 원칙을 깨면서 대신에 투명성을 확보하기 위한 패턴이다
- 투명성(transparancy)란
    - Components인터페이스에 자식들을 관리하기 위한 기능과 잎으로써의 기능을 전부 집어넣음으로써
      클라이언트에서 복합 객체와 잎 노드를 똑같은 방식으로 처리할 수 있도록 하는것
    - 어떤 원소가 복합 객체인지 잎 노드인지가 클라이언트 입장에서는 투명하게 느껴진다

안전성이 떨어진다
- Component클래스에는 두 종류의 기능이 모두 들어있다 보니 안전성은 떨어진다
- 클라이언트에서 어떤 원소에 대해 무의미한, 또는 부적절한 작업을 처리하려고 할 수도 있다 (메뉴 항목에 메뉴를 추가하다든가 하는일)
- 하지만 위 문제를 해결하기 위해서 다른 방향으로 디자인해서 여러 역학을 서로 다른 인터페이스로 분리시키면 매번 instance of연산자를 매번 호출해야 할 수도 있다
 */