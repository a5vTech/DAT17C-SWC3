public class Main {

    public static void main(String[] args){
        MyArrayList<Person> myArrayList = new MyArrayList<>();


        Person p = new Person();
        Person p1 = new Person();
        p.setName("Bob");
        p.setCpr("1234567890");
        p1.setName("Alice");
        p1.setCpr("1234567890");


       for (int i = 0; i < 26; i++){
           myArrayList.add(p);
       }

        System.out.println(myArrayList);
        System.out.println(myArrayList.size());

        myArrayList.remove(1);

        System.out.println(myArrayList);

        System.out.println(myArrayList.size());

        myArrayList.set(5,p1);
        System.out.println(myArrayList);




    }
}
