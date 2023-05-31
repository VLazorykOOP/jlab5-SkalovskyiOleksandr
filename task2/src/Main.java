import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        RecordStack recordStack = new RecordStack();

        // Додавання номерів записів до стеку
        recordStack.push(1);
        recordStack.push(2);
        recordStack.push(3);
        recordStack.push(4);
        recordStack.push(5);

        // Отримання елементів запису зі стеку
        int record1 = recordStack.getRecordByIndex(0);
        int record2 = recordStack.getRecordByIndex(1);
        int record3 = recordStack.getRecordByIndex(2);

        System.out.println("Номери записів:");
        System.out.println(record1);
        System.out.println(record2);
        System.out.println(record3);
    }

    static class RecordStack extends Stack<Integer> {
        public int getRecordByIndex(int index) {
            return get(size() - index - 1);
        }
    }
}
