import status.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import manager.TaskManager;

class Main {

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        Task taskOne = manager.createTask(new Task("Купить билет", "Купить билет на поезд. На 12-ое "
                + "августа."));
        Task taskTwo = manager.createTask(new Task("Отправить письмо", "Сообщить друзьям о своём "
                + "приезде."));

        Epic epicOne = manager.createEpic(new Epic("Прочитать книгу", "Прочитать книгу за два дня."));
        SubTask subTaskOneOfEpicOne = manager.createSubTask(new SubTask("Прочитать первые 10 глав",
                "Прочитать за один день", epicOne.getTaskId()));
        SubTask subTaskTwoOfEpicOne = manager.createSubTask(new SubTask("Прочитать оставшиеся 9 глав",
                "Прочитать за один день", epicOne.getTaskId()));

        Epic epicTwo = manager.createEpic(new Epic("Приготовить ужин", "Ужин на четверых"));
        SubTask subTaskOneOfEpicTwo = manager.createSubTask(new SubTask("Заказать еду из ресторана",
                "Выбрать ресторан.", epicTwo.getTaskId()));

        System.out.println("Эпики:");
        System.out.println(manager.getAllEpics());
        System.out.println("");
        System.out.println("Задачи:");
        System.out.println(manager.getAllTasks());
        System.out.println("");
        System.out.println("Подзадачи:");
        System.out.println(manager.getAllSubTasks());
        System.out.println("");

        manager.updateTask(taskOne, Status.DONE);
        Task newTaskOne = manager.getTask(taskOne.getTaskId());
        System.out.println("Обновили статус задачи " + "'" + newTaskOne.getName() + "':");
        System.out.println(newTaskOne);
        System.out.println("");

        manager.updateTask(taskTwo, Status.IN_PROGRESS);
        Task newTaskTwo = manager.getTask(taskTwo.getTaskId());
        System.out.println("Обновили статус задачи " + "'" + newTaskTwo.getName() + "':");
        System.out.println(newTaskTwo);
        System.out.println("");


        manager.updateSubTask(subTaskOneOfEpicOne, Status.DONE);
        SubTask newSubTaskOneOfEpicOne = manager.getSubTask(subTaskOneOfEpicOne.getTaskId());
        System.out.println("Обновили статус подзадачи " + "'" + newSubTaskOneOfEpicOne.getName() + "':");
        System.out.println(newSubTaskOneOfEpicOne);
        System.out.println("");

        manager.updateSubTask(subTaskTwoOfEpicOne, Status.IN_PROGRESS);
        SubTask newSubTaskTwoOfEpicOne = manager.getSubTask(subTaskTwoOfEpicOne.getTaskId());
        System.out.println("Обновили статус подзадачи " + "'" + newSubTaskTwoOfEpicOne.getName() + "':");
        System.out.println(newSubTaskTwoOfEpicOne);
        System.out.println("");

        manager.updateSubTask(subTaskOneOfEpicTwo, Status.DONE);
        SubTask newSubTaskOneOfEpicTwo = manager.getSubTask(subTaskOneOfEpicTwo.getTaskId());
        System.out.println("Обновили статус подзадачи " + "'" + newSubTaskOneOfEpicTwo.getName() + "':");
        System.out.println(newSubTaskOneOfEpicTwo);
        System.out.println("");

        System.out.println("Обновлённые эпики:");
        System.out.println(manager.getAllEpics());
        System.out.println("");

        manager.deleteTask(taskOne.getTaskId());
        System.out.println("Удалили задачу " + "'" + taskOne.getName() + "'. " + "Оставшиеся задачи:");
        System.out.println(manager.getAllTasks());
        System.out.println("");

        manager.deleteEpic(epicTwo.getTaskId());
        System.out.println("Удалили эпик " + "'" + epicTwo.getName() + "'. " + "Оставшиеся эпики:");
        System.out.println(manager.getAllEpics());
        System.out.println("");
    }
} 