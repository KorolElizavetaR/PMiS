package com.example.quiz

object Constants {

    fun getQuestion() : ArrayList<Question>{

        val questionsList = ArrayList<Question>()

        val que1 = Question(1,
            "Что используется для настройки внешнего вида и стилизации компонентов в Jetpack Compose?",
        "Модификаторы",
            "Стили",
            "Шаблонизаторы", "Менеджеры",
            1)

        val que2 = Question(2,
            "Как в котлине реализована асинхронность и параллельные вычисления?",
            "Synhronizer'ы",
            "Диспетчеры потоков",
            "Коорутины",
            "Рефлексия",
            3)

        val que3 = Question(3,
            "Как известно, мы не можем где угодно запускать корутины внутри компонента, так как асинхронный код может вносить изменения в состояние компонента из другой области корутины. Какой компонент это решает?",
            "CompositeLaunch",
            "LaunchedEffect",
            "CooroutineLauncher",
            "SideLauncher",
            2)

        val que4 = Question(4,
            "Какая библиотека упрощает работу с БД SQLite?",
            "SQLite Manager",
            "Repository",
            "Room",
            "Persistent memory",
            3)

        val que5 = Question(5,
            "Какая функция используется когда надо выполнить над объектом набор операций как единое целое?",
            "with",
            "let",
            "run",
            "apply",
            1)


        val que6 = Question(6,
            "Каким оператором можно объединить два потока данных?",
            "unfold",
            "merge",
            "zip",
            "flow",
            3)


        val que7 = Question(7,
            "Какой аннотацией помечаются функции, сообщающие компилятору, что данные должны преобразоваться в пользовательский интерфейс?",
            "ViewModel",
            "Composable",
            "Interface",
            "Controller",
            2)


        val que8 = Question(8,
            "Какой параметр НЕ принимает функция tween?",
            "durationMillis",
            "easing",
            "delayMillis",
            "timeMillis",
            4)


        val que9 = Question(9,
            "Какой компонент позволяет анимировать замену одного компонента на другой?",
            "ChangeContent",
            "ContentFade",
            "Crossfade",
            "FadeIn",
            3)


        val que10 = Question(10,
            "Какой объект предоставляет функции для программной прокрутки?",
            "ScrollState",
            "Scroller",
            "ScrollPositioner",
            "ScrollTo",
            1)

        questionsList.add(que1)
        questionsList.add(que2)
        questionsList.add(que3)
        questionsList.add(que4)
        questionsList.add(que5)
        questionsList.add(que6)
        questionsList.add(que7)
        questionsList.add(que8)
        questionsList.add(que9)
        questionsList.add(que10)

        return questionsList
    }

}