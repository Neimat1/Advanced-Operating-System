import java.util.Scanner;
import static java.lang.Integer.parseInt;
import static java.lang.System.exit;
public class Banker {
    private int [] sequence;
    private int [] available;
    private int [][] allocated;
    private int [][] need;
    private int [][] max;
    private int P;
    private int R;
    public Banker( int [] available, int [][] allocated,int [][] max,int P,int R){
        this.available     = available;
        this.allocated     = allocated;
        this.max           = max;
        this.P             = P;
        this.R             = R;
    }
    int [][] NeedCopy(){
        int [][]needCopy;
        needCopy = new int[need.length][];
        for (int i = 0; i < need.length; i++) {
            needCopy[i] = need[i].clone();
        }
        return  needCopy;
    }
    int [][] AllocatedCopy(){
        int [][] allocatedCopy;
        allocatedCopy = new int[allocated.length][];
        for (int i = 0; i < allocated.length; i++) {
            allocatedCopy[i] =allocated[i].clone();
        }
        return allocatedCopy;
    }
    int [] AvailableCopy(){
        int []availableCopy;
        availableCopy =available.clone();
        return availableCopy;
    }


    public int[][] calcNeed(){
        need=new int [P][R];
        System.out.print("----------Need Resources Matrix---------\n");
        System.out.print("P.no\t"+" "+"A"+"\t "+"B"+"\t "+"C\n");
        for (int i = 0 ; i < P ; i++)
        {
            System.out.print("P"+i);
            for (int j = 0 ; j < R ; j++)
            {
                need[i][j] = max[i][j] - allocated[i][j];
                System.out.print("\t "+need[i][j]+" ");
            }
            System.out.println();
        }
        return need;
    }
    ////////////////////////////

    public boolean checkNeed(int av[],int ne[],int r)
    {
        for(int i=0;i<r;i++)
        {
            if(ne[i]<=av[i]){}
            else
                return false;
        }
        return true;
    }
    public boolean checkFinish(boolean f[],int p)
    {
        for(int i=0;i<p;i++)
        {
            if(f[i]){}
            else
                return false;
        }
        return true;
    }
    public int[] sum(int av[],int al[],int r)
    {
        for(int i=0;i<r;i++)
        {
            av[i]=av[i]+al[i];
        }
        return av;
    }
    public void printEditedAvailable(int arr[],int n)
    {
        for(int i=0;i<n;i++)
        {
            System.out.print(" "+arr[i]+" ");
        }
        System.out.print(" \n");
    }
    /////////////////////////////

    public boolean Safety (int []availableCopy,int [][]allocatedCopy,int [][]needCopy)
    {

        boolean []finish = new boolean[P];
        sequence = new int[P];
        int count=0;
        int safe=0,unsafe=0;

        System.out.print("\nCurrent Avaialble resources: ");
        printEditedAvailable(availableCopy,R);
        for(int i=0;i<P;i++)
        {
            if(checkNeed(availableCopy,needCopy[i],R)&&finish[i]==false)
            {
                availableCopy=sum(availableCopy,allocatedCopy[i],R);

                System.out.println("\nP"+i+" need <= available "+" available = available + P"+i+" allocation");
                System.out.print("available: ");
                printEditedAvailable(availableCopy,R);
                System.out.println ();

                sequence[count]=i;
                count++;
                finish[i]=true;
                safe++;
            }

            if(i==P-1&&(!checkFinish(finish,P))&&safe!=unsafe)
            {
                unsafe=safe;
                i=-1;
            }
            else if(i==P-1&&(!checkFinish(finish,P))&&safe==unsafe)
            {
                break;
            }
        }

        for(int i=0;i<P;i++)
        {
            if(finish[i]==true){}
            else{return false;}
        }

        return true;

    }
    //////////////////////////////////////////////////////////

