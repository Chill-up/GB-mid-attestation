import java.io.*;
import java.util.*;

public class Main {
    public static Random random = new Random();
    public static void main(String[] args) {
        List<Toy> toys = readToysFromFile("src/toys.txt");
        System.out.println("Получаем список доступных игрушек из 'базы':");
        System.out.println("| ID    | Name                           | Quantity | Weight   |");
        System.out.println("|-------|--------------------------------|----------|----------|");

        //распечатываем то, что получили
        printToysList(toys);

        System.out.println("================================================================");

        //добавляем игрушку
        addNewToy(toys, "Новая игрушка", 1, 6.3f );

        System.out.printf("\nБыла добавлена новая игрушка:\n%s\n", toys.get(toys.size()-1));
        System.out.println("================================================================");

        //меняем "вес" у игрушки
        System.out.printf("\nМеняем вес у игрушки. \nДо:\n%s\n", toys.get(1));
        System.out.println("================================================================");
        System.out.println("После:");
        changeWeight(toys.get(1),55); //меняем вес
        System.out.println((toys.get(1)));
        System.out.println("================================================================");

        //удаляем игрушку
        System.out.println("\nУдаляем игрушку");
        System.out.println(toys.remove(0));
        System.out.println("================================================================");

        //печатаем список после удаления
        System.out.println("\nПечатаем список после удаления");
        printToysList(toys);
        System.out.println("================================================================");


        System.out.println("Выбираем 3 случайные игрушки из общего списка и помещаем их в список для выдачи");
        ArrayList<String> awaitingWinnings = getWinnings(3, toys);
        System.out.println("\nСписок призов для выдачи");
        printArrayListString(awaitingWinnings);

        System.out.println("================================================================");

        System.out.println("\n********************* Выдаем выигрыш ***************************");
        pushWin(awaitingWinnings);

        System.out.println("\nСписок ожидающих после выдачи приза:");
        printArrayListString(awaitingWinnings);
    }

    public static List<Toy> readToysFromFile(String filePath) {
        List<Toy> toys = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] toyData = line.split(",");

                String name = toyData[0].trim();
                int quantity = Integer.parseInt(toyData[1].trim());
                float weight = Float.parseFloat(toyData[2].trim());

                Toy toy = new Toy(name, quantity, weight);
                toys.add(toy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toys;
    }
    public static void addNewToy(List<Toy> listOfToys, String name, int quantity, float weight){
        Toy newToy = new Toy(name, quantity, weight);
        listOfToys.add(newToy);
    }
    public static void changeWeight(Toy toyToChange, float newWeight){
        toyToChange.setToy_weight(newWeight);
    }
    public static void changeQuantity(Toy toyToChange, int num){
        toyToChange.setToy_quantity(toyToChange.getToy_quantity() + num);
    }
    public static void printToysList(List<Toy> toyToPrint){
        for (Toy t: toyToPrint) {
            System.out.println(t);
        }
    }
    public static void printArrayListString(ArrayList<String> awaitingWinnings){
        for (String i: awaitingWinnings) {
            System.out.println(i);
        }
    }

    public static ArrayList<String> getWinnings(int quantWinners, List<Toy> toysList){
        ArrayList<String> winQueue = new ArrayList<>(quantWinners);
        int winIndex;
        for (int i = 0; i < quantWinners; i++) {
            winIndex = random.nextInt(0,toysList.size()); //выбираем случайные игрушки для списка выигрышей
            System.out.println("В список для выдачи попадает игрушка с Id = " + toysList.get(winIndex).getToy_id());
            winQueue.add(String.valueOf(toysList.get(winIndex)));
            changeQuantity(toysList.get(winIndex),-1); //уменьшаем оставшееся количество игрушек
        }
        return winQueue;
    }
    public static void pushWin(ArrayList<String> awaitingWinnings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/win.txt", false))){
            writer.write("| ID    | Name                           | Quantity | Weight   |\n");
            writer.write("|-------|--------------------------------|----------|----------|\n");
            writer.write(awaitingWinnings.remove(0));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

