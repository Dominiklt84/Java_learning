package lt.viko.eif.dalencinovic;


public class Main {
    static void main() {
        /*int[] intArray=new int[20];
        int[] r100=new int[100];
        for(int i=0;i<intArray.length;i++){
            intArray[i]=i;
        }
        for(int j=0;j<intArray.length;j++){
            System.out.printf("%d\t",intArray[j]);
        }
        for(int i=0;i<intArray.length;i++){
            r100[i]=((int)Math.random()*100);
        }
        for(int j=0;j<intArray.length;j++){
            System.out.printf("%d\n",r100[j]);
        }
        float[] floatValue=new float[20];
        for(int i=0;i<floatValue.length;i++){
            floatValue[i]=((float) Math.random()*100);
        }
        for(int j=0;j<floatValue.length;j++){
            System.out.printf("%f\n",floatValue[j]);
        }
        List<Integer> listValue = new ArrayList<>();
        for(int i=0;i<10;i++) {
            listValue.add((int)(Math.random()*100));
        }
        for(Integer ListValue : listValue) {
            System.out.println(ListValue);
        }

        List<Float> floatValue = new ArrayList<>();
        for(int i=0;i<10;i++) {
            floatValue.add((float)(Math.random()*100));
        }
        for(Float ListValue : floatValue) {
            System.out.println(ListValue);
        }

        List<Integer> list2 = new ArrayList<>();
        for(int i=0;i<120;i++){
            list2.add((int)(Math.random()*100));
        }

        List<Integer> list3= list2.stream().filter(l-> l<10).collect(Collectors.toList());
        int count = (int)list2.stream().count();
        int qn = (int)list3.stream().count();
        int chance = qn * 100 / count;

        list3.forEach(System.out::println);
        System.out.printf("Quantity: %d\n", count);
        System.out.printf("Chance number under 10: %d\n ", chance);

         List<Integer> list= new ArrayList<>();
         for(int i= 0;i<100;i++){
             list.add(((int)(Math.random()*100)));
         }
         System.out.println("1 list: ");
         for(int j=0;j<list.size();j++){
             System.out.printf("%d\t", list.get(j));
         }
        List<Integer> newlist= new ArrayList<>();
         for(int j=0;j<list.size();j++){
             if(list.get(j)%2!=0){
                 newlist.add(list.get(j));
             }
         }
         System.out.printf("\n2 list:\n ");
         for(int j=0;j<newlist.size();j++){
             System.out.printf("%d\t", newlist.get(j));
         }
         List<Integer> listend= newlist.stream().filter(p->p>10&&p<70).collect(Collectors.toList());
         System.out.printf("\n3 list: \n");
         for(int i=0;i<listend.size();i++){
             System.out.printf("%d\t", listend.get(i));
         }
         int count =(int)listend.stream().count();
         System.out.println("\nQuantity: "+count);

        int[] mas= new int[20];
            for(int i = 0;i<20;i++){
                mas[i]=(int) (Math.random()*100);
            }
            for(Integer k:mas){
                System.out.println(k);
            }
        */
        Test obj = new Test();
        obj.setQount(123);
        System.out.println(obj.getQount());


    }
}