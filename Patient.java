import java.time.*;
import java.util.Scanner;
import java.io.*;
class Patient
{
	Scanner sc=new Scanner(System.in);
	String name;
	private String diseaseKind;
	int age;
	LocalDate today=LocalDate.now();
	void genSeeToken()
	{
		DayOfWeek curDay=today.getDayOfWeek();
		System.out.println("-----------------------------Today's date and day is:"+today+","+curDay+"-----------------------------");
		int p;
		do
		{
			p=0;
			try
			{
				System.out.print("Enter patient's name:");
				name=sc.nextLine();
			}catch(Exception e)
			{
				sc.nextLine();
				p=1;
				System.out.println("Invalid input, please enter again");
			}
		}while(p==1);
		Token t=new Token();
		int checkPatient=-1;
		try
		{
			checkPatient=t.chkAppointment(name);
		}catch(FileNotFoundException fe)
		{
			System.out.println("File not found in the folder.");
			System.exit(0);
		}catch(IOException ie)
		{
			System.out.println("Something went wrong, please try again.");
			System.exit(0);
		}
		if(checkPatient==0)
		{
			try
			{
				inputNew(t);
			}catch(FileNotFoundException fe)
			{
				System.out.println("File not found in the folder.");
				System.exit(0);
			}
			catch(IOException ie)
			{
				System.out.println("Something went wrong, please try again.");
				System.exit(0);
			}
		}
		else
		if(checkPatient==1)
		{
			System.out.println("**********************For the reporting area:**********************");
			System.out.println("The patient has an appointment");
			System.out.println("***********Data of the patient:***********");
			try
			{
				dispReportArea(t);
			}catch(FileNotFoundException fe)
			{
				System.out.println("File not found in the folder.");
				System.exit(0);
			}catch(IOException ie)
			{
				System.out.println("Something went wrong, please try again.");
				System.exit(0);
			}
		}
		else
		{
			return;
		}
	}
	void removeDuplicate(String nameDup) throws FileNotFoundException, IOException
	{
		BufferedReader br=new BufferedReader(new FileReader("Reporting File.txt"));
		String oldData="";
		String line=br.readLine();
		while(line!=null)
		{
			if(("Name: " + nameDup).equalsIgnoreCase(line))
			{
				br.readLine();
				br.readLine();
				br.readLine();
			}
			else
				oldData=oldData+line+System.lineSeparator();
			line=br.readLine();
		}
		br.close();
		PrintWriter pw=new PrintWriter("Reporting File.txt");
		pw.print(oldData);
		pw.flush();
		pw.close();
		File file=new File("temp.txt");
		file.createNewFile();
		PrintWriter writer=new PrintWriter("temp.txt");
		br=new BufferedReader(new FileReader("Reporting File.txt"));
		line=br.readLine();
		while(line!=null)
		{
			if(!line.equals("null"))
				writer.println(line);
			line=br.readLine();
		}
		writer.flush();
		writer.close();
		br.close();
		writer=new PrintWriter("Reporting File.txt");
		br=new BufferedReader(new FileReader("temp.txt"));
		line=br.readLine();
		while(line!=null)
		{
			writer.println(line);
			line=br.readLine();
		}
		writer.flush();
		writer.close();
		br.close();
	}
	void inputNew(Token tNew) throws FileNotFoundException, IOException
	{
		//call function to get appointment for patient
		int flag=0;
		try
		{
			flag=tNew.getAppointment(name);
		}catch(FileNotFoundException fe)
		{
			System.out.println("File not found in the folder.");
			System.exit(0);
		}catch(IOException ie)
		{
			System.out.println("Something went wrong, please try again.");
			System.exit(0);
		}
		if(flag!=0)
		{
			int p;
			do
			{
				p=0;
				try
				{
					System.out.print("Enter patient's age:");
					age=sc.nextInt();
				}catch(Exception me)
				{
					sc.nextLine();
					p=1;
					System.out.println("Only numbers allowed here, please enter again");
					continue;
				}
				try
				{
					sc.nextLine();
					System.out.print("What kind of disease does the patient have:");
					diseaseKind=sc.nextLine();
				}catch(Exception e)
				{
					sc.nextLine();
					p=1;
					System.out.println("Invalid input, please enter again");
				}
			}while(p==1);
			//write in reporting file
			removeDuplicate(name);
			FileWriter fw=new FileWriter("Reporting File.txt",true);
			PrintWriter pw=new PrintWriter(fw);
			pw.println("Name: "+name);
			pw.println("Age : "+age);
			pw.println("TD  : "+diseaseKind);
			pw.println("-----------------------------------------------");
			pw.flush();
			pw.close();
			System.out.println("***********Your data has been successfully stored***********");
		}
		else
			return;
	}
	void dispReportArea(Token tExists) throws FileNotFoundException, IOException
	{
		BufferedReader br=new BufferedReader(new FileReader("Reporting File.txt"));
		String line=br.readLine();
		String detailLine1="",detailLine2="",detailLine3="";
		while(line!=null)
		{
			if(("Name: " + name ).equalsIgnoreCase(line))
			{
				detailLine1=line;
				detailLine2=br.readLine();
				detailLine3=br.readLine();
			}
			line=br.readLine();
		}
		System.out.println(detailLine1);
		System.out.println(detailLine2);
		System.out.println(detailLine3);
		System.out.println("Here \"TD\" stands for \"Type of Disease\"");
		br.close();
		int ch=0,e;
		do
		{
			e=1;
			try
			{
				System.out.print("If you want to see token of patient press 1 else any other number:");
				ch=sc.nextInt();
			}catch(Exception ke)
			{
				sc.nextLine();
				System.out.println("Only numbers allowed here, please enter again");
				e=0;
			}
		}while(e==0);
		if(ch==1)
			tExists.dispToken();
	}
	void seeDetails() throws FileNotFoundException, IOException
	{
		DayOfWeek curDay=today.getDayOfWeek();
		System.out.println("-----------------------------Today's date and day is:"+today+","+curDay+"-----------------------------");
		int p;
		do
		{
			p=0;
			try
			{
				System.out.print("Enter patient's name:");
				name=sc.nextLine();
			}catch(Exception e)
			{
				sc.nextLine();
				p=1;
				System.out.println("Invalid input, please enter again");
			}
		}while(p==1);
		int existence=0;
		BufferedReader br=new BufferedReader(new FileReader("Reporting File.txt"));
		String line=br.readLine();
		String detailLine1="",detailLine2="",detailLine3="";
		while(line!=null)
		{
			if(("Name: " + name ).equalsIgnoreCase(line))
			{
				existence=1;
				detailLine1=line;
				detailLine2=br.readLine();
				detailLine3=br.readLine();
			}
			line=br.readLine();
		}
		if(existence==1)
		{
			System.out.println(detailLine1);
			System.out.println(detailLine2);
			System.out.println(detailLine3);
			System.out.println("Here \"TD\" stands for \"Type of Disease\"");
		}
		else
			System.out.println("*****************Such a patient does not exist*****************");
		br.close();
	}	
	void remove(String nameRem) throws FileNotFoundException, IOException
	{
		removeDuplicate(nameRem);
		System.out.println("Enter new details:");
		int p;
		do
		{
			p=0;
			try
			{
				sc.nextLine();
				System.out.print("Enter patient's name:");
				nameRem=sc.nextLine();
			}catch(Exception e)
			{
				sc.nextLine();
				p=1;
				System.out.println("Invalid input, please enter again");
			}
		}while(p==1);
		do
		{
			p=0;
			try
			{
				System.out.print("Enter patient's age:");
				age=sc.nextInt();
			}catch(Exception me)
			{
				sc.nextLine();
				p=1;
				System.out.println("Only numbers allowed here, please enter again");
				continue;
			}
			try
			{
				sc.nextLine();
				System.out.print("What kind of disease does the patient have:");
				diseaseKind=sc.nextLine();
			}catch(Exception e)
			{
				sc.nextLine();
				p=1;
				System.out.println("Invalid input, please enter again");
			}
		}while(p==1);
		FileWriter fw=new FileWriter("Reporting File.txt",true);
		PrintWriter pw=new PrintWriter(fw);
		pw.println("Name: "+nameRem);
		pw.println("Age : "+age);
		pw.println("TD  : "+diseaseKind);
		pw.println("-----------------------------------------------");
		pw.flush();
		pw.close();
		System.out.println("***********Your data has been successfully stored***********");
		Token tChange=new Token();
		if(!(name.equalsIgnoreCase(nameRem)))
			tChange.findRep(name,nameRem);
	}
	void changeDetails() throws FileNotFoundException, IOException
	{
		DayOfWeek curDay=today.getDayOfWeek();
		System.out.println("-----------------------------Today's date and day is:"+today+","+curDay+"-----------------------------");
		int p;
		do
		{
			p=0;
			try
			{
				System.out.print("Enter patient's name:");
				name=sc.nextLine();
			}catch(Exception e)
			{
				sc.nextLine();
				p=1;
				System.out.println("Invalid input, please enter again");
			}
		}while(p==1);
		int existence=0;
		BufferedReader br=new BufferedReader(new FileReader("Reporting File.txt"));
		String line=br.readLine();
		String detailLine1="",detailLine2="",detailLine3="";
		while(line!=null)
		{
			if(("Name: " + name ).equalsIgnoreCase(line))
			{
				existence=1;
				detailLine1=line;
				detailLine2=br.readLine();
				detailLine3=br.readLine();
			}
			line=br.readLine();
		}
		if(existence==1)
		{
			int toChange=0;
			System.out.println("Current Data is:");
			System.out.println(detailLine1);
			System.out.println(detailLine2);
			System.out.println(detailLine3);
			System.out.println("Here \"TD\" stands for \"Type of Disease\"");
			int d=0;
			do
			{
				d=0;
				try
				{
					System.out.print("If the data is to be changed enter 1 else enter any other number:");
					toChange=sc.nextInt();
				}catch(Exception me)
				{
					sc.nextLine();
					d=1;
					System.out.println("Only numbers allowed here, please enter again");
				}
			}while(d==1);
			if(toChange==1)
			{
				remove(name);
			}
		}
		else
			System.out.println("*****************Such a patient does not exist*****************");
		br.close();
	}
	void deleteDetails() throws FileNotFoundException, IOException
	{
		DayOfWeek curDay=today.getDayOfWeek();
		System.out.println("-----------------------------Today's date and day is:"+today+","+curDay+"-----------------------------");
		int p;
		do
		{
			p=0;
			try
			{
				System.out.print("Enter patient's name:");
				name=sc.nextLine();
			}catch(Exception e)
			{
				sc.nextLine();
				p=1;
				System.out.println("Invalid input, please enter again");
			}
		}while(p==1);
		int existence=0;
		BufferedReader br=new BufferedReader(new FileReader("Reporting File.txt"));
		String line=br.readLine();
		String detailLine1="",detailLine2="",detailLine3="";
		while(line!=null)
		{
			if(("Name: " + name ).equalsIgnoreCase(line))
			{
				existence=1;
				detailLine1=line;
				detailLine2=br.readLine();
				detailLine3=br.readLine();
			}
			line=br.readLine();
		}
		if(existence==1)
		{
			int toChange=0;
			System.out.println("Current Data is:");
			System.out.println(detailLine1);
			System.out.println(detailLine2);
			System.out.println(detailLine3);
			System.out.println("Here \"TD\" stands for \"Type of Disease\"");
			int d=0;
			do
			{
				d=0;
				try
				{
					System.out.print("If the data is to be deleted enter 1 else enter any other number:");
					toChange=sc.nextInt();
				}catch(Exception me)
				{
					sc.nextLine();
					d=1;
					System.out.println("Only numbers allowed here, please enter again");
				}
			}while(d==1);
			if(toChange==1)
			{
				System.out.println("********************Data has been deleted********************");
				removeDuplicate(name);
				Token tDel=new Token();
				tDel.deleteSlot(name);
			}
		}
		else
			System.out.println("*****************Such a patient does not exist*****************");
		br.close();
	}
		
}