    public void request( int request[], int processnumber){
        int [] availableCopy=AvailableCopy();
        int [][] needCopy=NeedCopy();
        int [][]allocatedCopy=AllocatedCopy();

        int i;
        for(i=0;i<R;i++) {

            if (request[i] > needCopy[processnumber][i]) {
                System.out.println("Process has exceeded to max claim resources");
                break;
            }
        }
        int j;
        if(i==R){
            for( j=0;j<R;j++) {
                if (request[j] > availableCopy[j]) {
                    System.out.println("Process must be wait as the resources are not available");
                    break;
                }
            }
            if(j==R){
                for(int x=0;x<R;x++){
                    availableCopy[x]-=request[x];
                    allocatedCopy[processnumber][x]+=request[x];
                    needCopy[processnumber][x]-=request[x];
                }
            }


        }

        int []availableCopy2;
        availableCopy2 =availableCopy.clone();
        if(Safety(availableCopy2 ,allocatedCopy,needCopy)){
            allocated=allocatedCopy;
            available=availableCopy;
            need=needCopy;
            System.out.println("\nSafe State :- request is granted");
        }
        else System.out.println("\nUnsafe State :- request is not granted");

        System.out.println("after request");
        System.out.println("-------Allocation Matrix---------");
        System.out.print("P.no"+" "+"A"+"\t"+"B"+"\t"+"C\n");
        for(int n=0;n<P;n++){
            System.out.print("P"+n);
            for(int k=0;k<R;k++){
                System.out.print("  "+allocated[n][k]+" ");
            }
            System.out.println();
        }

        calcNeed();
        System.out.println("-------Available Resources---------");
        System.out.print("A"+"\t"+"B"+"\t"+"C\n");
        printEditedAvailable(available,R);

    }
    public void release( int release[], int processnumber){
        int [] availableCopy=AvailableCopy();
        int [][] needCopy=NeedCopy();
        int [][]allocatedCopy=AllocatedCopy();

        int i;
        for(i=0;i<R;i++) {

            if (release[i] > allocatedCopy[processnumber][i]) {
                System.out.println("Can't release :/");
                break;
            }
        }
        if(i==R){
            for(int x=0;x<R;x++){
                availableCopy[x]+=release[x];
                allocatedCopy[processnumber][x]-=release[x];
                needCopy[processnumber][x]+=release[x];
            }
            allocated=allocatedCopy;
            available=availableCopy;
            need=needCopy;
            System.out.println("\n");
            System.out.println("after release");
            System.out.println("-------Allocation Matrix---------");
            System.out.print("P.no"+" "+"A"+"\t"+"B"+"\t"+"C\n");
            for(int n=0;n<P;n++){
                System.out.print("P"+n);
                for(int k=0;k<R;k++){
                    System.out.print("  "+allocated[n][k]+" ");
                }
                System.out.println();
            }
            calcNeed();
            System.out.println("-------Available Resources---------");
            System.out.print("A"+"\t"+"B"+"\t"+"C\n");
            printEditedAvailable(available,R);
        }


    }
    ////////////////////////
    public static void main(String[] args) {
        // Test Program
        int P=5;
        int R=3;
        int av[]=new int[R];
        int al[][]=new int[P][R];
        int m[][]=new int[P][R];
        int[] request=new int[R];
        int[] release=new int[R];
        System.out.println("Enter the initial number of the available resources :- ");
        Scanner input=new Scanner(System.in);
        Scanner inputString=new Scanner(System.in);
        for(int i=0;i<R;i++){
            av[i]=input.nextInt();
        }
        System.out.println("Enter the initial initial number of the allocated resources :- ");
        for(int i=0;i<P;i++){
            for(int j=0;j<R;j++)
                al[i][j]=input.nextInt();
        }
        System.out.println("Enter the initial initial number of the max needed resources :- ");
        for(int i=0;i<P;i++){
            for(int j=0;j<R;j++)
                m[i][j]=input.nextInt();
        }
        Banker B=new Banker(av,al,m,P,R);
        B.calcNeed();
        int []availableCopy=B.AvailableCopy();
        int [][]allocatedCopy=B.AllocatedCopy();
        // Check system is in safe state or not
        if(B.Safety( availableCopy ,allocatedCopy,B.need))
        {
            System.out.print("safe and the sequence is:");
            for(int i=0;i<P;i++)
            {
                System.out.print(" P"+B.sequence[i]);
            }
            System.out.println("\n");

        }
        else {
            System.out.println("Unsafe!!!!!\n");
        }
        while(true){
            String command=inputString.nextLine();
            if(command.equals("Quit")){
                exit(0);
            }
            else{
                String []commandSpliter =command.split(" ");
                if(commandSpliter[0].equals("RQ")){
                    if(commandSpliter.length!=R+2||parseInt((commandSpliter[1]).substring(1))>=P)
                        System.out.println("Something Error :/");
                    else{
                        int counter=0;
                        for(int i=2;i<commandSpliter.length;i++){
                            request[counter]=parseInt(commandSpliter[i]);
                            counter++;
                        }
                        B.request(request,parseInt((commandSpliter[1]).substring(1)));
                    }
                }
                else if(commandSpliter[0].equals("RL")){
                    if(commandSpliter.length!=R+2||parseInt((commandSpliter[1]).substring(1))>=P)
                        System.out.println("Something Error :/");
                    else{
                        int counter=0;
                        for(int i=2;i<commandSpliter.length;i++){
                            release[counter]=parseInt(commandSpliter[i]);
                            counter++;
                        }
                        B.release(release,parseInt((commandSpliter[1]).substring(1)));
                    }

                }
                else{
                    System.out.println("Command not found :/");

                }
            }

        }



    }
}
/* Test Program
available
3 3 2
allocate
0 1 0
2 0 0
3 0 2
2 1 1
0 0 2
max
7 5 3
3 2 2
9 0 2
2 2 2
4 3 3
RQ p0 1 0 2
RL p0 0 1 0
Quit
 */
