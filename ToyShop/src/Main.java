import java.io.*;
import java.util.*;

public class Main {
    public static Random random = new Random();
    public static void main(String[] args) {
        System.out.println("Получаем список доступных игрушек из 'базы':");
        List<Toy> toys = readToysFromFile("toys.txt");
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
        pushWin(awaitingWinnings, "src/win.txt");

        System.out.println("\nСписок игрушек, ожидающих выдачи после выдачи выигрыша:");
        printArrayListString(awaitingWinnings);
    }

    /**
     * Метод получения списка доступных игрушек из файла.
     * @param filePath - путь к файлу
     * @return Метод возвращает список объектов типа Toy.
     */
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

    /**
     * Метод добавления новой игрушки в список.
     * @param listOfToys - список в которой мы хотим добавить новый элемент.
     * @param name - Название игрушки.
     * @param quantity - доступное количество.
     * @param weight - шанс выпадения, вес.
     */
    public static void addNewToy(List<Toy> listOfToys, String name, int quantity, float weight){
        Toy newToy = new Toy(name, quantity, weight);
        listOfToys.add(newToy);
    }

    /**
     * Метод изменения шанса выпадения игрушки, веса.
     * @param toyToChange - игрушка, для которой изменяем вес.
     * @param newWeight - новое значение веса (float).
     */

    public static void changeWeight(Toy toyToChange, float newWeight){
        toyToChange.setToy_weight(newWeight);
    }

    /**
     * Метод изменения доступного кол-ва. К текущему значению прибавляется переданное.
     * @param toyToChange - Игрушка, которую изменяем.
     * @param num - сколько хотим прибавить или убавить. Для убавления передавайте -num.
     */
    public static void changeQuantity(Toy toyToChange, int num){
        toyToChange.setToy_quantity(toyToChange.getToy_quantity() + num);
    }

    /**
     * Метод для красивого вывода списка игрушек на экран.
     * @param toyToPrint - какой список хотим распечатать.
     */
    public static void printToysList(List<Toy> toyToPrint){
        for (Toy t: toyToPrint) {
            System.out.println(t);
        }
    }

    /**
     * Метод для красивого вывода Массива строк ArrayList на экран.
     * @param awaitingWinnings - ArrayList<String> который нужно красиво распечатать.
     */
    public static void printArrayListString(ArrayList<String> awaitingWinnings){
        for (String i: awaitingWinnings) {
            System.out.println(i);
        }
    }

    /**
     * Метод формирования списка выигрышей.
     * @param quantWinners - необходимое кол-во выигрышей.
     * @param toysList - список игрушек из которого выбираются выигрыши.
     * @return - возвращается список строк ArrayList<String> с игрушками, ожидающими выдачи.
     */
    public static ArrayList<String> getWinnings(int quantWinners, List<Toy> toysList){
        ArrayList<String> winQueue = new ArrayList<>(quantWinners);
        int winIndex;
        for (int i = 0; i < quantWinners; i++) {
            winIndex = random.nextInt(0,toysList.size()); //выбираем случайные игрушки для списка выигрышей
            System.out.println("В список для выдачи попадает игрушка с Id = " + toysList.get(winIndex).getToy_id());
            winQueue.add(String.valueOf(toysList.get(winIndex)));
            changeQuantity(toysList.get(winIndex),-1); //уменьшаем оставшееся количество игрушек
            //если кол-во после уменьшения стало равно нулю, то убираем игрушку из общего списка.
            if (toysList.get(winIndex).getToy_quantity() == 0) {
                toysList.remove(winIndex);
            }
        }
        return winQueue;
    }

    /**
     * Метод выдачи выигрыша.
     * Берет первый элемент из списка выигрышей, записывает его в файл и удаляет из списка.
     * @param awaitingWinnings - список игрушек, ожидающих выдачи.
     * @param fileName - путь к файлу, куда записать выдачу.
     */
    public static void pushWin(ArrayList<String> awaitingWinnings, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))){
            writer.write("| ID    | Name                           | Quantity | Weight   |\n");
            writer.write("|-------|--------------------------------|----------|----------|\n");
            writer.write(awaitingWinnings.remove(0));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

