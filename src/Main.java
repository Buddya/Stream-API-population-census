import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)]));
        }

        getPplUnder18(persons); // люди до 18
        getRecruitList(persons); // призывники
        getWorkableList(persons); // работоспособные с высшим обр.
    }

    public static void getPplUnder18(Collection<Person> persons) {
        System.out.println("Количество несовершеннолетних:");
        long lessThan18 = persons.stream().filter(p -> p.getAge() < 18)
                .limit(10) // <- delete this for full list
                .count();
        System.out.println(lessThan18);
    }

    public static void getRecruitList(Collection<Person> persons) {
        System.out.println("Список фамилий призывников:");
        List<String> listOfRecruits = persons.stream()
                .filter(p -> p.getSex() == (Sex.MAN))
                .filter(p -> (p.getAge() >= 18 && p.getAge() <= 27))
                .limit(10) // <- delete this for full list
                .map(Person::getFamily)
                .collect(Collectors.toList());
        for (String family : listOfRecruits) {
            System.out.println(family);
        }
    }

    public static void getWorkableList(Collection<Person> persons) {
        System.out.println("Список работоспособных людей:");
        List<Person> listOfWorkablePpl = persons.stream()
                .filter(p -> p.getEducation() == Education.HIGHER)
                .filter(p -> p.getAge() >= 18)
                .filter(p -> (p.getSex() == Sex.WOMAN && p.getAge() < 60)
                        || (p.getSex() == Sex.MAN && p.getAge() < 65))
                .limit(10) // <- delete this for full list
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println(listOfWorkablePpl);
    }
}
