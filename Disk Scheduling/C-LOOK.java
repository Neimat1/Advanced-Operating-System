import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;
public class CLOOK  {
    static void CLOOK(int request[], int head,String direction){
        Vector<Integer>CopyRequests =new Vector<Integer>();
        int index=0;
        for(int i=0;i<request.length;i++){
            CopyRequests.add(request[i]);
        }

        CopyRequests.add(head);
        Collections.sort(CopyRequests);
        for(int i =0; i<CopyRequests.size();i++){
            if(CopyRequests.get(i) ==head){
                index=i;
                break;
            }
        }
        if(direction.equals("right")){
            PrintMovement(CopyRequests, index);
        }
        else if(direction.equals("left")){

            PrintMovement_left(CopyRequests, index);
        }
        else{
            System.out.println("Direction isn't correct");
        }
    }
    static void PrintMovement_left(Vector<Integer>CopyRequests,int index){
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Movement :-");
        Vector<Integer>SeekTime =new Vector<Integer>();
        for(int j=index;j>0;j--){
            SeekTime.add(Math.abs(CopyRequests.get(j-1)-CopyRequests.get(j)));
            System.out.println(CopyRequests.get(j)+"    -   "+CopyRequests.get(j-1));
            CopyRequests.remove(j);
        }
        for(int j=0;j<CopyRequests.size()-index+1;j++){
            SeekTime.add(Math.abs(CopyRequests.get(j+1)-CopyRequests.get(j)));
            System.out.println(CopyRequests.get(j)+"    -   "+CopyRequests.get(j+1));
        }
        PrintDistance(SeekTime, index);
    }

    static void PrintMovement(Vector<Integer>CopyRequests,int index){
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Movement :-");
        Vector<Integer>SeekTime =new Vector<Integer>();
        for(int j=index;j<CopyRequests.size()-1;j++){
            SeekTime.add(Math.abs(CopyRequests.get(j+1)-CopyRequests.get(j)));
            System.out.println(CopyRequests.get(j)+"    -   "+CopyRequests.get(j+1));
        }
        SeekTime.add(Math.abs(CopyRequests.get(CopyRequests.size()-1)-CopyRequests.get(0)));
        System.out.println(CopyRequests.get(CopyRequests.size()-1)+"    -   "+CopyRequests.get(0));
        for(int j=0;j<index-1;j++){
            SeekTime.add(Math.abs(CopyRequests.get(j+1)-CopyRequests.get(j)));
            System.out.println(CopyRequests.get(j)+"    -   "+CopyRequests.get(j+1));
        }
        PrintDistance(SeekTime, index);
    }
    static void PrintDistance(Vector<Integer>SeekTime,int index){
        int TotalCylinders=0;
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Distance :-");
        for(int i=0;i<SeekTime.size();i++){
            TotalCylinders+=SeekTime.get(i);
            System.out.println(SeekTime.get(i));

        }
        System.out.println("\n Total number of cylinders is " + TotalCylinders);
    }
    public static void main(String[] args) {
        System.out.println("Enter number Of requests :- ");
        Scanner input =new Scanner(System.in);
        Scanner inputString =new Scanner(System.in);
        int numRequests=input.nextInt();
        int Request[] = new int[numRequests];
        System.out.println("Enter requests :-");
        for(int i=0;i<numRequests;i++){
            int req=input.nextInt();
            Request[i]=req;
        }
        System.out.println("Enter head :- ");
        int head=input.nextInt();
        System.out.println("Enter direction :- ");
        String direction=inputString.nextLine();
        CLOOK(Request,head,direction);
    }
}/*
****** Test Program **************
Enter number Of requests :- 8
Enter requests :-
98
183
37
122
14
124
65
67
Enter head :- 53
Enter direction :- left
*/
