public class Toy {
    private static int nextId = 1;
    private final int toy_id;
    private final String toy_name;
    private int toy_quantity;
    private float toy_weight;


    public int getToy_id() {
        return toy_id;
    }

    public String getToy_name() {
        return toy_name;
    }

    public int getToy_quantity() {
        return toy_quantity;
    }

    public float getToy_weight() {
        return toy_weight;
    }
    public void setToy_quantity(int toy_quantity) {
        if (toy_quantity < 0) {
            throw new IllegalArgumentException("Количество не может быть меньше нуля!");
        }
        this.toy_quantity = toy_quantity;
    }

    public void setToy_weight(float toy_weight) {
        if (toy_weight < 0 || toy_weight > 100) {
            throw new IllegalArgumentException("Weight должен быть в диапазоне от 0 до 100");
        }
        this.toy_weight = toy_weight;
    }

    /**
     * Конструктор класса Toy. Id автоматически увеличивается на 1 при каждом добавлении.
     * @param toy_name - название игрушки.
     * @param toy_quantity - доступное количество.
     * @param toy_weight - шанс выпадения, вес.
     */
    public Toy(String toy_name, int toy_quantity, float toy_weight) {
        this.toy_id = nextId++;
        this.toy_name = toy_name;
        this.toy_quantity = toy_quantity;
        this.toy_weight = toy_weight;
    }

    @Override
    public String toString() {
        //делаем красивое форматирование с выравниванием по левой стороне и разной шириной символов
        return String.format("| %-5d | %-30s | %-8d | %-8.2f |", toy_id, toy_name, toy_quantity, toy_weight);
    }

}
