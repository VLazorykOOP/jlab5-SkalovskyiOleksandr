import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Comparator;

abstract class Record implements Comparable<Record> {
    protected String title;
    protected String author;

    public Record(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public abstract void display();

    @Override
    public int compareTo(Record other) {
        return this.title.compareTo(other.title);
    }
}

class Book extends Record {
    private int year;

    public Book(String title, String author, int year) {
        super(title, author);
        this.year = year;
    }

    public void display() {
        System.out.println("Книга: " + title + " (" + author + "), Рік видання: " + year);
    }
}

class Movie extends Record {
    private int duration;

    public Movie(String title, String author, int duration) {
        super(title, author);
        this.duration = duration;
    }

    public void display() {
        System.out.println("Фільм: " + title + " (" + author + "), Тривалість: " + duration + " хв");
    }
}

public class Main {
    public static void main(String[] args) {
        ArrayList<Record> records = new ArrayList<Record>();

        // Зчитування записів з файлів та додавання їх до колекції
        readRecordsFromFile("books.txt", records, "Book");
        readRecordsFromFile("movies.txt", records, "Movie");

        // Виведення прочитаних даних
        System.out.println("Прочитані дані:");
        displayRecords(records);

        // Сортування за допомогою Collections.sort()
        Collections.sort(records);

        // Виведення відсортованих даних
        System.out.println("Відсортовані дані:");
        displayRecords(records);

        // Додавання нових записів з клавіатури
        addRecordFromKeyboard(records);

        // Знову сортування та виведення на екран
        Collections.sort(records);
        System.out.println("Оновлені дані:");
        displayRecords(records);

        // Створення та сортування ArrayList на основі абстрактного класу
        ArrayList<Record> sortedList = new ArrayList<Record>(records);
        Collections.sort(sortedList, new RecordComparator());

        // Запис відсортованого ArrayList у файл
        writeRecordsToFile("sorted_records.txt", sortedList);
    }

    public static void readRecordsFromFile(String fileName, ArrayList<Record> records, String recordType) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (recordType.equals("Book")) {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    int year = Integer.parseInt(parts[2].trim());
                    Book book = new Book(title, author, year);
                    records.add(book);
                } else if (recordType.equals("Movie")) {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    int duration = Integer.parseInt(parts[2].trim());
                    Movie movie = new Movie(title, author, duration);
                    records.add(movie);
                }
            }
            reader.close();
        } catch (Exception ex) {
            System.out.println("Помилка при читанні файлу: " + ex.getMessage());
        }
    }

    public static void displayRecords(ArrayList<Record> records) {
        for (Record record : records) {
            record.display();
        }
        System.out.println();
    }

    public static void addRecordFromKeyboard(ArrayList<Record> records) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Додавання нового запису");
        System.out.print("Введіть тип запису (Book або Movie): ");
        String recordType = scanner.nextLine();
        System.out.print("Введіть назву: ");
        String title = scanner.nextLine();
        System.out.print("Введіть автора: ");
        String author = scanner.nextLine();
        if (recordType.equals("Book")) {
            System.out.print("Введіть рік видання: ");
            int year = scanner.nextInt();
            Book book = new Book(title, author, year);
            records.add(book);
        } else if (recordType.equals("Movie")) {
            System.out.print("Введіть тривалість: ");
            int duration = scanner.nextInt();
            Movie movie = new Movie(title, author, duration);
            records.add(movie);
        }
        System.out.println("Запис додано!\n");
    }

    public static void writeRecordsToFile(String fileName, ArrayList<Record> records) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (Record record : records) {
                writer.write(record.title + ", " + record.author);
                writer.newLine();
            }
            writer.close();
            System.out.println("Записи успішно записано у файл: " + fileName);
        } catch (Exception ex) {
            System.out.println("Помилка при записі у файл: " + ex.getMessage());
        }
    }

    static class RecordComparator implements Comparator<Record> {
        @Override
        public int compare(Record r1, Record r2) {
            return r1.title.compareTo(r2.title);
        }
    }
}
