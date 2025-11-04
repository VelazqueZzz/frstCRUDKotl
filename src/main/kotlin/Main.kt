data class User(
    val id: Int,
    var name: String,
    var email: String
)

class UserRepository {
    private val users = mutableListOf<User>()
    private var nextId = 1

    // CREATE - Создание пользователя
    fun createUser(name: String, email: String): User {
        val user = User(nextId++, name, email)
        users.add(user)
        return user
    }

    // READ - Получение всех пользователей
    fun getAllUsers(): List<User> {
        return users.toList()
    }

    // READ - Получение пользователя по ID
    fun getUserById(id: Int): User? {
        return users.find { it.id == id }
    }

    // UPDATE - Обновление пользователя
    fun updateUser(id: Int, name: String, email: String): Boolean {
        val user = getUserById(id)
        return if (user != null) {
            user.name = name
            user.email = email
            true
        } else {
            false
        }
    }

    // DELETE - Удаление пользователя
    fun deleteUser(id: Int): Boolean {
        return users.removeIf { it.id == id }
    }
}

fun main() {
    val userRepository = UserRepository()

    while (true) {
        println("\n=== CRUD Меню ===")
        println("1. Создать пользователя")
        println("2. Показать всех пользователей")
        println("3. Найти пользователя по ID")
        println("4. Обновить пользователя")
        println("5. Удалить пользователя")
        println("6. Выйти")
        print("Выберите действие: ")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                print("Введите имя: ")
                val name = readLine() ?: ""
                print("Введите email: ")
                val email = readLine() ?: ""
                val user = userRepository.createUser(name, email)
                println("Создан пользователь: $user")
            }
            2 -> {
                val users = userRepository.getAllUsers()
                if (users.isEmpty()) {
                    println("Пользователей нет")
                } else {
                    println("Список пользователей:")
                    users.forEach { println(it) }
                }
            }
            3 -> {
                print("Введите ID: ")
                val id = readLine()?.toIntOrNull()
                if (id != null) {
                    val user = userRepository.getUserById(id)
                    if (user != null) {
                        println("Найден пользователь: $user")
                    } else {
                        println("Пользователь с ID $id не найден")
                    }
                }
            }
            4 -> {
                print("Введите ID для обновления: ")
                val id = readLine()?.toIntOrNull()
                if (id != null) {
                    print("Введите новое имя: ")
                    val name = readLine() ?: ""
                    print("Введите новый email: ")
                    val email = readLine() ?: ""
                    val success = userRepository.updateUser(id, name, email)
                    if (success) {
                        println("Пользователь обновлен")
                    } else {
                        println("Пользователь с ID $id не найден")
                    }
                }
            }
            5 -> {
                print("Введите ID для удаления: ")
                val id = readLine()?.toIntOrNull()
                if (id != null) {
                    val success = userRepository.deleteUser(id)
                    if (success) {
                        println("Пользователь удален")
                    } else {
                        println("Пользователь с ID $id не найден")
                    }
                }
            }
            6 -> {
                println("Выход...")
                return
            }
            else -> println("Неверный ввод")
        }
    }
}