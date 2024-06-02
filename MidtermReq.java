// Name: TORRENO, Lerrica Jeremy S.
//Course/Year: BSCS-DS/1st year 
//Date: April 23, 2024/ June 3, 2024 
//Work title: Midterm and Finals Requirement in Programming 2 (lec)

//instructions: implement a program to solve a system of linear equations using matrices and big decimal 

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner; 

// start of code: input from user and displays the answer  
public class MidtermReq {
    public static void main(String[] args) {

        // myScanner - naming convention was applied for the variable 
        Scanner myScanner = new Scanner (System.in);

        // for 3x3 coefficient matrix and 3x1 constant matrix
        // NUM_ROWS, COEFFICIENT_COLUMNS, CONSTANT_COLUMNS - not considered as an actual constant but is fixed during the duration of program
        int NUM_ROWS = 3; 
        int COEFFICIENT_COLUMNS = 3; 
        int CONSTANT_COLUMNS = 1; 

        // for reference 
        // output is changed for the purpose of line length (limit: 80 characters)
        System.out.println
        ("Format to be followed to solve the system of linear equations: AX = B");

        // to create and store the input from user in the proper matrices 
        // coefficientMatrix, constantMatrix, and variableMatrix - naming conventions for variables is applied
        BigDecimal[][] coefficientMatrix = new BigDecimal[NUM_ROWS][COEFFICIENT_COLUMNS];
        BigDecimal[][] constantMatrix = new BigDecimal[NUM_ROWS][CONSTANT_COLUMNS];
        char[] variableMatrix = { 'x', 'y', 'z' }; 

        // input area for coefficient matrix 
        // looping for the specific numbers that the user may input (in this case, user can only put 3 numbers, separated by space)
        for (int row = 0; row < NUM_ROWS; row++) {
            System.out.println("Enter the coefficients of the equation " + (row + 1) + ":");
            for (int col = 0; col < COEFFICIENT_COLUMNS; col++) {
                coefficientMatrix[row][col] = myScanner.nextBigDecimal(); 
            }
        }

        // input area for constant matrix 
        // looping for the specific numbers that the user may input (in this case, user can only put 1 number)
        for (int row = 0; row < NUM_ROWS; row++) {
            System.out.println("Enter the constant of the equation " + (row + 1) + ":");
            for (int col = 0; col < CONSTANT_COLUMNS; col++) {
                constantMatrix[row][col] = myScanner.nextBigDecimal(); 
            }
        }

        // displays the coefficient, variable, and constant matrix (format: AX = B)
        System.out.println("Coefficient Matrix (A): ");
        displayMatrix(coefficientMatrix);

        System.out.println("Variable Matrix (X): ");
        displayVariableMatrix(variableMatrix);

        System.out.println("Constant Matrix (B): ");
        displayMatrix(constantMatrix);

        // main solution for system of linear equations 
        // mainSolution - applied naming convention in variables 
        BigDecimal[][] mainSolution = solveSystem(coefficientMatrix, constantMatrix);

        // displays the solution (solution: x =, y =, z = )
        System.out.println("\nSolution: ");
        for (int i = 0; i < mainSolution.length; i++) {
            System.out.println(variableMatrix[i] + " = " + mainSolution[i][0]);
        }
        myScanner.close(); 
    }

        // solving area for the system of linear equations 
        // method to display the coefficient and constant by the user in matrix format       
        public static void displayMatrix(BigDecimal[][] matrix) {
            for (int row = 0; row < matrix.length; row++) {
                System.out.print("[ "); 
                for (int col = 0; col < matrix[0].length; col++) {
                    System.out.print(matrix[row][col] + "\t");
                }
                System.out.println(" ]"); 
            }
        }

        // method to display the variable matrix in 1D matrix with 3 rows and 1 column 
        public static void displayVariableMatrix(char[] variable_matrix) {
            for (int i = 0; i < variable_matrix.length; i++) {
                System.out.println("[" + variable_matrix[i] + "]");
            }
        }

        // method that will start to solve the system of linear equations and store them in the variables
        public static BigDecimal[][] solveSystem(BigDecimal[][] coefficients, BigDecimal[][] constants) {
            int nSize = constants.length; 

            // matrixSolution - applied naming conventions in variables 
            BigDecimal[][] matrixSolution = new BigDecimal[nSize][1]; 

            // checks each row and looks for the largest value in same column that will be stored in the maxRow (to be used for Gaussian elimination)
            // Gaussian elimination 
            // nSize - applied naming conventions in variables 
            for (int i = 0; i < nSize; i++) {
                // max in column 
                int maxRow = i; 
                for (int j = i + 1; j < nSize; j++) {
                    if (coefficients[j][i].abs().compareTo(coefficients[maxRow][i].abs()) > 0) {
                        maxRow = j; 
                    }
                }

                // interchanging of rows for coefficient matrix and constant matrix 
                // mtempRow - applied naming conventions in variables 
                BigDecimal[] tempRow = coefficients[i];
                coefficients[i] = coefficients[maxRow];
                coefficients[maxRow] = tempRow;

                BigDecimal[] tempConstant = constants[i];
                constants[i] = constants[maxRow];
                constants[maxRow] = tempConstant;

                // performs elementary row operations (multiplying, adding, subtracting) to ensure that it will be sufficient for back-substitution 
                // operationFactor - applied naming conventions in variables 
                for (int j = i + 1; j < nSize; j++) {
                BigDecimal operationFactor = coefficients[j][i].divide(coefficients[i][i], 50, RoundingMode.HALF_UP);
                for (int k = i; k < nSize; k++) {
                    coefficients[j][k] = coefficients[j][k].subtract(operationFactor.multiply(coefficients[i][k]));
                }
                constants[j][0] = constants[j][0].subtract(operationFactor.multiply(constants[i][0]));
            }
        }

        // back Substitution, solves for the value of variable matrix and tests if it satisfies the equations given by the user
        // finalSum - applied naming conventions in variables 
        for (int i = nSize - 1; i >= 0; i--) {
            BigDecimal finalSum = BigDecimal.ZERO;
            for (int j = i + 1; j < nSize; j++) {
                finalSum = finalSum.add(coefficients[i][j].multiply(matrixSolution[j][0]));
            }
            matrixSolution[i][0] = (constants[i][0].subtract(finalSum)).divide(coefficients[i][i], 50, RoundingMode.HALF_UP);
        }
        return matrixSolution;
    }
}
// end of code 



