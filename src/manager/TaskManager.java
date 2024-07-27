package manager;

import java.util.ArrayList;
import java.util.HashMap;

import status.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public class TaskManager {
    private static int id = 0;
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, SubTask> subTasks = new HashMap<>();

    public Task createTask(Task task) {
        int taskId = generateId();
        task.setTaskId(taskId);
        tasks.put(task.getTaskId(), task);

        return task;
    }

    public HashMap<Integer, Task> getAllTasks() {
        return tasks;
    }

    public Task getTask(int idTask) {
        Task foundTask = tasks.get(idTask);

        return foundTask;
    }

    public Task updateTask(Task task, Status status) {
        task.setStatus(status);
        tasks.put(task.getTaskId(), task);

        return task;
    }

    public Task deleteTask(int idTask) {
        Task remoteTask = tasks.remove(idTask);

        return remoteTask;
    }

    public HashMap<Integer, Task> deleteAllTasks() {
        tasks.clear();

        return tasks;
    }

    public Epic createEpic(Epic epic) {
        int taskId = generateId();
        epic.setTaskId(taskId);
        epics.put(epic.getTaskId(), epic);

        return epic;
    }

    public HashMap<Integer, Epic> getAllEpics() {
        return epics;
    }

    public Epic getEpic(int idEpic) {
        Epic foundEpic = epics.get(idEpic);

        return foundEpic;
    }

    public Epic updateEpic(Epic epic) {
        epics.put(epic.getTaskId(), epic);

        return epic;
    }

    public Epic deleteEpic(int idEpic) {
        Epic foundEpic = epics.get(idEpic);
        ArrayList<Integer> idSubTasksDeleted = foundEpic.getIdSubTasks();

        for (int idSubTask : idSubTasksDeleted) {
            subTasks.remove(idSubTask);
        }

        epics.remove(idEpic);

        return foundEpic;
    }

    public HashMap<Integer, Epic> deleteAllEpics() {
        epics.clear();
        subTasks.clear();

        return epics;
    }

    public SubTask createSubTask(SubTask subTask) {
        int taskId = generateId();
        subTask.setTaskId(taskId);

        subTasks.put(subTask.getTaskId(), subTask);
        int idEpic = subTask.getIdEpic();

        if (epics.keySet().contains(idEpic)) {
            Epic foundEpic = epics.get(idEpic);
            foundEpic.getIdSubTasks().add(subTask.getTaskId());
            ArrayList<Integer> idSubTaskOfEpic = epics.get(subTask.getIdEpic()).getIdSubTasks();
            Status statusEpic = updateStatusEpic(idSubTaskOfEpic);
            foundEpic.setStatus(statusEpic);
        }

        return subTask;
    }

    public HashMap<Integer, SubTask> getAllSubTasks() {
        return subTasks;
    }

    public SubTask getSubTask(int idSubTask) {
        SubTask foundSubTask = subTasks.get(idSubTask);

        return foundSubTask;
    }

    public SubTask updateSubTask(SubTask subTask, Status status) {
        subTask.setStatus(status);
        subTasks.put(subTask.getTaskId(), subTask);
        Integer idEpic = subTask.getIdEpic();

        if (epics.keySet().contains(idEpic)) {
            Epic foundEpic = epics.get(idEpic);
            ArrayList<Integer> idSubTaskOfEpic = foundEpic.getIdSubTasks();
            Status statusEpic = updateStatusEpic(idSubTaskOfEpic);
            foundEpic.setStatus(statusEpic);
        }

        return subTask;
    }

    public SubTask deleteSubTask(int idSubTask) {
        SubTask foundSubTask = subTasks.get(idSubTask);
        int idEpic = foundSubTask.getIdEpic();
        subTasks.remove(idSubTask);
        Epic foundEpic = epics.get(idEpic);

        if (foundEpic != null) {
            ArrayList<Integer> idSubTasks = foundEpic.getIdSubTasks();
            idSubTasks.remove(idSubTasks.indexOf(idSubTask));
            Status statusEpic = updateStatusEpic(idSubTasks);
            foundEpic.setStatus(statusEpic);
        }

        return foundSubTask;
    }

    public HashMap<Integer, SubTask> deleteAllSubTasks() {
        subTasks.clear();

        for (Epic epic : epics.values()) {
            ArrayList<Integer> idSubTasks = epic.getIdSubTasks();
            idSubTasks.clear();
        }

        return subTasks;
    }

    private Status updateStatusEpic(ArrayList<Integer> idSubTaskOfEpic) {
        ArrayList<Status> statuses = new ArrayList<>();
        Status statusEpic;
        boolean allDone = true;
        boolean allNew = true;

        for (int idSubTask : idSubTaskOfEpic) {
            if (subTasks.keySet().contains(idSubTask)) {
                Status status = subTasks.get(idSubTask).getStatus();
                statuses.add(status);
            }
        }

        for (Status statusSubTask : statuses) {
            if (!statusSubTask.equals(Status.DONE)) {
                allDone = false;
            }

            if (!statusSubTask.equals(Status.NEW)) {
                allNew = false;
            }
        }

        if (allDone) {
            statusEpic = Status.DONE;
        } else if (statuses.isEmpty() || allNew) {
            statusEpic = Status.NEW;
        } else {
            statusEpic = Status.IN_PROGRESS;
        }

        return statusEpic;
    }

    public int generateId() {
        id++;

        return id;
    }

    public HashMap<Integer, SubTask> getSubTasksOfEpic(Epic epic) {
        HashMap<Integer, SubTask> subTasksOfEpic = new HashMap<>();
        int idEpic = epic.getTaskId();

        for (SubTask subTask : subTasks.values()) {
            if (idEpic == subTask.getIdEpic()) {
                subTasksOfEpic.put(subTask.getTaskId(), subTask);
            }
        }

        return subTasksOfEpic;
    }
}
