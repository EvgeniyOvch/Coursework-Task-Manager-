package ru.netology.javacore;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TodoServer {
    private final int port;
    private Todos todos;


    public TodoServer(int port, Todos todos) {
        this.port = port;
        this.todos = todos;
    }

    public void start() throws IOException {
        System.out.println("Starting server at " + port + "...");

        try (ServerSocket serverSocket = new ServerSocket(port);) { // стартуем сервер один(!) раз
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());

                ) {
                    final String inputJsonTask = in.readLine();
                    final JsonObject root = JsonParser.parseString(inputJsonTask).getAsJsonObject();
                    String type = root.get("type").toString().replaceAll("\"", "");
                    String task = root.get("task").toString().replaceAll("\"", "");

                    switch (type) {
                        case "ADD":
                            todos.addTask(task);
                            break;
                        case "REMOVE":
                            todos.removeTask(task);
                            break;

                    }
                    out.println("Задачи: " + todos.getAllTasks());
                    // обработка одного подключения
                }

            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }

    }
}

