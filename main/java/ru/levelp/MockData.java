package ru.levelp;

import java.util.*;

public class MockData {

    private static final String[] names = {
            "Billy", "Raymond", "Christina", "Lillian", "Louise", "Todd", "Eugene",
            "Jennifer", "Virginia", "Joyce", "Christopher", "Cheryl", "Henry", "Chris",
            "Cynthia", "Amy", "Carl", "Shawn", "Frank", "Jason", "Rachel", "James",
            "Jeremy", "Robin", "Gloria", "David", "Scott", "Scott", "Kevin", "Raymond",
            "Paula", "Adam", "Anthony", "Jack", "Gary", "Christina", "Julie", "Lori",
            "Martha", "Justin", "Johnny", "Russell", "Kelly", "Roy", "Janice", "Evelyn",
            "Willie", "Chris", "Alan", "Jane", "Linda", "Carol", "Sean", "Lillian",
            "Louise", "Marilyn", "Mary", "Julie", "Sean", "Robert", "Adam", "Samuel",
            "Evelyn", "Philip", "Melissa", "Jeremy", "Pamela", "Julie", "Kevin",
            "Kathy", "Heather", "Matthew", "Carl", "Jennifer", "Kimberly", "Christopher",
            "Scott", "Jesse", "Dennis", "John", "Donna", "Louis", "Victor", "Gloria",
            "Emily", "Betty", "Aaron", "Irene", "Teresa", "Mildred", "Martin", "Lisa",
            "Carol", "Donald", "Adam", "Beverly", "Aaron", "Fred", "Martin", "Daniel"
    };

    private static final String[] lastNames = {
            "Rogers", "Mendoza", "Gonzalez", "Schmidt", "Olson", "Webb", "Owens", "Mendoza",
            "Hunt", "Ferguson", "Olson", "Phillips", "West", "Morales", "Jordan", "Ramos",
            "Franklin", "Fowler", "Taylor", "Taylor", "Webb", "Webb", "Jenkins", "Coleman",
            "Hunter", "Hicks", "Kelley", "Hernandez", "Crawford", "Watson", "Bowman", "Ryan",
            "Gardner", "Armstrong", "Lopez", "Cruz", "Nelson", "Jones", "Evans", "Chapman",
            "Reynolds", "Mills", "Harvey", "Wilson", "Sullivan", "Bishop", "Marshall",
            "Campbell", "Dean", "Williams", "Fields", "Wheeler", "Owens", "Woods", "James",
            "Ward", "Mccoy", "Fernandez", "Robinson", "Williams", "Fields", "Wood", "Carr",
            "Barnes", "Clark", "Hunt", "Ortiz", "Morgan", "Cook", "Scott", "Richardson",
            "Cunningham", "Wood", "Hudson", "Mcdonald", "Howard", "Perez", "Campbell",
            "Garza", "Rivera", "Wells", "Lewis", "Rice", "Alvarez", "Nelson", "Johnson",
            "Montgomery", "Jackson", "Jacobs", "Edwards", "Day", "Owens", "White", "Owens",
            "Flores", "Butler", "Davis", "Fox", "Day", "Moreno"
    };

    private Random random;

    public MockData() {
        this.random = new Random();
    }

    public String generateName(){
        return names[random.nextInt(names.length)] + " " + lastNames[random.nextInt(lastNames.length)];
    }

    public String[] generatePhones() {
        String[] phones = new String[random.nextInt(10) + 1];
        for (int i = 0; i < phones.length; i++) {
            phones[i] = generatePhone();
        }
        return phones;
    }

    private String generatePhone() {
        return String.format(
                "%s-(%s)%s-%s",
                generateNumbers(random, random.nextInt(3) + 1),
                generateNumbers(random, 3),
                generateNumbers(random, 3),
                generateNumbers(random, 4)
        );
    }

    ;

    public String generateNumbers(Random r, int num) {
        String s = "";
        for (int i = 0; i < num; i++) {
            s += r.nextInt(10);
        }
        return s;
    }
}
