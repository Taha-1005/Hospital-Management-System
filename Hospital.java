import java.util.Scanner;
import java.io.*;
import java.util.*;
class Hospital
{
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		int choice=0;
		do
		{
			Token finUse=new Token();
			System.out.println("------------------------------Main Menu:------------------------------");
			System.out.println("1. Generate/get token of new/existing patient.");
			System.out.println("2. See details(latest) of a patient,for the reporting area.");
			System.out.println("3. Change/Delete Token of a patient");
			System.out.println("4. Change Patient Details");
			System.out.println("5. Delete Patient Details");
			System.out.println("0.Exit.");
			int d;
			do
			{
				d=0;
				try
				{
					System.out.print("Enter Choice:");
					choice=sc.nextInt();
				}catch(Exception me)
				{
					sc.nextLine();
					d=1;
					System.out.println("Only numbers allowed here, please enter again");
				}
			}while(d==1);
			switch(choice)
			{
				case 1: finUse.genSeeToken();break;
				case 2:
					try
					{
						finUse.seeDetails();
					}catch(FileNotFoundException fe)
					{
						System.out.println("File is not visible");
						System.exit(0);
					}catch(IOException ie)
					{
						System.out.println("Something went wrong, please try again.");
						System.exit(0);
					}
					break;
				case 3:
					try
					{
						finUse.changeAppointment();
					}catch(FileNotFoundException fe)
					{
						System.out.println("File is not visible");
						System.exit(0);
					}catch(IOException ie)
					{
						System.out.println("Something went wrong, please try again.");
						System.exit(0);
					}
					break;
				case 4:
					try
					{
						finUse.changeDetails();
					}catch(FileNotFoundException fe)
					{
						System.out.println("File is not visible");
						System.exit(0);
					}catch(IOException ie)
					{
						System.out.println("Something went wrong, please try again.");
						System.exit(0);
					}
					break;
				case 5:
					try
					{
						finUse.deleteDetails();
					}catch(FileNotFoundException fe)
					{
						System.out.println("File is not visible");
						System.exit(0);
					}catch(IOException ie)
					{
						System.out.println("Something went wrong, please try again.");
						System.exit(0);
					}
					break;
				case 0: System.out.println("Programme Exited");break;
				default: System.out.println("Invalid Input");break;
			}
		}while(choice!=0);
		System.out.println("------------------------------Thank You!! Have a Great Day!!------------------------------");
	}
}