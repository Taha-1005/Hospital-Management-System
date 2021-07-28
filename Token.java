import java.time.*;
import java.util.Scanner;
import java.io.*;
class Token extends Patient
{
	Scanner sc=new Scanner(System.in);
	String instruct="****************************Instructions:****************************"+
					"\nThe format of the n in: yyyy-mm-dd_slotnumber,\n"+
					"slot number indicates the time of appointment on the"+
					" allocated day";
	int slot;
	String time;
	LocalDate dateOfAppt=LocalDate.now();
	String getTime(int s)
	{
		String t="";
		switch(s)
		{
			case 1:t="10:00 AM";break;
			case 2:t="10:15 AM";break;
			case 3:t="10:30 AM";break;
			case 4:t="10:45 AM";break;
			case 5:t="11:00 AM";break;
			case 6:t="11:15 AM";break;
			case 7:t="11:30 AM";break;
			case 8:t="11:45 AM";break;
			case 9:t="12:00 PM";break;
			case 10:t="12:15 PM";break;
			case 11:t="12:30 PM";break;
			case 12:t="12:45 PM";break;
			default:System.out.println("It should not come here");
		}
		return t;
	}
	int chkAppointment(String name_avail) throws FileNotFoundException, IOException
	{
		int f=1,patientExists=0;
		for(int i=0;f==1;i++)
		{
			String line="";
			if(i!=0)
				dateOfAppt=dateOfAppt.plusDays(1);
			String nameOfFile=dateOfAppt+"-list.txt";
			File file=new File(nameOfFile);
			boolean checkExist=file.exists();
			if(checkExist)
			{
				BufferedReader br=new BufferedReader(new FileReader(nameOfFile));
				line=br.readLine();
				while(line!=null && f==1)
				{
					if(("Name: " + name_avail ).equalsIgnoreCase(line))
					{
						patientExists=1;
						f=0;
						line=br.readLine();
						line=line.substring(6);
						slot=Integer.parseInt(line);
						// time=getTime(slot);
					}
					line=br.readLine();
				}
				br.close();
			}
			else
				f=-1;
		}
		if(f==-1)
		{
			System.out.println("**********************For the reporting area:**********************");
			System.out.println("The patient does not have an appointment");
			System.out.println("*******************************************************************");
			dateOfAppt=LocalDate.now();
		}
		return patientExists;
	}
	void dispToken()
	{
		System.out.println("--------------------------The Token number is:--------------------------");
		System.out.println(dateOfAppt+"_"+slot);
		System.out.println("The time according to the slot is:**"+getTime(slot)+"**");
		System.out.println(instruct);
	}
	int displaySlots(int takenSlots[])
	{
		//get slots not filled
		int notTakenSlots[]=new int[12];
		int index=0;
		for(int i=1;i<=12;i++)
		{
			int present=0;
			for(int j=0;j<12 && present==0;j++)
			{
				if(i==takenSlots[j])
					present=1;
			}
			if(present==0)
			{
				notTakenSlots[index]=i;
				index++;
			}
		}
		//ask for choice:available slots,next date,cancel apptmt
		for(int i=0;i<index;i++)
		{
			String tempTime=getTime(notTakenSlots[i]);
			System.out.println(notTakenSlots[i]+". "+tempTime);
		}
		System.out.println("13. Show available slots for next date.");
		System.out.println("0. Cancel the appointment.");
		int c=0,valid=0;	
		do
		{
			int d=0;
			do
			{
				d=0;
				try
				{
					System.out.print("Enter choice:");
					c=sc.nextInt();
				}catch(Exception me)
				{
					sc.nextLine();
					d=1;
					System.out.println("Only numbers allowed here, plese enter again");
				}
			}while(d==1);
			valid=0;
			for(int i=0;i<index && valid==0;i++)
				if(c==notTakenSlots[i] || c==13 || c==0)
					valid=1;
			if(valid==0)
				System.out.println("Invalid Input,please enter again");
		}while(valid==0);
		//return choice
		return c;
	}
	int getAppointment(String name_appt) throws FileNotFoundException, IOException
	{	
		dateOfAppt=LocalDate.now();
		int f=1,lCount=0,curSlot,slotIndex=0,ch=0;
		int maxDays=1,toAdd=0;
		int slotsFilled[];
		for(int i=0;f==1;i++)
		{
			slotsFilled=new int[12];
			slotIndex=0;
			lCount=0;
			if(toAdd==1)
				dateOfAppt=dateOfAppt.plusDays(1);
			toAdd=1;
			String nameOfFile=dateOfAppt+"-list.txt";
			File file=new File(nameOfFile);
			boolean checkExist=file.exists();
			if(!checkExist)
				file.createNewFile();
			else
			{
				BufferedReader br=new BufferedReader(new FileReader(nameOfFile));
				String line=br.readLine();
				while(line!=null && f==1)
				{
					lCount++;
					String lineCopy=line.substring(0,4);
					if(("Slot").equalsIgnoreCase(lineCopy))
					{
						lCount++;
						line=line.substring(6);
						curSlot=Integer.parseInt(line);
						slotsFilled[slotIndex]=curSlot;
						slotIndex++;
					}
					line=br.readLine();
				}
				br.close();
			}
			if(lCount==48)
			{
				if(i==maxDays)
				{
					System.out.println("******************No more dates available,please come back tommorrow******************");
					f=0;
				}
				else
					continue;
			}
			else
			{
				int surity;
				do
				{
					surity=1;
					f=1;
					System.out.println("Slots available on :"+dateOfAppt+" are:");
					ch=displaySlots(slotsFilled);
					if(ch==0)//for cancelling
					{
						f=0;
						System.out.println("***************************Your appointment has been cancelled***************************");
						surity=0;
					}
					else
					if(ch==13)//for next date
					{
						if(i==maxDays)
						{
							System.out.println("asd");
							System.out.println("***********No more dates available,please enter another choice or cancel the appointment***********");
							surity=0;
							toAdd=0;
						}
					}
					int d=0;
					if(surity==1)
						do
						{
							d=0;
							try
							{
								System.out.print("If the entered choice is to be change for any reason then");
								System.out.print(" enter 1 else enter any other number:");
								surity=sc.nextInt();
							}catch(Exception me)
							{
								sc.nextLine();
								d=1;
								System.out.println("Only numbers allowed here, please enter again");
							}
						}while(d==1);
				}while(surity==1);
				if(!(ch==0 || ch==13))
				{
					f=0;
					slot=ch;
					FileWriter fw=new FileWriter(file,true);
					PrintWriter pw=new PrintWriter(fw);
					pw.println("Name: "+name_appt);
					pw.println("Slot: "+slot);
					pw.println("-----------------------------------------------");
					pw.flush();
					pw.close();
					dispToken();
				}
				else
				if(ch==0)
					System.out.println("******************Your Data has been discarded******************");
			}
		}
		return ch;
	}
	void changeAppointment() throws FileNotFoundException, IOException
	{
		dateOfAppt=LocalDate.now();
		String name="";
		int p;
		do
		{
			p=0;
			try
			{
				sc.nextLine();
				System.out.print("Enter patient's name:");
				name=sc.nextLine();
			}catch(Exception e)
			{
				sc.nextLine();
				p=1;
				System.out.println("Invalid input, please enter again");
			}
		}while(p==1);
		String oldData="";
		String nameOfFile="";
		int f=1,toChange=0,patientExists=0,i=0;
		while(f==1 && patientExists==0)
		{
			oldData="";
			String line="";
			if(i!=0)
				dateOfAppt=dateOfAppt.plusDays(1);
			nameOfFile=dateOfAppt+"-list.txt";
			File file=new File(nameOfFile);
			boolean checkExist=file.exists();
			if(checkExist)
			{
				BufferedReader br=new BufferedReader(new FileReader(nameOfFile));
				line=br.readLine();
				while(line!=null && f==1)
				{
					if(("Name: "+name).equalsIgnoreCase(line))
					{
						patientExists=1;
						line=br.readLine();
						line=line.substring(6);
						slot=Integer.parseInt(line);
						System.out.println("Current token is:");
						dispToken();
						int d=0;
						do
						{
							d=0;
							try
							{
								System.out.print("If token is to be changed enter 1 else enter any other number:");
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
							line=br.readLine();
							line=br.readLine();
							oldData=oldData+line+System.lineSeparator();
						}
						else
							f=0;
					}
					else
					if(line!=null)
						oldData=oldData+line+System.lineSeparator();
					line=br.readLine();
				}
				br.close();
			}
			else
				f=-1;
			i++;
		}
		if(patientExists==0)
		{
			System.out.println("The patient does not have an appointment");
			dateOfAppt=LocalDate.now();
		}
		else
		if(toChange==1)
		{
			PrintWriter pw=new PrintWriter(nameOfFile);
			pw.print(oldData);
			pw.flush();
			pw.close();
			File file=new File("temp.txt");
			file.createNewFile();
			PrintWriter writer=new PrintWriter("temp.txt");
			BufferedReader br=new BufferedReader(new FileReader(nameOfFile));
			String line=br.readLine();
			while(line!=null)
			{
				if(!line.equals("null"))
					writer.println(line);
				line=br.readLine();
			}
			writer.flush();
			writer.close();
			br.close();
			writer=new PrintWriter(nameOfFile);
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
			getAppointment(name);
		}
	}
	void findRep(String find,String replaceWith) throws FileNotFoundException, IOException
	{
		dateOfAppt=LocalDate.now();
		String oldData="";
		String nameOfFile="";
		int i=0;
		int f=1,patientExists=0;
		while(f==1 && patientExists==0)
		{
			oldData="";
			String line="";
			if(i!=0)
				dateOfAppt=dateOfAppt.plusDays(1);
			nameOfFile=dateOfAppt+"-list.txt";
			File file=new File(nameOfFile);
			boolean checkExist=file.exists();
			if(checkExist)
			{
				BufferedReader br=new BufferedReader(new FileReader(nameOfFile));
				line=br.readLine();
				while(line!=null && f==1)
				{
					if(("Name: "+find).equalsIgnoreCase(line))
					{
						patientExists=1;
						line=line.replaceAll(find,replaceWith);
					}
					oldData=oldData+line+System.lineSeparator();
					line=br.readLine();
				}
				br.close();
			}
			else
				f=-1;
			i++;
		}
		PrintWriter pw=new PrintWriter(nameOfFile);
		pw.print(oldData);
		pw.flush();
		pw.close();
	}
	void deleteSlot(String nDel) throws FileNotFoundException, IOException
	{
		dateOfAppt=LocalDate.now();
		String oldData="";
		String nameOfFile="";
		int i=0;
		int f=1,patientExists=0;
		while(f==1 && patientExists==0)
		{
			oldData="";
			String line="";
			if(i!=0)
				dateOfAppt=dateOfAppt.plusDays(1);
			nameOfFile=dateOfAppt+"-list.txt";
			File file=new File(nameOfFile);
			boolean checkExist=file.exists();
			if(checkExist)
			{
				BufferedReader br=new BufferedReader(new FileReader(nameOfFile));
				line=br.readLine();
				while(line!=null && f==1)
				{
					if(("Name: "+nDel).equalsIgnoreCase(line))
					{
						patientExists=1;
						line=br.readLine();
						line=br.readLine();
						line=br.readLine();
					}
					oldData=oldData+line+System.lineSeparator();
					line=br.readLine();
				}
				br.close();
			}
			else
				f=-1;
			i++;
		}
		PrintWriter pw=new PrintWriter(nameOfFile);
		pw.print(oldData);
		pw.flush();
		pw.close();
		File file=new File("temp.txt");
		file.createNewFile();
		PrintWriter writer=new PrintWriter("temp.txt");
		BufferedReader br=new BufferedReader(new FileReader(nameOfFile));
		String line=br.readLine();
		while(line!=null)
		{
			if(!line.equals("null"))
				writer.println(line);
			line=br.readLine();
		}
		writer.flush();
		writer.close();
		br.close();
		writer=new PrintWriter(nameOfFile);
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
}