package laboratorinisvienas;

import java.util.Arrays;
//#1
public class LaboratorinisVienas{
        
     public static void main(String []args){
        //#2
        /*
            Kornelijus Pacevicius PRif-17/6 20173570
        */
        
        int n = 10; // Vardo raidžiu kiekis
        int m = 10; // Pavardes raidžiu kiekis
        int a = 1; // a raidžiu kiekis varde ir pavardeje
        int b = 20; // bendras vardo ir pavardes simboliu skaicius
        
        //#3
        int[][] array = new int [n][m];
        
        //#4
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
               array[i][j] = a + (int)(Math.random() * ((b - a) + 1 ));
            }
        }
        
        //#5
        for(int i=0; i<n; i++){
            if (i == 0){
                System.out.print("     ");
            }
            
            System.out.print( (i + 1) + " ");
        }
        
        System.out.println();
        
        for(int i=0; i<n; i++){
            if (i >= 9) 
                System.out.print( (i + 1) + "||");
            else 
                System.out.print((i + 1) + " " + "||" + " " ); 
            
            for(int j=0; j<m; j++){
               System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        
        //#6
        double[] columnsArray = new double [n];
        double[] rowsArray = new double [m];
        
        int column_Sum = 0;
        int row_Sum = 0;
        
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                row_Sum += array[i][j];
            }
            rowsArray[i] = (double) row_Sum / n;
            row_Sum = 0;
        }
        
        System.out.println();
        System.out.println("Rows average");
        System.out.println(Arrays.toString(rowsArray));
        
        for(int j=0; j<n; j++){
            for(int i=0; i<m; i++){
                column_Sum += array[i][j];
            }
            columnsArray[j] = (double) column_Sum / m;
            column_Sum = 0;
        }
        
        System.out.println();
        System.out.println("Columns average");
        System.out.println(Arrays.toString(columnsArray));
        
        //#7
        int numberQuantity = 0;
        
        System.out.println();
        
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(array[i][j] > rowsArray[i]){
                    numberQuantity++;
                }
            }
            System.out.println("row " + (i + 1) + "    " + "array elements bigger than the rows average:" + numberQuantity);
            numberQuantity = 0;
        }
        
        System.out.println();
        
        for(int j=0; j<m; j++){
            for(int i=0; i<n; i++){
                if(array[j][i] > columnsArray[j]){
                    numberQuantity++;
                }
            }
            System.out.println("column " + (j + 1) + "    " + "array elements bigger than the column average:" + numberQuantity);
            numberQuantity = 0;
        }
        
        //#8
        int maxElement = array[1][1];
        
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if ((a-1) != i && (a-1) != j && array[i][j] > maxElement){
                    maxElement = array[i][j];
                }
            }
        }
        
        System.out.println();
        System.out.println("Biggest array element not including the " + a  + " row and " + a  + " column is" +  maxElement);
    
        //#9
        int[] sortedArray = array[a-1].clone();
        Arrays.sort(sortedArray);
        
        System.out.println();
        System.out.println("Sorted row");
        System.out.println(Arrays.toString(sortedArray));
     
        //#10
        int[] sortedArray2 = new int[n];
        
        for(int i = 0; i<n; i++){
            sortedArray2[i] = array[i][a-1];
        }
        
        Arrays.sort(sortedArray2);
        //ArrayUtils.reverse(sortedArray2);
        
        System.out.println();
        System.out.println("sorted column");
        for(int i=n-1; i>0; i--){
                    System.out.print(sortedArray2[i] + " ");
        }
        
        System.out.println();
        
        //#11
        double minElement = columnsArray[0];
        int column = 0;
        
        for(int i=0; i<m; i++){
            if(columnsArray[i] < minElement){
                minElement = columnsArray[i];
                column = i;
            }
        }

        System.out.println();
        System.out.println("lowest column is: " + (column + 1) + " with a array element value of " + minElement);
        
     }
}